package Utility;

//Overview: the send machine extends from machine
public class Sender extends Machine{
	private boolean ability;
	
	public Sender(){
		super.setFunction("Sender");
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
