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
		printRecipes(input);
		return RecipeSelect.recursiveLayers(input);
	}
	
	public static void printRecipes(String input){
		List<Item[]> result = RecipeSelect.recursiveLayers(input);
		if (result != null) {
			System.out.println("----------------------------------------------------------------------------------");
			for (int b = 0; b < result.size(); b++) {
				if (result.get(b) != null) {
					for (int a = 0; a < result.get(b).length; a++) {
						if (result.get(b)[a] != null) {
							System.out.println(result.get(b)[a].toString());
						}
					} 
				}
				if (result.get(b) != null) {
					System.out.println("----------------------------------------------------------------------------------");
				}
			}
		}
	}
	
	public static int[] findByName(String[][][] input, String goal){
		int c = 0;
		int s = 1;
		boolean foundGoal = false;
		int[] result = new int[2];
		while(s<10&&foundGoal==false){
			while(c<10&&foundGoal==false){
				if(input[c][0][s].contentEquals(goal)){
					result[0] = c;
					result[1] = s;
					foundGoal = true;
				}
				c++;
			}
			c=0;
			s++;
		}
		return result;
	}
	
	public static String[][] findInputs(String[][][] input, int column, int sheet){
		int i = 0;
		int a = 0;
		String[][] inputs = new String[10][2];
		while(i<20||a<10){
			if(testForText(input[column][i][sheet])==true){
				inputs[a][0] = input[column][i][sheet];
				try {
					inputs[a][1] = input[column][i+1][sheet];
				} catch (NullPointerException e) {
				}
				a++;
			}
			i++;
		}
		return inputs;
	}
	
	public static boolean testForText(String input){
		boolean isText = false;
		double isNum = 0;
		try {
			isNum = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			isText = true;
		}
		return isText;
	}
}
