package factoryBalance;

public class Machine {
	
	private String name;
	
	private double craftingSpeed;
	
	public Machine(String machineName, double machineCraftingSpeed) {
		this.name = machineName;
		this.craftingSpeed = machineCraftingSpeed;
	}
	
	public String name() {
		return this.name;
	}
	
	public double craftingSpeed() {
		return this.craftingSpeed;
	}
}
