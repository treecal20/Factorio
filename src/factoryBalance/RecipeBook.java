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
	
	private static final String[] SIMILAR_ITEM_OPTIONS = {"Replace Items","Add This Item","Don't Add This Item"};
	private static final String[][] SIMILAR_ITEM_OPTIONS_SEARCH = {{"replaceitems","ri","1"},{"addthisiItem","ati","2"},{"don'taddthisitem","dontaddthisitem","dati","3"}};
	
	public RecipeBook(String bookName, String[] types, String[] names, double[] outputs, double[] times, int[][] inputNumbers, String[][] inputNames, String[] formats) {
		this.size = names.length;
		this.recipeBookName = bookName;
		this.items = new ArrayList<Item>();
		for(int i=0; i<size; i++) {
			this.items.add(new Item(types[i], names[i], outputs[i], times[i], inputNumbers[i], inputNames[i], formats[i]));
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
	
	public int getItemIndex(String itemName) {
		for(int i=0; i<this.size; i++) {
			if(this.items.get(i).hasThisOutput(itemName)) {
				return i;
			}
		}
		return -1;
	}
	
	public Item findItem(String itemName) {
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.hasThisOutput(itemName)) {
				return thisItem;
			}
		}
		return null;
	}
	
	public List<Item> findEveryItem(String itemName) {
		List<Item> result = new ArrayList<Item>();
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.hasThisOutput(itemName)) {
				result.add(thisItem);
			}
		}
		return result;
	}
	
	public boolean doMultiplesExist(String itemName) {
		boolean alreadyExists = false;
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.hasThisOutput(itemName)) {
				if(alreadyExists) {
					return true;
				}
				alreadyExists = true;
			}
		}
		return false;
	}
	
	public int numberOfDuplicates(String itemName) {
		int result = 0;
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.hasThisOutput(itemName)) {
				result++;
			}
		}
		return result;
	}
	
	public Item findDuplicate(String itemName, int dupeNum) {
		int k=0;
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.hasThisOutput(itemName)) {
				if(k==dupeNum) {
					return thisItem;
				}
				k++;
			}
		}
		return null;
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
			rtn = rtn + padString(centerString("Item " + (i+1) + ":", lineLength), lineLength) + "\n" + padString("", lineLength) + "\n" + this.items.get(i).toString();
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
			s = s + thisItem.format() + " " + thisItem.name() + " " + thisItem.type() + " ";
			if(thisItem.hasMultipleOutputs()) {
				for(int j=0; j<thisItem.outputSize(); j++) {
					s = s + thisItem.multipleOutputs()[j] + " ";
				}
				for(int j=0; j<thisItem.outputSize(); j++) {
					s = s + thisItem.multipleOutputNames()[j] + " ";
				}
				s = s + thisItem.time() + " ";
			} else {
				s = s + thisItem.output() + " " + thisItem.time() + " ";
			}
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
		
		int newSize = 0;
		
		while(book.hasNextLine()) {
			bookText.add(book.nextLine());
		}
		book.close();
		for(int i=1; i<bookText.size(); i++) {
			Scanner scanString = new Scanner(bookText.get(i));
			List<String> splitString = new ArrayList<String>();
			while(scanString.hasNext()) {
				splitString.add(scanString.next());
			}
			scanString.close();
			if(splitString.get(0).contentEquals("Linear")) {
				int offset = 5;
				String name = splitString.get(1);
				String type = splitString.get(2);
				double output = Double.parseDouble(splitString.get(3));
				double time = Double.parseDouble(splitString.get(4));
				int[] inputNumbers = new int[(splitString.size() - offset) / 2];
				String[] inputNames = new String[(splitString.size() - offset) / 2];
				for (int j = 0; j < splitString.size() - offset; j++) {
					if (j < (splitString.size() - offset) / 2) {
						inputNumbers[j] = Integer.parseInt(splitString.get(j + offset));
					} else {
						inputNames[j - ((splitString.size() - offset) / 2)] = splitString.get(j + offset);
					}
				}
				newSize++;
				this.items.add(new Item(type, name, output, time, inputNumbers, inputNames, "Linear"));
			}
			if(splitString.get(0).contentEquals("Multiple")) {
				int offset = 3;
				String name = splitString.get(1);
				String type = splitString.get(2);
				int outputLength=0;
				while(isNum(splitString.get(outputLength + offset))) {
					outputLength++;
				}
				offset = offset + outputLength*2 + 1;
				String[] outputNames = new String[outputLength];
				double[] outputNumbers = new double[outputLength];
				for (int j = 0; j < outputLength; j++) {
					outputNumbers[j] = Double.parseDouble(splitString.get(j + 3));
					outputNames[j] = splitString.get(j + 3 + outputLength);
				}
				double time = Double.parseDouble(splitString.get(offset-1));
				int[] inputNumbers = new int[(splitString.size() - offset) / 2];
				String[] inputNames = new String[(splitString.size() - offset) / 2];
				for (int j = 0; j < splitString.size() - offset; j++) {
					if (j < (splitString.size() - offset) / 2) {
						inputNumbers[j] = Integer.parseInt(splitString.get(j + offset));
					} else {
						inputNames[j - ((splitString.size() - offset) / 2)] = splitString.get(j + offset);
					}
				}
				newSize++;
				this.items.add(new Item(type, name, outputNames, outputNumbers, time, inputNumbers, inputNames, "Multiple"));
			}
		}
		
		this.size = newSize;
		System.out.println("Done!");
	}
	
	public void addItemToFile() throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		boolean inputNotDone = true;
		List<String> inputText = new ArrayList<String>();
		List<Item> itemsToAdd = new ArrayList<Item>();
		
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
		scan.close();
		int newSize = 0;
		for(int i=0; i<inputText.size(); i++) {
			Scanner scanString = new Scanner(inputText.get(i));
			List<String> splitString = new ArrayList<String>();
			while(scanString.hasNext()) {
				splitString.add(scanString.next());
			}
			scanString.close();
			if(splitString.get(0).contentEquals("Linear")) {
				int offset = 5;
				String name = splitString.get(1);
				String type = splitString.get(2);
				double output = Double.parseDouble(splitString.get(3));
				double time = Double.parseDouble(splitString.get(4));
				int[] inputNumbers = new int[(splitString.size() - offset) / 2];
				String[] inputNames = new String[(splitString.size() - offset) / 2];
				for (int j = 0; j < splitString.size() - offset; j++) {
					if (j < (splitString.size() - offset) / 2) {
						inputNumbers[j] = Integer.parseInt(splitString.get(j + offset));
					} else {
						inputNames[j - ((splitString.size() - offset) / 2)] = splitString.get(j + offset);
					}
				}
				newSize++;
				itemsToAdd.add(new Item(type, name, output, time, inputNumbers, inputNames, "Linear"));
			}
			if(splitString.get(0).contentEquals("Multiple")) {
				int offset = 3;
				String name = splitString.get(1);
				String type = splitString.get(2);
				int outputLength=0;
				while(isNum(splitString.get(outputLength + offset))) {
					outputLength++;
				}
				offset = offset + outputLength*2 + 1;
				String[] outputNames = new String[outputLength];
				double[] outputNumbers = new double[outputLength];
				for (int j = 0; j < outputLength; j++) {
					outputNumbers[j] = Double.parseDouble(splitString.get(j + 3));
					outputNames[j] = splitString.get(j + 3 + outputLength);
				}
				double time = Double.parseDouble(splitString.get(offset-1));
				int[] inputNumbers = new int[(splitString.size() - offset) / 2];
				String[] inputNames = new String[(splitString.size() - offset) / 2];
				for (int j = 0; j < splitString.size() - offset; j++) {
					if (j < (splitString.size() - offset) / 2) {
						inputNumbers[j] = Integer.parseInt(splitString.get(j + offset));
					} else {
						inputNames[j - ((splitString.size() - offset) / 2)] = splitString.get(j + offset);
					}
				}
				newSize++;
				itemsToAdd.add(new Item(type, name, outputNames, outputNumbers, time, inputNumbers, inputNames, "Multiple"));
			}
		}
		this.size = this.size + newSize;
		similarItemFilter(itemsToAdd);
		this.printToFile();
	}
	
	public void similarItemFilter(List<Item> inputItems){
		Scanner scanInput = new Scanner(System.in);
		String options = "Available options: " + SIMILAR_ITEM_OPTIONS.length;
		for(int i=0; i<SIMILAR_ITEM_OPTIONS.length; i++) {
			options = options + "\n" + (i+1) + ": " + SIMILAR_ITEM_OPTIONS[i];
		}
		options = options + "\nSelection:";
		for(int i=0; i<inputItems.size(); i++) {
			Item thisItem = inputItems.get(i);
			List<Item> foundItems = new ArrayList<Item>();
			for(int j=0; j<this.size; j++) {
				Item searchItem = this.items.get(i);
				if(searchItem.name().contentEquals(thisItem.name())) {
					foundItems.add(searchItem);
				}
			}
			for(int j=0; j<foundItems.size(); j++) {
				System.out.println("An Item with this name already exists: " + foundItems.get(j).name() + " Item number " + (j+1));
				System.out.println(foundItems.get(j) + "\n");
			}
			System.out.println("Your Item: " + thisItem.name());
			System.out.println(thisItem + "\n");
			System.out.println(options);
			String input = scanInput.nextLine();
			if(fuzzyContentFilter(input,SIMILAR_ITEM_OPTIONS_SEARCH[0])) {
				System.out.println("Which items do you want to replace? (ItemNum1 ItemNum2...)");
				input = scanInput.nextLine();
				Scanner tempScan = new Scanner(input);
				while(tempScan.hasNextInt()) {
					this.items.remove(this.getItemIndex(foundItems.get(tempScan.nextInt()).name()));
				}
				this.items.add(thisItem);
				tempScan.close();
			} else if(fuzzyContentFilter(input,SIMILAR_ITEM_OPTIONS_SEARCH[1])) {
				this.items.add(thisItem);
			} else if(fuzzyContentFilter(input,SIMILAR_ITEM_OPTIONS_SEARCH[2])) {
				
			}
		}
		scanInput.close();
	}
	
	public boolean isNum(String input) {
		boolean rtn = true;
		try {
			double temp = Double.parseDouble(input);
		} catch(NumberFormatException e) {
			rtn = false;
		}
		return rtn;
	}
	
	public static boolean fuzzyContentFilter(String input, String[] search) {
		input = input.replaceAll(" ", "").replaceAll("_", "");
		for(int i=0; i<search.length; i++) {
			if(input.toLowerCase().contentEquals(search[i].toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
