package factoryBalance;


import java.util.Scanner;

public class InputDetection {
	static String Input;
	static Scanner scanInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		Calculations.Calculations();
	}
	
	public static String[][][] RecieveInput(){
		System.out.println("Recipe?");
		Input = scanInput.nextLine();
		return DetectInput(Input);
	}
	
	public static String[][][] DetectInput(String input){
		return CombineRecipes(input);
	}
	
	public static String[][][] CombineRecipes(String input){
		int i = 1;
		int a = 1;
		int b = 1;
		String[][][] Result = new String[10][100][5];
		String[][][] RecipeSel = null;
		RecipeSel = RecipeSelect.RecipeSelection(input);
		Result = RecipeSel;
		i=0;
		a=0;
		b=0;
		while(b<2){
			while(a<10){
				while(i<20){
					try {
						if(!Result[a][i][b].contentEquals("")){
							System.out.println("Result " + Result[a][i][b] + "   Column " + a + " Row " + i + " Sheet " + b);
							}
					} catch (NullPointerException e) {
						//System.out.println("Null " + a + " " + i + " " + b);
					}
					i++;
				}
				i=0;
				a++;
			}
			a=0;
			b++;
		}
		return Result;
	}
	
	public static int[] FindByName(String[][][] input, String goal){
		int c = 0;
		int s = 1;
		boolean foundGoal = false;
		int[] result = new int[2];
		while(s<10&&foundGoal==false){
			while(c<10&&foundGoal==false){
				try {
					if(input[c][0][s].contentEquals(goal)){
						result[0] = c;
						result[1] = s;
						foundGoal = true;
					}
				} catch (NullPointerException e) {
				}
				c++;
			}
			c=0;
			s++;
		}
		return result;
	}
	
	public static String[][] FindInputs(String[][][] input, int column, int sheet){
		int i = 0;
		int a = 0;
		String[][] Inputs = new String[10][2];
		while(i<20||a<10){
			if(TestForText(input[column][i][sheet])==true){
				Inputs[a][0] = input[column][i][sheet];
				try {
					Inputs[a][1] = input[column][i+1][sheet];
				} catch (NullPointerException e) {
				}
				a++;
			}
			i++;
		}
		return Inputs;
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
}












//