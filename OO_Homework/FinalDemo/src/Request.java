import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Request{
	private String inOut;
	private int toFloor;
	private int onFloor;
	private String upDown;
	private int e_ID = 0;
	private boolean leave;
	private boolean join;
	private long reqTime;
	private boolean valid;
	
	private static Scanner sc = new Scanner(System.in);
	
	public Request cloneAnObj(){
		Request tmp = new Request();
		tmp.inOut = this.inOut;
		tmp.toFloor = this.toFloor;
		tmp.onFloor = this.onFloor;
		tmp.upDown = this.upDown;
		tmp.e_ID = this.e_ID;
		tmp.leave = this.leave;
		tmp.join = this.join;
		tmp.reqTime = this.reqTime;
		tmp.valid = this.valid;
		return tmp;
	}
	
//	public Object clone(){
//		Request o = null;
//		try{
//			o = (Request)super.clone();
//		　　}catch(CloneNotSupportedException e){
//		　　　e.printStackTrace();
//		　　}
//		　　return o;
//		　}
	
	public String getInOut(){
		return this.inOut;
	}
	
	public int getToFloor(){
		return this.toFloor;
	}
	
	public int getOnFloor(){
		return this.onFloor;
	}
	
	public String getUpDown(){
		return this.upDown;
	}
	
	public int gete_ID(){
		return this.e_ID;
	}
	
	public boolean getLeave(){
		return this.leave;
	}
	
	public boolean getJoin(){
		return this.join;
	}
	
	public long getReqTime(){
		return this.reqTime;
	}
	
	public boolean getValid(){
		return this.valid;
	}
	
	public void setValid(boolean valid){
		this.valid = valid;
	}
	
	public boolean readRequest(){
	//	sc = new Scanner(System.in);
		String str = null;
		
		String regex1 = "\\((F_R),([1-9]|10),(UP|DOWN),(0|[1-9]\\d{0,9})\\)";
		String regex2 = "\\((E_R),([1-9]|10),(1|2|3),(0|[1-9]\\d{0,9})\\)";
		//(leave, e_ID, t), (join, e_ID, t)
		String regex3 = "\\((leave),(1|2|3),(0|[1-9]\\d{0,9})\\)";
		String regex4 = "\\((join),(1|2|3),(0|[1-9]\\d{0,9})\\)";
		
		Pattern p1 = Pattern.compile(regex1);
		Pattern p2 = Pattern.compile(regex2);
		Pattern p3 = Pattern.compile(regex3);
		Pattern p4 = Pattern.compile(regex4);
		
		try{
			str = sc.nextLine();
		}catch(Exception e){
			System.out.println("Input error!");
			System.exit(1);
		}
		while(!str.equals("$")){
			if( !(str.matches(regex1) || str.matches(regex2) 
					|| str.matches(regex3) || str.matches(regex4) ) ){
				System.out.println("Illegal input!");
				System.exit(1);
			}
			else{
				Matcher m1 = p1.matcher(str);
				Matcher m2 = p2.matcher(str);
				Matcher m3 = p3.matcher(str);
				Matcher m4 = p4.matcher(str);
				if(m1.find()){					//floor instruction
					this.inOut = m1.group(1);
					this.onFloor = Integer.parseInt(m1.group(2));
					this.upDown = m1.group(3);
					this.reqTime = Long.parseLong(m1.group(4));
					Timer.setExitTime((int)(reqTime * 2));
					this.e_ID = 0;				//0 means it's floor, not an elevator
					this.leave = false;
					this.join = false;
					this.valid = true;
					return true;
				}
				else if(m2.find()){				//elevator instruction
					this.inOut = m2.group(1);
					this.toFloor = Integer.parseInt(m2.group(2));
					this.upDown = null;
					this.e_ID = Integer.parseInt(m2.group(3));
					this.reqTime = Long.parseLong(m2.group(4));
					Timer.setExitTime((int)(reqTime * 2));
					this.leave = false;
					this.join = false;
					this.valid = true;
					return true;
				}
				else if(m3.find()){			//leave instruction
					this.inOut = null;
					this.valid = true;
					this.leave = true;
					this.join = false;
					this.e_ID = Integer.parseInt(m3.group(2));
					this.reqTime = Long.parseLong(m3.group(3));
					Timer.setExitTime((int)(reqTime * 2));
					return true;
				}
				else if(m4.find()){			//join instruction
					this.inOut = null;
					this.valid = true;
					this.leave = false;
					this.join = true;
					this.e_ID = Integer.parseInt(m4.group(2));
					this.reqTime = Long.parseLong(m4.group(3));
					Timer.setExitTime((int)(reqTime * 2));
					return true;
				}
				return false;
			}
		}
		return false;
	}

}