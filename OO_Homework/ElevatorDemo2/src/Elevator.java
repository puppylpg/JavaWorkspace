
public class Elevator implements ElevatorMove{
	private int floor;
	private String state;
	private char P_S;					//////////
	private long timeUnit;
	static final long OVERFLOW = 9999999999L;
	
	public Elevator(int floor, long timeUnit, String state, char P_S){
		this.floor = floor;
		this.timeUnit = timeUnit;
		this.state = state;
		this.P_S = P_S;
	}
	
	public int getFloor(){
		return floor;
	}
	
	public String getState(){
		return state;
	}
	
	public char getP_S(){
		return P_S;
	}
	
	public long getTimeUnit(){
		return timeUnit;
	}
	
	public void setFloor(int floor){
		this.floor = floor;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public void setP_S(char P_S){
		this.P_S = P_S;
	}
	
	public void setTimeUnit(long timeUnit){
		this.timeUnit = timeUnit;
	}
	
	public void moveUp(){
		this.floor++;
	}
	
	public void moveDown(){
		this.floor--;
	}
	
	public String toString(){
		return "(" + floor + "," + state + "," + P_S + "," + timeUnit * 0.5 + ")";
	}
	
}
