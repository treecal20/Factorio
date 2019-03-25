package factoryBalance;

import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.ArrayList;

public class RecipeSelect {
	public static boolean THREADS_DONE = true;
	public static VanillaRecipes RECIPES = new VanillaRecipes();
	public static List<Item[]> requestedRecipes;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Recipe?");
		String input = scan.nextLine();
		RecipeTree temp = new RecipeTree(RECIPES.findItem(input), RECIPES);
		//temp.printTree();
		System.out.println("OutPerSec?");
		BigDecimal inputDouble = new BigDecimal(scan.nextDouble());
		System.out.println("Crafting Speed?");
		BigDecimal craftSpeed = new BigDecimal(scan.nextDouble());
		temp.calculateTree(inputDouble, craftSpeed);
		scan.close();
		//Calculations.calculate();
	}
	
	public static void recursiveLayers(String input) {
		requestedRecipes = new ArrayList<Item[]>();
		List<String> goal = new ArrayList<String>();
		int i = 0;
		do {
			if(i == 0) {
				goal.add(input);
				Item[] temp = {RECIPES.findItem(goal.get(0))};
				requestedRecipes.add(temp);
				
				if(!checkThread(requestedRecipes.get(0)[0])) {
					THREADS_DONE = true;
				} else {
					THREADS_DONE = false;
				}
			} else {
				THREADS_DONE = true;
				
				goal = findGoals(requestedRecipes.get(i-1));
				requestedRecipes.add(layer(goal));
			}
			i++;
		} while(THREADS_DONE == false);
	}
	
	public static boolean checkThread(Item inputRecipe) {
		boolean result = false;
		String[] inputRecipeInputs = inputRecipe.inputNames();
		if(inputRecipeInputs != null) {
			for(int i=0; i<inputRecipeInputs.length; i++) {
				if(inputRecipeInputs[i] != null && !inputRecipeInputs[i].contentEquals("RAW_MAT")) {
					result = true;
				}
			}
		}
		return result;
	}
	
	public static List<String> findGoals(Item[] inputRecipe) {
		List<String[]> inputRecipeInputs = new ArrayList<String[]>();
		List<String> result = new ArrayList<String>();
		for(int i=0; i<inputRecipe.length; i++) {
			//System.out.println(inputRecipe[i].toString());
			inputRecipeInputs.add(inputRecipe[i].inputNames());
		}
		if(inputRecipeInputs != null) {
			for(int i=0; i<inputRecipeInputs.size(); i++) {
				if(inputRecipeInputs.get(i) != null) {
					for(int j=0; j<inputRecipeInputs.get(i).length; j++) {
						if(testForText(inputRecipeInputs.get(i)[j]) && !inputRecipeInputs.get(i)[j].contentEquals("RAW_MAT")) {
							
							THREADS_DONE = false;
							
							result.add(inputRecipeInputs.get(i)[j]);
							System.out.println(result.get(result.size()-1));
						}
					}
				}
			}
		}
		return result;
	}
	
	public static Item[] layer(List<String> input){
		Item[] result = new Item[input.size()];
		for(int i=0; i<input.size(); i++) {
			if(input.get(i) != null) {
				if(!input.get(i).contentEquals("") && !input.get(i).contentEquals("RAW_MAT")) {
					
					if(Calculations.TEST_MODE)
					System.out.println("Find " + input.get(i));
					
					result[i] = RECIPES.findItem(input.get(i));
				}
			}
		}
		return result;
	}
	
	public static boolean testForText(String input){
		boolean isText = true;
		String[] numList = {"0","1","2","3","4","5","6","7","8","9"};
		for(int i=0; i<10; i++) {
			if(input.contains(numList[i])) {
				isText = false;
			}
		}
		return isText;
	}
	
	
}