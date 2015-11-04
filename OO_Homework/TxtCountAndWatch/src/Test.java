import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class Test {

	static HashMap< String, ArrayList<WordInfo> > hashMap= new HashMap< String, ArrayList<WordInfo> >();
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: start a thread ct to begin and start a thread timer to scan the directories background
	public static void main(String[] args) {
		try{
			// TODO Auto-generated method stub
			CountTxt ct = new CountTxt();
			ct.setHashMap(Test.hashMap);
			Thread t = new Thread(ct);
			t.start();
			
			Timer timer = new Timer();  
	        timer.schedule(new MyTask(), 1000, 5000);		// start after 1s, and every 5s execute once
		}catch(Exception e){
			System.out.println("Error! Please input again.");
		}
	}

}

class MyTask extends TimerTask {  
//Overview: to scan the exact directories to observe the changes in text files
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: override run() in TimerTask
    @Override  
    public void run() {  
    	try{
	        CountTxt ct = new CountTxt();
	        ct.setHashMap(Test.hashMap);
	        if(ct.getPathHasBeenReadFlag() == true){
	        	ct.scanDirectory(ct.getPath());
	        }
    	}catch(Exception e){
    		System.out.println("Error! Please input again.");
    	}
    }  
    
    public boolean repOK(){
    	return true;
    }
}  