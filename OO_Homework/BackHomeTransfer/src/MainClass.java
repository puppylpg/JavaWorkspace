import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Utility.BackRoom;
import Utility.Receiver;
import Utility.Sender;

//Overview: the main function is here to start the total project
public class MainClass {

	private static ArrayList<ArrayList <Integer> > path = new ArrayList<ArrayList <Integer> >();
	
	private ReadConfiguration rconfig = new ReadConfiguration();
	private ReadConsole rc = new ReadConsole();
	private FindPath fp = new FindPath();
	
	/*  requires: none
	 *  modifiers: none
	 *  effects: main function where to start the program from 
	 */ 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			MainClass t = new MainClass();
			t.rconfig.init(BackRoom.getSend(), BackRoom.getReceive(), BackRoom.getTrans());
			t.rconfig.readConfigurationFile();
			
			while(true){
				t.rc.readCommand();
				t.fp.init(t.rc.getG(), ReadConfiguration.getTotalMachine(), MainClass.path);
				t.fp.traversePoints();
				
				ReadConfiguration read = new ReadConfiguration();
				Thread thr1 = new Thread(read);
				thr1.start();
				
//		System.out.println("用邻接矩阵表示连通性和网络拓扑结构(67108863即INF，意为两节点之间不可达)：\nThe result of the network(expressd by adjacent matrix): ");
				t.rc.structure();
				
				FindPath find = new FindPath();
				Thread thr2 = new Thread(find);
				thr2.start();
				
				t.output();
				t.rconfig = new ReadConfiguration();
				t.rc = new ReadConsole();
				t.fp = new FindPath();
				BackRoom.init();
				path = new ArrayList<ArrayList <Integer> >();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error! Please restart again!");
			System.exit(1);
		}
	}
	
	/*  requires: none
	 *  modifiers: none
	 *  effects: output the essential information 
	 */ 
	public void output(){
		System.out.print("Sender" + rc.getPoster() + "; Receiver" + rc.getMailbox() + "; " + decode(rc.getMessage()) + "; ");
		this.depath();
	}
	
	/*  requires: none
	 *  modifiers: arraylist repath
	 *  effects:  to traverse the path
	 */ 
	public void depath(){
		int tmpTime = 0;
		int SID = rc.getPoster();
		int RID = rc.getMailbox();
		int s = SID;
		int r = RID + ReadConfiguration.getSender() + ReadConfiguration.getTransfer();
		ArrayList<String> repath = new ArrayList<String>();
		
		int jump = 1;
		for(ArrayList <Integer> p : path){			// find the starting point
			if(jump == 1){
				jump = 0;
				continue;
			}
			if(p.get(s) == s){						// this p stores the path information
				if(p.get(r) == -1){
					System.out.println("The message can't send to a not connected receiver!!! Exit!");
					System.exit(1);
				}
				int pre = p.get(r);			//get pre point's number
				String previous = null;
				
				while(pre != s){
					if(pre > ReadConfiguration.getSender() && pre <= ReadConfiguration.getTransfer() + ReadConfiguration.getSender()){
						previous = "Transfer" + (pre - ReadConfiguration.getSender());
					}
					repath.add(previous);
					if(p.get(r) == -1){
						System.out.println("The message can't send to a not connected receiver!!! Exit!");
						System.exit(1);
					}
					pre = p.get(pre);
				}
			}
		}
		Collections.reverse(repath);
		
		for(String str : repath){
			System.out.print(str + ", ");
			tmpTime++;
		}
		
		System.out.println("Send Time: " + rc.getTime() + ", Receive Time: " + (rc.getTime() + tmpTime + 1) + "." + "\n");
	}
	
	/*  requires: a string message
	 *  modifiers: message
	 *  effects: use Iterator to change the link, if necessary, decode the message 
	 */ 
	public String decode(String s){
		int SID = rc.getPoster();
		int RID = rc.getMailbox();
		boolean SAbility = false;
		boolean RAbility = false;

		
		for(Iterator iter = BackRoom.getSend().iterator(); iter.hasNext(); ){
			Sender sender = (Sender)iter.next();
			if(SID == sender.getID()){
				SAbility = sender.isAbility();
			}
		}

		for(Iterator iter = BackRoom.getReceive().iterator(); iter.hasNext(); ){
			Receiver receiver = (Receiver)iter.next();
			if(RID == receiver.getID()){
				RAbility = receiver.isAbility();
			}
		}
		
		if(SAbility == true && RAbility == true){
			String t = null;
			char [] ss = s.toCharArray();
			for(int i = 0; i < ss.length; i++){
				char c;
				if(i+1 < ss.length && i % 2 == 0){
					c = ss[i];
					ss[i] = ss[i+1];
					ss[i+1] = c;
				}
			}
			t = String.valueOf(ss);
			return t;
		}
		else{
			return s;
		}
	}
	
	
}









