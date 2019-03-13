package factoryBalance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class VanillaRecipes extends RecipeBook{
	
	private static final String[][] ITEM_TYPES = {{"Storage","Belt_Transport_System","Inserters","Energy_And_Pipe_Distribution","Transport","Logistic_Network","Circuit_Network","Terrain"},
												  {"Tools","Electricity","Resource_Extraction","Furnaces","Production","Modules"},
												  {"Resources_And_Fluids","Materials","Crafting_Components","Science_Packs"},
												  {"Weapons","Ammo","Capsules","Armor","Armor_Modules","Defense"}};
	private static final String[] TYPE_CATEGORIES = {"Logistics","Production","Intermediate_Products","Combat"};
	
	public static final String BOOK_NAME = "Vanilla";
	
	public VanillaRecipes() {
		super(BOOK_NAME,TYPE_CATEGORIES,ITEM_TYPES);
	}
}
