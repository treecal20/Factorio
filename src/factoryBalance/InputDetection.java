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
		return CombineRecipes(Input);
	}
	
	public static String[][][] CombineRecipes(String input){
		String[][][] Result = RecipeSelect.RecipeSelection(input);
		System.out.println("----------------------------------------------------------------------------------");
		for(int b=0; b<10; b++){
			for(int a=0; a<20; a++){
				for(int i=0; i<20; i++){
					try {
						if(Result[b][a][i] != null){
							System.out.println("Result " + Result[b][a][i] + " \tRow " + i + " \tColumn " + a + " \tSheet " + b);
						}
					} catch (Exception e) {}
				}
				try {
					if(Result[b][a][0] != null){
						System.out.println("----------------------------------------------------------------------------------");
					}
				} catch (Exception e) {}
			}
			try {
				if(Result[b][0][0] != null){
					System.out.println("----------------------------------------------------------------------------------");
				}
			} catch (Exception e) {}
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