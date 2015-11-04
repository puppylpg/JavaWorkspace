package Utility;

//Overview: the receive machine extends from machine
public class Receiver extends Machine{
	private boolean ability;
	
	public Receiver(){
		super.setFunction("Receiver");
	}
	
	// repOK: same as its parent class.
	
	/**
	 * @return the ability
	 */
	public boolean isAbility() {
		return ability;
	}
	/**
	 * @param ability the ability to set
	 */
	public void setAbility(boolean ability) {
		this.ability = ability;
	}
	
	
	
}
