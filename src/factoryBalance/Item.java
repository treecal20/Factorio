package factoryBalance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Item {
	
	private String[] inputNames;
	private String[] multipleOutputNames;
	
	private String name;
	private String type;
	
	private BigDecimal[] inputQuantities;
	
	private int allSize;
	private int inputSize;
	private int longestStringLength;
	private int padLength;
	
	private BigDecimal[] multipleOutputs;
	
	private BigDecimal time;
	private BigDecimal output;
	private BigDecimal machineNum;
	
	private boolean padToStringMethod;
	private boolean hasMultipleOutputs;
	
	public Item(String itemType, String itemName, double itemOutput, double itemTime, int[] itemInputQuantities, String[] itemInputNames) {
		this.type = itemType;
		this.machineNum = new BigDecimal(1.0);
		this.name = itemName;
		this.output = new BigDecimal(itemOutput, MathContext.DECIMAL32);
		this.time = new BigDecimal(itemTime, MathContext.DECIMAL32);
		this.inputQuantities = new BigDecimal[itemInputQuantities.length];
		for(int i=0; i<this.inputQuantities.length; i++) {
			this.inputQuantities[i] = new BigDecimal(itemInputQuantities[i], MathContext.DECIMAL32);
		}
		this.inputNames = itemInputNames;
		this.inputSize = itemInputQuantities.length;
		this.allSize = 4 + itemInputQuantities.length + itemInputNames.length;
		this.padLength = 0;
		this.padToStringMethod = false;
		this.hasMultipleOutputs = false;
		findLongestStringLength();
	}
	
	public Item(String itemType, String itemName, String[] itemMultiOutputNames, double[] itemMultiOutputs, double itemTime, int[] itemInputQuantities, String[] itemInputNames) {
		this.type = itemType;
		this.machineNum = new BigDecimal(1.0);
		this.name = itemName;
		this.multipleOutputNames = itemMultiOutputNames;
		this.multipleOutputs = new BigDecimal[itemMultiOutputs.length];
		for(int i=0; i<this.multipleOutputs.length; i++) {
			this.multipleOutputs[i] = new BigDecimal(itemMultiOutputs[i], MathContext.DECIMAL32);
		}
		this.time = new BigDecimal(itemTime, MathContext.DECIMAL32);
		this.inputQuantities = new BigDecimal[itemInputQuantities.length];
		for(int i=0; i<this.inputQuantities.length; i++) {
			this.inputQuantities[i] = new BigDecimal(itemInputQuantities[i], MathContext.DECIMAL32);
		}
		this.inputNames = itemInputNames;
		this.inputSize = itemInputQuantities.length;
		this.allSize = 2 + itemMultiOutputNames.length + itemMultiOutputs.length + itemInputQuantities.length + itemInputNames.length;
		this.padLength = 0;
		this.padToStringMethod = false;
		this.hasMultipleOutputs = true;
		findLongestStringLength();
	}
	
	public Item(String itemName, double itemOutput, double itemTime) {
		this.machineNum = new BigDecimal(1.0);
		this.type = "infinite";
		this.name = itemName;
		this.output = new BigDecimal(itemOutput, MathContext.DECIMAL32);
		this.time = new BigDecimal(itemTime, MathContext.DECIMAL32);
		this.allSize = 4;
		this.padToStringMethod = false;
		this.padLength = 0;
	}
	
	public String name() {
		return this.name;
	}
	
	public String type() {
		return this.type;
	}
	
	public BigDecimal time() {
		return this.time;
	}
	
	public BigDecimal output() {
		return this.output;
	}
	
	public BigDecimal[] multipleOutputs() {
		return this.multipleOutputs;
	}
	
	public String[] multipleOutputNames() {
		return this.multipleOutputNames;
	}
	
	public BigDecimal[] inputQuantities() {
		return this.inputQuantities;
	}
	
	public String[] inputNames() {
		return this.inputNames;
	}
	
	public int allSize() {
		return this.allSize;
	}
	
	public int inputSize() {
		return this.inputSize;
	}
	
	public BigDecimal machineNum() {
		return this.machineNum;
	}
	
	public boolean hasMultipleOutputs() {
		return this.hasMultipleOutputs;
	}
	
	public BigDecimal outPerSec() {
		return this.output.divide(this.time, 5, RoundingMode.HALF_UP);
	}
	
	public BigDecimal[] inPerSec() {
		BigDecimal[] rtn = new BigDecimal[this.inputQuantities.length];
		for(int i=0; i<rtn.length; i++) {
			rtn[i] = this.inputQuantities[i].divide(this.time, 5, RoundingMode.HALF_UP);
		}
		return rtn;
	}
	
	public void setPadToStringMethod(boolean input) {
		this.padToStringMethod = input;
	}
	
	public void setPadLength(int input) {
		this.padLength = input;
	}
	
	public String toString() {
		String rtn = "";
		if(this.padToStringMethod) {
			rtn = this.padString("Name: " + this.name,this.padLength) + "\n" + this.padString("Type: " + this.type,this.padLength) + "\n" + this.padString("Number of Machines = " + this.machineNum,this.padLength) + "\n" + this.padString("Size = " + this.allSize,this.padLength) + "\n";
			if(this.hasMultipleOutputs) {
				for(int i=0; i<this.multipleOutputNames.length; i++) {
					rtn = rtn + this.padString("Output " + (i+1) + ": " + this.multipleOutputNames[i] + " at", this.padLength) + "\n" + this.padString(this.multipleOutputs[i] + " out every " + this.time, this.padLength);
				}
			} else {
				rtn = rtn + this.padString(this.output + " out every " + this.time,this.padLength) + "\n" + this.padString("Inputs:",this.padLength) + "\n";
			}
			for(int i=0; i<inputSize; i++) {
				rtn = rtn + this.padString(this.inputQuantities[i] + "   " + this.inputNames[i],this.padLength);
				if(i!=inputSize-1) {
					rtn = rtn + "\n";
				}
			}
			this.padToStringMethod = false;
			this.padLength = 0;
		} else {
			rtn = "Name: " + this.name + "\nType: " + this.type + "\nNumber of Machines = " + this.machineNum + "\nSize = " + this.allSize + "\n" + this.output + " out every " + this.time + "\nInputs:\n";
			for(int i=0; i<inputSize; i++) {
				rtn = rtn + this.inputQuantities[i] + "   " + this.inputNames[i];
				if(i!=inputSize-1) {
					rtn = rtn + "\n";
				}
			}
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
	
	public List<String> toStringList() {
		List<String> rtn = new ArrayList<String>();
		rtn.add(this.name);
		rtn.add("" + this.machineNum);
		if(this.hasMultipleOutputs) {
			for(int i=0; i<this.multipleOutputNames.length; i++) {
				rtn.add(this.multipleOutputNames[i]);
				rtn.add("" + this.multipleOutputs[i]);
			}
		} else {
			rtn.add("" + this.output);
		}
		rtn.add("" + this.time);
		for(int i=0; i<inputSize; i++) {
			rtn.add(this.inputNames[i]);
			rtn.add("" + this.inputQuantities[i]);
		}
		return rtn;
	}
	
	public void setMachineNumber(BigDecimal inputMachineNum) {
		this.machineNum = inputMachineNum;
	}
	
	private void findLongestStringLength() {
		int longestLength = ("Name: " + this.name).length();
		if(("Type: " + this.type).length()>longestLength) {
			longestLength = ("Type: " + this.type).length();
		}
		if(("Number of Machines = " + this.machineNum).length()>longestLength) {
			longestLength = ("Number of Machines = " + this.machineNum).length();
		}
		if(("Size = " + this.allSize).length()>longestLength) {
			longestLength = ("Size = " + this.allSize).length();
		}
		if (this.hasMultipleOutputs) {
			for(int i=0; i<this.multipleOutputNames.length; i++) {
				if(("Output " + (i+1) + ": " + this.multipleOutputNames[i] + " at").length()>longestLength) {
					longestLength = ("Output " + (i+1) + ": " + this.multipleOutputNames[i] + " at").length();
				}
				if((this.multipleOutputs[i] + " out every " + this.time).length()>longestLength) {
					longestLength = (this.multipleOutputs[i] + " out every " + this.time).length();
				}
			}
		} else {
			if ((this.output + " out every " + this.time).length() > longestLength) {
				longestLength = (this.output + " out every " + this.time).length();
			} 
		}
		if(("Inputs:").length()>longestLength) {
			longestLength = ("Number of Machines = " + this.machineNum).length();
		}
		for(int i=0; i<inputSize; i++) {
			if((this.inputQuantities[i] + "   " + this.inputNames[i]).length()>longestLength) {
				longestLength = (this.inputQuantities[i] + "   " + this.inputNames[i]).length();
			}
		}
		this.longestStringLength = longestLength;
	}
	
	public int longestStringLength() {
		return this.longestStringLength;
	}
	
	public boolean isAllRaw() {
		boolean result = true;
		for(int i=0; i<this.inputNames.length; i++) {
			if(!this.inputNames[i].contentEquals("RAW_MAT")) {
				result = false;
			}
		}
		return result;
	}
}
