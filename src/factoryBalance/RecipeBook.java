package factoryBalance;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class RecipeBook {
	
	private List<Item> items;
	
	private int size;
	private int itemTypesSize;
	
	private String[][] itemTypes;
	private String[] typeCategories;
	private String recipeBookName;
	
	public RecipeBook(String bookName, String[] types, String[] names, double[] outputs, double[] times, int[][] inputNumbers, String[][] inputNames) {
		this.size = names.length;
		this.recipeBookName = bookName;
		this.items = new ArrayList<Item>();
		for(int i=0; i<size; i++) {
			this.items.add(new Item(types[i], names[i], outputs[i], times[i], inputNumbers[i], inputNames[i]));
		}
	}
	
	public RecipeBook(String bookName, Item[] inputItems) {
		this.size = inputItems.length;
		this.recipeBookName = bookName;
		this.items = new ArrayList<Item>();
		for(int i=0; i<size; i++) {
			this.items.add(inputItems[i]);
		}
	}
	
	public RecipeBook(String bookName, String[] bookTypeCategories, String[][] bookItemTypes) {
		this.itemTypes = bookItemTypes;
		this.typeCategories = bookTypeCategories;
		this.itemTypesSize = 0;
		for(int i=0; i<this.itemTypes.length; i++) {
			this.itemTypesSize += this.itemTypes[i].length;
		}
		this.recipeBookName = bookName;
		this.items = new ArrayList<Item>();
		try {
			readFromFile();
		} catch(FileNotFoundException e) {
			System.out.println("No File Found");
			System.out.println(e);
		}
	}
	
	public int size() {
		return this.size;
	}
	
	public Item getItem(int index) {
		return this.items.get(index);
	}
	
	public Item findItem(String itemName) {
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.name().contentEquals(itemName)) {
				return thisItem;
			}
		}
		String[] a = {"",""};
		int[] b = {-1,-1};
		return new Item("","",-1.0,-1.0,b,a);
	}
	
	public String toString() {
		int lineLength = this.items.get(0).longestStringLength();
		for(int i=1; i<this.items.size(); i++) {
			if(this.items.get(i).longestStringLength()>lineLength) {
				lineLength = this.items.get(i).longestStringLength();
			}
		}
		String rtn = "Size = " + this.size + " \n";
		String line = "";
		for(int i=0; i<lineLength; i++) {
			line = line + "-";
		}
		line = "+" + line + "+";
		for(int i=0; i<this.size; i++) {
			rtn = rtn + line + "\n";
			this.items.get(i).setPadToStringMethod(true);
			this.items.get(i).setPadLength(lineLength);
			rtn = rtn + padString(centerString("Item " + (i+1) + ":",lineLength), lineLength)+"\n"+padString("",lineLength)+"\n" + this.items.get(i).toString();
			if(i!=this.size-1) {
				rtn = rtn + "\n";
			}
		}
		rtn = rtn + "\n" + line;
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
	
	public void orderByType() {
		List<Item> sortedList = new ArrayList<Item>();
		for(int i=0; i<this.itemTypes.length; i++) {
			if (this.items.size()>=0) {
				for (int j = 0; j < this.itemTypes[i].length; j++) {
					int tempSize = this.items.size();
					for (int k = 0; k < tempSize; k++) {
						if (this.items.size()>=0&&isTypeRemaining(this.itemTypes[i][j])) {
							sortedList.add(this.items.remove(findTypeIndex(this.itemTypes[i][j])));
						}
					}
				}
			}
		}
		if (this.items.size()>=0) {
			for (int k = 0; k < this.items.size(); k++) {
				System.out.println(this.items.get(k));
				sortedList.add(this.items.remove(k));
			}
		}
		for(int i=0; i<sortedList.size(); i++) {
			this.items.add(sortedList.get(i));
		}
	}
	
	public int findTypeIndex(String typeName) {
		for(int i=0; i<this.items.size(); i++) {
			if(this.items.get(i).type().contentEquals(typeName)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isTypeRemaining(String typeName) {
		for(int i=0; i<this.items.size(); i++) {
			if(this.items.get(i).type().contentEquals(typeName)) {
				return true;
			}
		}
		return false;
	}
	
	public void printToFile() throws FileNotFoundException {
		orderByType();
		String separator = System.getProperty("line.separator");
		PrintStream book = new PrintStream(new File("C:/Users/Calvin/Documents/GitHub/Factorio/RecipeBooks/" + this.recipeBookName));
		String s = "" + this.recipeBookName + separator;
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			s = s + thisItem.name() + " " + thisItem.type() + " " + thisItem.output() + " " + thisItem.time() + " ";
			for(int j=0; j<thisItem.inputQuantities().length; j++) {
				s = s + "" + thisItem.inputQuantities()[j] + " ";
			}
			for(int j=0; j<thisItem.inputNames().length; j++) {
				if(j==thisItem.inputNames().length-1) {
					s = s + thisItem.inputNames()[j];
				} else {
					s = s + thisItem.inputNames()[j] + " ";
				}
			}
			if(i!=this.size-1) {
				s = s + separator;
			}
		}
		book.print(s);
		System.out.println("Done!");
	}
	
	public void readFromFile() throws FileNotFoundException {
		if(!this.items.isEmpty()) {
			this.items.clear();
		}
		
		Scanner book = new Scanner(new File("C:/Users/Calvin/Documents/GitHub/Factorio/RecipeBooks/" + this.recipeBookName));
		List<String> bookText = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<Double> outputs = new ArrayList<Double>(), times = new ArrayList<Double>();
		List<int[]> inputNumbers = new ArrayList<int[]>();
		List<String[]> inputNames = new ArrayList<String[]>();
		
		while(book.hasNextLine()) {
			bookText.add(book.nextLine());
		}
		
		for(int i=1; i<bookText.size(); i++) {
			Scanner scanString = new Scanner(bookText.get(i));
			List<String> splitString = new ArrayList<String>();
			while(scanString.hasNext()) {
				splitString.add(scanString.next());
			}
			int offset = 4;
			names.add(splitString.get(0));
			types.add(splitString.get(1));
			outputs.add(Double.parseDouble(splitString.get(2)));
			times.add(Double.parseDouble(splitString.get(3)));
			int[] tempInputNumbers = new int[(splitString.size()-offset)/2];
			String[] tempInputNames = new String[(splitString.size()-offset)/2];
			for(int j=0; j<splitString.size()-offset; j++) {
				if(j<(splitString.size()-offset)/2) {
					tempInputNumbers[j] = Integer.parseInt(splitString.get(j+offset));
				} else {
					tempInputNames[j-((splitString.size()-offset)/2)] = splitString.get(j+offset);
				}
			}
			inputNumbers.add(tempInputNumbers);
			inputNames.add(tempInputNames);
		}
		
		this.size = names.size();
		for(int i=0; i<this.size; i++) {
			this.items.add(new Item(types.get(i), names.get(i), outputs.get(i), times.get(i), inputNumbers.get(i), inputNames.get(i)));
		}
		System.out.println("Done!");
	}
	
	public void addItemToFile() throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		boolean inputNotDone = true;
		List<String> inputText = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<Double> outputs = new ArrayList<Double>(), times = new ArrayList<Double>();
		List<int[]> inputNumbers = new ArrayList<int[]>();
		List<String[]> inputNames = new ArrayList<String[]>();
		
		System.out.println("Item format: name type output time inputNumber1-inputNumberN inputName1-inputNameN");
		while(inputNotDone) {
			if(scan.hasNextLine()) {
				String temp = scan.nextLine();
				if(temp.toLowerCase().contentEquals("done")) {
					inputNotDone = false;
				} else if(!temp.contentEquals("")) {
					inputText.add(temp);
				}
			}
		}
		
		for(int i=0; i<inputText.size(); i++) {
			Scanner scanString = new Scanner(inputText.get(i));
			List<String> splitString = new ArrayList<String>();
			while(scanString.hasNext()) {
				splitString.add(scanString.next());
			}
			int offset = 4;
			names.add(splitString.get(0));
			types.add(splitString.get(1));
			outputs.add(Double.parseDouble(splitString.get(2)));
			times.add(Double.parseDouble(splitString.get(3)));
			int[] tempInputNumbers = new int[(splitString.size()-offset)/2];
			String[] tempInputNames = new String[(splitString.size()-offset)/2];
			for(int j=0; j<splitString.size()-offset; j++) {
				if(j<(splitString.size()-offset)/2) {
					tempInputNumbers[j] = Integer.parseInt(splitString.get(j+offset));
				} else {
					tempInputNames[j-((splitString.size()-offset)/2)] = splitString.get(j+offset);
				}
			}
			inputNumbers.add(tempInputNumbers);
			inputNames.add(tempInputNames);
		}
		this.size = this.size + names.size();
		for(int i=0; i<names.size(); i++) {
			this.items.add(new Item(types.get(i), names.get(i), outputs.get(i), times.get(i), inputNumbers.get(i), inputNames.get(i)));
		}
		this.printToFile();
	}
}
