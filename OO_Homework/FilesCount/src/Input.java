import java.util.Scanner;


public class Input {
	private static String path = null;
	private static String mode = null;
	private static Scanner sc = new Scanner(System.in);
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return the String path
	public static String getPath() {
		return path;
	}

	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return the String mode
	public static String getMode() {
		return mode;
	}

	//REQUIRES: System.in
	//MODIFIES: none
	//EFFECTS: remember the input strings
	public static void InputString(){
		try{
			System.out.print("Input a valid path please:");
			path = sc.nextLine();
			System.out.print("Input a mode that you wish to sort the files(within \"name\", \"size\", or \"type\"):");
			mode = sc.nextLine(); 
			if( !mode.equals("name") && !mode.equals("size") && !mode.equals("type")){
				System.out.println("The sort-MODE you input is incorrect! \nTry again please!");
				System.exit(1);
			}
		}catch(Exception e){
			System.out.println("Invalid input!");
			System.exit(1);
		}
	}
		
}

















