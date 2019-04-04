package factoryBalance;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class RecipeTree {
	
	private List<String> textTree;
	
	private RecipeNode overallRoot;
	private RecipeBook book;
	
	private int treeLevels;
	
	public RecipeTree(Item inputRecipe, RecipeBook inputBook) {
		this.book = inputBook;
		this.textTree = new ArrayList<String>();
		List<Item> temp = new ArrayList<Item>();
		List<Integer> temp2 = new ArrayList<Integer>();
		this.treeLevels = 1;
		temp.add(inputRecipe);
		temp2.add(1);
		this.overallRoot = buildTree(1, temp, temp2);
	}
	
	public RecipeNode overallRoot() {
		return this.overallRoot;
	}
	
	private RecipeNode buildTree(int n, List<Item> input, List<Integer> prevLayerFormat) {
		/*if(input==null||input.size()==0) {
			System.out.println("Input is null or empty");
			return null;
		}*/
        if(checkForAllRawMat(input)) {
    		//System.out.println("Raw at: " + n);
            return null;
        }
    	//System.out.println("Level: " + n);
        //System.out.println(input.size());
        List<Integer> layerFormat = new ArrayList<Integer>();
        for(int i=0; i<input.size(); i++) {
        	layerFormat.add(input.get(i).inputSize());
        }
        List<RecipeNode> childNodes = new ArrayList<RecipeNode>();
        List<List<Item>> temp = getAllOptions(input);
        for(int j=0; j<temp.size(); j++) {
           	childNodes.add(buildTree(n + 1, temp.get(j), layerFormat));
        }
        if(n>this.treeLevels) {
        	this.treeLevels = n;
        }
        return new RecipeNode(input, childNodes, prevLayerFormat);
        
    }
	
	private List<List<Item>> getAllOptions(List<Item> input) {
		List<String> goals = new ArrayList<String>();
		for(int i=0; i<input.size(); i++) {
			for(int j=0; j<input.get(i).inputSize(); j++) {
				goals.add(input.get(i).inputNames()[j]);
			}
		}
		List<List<Item>> result = new ArrayList<List<Item>>();
		List<List<Item>> allDupes = new ArrayList<List<Item>>();
		int[] multiLength = new int[goals.size()];
		boolean isSingle = true;
		for(int i=0; i<goals.size(); i++) {
			multiLength[i] = this.book.numberOfDuplicates(goals.get(i));
			//System.out.println("Length: " + multiLength[i]);
			if(multiLength[i] != 1) {
				isSingle = false;
			}
		}
		if (!isSingle) {
			for (int i = 0; i < multiLength.length; i++) {
				List<Item> temp = new ArrayList<Item>();
				for (int j = 0; j < multiLength[i]; j++) {
					temp.add(this.book.findDuplicate(goals.get(i), j));
					//System.out.println("Item " + j + ":\n" + temp.get(j));
				}
				allDupes.add(temp);
			}
			for (int i = 0; i < maxNumOfCombinations(multiLength); i++) {
				List<Item> temp = new ArrayList<Item>();
				for (int j = 0; j < allDupes.size(); j++) {
					temp.add(allDupes.get(j).get(i % (multiLength[j])));
					//System.out.println("Item " + j + ":\n" + temp.get(j));
				}
				result.add(temp);
			}
		} else {
			List<Item> temp = new ArrayList<Item>();
			for(int i=0; i<goals.size(); i++) {
				temp.add(this.book.findItem(goals.get(i)));
			}
			result.add(temp);
		}
		/*System.out.println(result.size());
		for(int i=0; i<result.size(); i++) {
			System.out.println(result.get(i).size());
			for(int j=0; j<result.get(i).size(); j++) {
				System.out.println(result.get(i).get(j));
			}
		}*/
		return result;
	}
	
	private int maxNumOfCombinations(int[] input) {
		int result=1;
		for(int i=0; i<input.length; i++) {
			result = result * input[i];
		}
		return result;
	}
	
	private boolean checkForAllRawMat(List<Item> input) {
		boolean result = true;
		for(int i=0; i<input.size(); i++) {
			if(!input.get(i).isAllRaw()) {
				result = false;
			} else {
				//System.out.println(input.get(i));
			}
		}
		return result;
	}
	
	public void calculateTree(BigDecimal outPerSec, BigDecimal craftingSpeed) {
		List<BigDecimal> temp = new ArrayList<BigDecimal>();
		temp.add(outPerSec);
		calculateTree(this.overallRoot, temp, craftingSpeed);
		printTree();
	}
	
	private void calculateTree(RecipeNode node, List<BigDecimal> requiredOutPerSec, BigDecimal craftingSpeed) {
		if(node == null) {
			return;
		}
		List<BigDecimal> requiredInPerSec = new ArrayList<BigDecimal>();
		int place=0;
		for(int i=0; i<node.layerSize().size(); i++) {
			for (int j = 0; j < node.layerSize().get(i); j++) {
				Item thisItem = node.getItem(j, i);
				BigDecimal[] input = thisItem.inPerSec();
				node.setMachineNum(requiredOutPerSec.get(place).multiply(thisItem.time()).divide(craftingSpeed.multiply(thisItem.output()), 2, RoundingMode.HALF_UP), j, i);
				//node.setMachineNum(requiredOutPerSec.get(place).divide(craftingSpeed.multiply(thisItem.outPerSec()), 5, RoundingMode.HALF_UP), j, i);
				for (int k = 0; k < input.length; k++) {
					requiredInPerSec.add(node.getMachineNum(j, i).multiply(craftingSpeed.multiply(input[k])));
				}
				place++;
			}
		}
		for(int i=0; i<node.numOfChildren(); i++) {
			calculateTree(node.getChildNode(i), requiredInPerSec, craftingSpeed);
		}
	}
	
	public int treeLevels() {
		return this.treeLevels;
	}
	
	public void printTree() {
		//System.out.println(this.treeLevels);
		for(int i=1; i<=this.treeLevels; i++) {
			printLevel(i);
		}
	}
	
	public void printLevel(int n) {
		printLevel(this.overallRoot, n);
		System.out.print(convertToString());
		this.textTree.clear();
	}
	
	private void printLevel(RecipeNode node, int n) {
		if(node == null) {
			return;
		}
		if(n == 1) {
			combineNodeStringList(node.toStringList());
			return;
		}
		for(int i=0; i<node.numOfChildren(); i++) {
			printLevel(node.getChildNode(i), n-1);
		}
	}
	
	private void combineNodeStringList(List<String> input) {
		if(this.textTree.size()==0) {
			this.textTree.addAll(input);
		} else if(this.textTree.size()==input.size()) {
			for(int i=0; i<this.textTree.size(); i++) {
				String temp = this.textTree.get(i);
				this.textTree.set(i, temp + input.get(i));
			}
		} else if(this.textTree.size()>input.size()) {
			int inputSize = input.size(), padLength = input.get(0).length();
			for(int i=0; i<this.textTree.size()-inputSize; i++) {
				input.add(padString("", padLength-2));
			}
			for(int i=0; i<this.textTree.size(); i++) {
				String temp = this.textTree.get(i);
				this.textTree.set(i, temp + input.get(i));
			}
		} else if(this.textTree.size()<input.size()) {
			int textTreeSize = this.textTree.size(), padLength = this.textTree.get(0).length();
			for(int i=0; i<input.size()-textTreeSize; i++) {
				this.textTree.add(padString("", padLength-2));
			}
			for(int i=0; i<this.textTree.size(); i++) {
				String temp = this.textTree.get(i);
				this.textTree.set(i, temp + input.get(i));
			}
		}
	}
	
	private String convertToString() {
		String rtn = "";
		for(int i=0; i<this.textTree.size(); i++) {
			rtn = rtn + this.textTree.get(i) + "\n";
		}
		return rtn;
	}
	
	private String padString(String input, int padLength) {
		while(input.length()<padLength) {
			input = input + " ";
		}
		input = "|" + input + "|";
		return input;
	}
}
