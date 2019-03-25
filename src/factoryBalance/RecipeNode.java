package factoryBalance;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

public class RecipeNode {
	
	private List<RecipeNode> childNodes;
	private List<Item> itemLayer;
	private List<BigDecimal> machineNum;
	
	private int layerSize;
	private int numOfChildren;
	
	private boolean hasChild;
	
	public RecipeNode(List<Item> data, List<RecipeNode> inputChildNodes) {
		this.itemLayer = new ArrayList<Item>();
		this.childNodes = new ArrayList<RecipeNode>();
		this.machineNum = new ArrayList<BigDecimal>();
		for(int i=0; i<data.size(); i++) {
			this.machineNum.add(new BigDecimal(1.0));
		}
		this.itemLayer.addAll(data);
		this.childNodes.addAll(inputChildNodes);
		this.layerSize = data.size();
		this.numOfChildren = inputChildNodes.size();
		checkForChild();
	}
	
	public RecipeNode(List<Item> data) {
		this.itemLayer = new ArrayList<Item>();
		this.machineNum = new ArrayList<BigDecimal>();
		for(int i=0; i<data.size(); i++) {
			this.machineNum.add(new BigDecimal(1.0));
		}
		this.itemLayer.addAll(data);
		this.layerSize = this.itemLayer.size();
		this.numOfChildren = 0;
		checkForChild();
	}
	
	public void setMachineNum(BigDecimal input, int place) {
		this.machineNum.set(place, input);
	}
	
	public BigDecimal getMachineNum(int place) {
		return this.machineNum.get(place);
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
	
	public void addItem(Item input) {
		this.itemLayer.add(input);
		checkForChild();
	}
	
	public Item getItem(int input) {
		return this.itemLayer.get(input);
	}
	
	public List<Item> itemLayer() {
		return this.itemLayer;
	}
	
	public boolean hasChild() {
		return this.hasChild;
	}
	
	private void checkForChild() {
		boolean result = true;
		for(int i=0; i<this.itemLayer.size(); i++) {
			if(!this.itemLayer.get(i).isAllRaw()) {
				result = false;
			}
		}
		this.hasChild = result;
	}
	
	public int layerSize() {
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
		int longestString = longestLength(this.itemLayer.get(0).toStringList(),0);
		for(int i=1; i<this.itemLayer.size(); i++) {
			if(longestLength(this.itemLayer.get(i).toStringList(),i)>longestString) {
				longestString = longestLength(this.itemLayer.get(i).toStringList(),i);
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
			if(i!=0) {
				rtn.add(padString("", longestString));
			}
			rtn.add(padString(centerString("Item " + (i+1) + ":", longestString), longestString));
			List<String> thisItemList = this.itemLayer.get(i).toStringList();
			thisItemList.set(1, this.machineNum.get(i)+"");
			for(int j=0; j<thisItemList.size(); j++) {
				rtn.add(padString(thisItemList.get(j), longestString));
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
		return rtn;
	}
	
	public int toStringListHeight() {
		int rtn = 3;
		for(int i=0; i<this.itemLayer.size(); i++) {
			if(i!=0) {
				rtn++;
			}
			rtn++;
			List<String> thisItemList = this.itemLayer.get(i).toStringList();
			for(int j=0; j<thisItemList.size(); j++) {
				rtn++;
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
