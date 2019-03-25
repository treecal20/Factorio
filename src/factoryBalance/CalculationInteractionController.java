package factoryBalance;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

public class CalculationInteractionController {

	public static Scanner SCAN_INPUT = new Scanner(System.in);
	
	public static final String[][] OPERATION_SEARCH = {{"printtree","pt","1"},{"calculatetree","ct","2"},{"exit","e","3"}};
	public static final String[][] BOOK_SEARCH = {{"vanilla","van","v","1"}};
	public static final String[] BOOKS = {"Vanilla"};
	public static final String[] OPERATIONS = {"Print Tree","Calculate Tree","Exit"};
	
	public static RecipeBook selectedBook;
	
	public static void startInteraction() throws FileNotFoundException {
		chooseBook();
		chooseOperation();
		FactoryGUI.factoryInterfaceOptions();
	}
	
	public static void chooseOperation() throws FileNotFoundException {
		System.out.println("Recipe?");
		String input = SCAN_INPUT.nextLine();
		RecipeTree thisTree = new RecipeTree(selectedBook.findItem(input), selectedBook);
		SCAN_INPUT.close();
		String operations = "Available operations: " + OPERATIONS.length;
		for(int i=0; i<OPERATIONS.length; i++) {
			operations = operations + "\n" + (i+1) + ": " + OPERATIONS[i];
		}
		operations = operations + "\nSelection:";
		System.out.println(operations);
		String inputOperation = SCAN_INPUT.nextLine();
		if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[0])) {
			thisTree.printTree();
			chooseOperation();
		} else if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[1])) {
			System.out.println("OutPerSec?");
			BigDecimal inputDouble = new BigDecimal(SCAN_INPUT.nextDouble());
			System.out.println("Crafting Speed?");
			BigDecimal craftSpeed = new BigDecimal(SCAN_INPUT.nextDouble());
			thisTree.calculateTree(inputDouble, craftSpeed);
			chooseOperation();
		} else if(fuzzyContentFilter(inputOperation,OPERATION_SEARCH[2])) {
			System.out.println("File Session Ended");
		}
	}
	
	public static void chooseBook() {
		String fileNames = "Available books: " + BOOKS.length;
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
