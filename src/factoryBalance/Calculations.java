package factoryBalance;

import java.util.Scanner;

public class Calculations {
	public static boolean TEST_MODE = false;
	public static Scanner SCAN_INPUT = new Scanner(System.in);
	
	public static void main(String[] args) {
		calculate();
	}
	
	public static void calculate() {
		System.out.println("Goal?");
		String input = SCAN_INPUT.nextLine();
		double goal = Double.parseDouble(input);
		String[][][] recipeImport = InputDetection.recieveInput();
		String[][][] result = calcLayers(recipeImport, goal);
		System.out.println();
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
	}
	
	public static String[][][] calcLayers(String[][][] input, double startGoal) {
		String[][][] result = input;
		double[] goalFirst = {startGoal};
		for(int i=0; i<result.length; i++) {
			System.out.println();
			if(i==0) {
				result[i] = calculateLayer(result[i], goalFirst);
			} else {
				result[i] = calculateLayer(result[i], findCalcGoals(result[i-1]));
			}
		}
		return result;
	}
	
	public static String[][] calculateLayer(String[][] layer, double[] goal) {
		String[][] result = layer;
		int goalState = 0;
		if(goal != null) {
			for(int i = 0; i < layer.length; i++) {
				boolean isRAW = false;
				try {
					if(layer[i][1].contentEquals("RAW MAT")) {
						isRAW = true;
					}
				} catch(Exception e) {isRAW = true;}
				if(!isRAW) {
					double output = Double.parseDouble(layer[i][3]), time = Double.parseDouble(layer[i][4]);
					double outPerSec = output / time;
					
					int inputCount = Integer.parseInt(layer[i][2]);
					int[] inputs = new int[inputCount];
					double[] inPerSec = new double[inputCount];
					for (int j = 0; j < inputs.length; j++) {
						inputs[j] = Integer.parseInt(layer[i][6 + (j * 2)]);
						inPerSec[j] = inputs[j] / time;
					}
					
					double numOfMachines = goal[goalState] / outPerSec;
					result[i][1] = numOfMachines + "";
					double[] numOfInputs = new double[inputCount];
					for (int j = 0; j < inputs.length; j++) {
						numOfInputs[j] = inPerSec[j] * numOfMachines;
						result[i][6 + (j * 2)] = numOfInputs[j] + "";
					}
					if(TEST_MODE) {
						for(int j = 0; j < result[i].length; j++) {
							try {
								if(result[i][j] != null) {
									System.out.println(result[i][j]);
								}
							} catch(Exception e) {}
						}
						System.out.println("-");
					}
				}
				goalState++;
			} 
		}
		return result;
	}
	
	public static double[] findCalcGoals(String[][] layer) {
		int max = 0;
		for(int i=0; i<layer.length; i++) {
			try {
				if(layer[i] != null && !layer[i][1].contentEquals("RAW MAT")) {
					max += Integer.parseInt(layer[i][2]);
				}
			} catch (Exception e) {}
		}
		if(TEST_MODE)
		System.out.println(max);
		double[] result = new double[max];
		int a=0;
		for(int i=0; i<layer.length; i++) {
			try {
				if(layer[i][0] != null && !layer[i][1].contentEquals("RAW MAT")) {
					for (int j = 0; j < Integer.parseInt(layer[i][2]); j++) {
						if(layer[i][6 + j * 2] != null) {
							result[a] = Double.parseDouble(layer[i][6 + j * 2]);
							a++;
						}
					} 
				}
			} catch (Exception e) {}
		}
		return result;
	}
}
