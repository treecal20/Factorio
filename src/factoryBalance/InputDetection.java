package factoryBalance;

public class InputDetection {
	
	public static void main(String[] args) {
		Calculations.calculate();
	}
	
	public static String[][][] recieveInput(){
		System.out.println("Recipe?");
		String input = Calculations.SCAN_INPUT.nextLine();
		return combineRecipes(input);
	}
	
	public static String[][][] combineRecipes(String input){
		String[][][] result = RecipeSelect.recipeSelection(input);
		System.out.println("----------------------------------------------------------------------------------");
		for(int b=0; b<10; b++){
			for(int a=0; a<20; a++){
				for(int i=0; i<20; i++){
					try {
						if(result[b][a][i] != null){
							System.out.println("Result " + result[b][a][i] + " \tRow " + i + " \tColumn " + a + " \tSheet " + b);
						}
					} catch (Exception e) {}
				}
				try {
					if(result[b][a][0] != null){
						System.out.println("----------------------------------------------------------------------------------");
					}
				} catch (Exception e) {}
			}
			try {
				if(result[b][0][0] != null){
					System.out.println("----------------------------------------------------------------------------------");
				}
			} catch (Exception e) {}
		}
		return result;
	}
	
	public static int[] findByName(String[][][] input, String goal){
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
