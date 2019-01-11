package factoryBalance;

public class RecipeSelect {
	public static String[][][] RECIPE_IMPORT = RecipeLists.RecipeList();
	public static boolean THREADS_DONE = true;
	
	public static void main(String[] args) {
		Calculations.calculate();
	}
	
	public static String[][][] recipeSelection(String input){
		String[][][] result = decode(RECIPE_IMPORT, input);
		return result;
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
	
	public static String[][][] decode(String[][][] input, String goal){
		return recursiveLayers(goal);
	}
	
	public static String[][][] recursiveLayers(String input) {
		String[][][] allLayers = new String[10][20][20];
		String[] goal = new String[10];
		int i = 0;
		do {
			if(i == 0) {
				goal[0] = input;
				allLayers[0] = layer(goal);
				if(!checkThread(allLayers[0])) {
					THREADS_DONE = true;
				} else {
					THREADS_DONE = false;
				}
			} else {
				THREADS_DONE = true;
				goal = findGoals(allLayers[i-1]);
				allLayers[i] = layer(goal);
			}
			i++;
		} while(THREADS_DONE == false);
		return allLayers;
	}
	
	public static boolean checkThread(String[][] input) {
		boolean result = false;
		try {
			for(int i=0; i<input.length; i++) {
				for(int j=0; j<input[i].length; j++) {
					if(!input[i][j].contentEquals("RAW MAT")) {
						result = true;
					}
				}
			}
		} catch (NullPointerException e) {}
		return result;
	}
	
	public static String[] findGoals(String[][] input) {
		String[] result = new String[30];
		int a=0;
		try {
			for(int i=0; i<input.length; i++) {
				for(int j=1; j<input[i].length; j++) {
					if(testForText(input[i][j]) && !input[i][j].contentEquals("RAW MAT")) {
						THREADS_DONE = false;
						result[a] = input[i][j];
						System.out.println(result[a]);
						a++;
					}
				}
			}
		} catch (NullPointerException e) {}
		return result;
	}
	
	public static String[][] layer(String[] input){
		String[][] result = new String[input.length][];
		for(int i=0; i<input.length; i++) {
			try {
				if(!input[i].contentEquals("") && !input[i].contentEquals("RAW MAT")) {
					if(Calculations.TEST_MODE)
					System.out.println("Find " + input[i]);
					result[i] = findRecipe(RECIPE_IMPORT, input[i]);
				}
			} catch(NullPointerException e) {}
		}
		return result;
	}
	
	public static String[] findRecipe(String[][][] input, String goal){
		int i = 0;
		int c = 0;
		int sheet = 0;
		int column = 0;
		String[] result = null;
		while(c<10){
			while(i<10){
				try {
					if(input[c][i][0].contentEquals(goal)){
						if(Calculations.TEST_MODE)
						System.out.println("Goal Name Found");
						String[] Temp = spliceCode(input[c][i][1]);
						column = Integer.parseInt(Temp[0]);
						if(Calculations.TEST_MODE)
						System.out.println(column);
						sheet = Integer.parseInt(Temp[1]);
						if(Calculations.TEST_MODE)
						System.out.println(sheet);
						String[] tempRecipe = getRecipe(input,column,sheet,goal);
						result = tempRecipe;
					}
				} catch (NullPointerException e) {}
				i++;
			}
			i=0;
			c++;
		}
		return result;
	}
	
	public static boolean testForText(String input){
		boolean isText = false;
		double isNum = 0;
		try {
			isNum = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			isText = true;
		} catch (NullPointerException e) {}
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