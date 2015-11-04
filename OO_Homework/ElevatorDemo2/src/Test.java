
public class Test {
	ALSManager alsmanager = new ALSManager();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test();
		try{
			test.alsmanager.order();
		}catch(Exception e){
			System.out.println("Error");
		}
	}

}
