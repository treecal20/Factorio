package factoryBalance;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

public class RecipeNode {
	
	private List<RecipeNode> childNodes;
	private List<List<Item>> itemLayer;
	private List<List<BigDecimal>> machineNum;
	private List<Integer> layerSize;
	
	private int numOfChildren;
	
	private boolean hasChild;
	
	public RecipeNode(List<Item> data, List<RecipeNode> inputChildNodes, List<Integer> inputSeperation) {
		this.itemLayer = new ArrayList<List<Item>>();
		this.childNodes = new ArrayList<RecipeNode>();
		this.machineNum = new ArrayList<List<BigDecimal>>();
		this.layerSize = new ArrayList<Integer>();
		this.layerSize.addAll(inputSeperation);
		int place = 0;
		for(int i=0; i<this.layerSize.size(); i++) {
			List<Item> temp = new ArrayList<Item>();
			for(int j=0; j<this.layerSize.get(i); j++) {
				temp.add(data.get(place));
				place++;
			}
			this.itemLayer.add(temp);
		}
		for(int i=0; i<this.layerSize.size(); i++) {
			List<BigDecimal> temp = new ArrayList<BigDecimal>();
			for(int j=0; j<this.layerSize.get(i); j++) {
				temp.add(new BigDecimal(1.0));
			}
			this.machineNum.add(temp);
		}
		this.childNodes.addAll(inputChildNodes);
		this.numOfChildren = inputChildNodes.size();
		checkForChild();
	}
	
	public RecipeNode(List<List<Item>> data) {
		this.itemLayer = new ArrayList<List<Item>>();
		this.machineNum = new ArrayList<List<BigDecimal>>();
		for(int i=0; i<data.size(); i++) {
			List<BigDecimal> temp = new ArrayList<BigDecimal>();
			for(int j=0; j<data.get(i).size(); j++) {
				temp.add(new BigDecimal(1.0));
			}
			this.machineNum.add(temp);
		}
		this.itemLayer.addAll(data);
		this.numOfChildren = 0;
		checkForChild();
	}
	
	public void setMachineNum(BigDecimal input, int place, int layer) {
		this.machineNum.get(layer).set(place, input);
	}
	
	public BigDecimal getMachineNum(int place, int layer) {
		return this.machineNum.get(layer).get(place);
	}
	
	public void addChildNode(RecipeNode input) {
		this.childNodes.add(input);
		this.numOfChildren++;
	}
	
	public RecipeNode getChildNode(int input) {
		return this.childNodes.get(input);
	}
	
	public List<RecipeNode> childNodes() {
		return this.childNodes;
	}
	
	public Item getItem(int itemNum, int layerNum) {
		return this.itemLayer.get(layerNum).get(itemNum);
	}
	
	public List<Item> itemLayer(int layer) {
		return this.itemLayer.get(layer);
	}
	
	public List<List<Item>> allItems() {
		return this.itemLayer;
	}
	
	public boolean hasChild() {
		return this.hasChild;
	}
	
	private void checkForChild() {
		boolean result = true;
		for(int i=0; i<this.itemLayer.size(); i++) {
			for (int j = 0; j < this.itemLayer.get(i).size(); j++) {
				if (!this.itemLayer.get(i).get(j).isAllRaw()) {
					result = false;
				} 
			}
		}
		this.hasChild = result;
	}
	
	public List<Integer> layerSize() {
		return this.layerSize;
	}
	
	public int numOfChildren() {
		return this.numOfChildren;
	}
	
	public String toString() {
		String rtn = "";
		List<String> input = toStringList();
		for(int i=0; i<input.size(); i++) {
			rtn = rtn + input.get(i) + "\n";
		}
		return rtn;
	}
	
	public List<String> toStringList() {
		List<String> rtn = new ArrayList<String>();
		int longestString = longestLength(this.itemLayer.get(0).get(0).toStringList(),0);
		for(int i=1; i<this.itemLayer.size(); i++) {
			for (int j = 0; j < this.itemLayer.get(i).size(); j++) {
				if (longestLength(this.itemLayer.get(i).get(j).toStringList(), i) > longestString) {
					longestString = longestLength(this.itemLayer.get(i).get(j).toStringList(), i);
				} 
			}
		}
		
		String line = "+";
		for(int i=0; i<longestString; i++) {
			line = line + "-";
		}
		line = line + "+";
		
		rtn.add(line);
		rtn.add(padString(centerString("Size: " + this.layerSize, longestString), longestString));
		for(int i=0; i<this.itemLayer.size(); i++) {
			rtn.add(padString(centerString("Layer " + (i + 1) + ":", longestString), longestString));
			for (int j = 0; j < this.itemLayer.get(i).size(); j++) {
				if (j != 0) {
					rtn.add(padString("", longestString));
				}
				rtn.add(padString(centerString("Item " + (j + 1) + ":", longestString), longestString));
				List<String> thisItemList = this.itemLayer.get(i).get(j).toStringList();
				thisItemList.set(1, this.machineNum.get(i).get(j) + "");
				for (int k = 0; k < thisItemList.size(); k++) {
					rtn.add(padString(thisItemList.get(k), longestString));
				}
			}
		}
		rtn.add(line);
		return rtn;
	}
	
	private int longestLength(List<String> input, int n) {
		int rtn = input.get(0).length();
		for(int i=1; i<input.size(); i++) {
			if(input.get(i).length()>rtn) {
				rtn = input.get(i).length();
			}
		}
		if((this.machineNum.get(n)+"").length()>rtn) {
			rtn = (this.machineNum.get(n)+"").length();
		}
		if(("Size: "+this.layerSize).length()>rtn) {
			rtn = ("Size: "+this.layerSize).length();
		}
		return rtn;
	}
	
	public int toStringListHeight() {
		int rtn = 3;
		for(int i=0; i<this.itemLayer.size(); i++) {
			for (int j = 0; j < this.itemLayer.get(i).size(); j++) {
				if (j != 0) {
					rtn++;
				}
				rtn++;
				List<String> thisItemList = this.itemLayer.get(i).get(j).toStringList();
				for (int k = 0; k < thisItemList.size(); k++) {
					rtn++;
				}
			}
		}
		rtn++;
		return rtn;
	}
	
	private String centerString(String input, int widthSize) {
		int inputCenter = input.length()/2, widthCenter = widthSize/2;
		int padLeft = widthCenter - inputCenter;
		for(int i=0; i<padLeft; i++) {
			input = " " + input;
		}
		return input;
	}
	
	private String padString(String input, int padLength) {
		while(input.length()<padLength) {
			input = input + " ";
		}
		input = "|" + input + "|";
		return input;
	}
}
