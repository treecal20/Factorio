package factoryBalance;

public class RecipeSelect {
	public static String[][][] RecipeSelection(String input){
		String[][][] RecipeImport = RecipeLists.RecipeList();
		String[][][] Result = Decode(RecipeImport, input);
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
		String[][][] Recipes = new String[10][20][10];
		String[][][] Result = null;
		String[] tempRecipe = null;
		String[] tempRecipe2 = null;
		tempRecipe = FindRecipe(input,goal);
		//	Main sheet 0	column 0
		Recipes = CT3D(Recipes,tempRecipe,0,0);
		//Sheet++;
		while(b<10){
			if(TestForText(Recipes[0][b][0])==true){
				try {
					if(Recipes[0][b][0].contentEquals("Need Inputs")){
						System.out.println("Need Inputs");
						
					} else {
						Find = Recipes[0][b][0];
						System.out.println("Find " + Find);
						tempRecipe2 = FindRecipe(input,Find);
						Recipes = CT3D(Recipes,tempRecipe2,Column,Sheet);
						Column++;
					}
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
			}
			b++;
		}
		Result = Recipes;
		return Result;
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
		double IsInt = 0;
		try {
			IsInt = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			IsText = true;
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