package factoryBalance;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInteractionController {

	public static Scanner SCAN_INPUT = new Scanner(System.in);
	
	public static final String[][] OPERATION_SEARCH = {{"writetofile","wf","wtf","1"}, {"readfromfile","rff","2"},{"printfiletoconsole","pfc","pftc","3"},{"additemtofile","aif","aitf","4"},{"switchfile","sf","5"},{"exit","e","6"}};
	public static final String[][] BOOK_SEARCH = {{"vanilla","van","v","1"}};
	public static final String[] BOOKS = {"Vanilla"};
	public static final String[] OPERATIONS = {"Write to File","Read from File","Print File to Console","Add Item to File","Switch File","Exit"};
	
	public static RecipeBook selectedBook;
	
	public static void startInteraction() throws FileNotFoundException {
		chooseFile();
		chooseOperation();
		FactoryGUI.factoryInterfaceOptions();
	}
	
	public static void chooseOperation() throws FileNotFoundException {
		String operations = "Available operations: " + OPERATIONS.length;
		for(int i=0; i<OPERATIONS.length; i++) {
			operations = operations + "\n" + (i+1) + ": " + OPERATIONS[i];
		}
		operations = operations + "\nSelection:";
		System.out.println(operations);
		String inputOperation = SCAN_INPUT.nextLine();
		if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[0])) {
			selectedBook.printToFile();
			chooseOperation();
		} else if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[1])) {
			selectedBook.readFromFile();
			chooseOperation();
		} else if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[2])) {
			selectedBook.readFromFile();
			System.out.println(selectedBook.toString());
			chooseOperation();
		} else if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[3])) {
			selectedBook.addItemToFile();
			chooseOperation();
		} else if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[4])) {
			chooseFile();
			chooseOperation();
		} else if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[5])) {
			System.out.println("File Session Ended");
		}
	}
	
	public static void chooseFile() {
		String fileNames = "Available files: " + BOOKS.length;
		for(int i=0; i<BOOKS.length; i++) {
			fileNames = fileNames + "\n" + (i+1) + ": " + BOOKS[i];
		}
		fileNames = fileNames + "\nSelection:";
		System.out.println(fileNames);
		String inputFileName = SCAN_INPUT.nextLine();
		if(fuzzyContentFilter(inputFileName,BOOK_SEARCH[0])) {
			selectedBook = new VanillaRecipes();
		}
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
