package factoryBalance;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Calculations {
	public static boolean TEST_MODE = false;
	public static Scanner SCAN_INPUT = new Scanner(System.in);
	
	public static void main(String[] args) {
		calculate();
	}
	
	public static void calculate() {
		System.out.println("Goal Output per Second:");
		String input = SCAN_INPUT.nextLine();
		double goal = Double.parseDouble(input);
		List<Item[]> result = calcLayers(InputDetection.recieveInput(), goal);
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------");
		for(int i=0; i<result.size(); i++){
			for(int j=0; j<result.get(i).length; j++) {
				System.out.println(result.get(i)[j].toString());
			}
			System.out.println("----------------------------------------------------------------------------------");
		}
	}
	
	public static List<Item[]> calcLayers(List<Item[]> input, double startGoal) {
		List<Item[]> result = input;
		double[] goalFirst = {startGoal};
		for(int i=0; i<result.size(); i++) {
			System.out.println();
			if(i==0) {
				result.set(i, calculateLayer(result.get(i), goalFirst));
			} else {
				result.set(i, calculateLayer(result.get(i), findCalcGoals(result.get(i-1))));
			}
		}
		return result;
	}
	
	public static Item[] calculateLayer(Item[] layer, double[] goal) {
		Item[] result = layer;
		int goalState = 0;
		if(goal != null) {
			for(int i = 0; i < layer.length; i++) {
				boolean isRAW = false;
				if(layer[i].inputNames()[0].contentEquals("RAW_MAT")) {
					isRAW = true;
				}
				if(!isRAW) {
					double output = layer[i].output(), time = layer[i].time();
					double outPerSec = output / time;
					
					int inputCount = layer[i].inputSize();
					int[] inputs = layer[i].inputQuantities();
					double[] inPerSec = new double[inputCount];
					for (int j = 0; j < inputs.length; j++) {
						inPerSec[j] = inputs[j] / time;
					}
					
					layer[i].setMachineNumber(goal[goalState] / outPerSec);
					if(TEST_MODE) {
						if(layer.toString() != null) {
							System.out.println(layer.toString());
						}
						System.out.println("-");
					}
				}
				goalState++;
			} 
		}
		return result;
	}
	
	public static double[] findCalcGoals(Item[] layer) {
		int max = 0;
		for(int i=0; i<layer.length; i++) {
			if(layer[i] != null && !layer[i].name().contentEquals("RAW MAT")) {
				max += layer[i].inputSize();
			}
		}
		if(TEST_MODE)
		System.out.println(max);
		double[] result = new double[max];
		int a=0;
		for(int i=0; i<layer.length; i++) {
			try {
				if(layer[i] != null && !layer[i].name().contentEquals("RAW MAT")) {
					for (int j = 0; j < layer[i].inputSize(); j++) {
						if(layer[i].inputNames()[j] != null) {
							result[a] = layer[i].inputQuantities()[j];
							a++;
						}
					} 
				}
			} catch (Exception e) {}
		}
		return result;
	}
}
