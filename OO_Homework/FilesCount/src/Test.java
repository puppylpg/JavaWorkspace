
public class Test {

	//REQUIRES: System.in
	//MODIFIES: sd
	//EFFECTS: come into this project and run step by step	
	public static void main(String[] args) {			
		// TODO Auto-generated method stub
		try{
			Input.InputString();
			Directory dir = new Directory();
			ScanDirectory sd = new ScanDirectory(Input.getPath(), dir);
			new Thread(sd).start();
		}catch(Exception e){
			System.out.println("Error! Please input again!!!");
		}
	}

}
