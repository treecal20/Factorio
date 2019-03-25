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
	
	public int getItemIndex(String itemName) {
		for(int i=0; i<this.size; i++) {
			if(this.items.get(i).name().contentEquals(itemName)) {
				return i;
			}
		}
		return -1;
	}
	
	public Item findItem(String itemName) {
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.name().contentEquals(itemName)) {
				return thisItem;
			}
		}
		return null;
	}
	
	public List<Item> findEveryItem(String itemName) {
		List<Item> result = new ArrayList<Item>();
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.name().contentEquals(itemName)) {
				result.add(thisItem);
			}
		}
		return result;
	}
	
	public boolean doMultiplesExist(String itemName) {
		boolean alreadyExists = false;
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.name().contentEquals(itemName)) {
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
			if(thisItem.name().contentEquals(itemName)) {
				result++;
			}
		}
		return result;
	}
	
	public Item findDuplicate(String itemName, int dupeNum) {
		int k=0;
		for(int i=0; i<this.size; i++) {
			Item thisItem = this.items.get(i);
			if(thisItem.name().contentEquals(itemName)) {
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
		book.close();
		for(int i=1; i<bookText.size(); i++) {
			Scanner scanString = new Scanner(bookText.get(i));
			List<String> splitString = new ArrayList<String>();
			while(scanString.hasNext()) {
				splitString.add(scanString.next());
			}
			scanString.close();
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
		for(int i=0; i<inputText.size(); i++) {
			Scanner scanString = new Scanner(inputText.get(i));
			List<String> splitString = new ArrayList<String>();
			while(scanString.hasNext()) {
				splitString.add(scanString.next());
			}
			scanString.close();
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
			itemsToAdd.add(new Item(types.get(i), names.get(i), outputs.get(i), times.get(i), inputNumbers.get(i), inputNames.get(i)));
		}
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
