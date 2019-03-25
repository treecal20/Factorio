package factoryBalance;

import java.util.List;
import java.util.ArrayList;

public class InputDetection {
	
	public static void main(String[] args) {
		Calculations.calculate();
	}
	
	public static List<Item[]> recieveInput(){
		System.out.println("Recipe?");
		String input = Calculations.SCAN_INPUT.nextLine();
		RecipeSelect.recursiveLayers(input);
		List<Item[]> result = RecipeSelect.requestedRecipes;
		printRecipes(result);
		return result;
	}
	
	public static void printRecipes(List<Item[]> input){
		if (input != null) {
			System.out.println("----------------------------------------------------------------------------------");
			for (int b = 0; b < input.size(); b++) {
				if (input.get(b) != null) {
					for (int a = 0; a < input.get(b).length; a++) {
						if (input.get(b)[a] != null) {
							System.out.println(input.get(b)[a].toString());
						}
					} 
				}
				if (input.get(b) != null) {
					System.out.println("----------------------------------------------------------------------------------");
				}
			}
		}
	}
}
