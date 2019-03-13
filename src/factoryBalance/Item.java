package factoryBalance;

public class Item {
	
	private String[] inputNames;
	
	private String name;
	private String type;
	
	private int[] inputQuantities;
	
	private int allSize;
	private int inputSize;
	private int longestStringLength;
	private int padLength;
	
	private double time;
	private double output;
	private double machineNum;
	
	private boolean padToStringMethod;
	
	public Item(String itemType, String itemName, double itemOutput, double itemTime, int[] itemInputQuantities, String[] itemInputNames) {
		this.type = itemType;
		this.machineNum = 1.0;
		this.name = itemName;
		this.output = itemOutput;
		this.time = itemTime;
		this.inputQuantities = itemInputQuantities;
		this.inputNames = itemInputNames;
		this.inputSize = itemInputQuantities.length;
		this.allSize = 4 + itemInputQuantities.length + itemInputNames.length;
		this.padLength = 0;
		this.padToStringMethod = false;
		findLongestStringLength();
	}
	
	public Item(String itemName, double itemOutput, double itemTime) {
		this.machineNum = 1.0;
		this.type = "infinite";
		this.name = itemName;
		this.output = itemOutput;
		this.time = itemTime;
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
	
	public double time() {
		return this.time;
	}
	
	public double output() {
		return this.output;
	}
	
	public int[] inputQuantities() {
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
	
	public double machineNum() {
		return this.machineNum;
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
			rtn = this.padString("Name: " + this.name,this.padLength) + "\n"+this.padString("Type: " + this.type,this.padLength) + "\n"+this.padString("Number of Machines = " + this.machineNum,this.padLength) + "\n"+this.padString("Size = " + this.allSize,this.padLength) + "\n" + this.padString(this.output + " out every " + this.time,this.padLength) + "\n"+this.padString("Inputs:",this.padLength)+"\n";
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
	
	public String[] toStringArray() {
		String[] rtn = new String[allSize];
		rtn[0] = this.name;
		rtn[1] = "" + this.output;
		rtn[2] = "" + this.time;
		for(int i=0; i<inputSize; i++) {
			rtn[i*2+3] = this.inputNames[i];
			rtn[i*2+4] = "" + this.inputQuantities[i];
		}
		return rtn;
	}
	
	public void setMachineNumber(double inputMachineNum) {
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
		if((this.output + " out every " + this.time).length()>longestLength) {
			longestLength = (this.output + " out every " + this.time).length();
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
}
