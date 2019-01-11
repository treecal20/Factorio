package factoryBalance;

public class RecipeLists {
	/*	Codes:
	 * ? - Name
	 * !(Amount) - Time
	 * $(Amount) - Output
	 * :(Amount) - Input Name/Code
	 * ; - End code type and information
	 * . - End all
	 * NAI - Needs an input
	 * NIR - No input required
	*/
	public static String[][][] CT3D(String[][][] list, String[] input, int column, int sheet){
		String[][][] result = list;
		int row = 0;
		while(row<input.length){
			result[column][row][sheet] = input[row];
			row++;
		}
		return result;
	}
	public static String[][][] RecipeList(){
		String[][][] Small = new String[10][20][10];
		//	Name: Codes		Sheet: 0-1
		Small[0][0][0]="Need Inputs";/**/Small[0][1][0]="No Input Needed";	/**/Small[0][2][0]="RAW MAT";
		Small[0][0][1]="0 2";        /**/Small[0][1][1]="1 2";				/**/Small[0][2][1]="2 2";
		//		Science			 Inserters and Belts			Electronics				Oil/Liquids				Raw Inputs				Components
		Small[1][0][0]="SPR";/**/Small[2][0][0]="II1";/**/Small[3][0][0]="EB1";/**/Small[4][0][0]="OB1";/**/Small[5][0][0]="RIP";/**/Small[6][0][0]="CG";/**/
		Small[1][0][1]="0 3";/**/Small[2][0][1]="0 4";/**/Small[3][0][1]="0 5";/**/Small[4][0][1]="0 6";/**/Small[5][0][1]="0 7";/**/Small[6][0][1]="0 8";
		Small[1][1][0]="SPG";/**/Small[2][1][0]="II2";/**/Small[3][1][0]="EB2";/**/Small[4][1][0]="OP"; /**/Small[5][1][0]="RCP";
		Small[1][1][1]="1 3";/**/Small[2][1][1]="1 4";/**/Small[3][1][1]="1 5";/**/Small[4][1][1]="2 6";/**/Small[5][1][1]="1 7";
		Small[1][2][0]="SPB";/**/Small[2][2][0]="II3";/**/Small[3][2][0]="EB3";/**//*                 *//**/
		Small[1][2][1]="2 3";/**/Small[2][2][1]="2 4";/**/Small[3][2][1]="2 5";/**//*                 *//**/
		/*                 *//**/Small[2][3][0]="II4";/**/Small[3][3][0]="ECW";
		/*                 *//**/Small[2][3][1]="3 4";/**/Small[3][3][1]="3 5";
		/*                 *//**/Small[2][4][0]="II5";
		/*                 *//**/Small[2][4][1]="4 4";
		/*                 *//**/Small[2][5][0]="II6";
		/*                 *//**/Small[2][5][1]="5 4";
		/*                 *//**/Small[2][6][0]="IB1";
		/*                 *//**/Small[2][6][1]="6 4";
		/*                 *//**/Small[2][7][0]="IB2";
		/*                 *//**/Small[2][7][1]="7 4";
		
		
	//	Type: Special				Code: 		Sheet: 2
		//	Type: Need Inputs
		String[] NeedInputs = {"1","1","No Input Needed"};
		//	Type: No Input Needed
		String[] NoInputNeeded = {"1","1"};
		
		//	Type: 
		
		
	//	Type: Science				Code: S		Sheet: 3
		//	Type: Red Science Pack		Code: SPR
		String[] SPR = {"1","2","1","5",/**/"CG","1",/**/"RCP","1"};
		Small=CT3D(Small,SPR,0,3);
		//	Type: Green Science Pack	Code: SPG
		String[] SPG = {"1","2","1","6",/**/"II1","1",/**/"IB1","1"};
		Small=CT3D(Small,SPG,1,3);
		//	Type: Blue Science Pack		Code: SPB
		String[] SPB = {"1","4","1","12",/**/"AAA","1",/**/"EB3","1",/**/"OB1","1",/**/"OP","1"};
		Small=CT3D(Small,SPB,2,3);
		
		
	//	Type: Inserters and Belts		Code: I		Sheet: 4
		//	Type: Inserter				Code: IY1
		String[] II1 = {"1","3","1","0.5",/**/"EB1","1",/**/"CG","1",/**/"RIP","1"};
		Small=CT3D(Small,II1,0,4);
		//	Type: Fast Inserter			Code: IB1
		String[] II2 = {"1","3","1","0.5",/**/"EB1","2",/**/"II1","1",/**/"RIP","2"};
		Small=CT3D(Small,II2,1,4);
		//	Type: Filter Inserter		Code: IF1
		String[] II3 = {"1","1","1","0.5",/**/"Need Inputs"};
		Small=CT3D(Small,II3,2,4);
		//	Type: Inserter				Code: IY1
		String[] II4 = {"1","3","1","0.5",/**/"EB1","1",/**/"CG","1",/**/"RIP","1"};
		Small=CT3D(Small,II4,3,4);
		//	Type: Fast Inserter			Code: IB1
		String[] II5 = {"1","3","1","0.5",/**/"EB1","2",/**/"II1","1",/**/"RIP","2"};
		Small=CT3D(Small,II5,4,4);
		//	Type: Filter Inserter		Code: IF1
		String[] II6 = {"1","1","1","0.5",/**/"Need Inputs"};
		Small=CT3D(Small,II6,5,4);
		//	Type: Belt 1			Code: IB1
		String[] IB1 = {"1","2","2","0.5",/**/"CG","1",/**/"RIP","1"};
		Small=CT3D(Small,IB1,6,4);
		//	Type: Belt 2			Code: IB2
		String[] IB2 = {"1","1","1","0.5",/**/"Need Inputs"};
		Small=CT3D(Small,IB2,7,4);
		
		
	//	Type: Electronics				Code: E		Sheet: 5
		//	Type: Board Mk1				Code: EB1
		String[] EB1 = {"1","2","1","0.5",/**/"ECW", "3",/**/"RIP","1"};
		Small=CT3D(Small,EB1,0,5);
		//	Type: Board Mk2				Code: EB2
		
		//	Type: Board Mk3				Code: EB3
		String[] EB3 = {"1","1","1","5",/**/"Need Inputs"};
		Small=CT3D(Small,EB3,2,5);
		//	Type: Copper Wire			Code: ECW
		String[] ECW = {"1","1","2","0.5",/**/"RCP","1"};
		Small=CT3D(Small,ECW,3,5);
		
		
	//	Type: Oil/Liquids				Code: O		Sheet: 6
		//	Type: Battery Mk1			Code: OB1
		String[] OB1 = {"1","1","1","5",/**/"Need Inputs"};
		Small=CT3D(Small,OB1,0,6);
		//	Type: Plastic			Code: OP
		String[] OP = {"1","1","1","1",/**/"Need Inputs"};
		Small=CT3D(Small,OP,2,6);
		
		
	//	Type: Raw Inputs				Code: R		Sheet: 7
		//	Type: Iron Plate			Code: RIP
		String[] RIP = {"RAW MAT"};
		Small=CT3D(Small,RIP,0,7);
		//	Type: Copper Plate			Code: RCP
		String[] RCP = {"RAW MAT"};
		Small=CT3D(Small,RCP,1,7);
		
		
		
	//	Type: Components				Code: C		Sheet: 8
		//	Type: Gear					Code: CG
		String[] CG = {"1","1","1","0.5",/**/"RIP","2"};
		Small=CT3D(Small,CG,0,8);
		return Small;
	}

	/*"?SPB!(1)$(12):(1)IF1;:(1)EB3;:(1)OB1;:(1)OP;.",
	"?IF1!(1)$(0.5):(NAI)NAI;.",
	"?EB3!(1)$(5):(NAI)NAI;.",
	"?OB1!(1)$(5):(NAI)NAI;.",
	"?OP!(1)$(1):(NAI)NAI;.";*/
	public static void main(String[] args) {
		Calculations.calculate();
	}
}
