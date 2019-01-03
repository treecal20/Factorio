package factoryBalance;

public class RecipeSelect {
	public static String[][][] RECIPE_IMPORT = RecipeLists.RecipeList();
	public static boolean THREADS_DONE = true;
	
	public static void main(String[] args) {
		Calculations.Calculations();
	}
	
	public static String[][][] RecipeSelection(String input){
		String[][][] Result = Decode(RECIPE_IMPORT, input);
		return Result;
	}
	
	public static String[][][] CT3D(String[][][] list, String[] input, int column, int sheet){
		String[][][] result = list;
		int row = 0;
		while(row<input.length){
			result[column][row][sheet] = input[row];
			/*try {
				if(!result[column][row][sheet].contentEquals("")){
					System.out.println("Result " + result[column][row][sheet]);
					}
			} catch (NullPointerException e) {
			}*/
			row++;
		}
		return result;
	}
	
	public static String[][][] Decode(String[][][] input, String goal){
		int i = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		int Sheet = 0;
		int Column = 0;
		String Find = "";
		String[][][] recipes = new String[10][20][10];
		String[][][] result = null;
		String[] tempRecipe = null;
		String[] tempRecipe2 = null;
		/*tempRecipe = FindRecipe(input,goal);
		//	Main sheet 0	column 0
		recipes = CT3D(recipes,tempRecipe,0,0);
		//Sheet++;
		while(b<10){
			if(TestForText(recipes[0][b][0])==true){
				try {
					if(recipes[0][b][0].contentEquals("Need Inputs")){
						System.out.println("Need Inputs");
						
					} else {
						Find = recipes[0][b][0];
						System.out.println("Find " + Find);
						tempRecipe2 = FindRecipe(RECIPE_IMPORT,Find);
						recipes = CT3D(recipes,tempRecipe2,Column,Sheet);
						Sheet++;
					}
				} catch (NullPointerException e) {}
				
			}
			b++;
		}
		//result = Recipes;
		//result = RecursiveLayers(goal);*/
		return RecursiveLayers(goal);
	}
	
	public static String[][][] RecursiveLayers(String input) {
		String[][][] allLayers = new String[10][20][20];
		String[] goal = new String[10];
		int i = 0;
		do {
			if(i == 0) {
				goal[0] = input;
				allLayers[0] = Layer(goal);
				if(!CheckThread(allLayers[0])) {
					THREADS_DONE = true;
				} else {
					THREADS_DONE = false;
				}
			} else {
				THREADS_DONE = true;
				goal = FindGoals(allLayers[i-1]);
				allLayers[i] = Layer(goal);
			}
			i++;
		} while(THREADS_DONE == false);
		return allLayers;
	}
	
	public static boolean CheckThread(String[][] input) {
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
	
	public static String[] FindGoals(String[][] input) {
		String[] result = new String[30];
		int a=0;
		try {
			for(int i=0; i<input.length; i++) {
				for(int j=1; j<input[i].length; j++) {
					if(TestForText(input[i][j]) && !input[i][j].contentEquals("RAW MAT")) {
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
	
	public static String[][] Layer(String[] input){
		String[][] result = new String[input.length][];
		for(int i=0; i<input.length; i++) {
			try {
				if(!input[i].contentEquals("") && !input[i].contentEquals("RAW MAT")) {
					System.out.println("Find " + input[i]);
					result[i] = FindRecipe(RECIPE_IMPORT, input[i]);
				}
			} catch(NullPointerException e) {}
		}
		return result;
	}
	
	public static String[] FindRecipe(String[][][] input, String goal){
		int i = 0;
		int c = 0;
		int Sheet = 0;
		int Column = 0;
		String[] result = null;
		while(c<10){
			while(i<10){
				try {
					if(input[c][i][0].contentEquals(goal)){
						System.out.println("Goal Name Found");
						String[] Temp = SpliceCode(input[c][i][1]);
						Column = Integer.parseInt(Temp[0]);
						//System.out.println(Column);
						Sheet = Integer.parseInt(Temp[1]);
						//System.out.println(Sheet);
						String[] tempRecipe = GetRecipe(input,Column,Sheet,goal);
						result = tempRecipe;
					}
				} catch (NullPointerException e) {
					//System.out.println("Null");
				}
				i++;
			}
			i=0;
			c++;
		}
		return result;
	}
	
	public static boolean TestForText(String input){
		boolean IsText = false;
		double IsNum = 0;
		try {
			IsNum = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			IsText = true;
		} catch (NullPointerException e) {
			
		}
		return IsText;
	}
	
	public static String[] SpliceCode(String Input){
		int i = 0;
		int b = 0;
		int length = Input.length();
		char temp;
		String ati = null;
		String[] spliced = new String[length];
		while(i<length){
			temp = Input.charAt(i);
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
	
	public static String[] GetRecipe(String[][][] spliced, int column, int sheet, String name){
		int i = 1;
		String[] Recipe = new String[20];
		Recipe[0] = name;
		while(i<20){
			Recipe[i] = spliced[column][i-1][sheet];
			i++;
		}
		return Recipe;
	}
	
	
}