package factoryBalance;

import java.util.Scanner;

public class Calculations {
	
	static int NumOfMachines = 5;
	static double[][][] Result = new double[10][20][10];
	static double[][][] Ratio = new double[10][10][10];
	static double[][][] Ratio2 = new double[10][1][10];
	static double MainRatio = 1;
	static double Goal = 1;
	static String Input;
	static Scanner scanInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		Calculations();
	}
	
	public static void Calculations() {
		System.out.println("Goal?");
		Input = scanInput.nextLine();
		Goal = Double.parseDouble(Input);
		String[][][] RecipeImport = InputDetection.RecieveInput();
		double[] divisors = new double[10], multiply = new double[10];
		double[] numOfInports = new double[10];
		double temp[] = new double[2];
		String testForNull = "1";
		int s=0, c=0, a=0, r=0, b=0;
		try {
			numOfInports[0] = Double.parseDouble(RecipeImport[0][1][0]);
			multiply[0] = Double.parseDouble(RecipeImport[0][2][0]);
			divisors[0] = Double.parseDouble(RecipeImport[0][3][0]);
		} catch (Exception e) {}
		Ratio[0][0][0] = (Double.parseDouble(RecipeImport[0][2][0]) / divisors[0]) * multiply[0];
		a++;
		while(a<numOfInports[0]+1) {
			if((r-3)>0 && (r-3)%2==0) {
				Ratio[0][a][0] = (Double.parseDouble(RecipeImport[0][r][0]) / divisors[0]) * multiply[0];
				System.out.println("Ratio 1 " + Ratio[0][a][0] + " Row " + a + " Goal " + (1 / Ratio[0][a][0]));
				multiply[a] = (1 / Ratio[0][a][0]);
				while(b<numOfInports[1]+1) {
					b++;
				}
				a++;
			}
			r++;
		}
		/*while(s<10){
			while(c<10){
				try {
					temp[0] = Double.parseDouble(RecipeImport[c][1][s]);
					temp[1] = Double.parseDouble(RecipeImport[c][2][s]);
					Ratio[a][0][s] = Ratios(temp);
					System.out.println("Ratio 1 " + Ratio[a][0][s] + "   Column " + a + " Sheet " + s);
					a++;
				} catch (Exception e) {}
				c++;
			}
			c=0;
			a=0;
			s++;
		}
		s=0;
		c=0;
		a=0;
		
		while(s<10){
			while(c<10){
				try {
					testForNull = String.valueOf(Ratio[c][0][s]);
					Ratio2[a][0][s] = Multiplyers(Ratio[c][0][s], Ratio[c][0][s]);
					if(Ratio2[a][0][s]!=0){
						System.out.println("Ratio 2 " + Ratio2[a][0][s]);
						Ratio2[a][0][s] = Ratio2[a][0][s] * Goal;
						System.out.println("Goal # of Machines " + Ratio2[a][0][s]);
					}
					a++;
				} catch (NullPointerException e) {}
				c++;
			}
			c=0;
			a=0;
			s++;
		}*/
		Result = Balance(Goal, Ratio2);
		/*for(int i=0; i<Result.length;i++){
			System.out.println(Result[0][i][0]);
		}*/
	}
	
	public static double Ratios(double[] input){
		double result = input[0] / input [1];
		return result;
	}
	
	public static double Multiplyers(double incoming, double add){
		int i = 0;
		double result = 0;
		if(incoming!=0){
			result = 1;
		}
		if(incoming%1==0) {
			return incoming;
		}
		while(i == 0){
			if(incoming%1==0){
				i = 1;
			} else {
				incoming = incoming + add;
				result++;
			}
		}
		return result;
	}
	
	public static double[][][] Balance(double Goal, double[][][] ratio2){
		double[][][] result = new double[10][20][10];
		double[] multiplyer = new double[20];
		int i = 0;
		while(i<(result.length)){
			multiplyer[i] = 1.0 / ratio2[i][0][1];
			i++;
		}
		i = 0;
		while(i<(result.length)){
			result[0][i][0] = multiplyer[i] * Goal;
			i++;
		}
		return result;
	}
	
	public static double[] doubleFix(double[] input){
		int i = 0;
		double[] result = new double[input.length];
		while(i < input.length-1){
			if(input[i]%1 == 0){
				result[i] = input[i];
			} else {
				result[i] = Math.nextDown(input[i]);
			}
			i++;
		}
		return result;
	}
}