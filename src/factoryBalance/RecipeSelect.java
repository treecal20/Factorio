package factoryBalance;

import java.util.List;
import java.util.ArrayList;

public class RecipeSelect {
	public static boolean THREADS_DONE = true;
	public static VanillaRecipes RECIPES = new VanillaRecipes();
	public static List<Item[]> requestedRecipes;
	
	public static void main(String[] args) {
		Calculations.calculate();
	}
	
	public static String[][][] CT3D(String[][][] list, String[] input, int column, int sheet){
		String[][][] result = list;
		int row = 0;
		while(row<input.length){
			result[column][row][sheet] = input[row];
			row++;
		}
		return result;
	}
	
	public static List<Item[]> recursiveLayers(String input) {
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
		return requestedRecipes;
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
		boolean isText = false;
		double isNum = 0;
		if(input != null) {
			try {
				isNum = Double.parseDouble(input);
			} catch (NumberFormatException e) {
				isText = true;
			}
		}
		return isText;
	}
	
	public static String[] spliceCode(String input){
		int i = 0;
		int b = 0;
		int length = input.length();
		char temp;
		String ati = null;
		String[] spliced = new String[length];
		while(i<length){
			temp = input.charAt(i);
			ati = String.valueOf(temp);
			if(ati.contentEquals(" ")){
				b++;
			} else {
				spliced[b] = ati;
			}
			i++;
		}
		return spliced;
	}
	
	public static String[] getRecipe(String[][][] spliced, int column, int sheet, String name){
		int i = 1;
		String[] recipe = new String[20];
		recipe[0] = name;
		while(i<20){
			recipe[i] = spliced[column][i-1][sheet];
			i++;
		}
		return recipe;
	}
	
	
}