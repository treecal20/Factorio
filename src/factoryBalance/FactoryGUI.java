package factoryBalance;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class FactoryGUI {
	
	public static Scanner SCAN_INPUT = new Scanner(System.in);
	
	public static final String[] INTERFACE_LIST = {"Calculator","Recipe File","Exit"};
	public static final String[][] SEARCH_FOR = {{"calculator","calc","1"}, {"recipefile","rf","2"},{"exit","e","3"}};
	
	public static void main(String[] args) throws FileNotFoundException {
		factoryInterfaceOptions();
	}
	
	public static void factoryInterfaceOptions() throws FileNotFoundException {
		String s = "Available interfaces: " + INTERFACE_LIST.length;
		for(int i=0; i<INTERFACE_LIST.length; i++) {
			s = s + "\n" + (i+1) + ": " + INTERFACE_LIST[i];
		}
		s = s + "\nSelection:";
		System.out.println(s);
		String input = SCAN_INPUT.nextLine();
		if(fuzzyContentFilter(input,SEARCH_FOR[0])) {
			CalculationInteractionController.startInteraction();
		} else if(fuzzyContentFilter(input,SEARCH_FOR[1])) {
			FileInteractionController.startInteraction();
		} else if(fuzzyContentFilter(input,SEARCH_FOR[2])) {
			System.out.println("Factory Interaction Ended");
		}
		factoryInterfaceOptions();
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
