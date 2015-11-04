import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utility.BackRoom;
import Utility.Receiver;
import Utility.Sender;
import Utility.Transfer;

//Overview: get the command from the console to do something
public class ReadConsole{
	private int size = ReadConfiguration.getTotalMachine() + 1;
	private int sender = ReadConfiguration.getSender();
	private int receiver = ReadConfiguration.getReceiver();
	private int transfer = ReadConfiguration.getTransfer();
	
	private String message = null;		// information
	private int poster = 0;			// sender
	private int mailbox = 0;			// receiver
	private int time = 0;				// time
	
	private int INF = 0x3ffffff;
	
	private static Scanner sc = new Scanner(System.in);

	String str = null;
	private int [][] G;
	
	
	/**
	 * @return the poster
	 */
	public int getPoster() {
		return poster;
	}
	
	/**
	 * @param poster the poster to set
	 */
	public void setPoster(int poster) {
		this.poster = poster;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return the mailbox
	 */
	public int getMailbox() {
		return mailbox;
	}
	
	/**
	 * @param mailbox the mailbox to set
	 */
	public void setMailbox(int mailbox) {
		this.mailbox = mailbox;
	}
	
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/*  requires: some values
	 *  modifiers: size, sender, receiver, transfer
	 *  effects: to change the variables above
	 */ 
	public void init(){
		size = ReadConfiguration.getTotalMachine() + 1;
		sender = ReadConfiguration.getSender();
		receiver = ReadConfiguration.getReceiver();
		transfer = ReadConfiguration.getTransfer();
		G = new int [size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				G[i][j] = 0;
			}
		}
	}
	
	//effects: return the value of G
	public int[][] getG() {
		return G;
	}
	
	/*  requires: g
	 *  modifiers: G
	 *  effects: set G 
	 */ 
	public void setG(int[][] g) {
		G = g;
	}
	
	//effects: repOK != null
	public boolean repOK(){
		return (message != null && str != null);
	}
	
	/*  requires: none
	 *  modifiers: none
	 *  effects: read some commands from the console 
	 */ 
	public void readCommand(){
		this.init();
		helpInfo();
		while(!"EXIT".equals(str)){
			try{
				str = sc.nextLine();
				if("EXIT".equals(str)){
					break;
				}
			}
			catch(Exception e){
				System.out.println("Command input ERROR!\nRestart the program please!");
				System.exit(1);
			}
			if("Help".equals(str)){
				helpInfo();
			}
			else if("Show Available Sender".equals(str)){
				for(Sender s : BackRoom.getSend()){
					System.out.println(s.getFunction() + s.getID() + "(" + s.isAbility() + ")");
				}
			}
			else if("Show Available Receiver".equals(str)){
				for(Receiver s : BackRoom.getReceive()){
					System.out.println(s.getFunction() + s.getID() + "(" + s.isAbility() + ")");
				}
			}
			else if("Show Available Transfer".equals(str)){
				for(Transfer s : BackRoom.getTrans()){
					System.out.println(s.getFunction() + s.getID());
				}
			}
			else if("SendMessage".equals(str)){
				String tmp = null;
				String regex = "Sender: (\\d); Receiver: (\\d); Time: (\\d);";
				System.out.println("Input the message you want to transfer:");
				message = sc.nextLine();
				Pattern p = Pattern.compile(regex);
				
				System.out.println("Input the Sender and Receiver machine, for examble: Sender: 1;Receiver: 1");
				tmp = sc.nextLine();
				if(!tmp.matches(regex)){
					System.out.println("Error! Eg: \"Sender: 1;Receiver: 1; Time: 3;\". Restart the program again!");
					System.exit(1);
				}
				else{
					// assign poster and mailbox
					Matcher m = p.matcher(tmp);
					if(m.find()){
						int tt = Integer.parseInt(m.group(1));
						int pp = Integer.parseInt(m.group(2));
						int ti = Integer.parseInt(m.group(3));
						if(tt <=0 || tt > ReadConfiguration.getSender() || pp <= 0 || pp > ReadConfiguration.getReceiver()){
							System.out.println("Error! Eg: \"Sender: 1; Receiver: 1; Time: 3;\". Restart the program again!");
							System.exit(1);
						}
						else{
							if(ti < 0 || ti > this.INF){
								System.out.println("Time is too big! Program exits!");
								System.exit(1);
							}
							setPoster(tt);
							setMailbox(pp);
							setTime(ti);
						}
					}
				}
			}
			else if("Connection Stars".equals(str)){
				String tmp = null;
				String regex = "(R|S|T)(\\d)-(R|S|T)(\\d)";
				Pattern p = Pattern.compile(regex);
				
				while(!"Connection Ends".equals(tmp)){
					try{
						tmp = sc.nextLine();
						if("Connection Ends".equals(tmp)){
							break;
						}
					}
					catch(Exception e){
						System.out.println("Command input ERROR!\nRestart the program please!");
						System.exit(1);
					}
					
					if(!tmp.matches(regex)){
						System.out.println("Connection Formation Error! Input again!");
						continue;
					}
					else{
						Matcher m = p.matcher(tmp);
						String from = null;
						int fID = 0;
						String to = null;
						int tID = 0;
						if(m.find()){
							from = m.group(1);
							fID = Integer.parseInt(m.group(2));
							to = m.group(3);
							tID = Integer.parseInt(m.group(4));
						}
						
						//to judge whether the input is legal
						if(judgeInput(from, fID) && judgeInput(to, tID)){	//legal input
							int i = 0, j = 0;
//							from.intern();						//TODO
							if(from.equals("S")){				//i
								i = fID;
							}
							else if(from.equals("R")){
								System.out.println("Error! The connection is illegal:\n"
										+ "Receiver Machine Can't be at the front!");
								System.exit(1);
							}
							else if(from.equals("T")){
								i = fID + ReadConfiguration.getSender();
							}
							if(to.equals("S")){					//j
								System.out.println("Error! The connection is illegal:\n"
										+ "Sender Machine Should be at the front!");
								System.exit(1);
							}
							else if(to.equals("R")){
								j = ReadConfiguration.getSender() + ReadConfiguration.getTransfer() + tID;
							}
							else if(to.equals("T")){
								j = ReadConfiguration.getSender() + tID;
							}
							
							if(i == j){
								System.out.println("Error! Can't connect a machine itself!");
								System.exit(1);
							}
							
							G[i][j] = 1;
						}
						else{												// illegal input
							System.out.println("Wrong connection command!");
							System.exit(1);
						}
					}
				}
			}
			else{
				System.out.println("No such Command! Input again!");
				continue;
			}
		}
		this.postInit();

	}

	/*  requires: none
	 *  modifiers: none
	 *  effects: input the information to help tester to use 
	 */ 
	public void helpInfo() {
		System.out.println("Input a command from below to do sth: \n"
				+ "Show Available Sender\n"
				+ "			------Show how many Sender Machine you can use.\n"
				+ "Show Available Receiver\n"
				+ "			------Show how many Receiver Machine you can use.\n"
				+ "Show Available Transfer\n"
				+ "			------Show how many Transfer Machine you can use.\n"
				+ "Connection Stars\n"
				+ "			------Once you input this command, input some correct connection to form the network: "
				+ "such as S2-T1.\n"
				+ "Connection Ends\n"
				+ "			------The command to end connection of network.\n"
				+ "SendMessage\n"
				+ "			------To send message.\n"
				+ "EXIT\n"
				+ "			------End of input. Use only after command \"Connection Ends\".\n"
				+ "Help\n"
				+ "			------To show this help manual again.\n");
	}

	/*  requires: a string s, a integer t
	 *  modifiers: none
	 *  effects: to judge if s and t are legal
	 */ 
	public boolean judgeInput(String s, int t){
		if(s.equals("S") && t >= 1 && t <= ReadConfiguration.getSender()){
			return true;
		}
		else if(s.equals("R") && t >= 1 && t <= ReadConfiguration.getReceiver()){
			return true;
		}
		else if(s.equals("T") && t >= 1 && t <= ReadConfiguration.getTransfer()){
			return true;
		}
		else {
			return false;
		}
	}
	
	/*  requires: none
	 *  modifiers: none
	 *  effects: refresh the map 
	 */ 
	public void postInit(){		
		for(int i = 1; i < size; i++){
			for(int j = 1; j < size; j++){
				if(i == j){
					G[i][j] = 0;
				}
				else if(G[i][j] == 0){
					G[i][j] = INF;
				}
			}
		}
	}

	/*  requires: none
	 *  modifiers: none
	 *  effects: to output the values of the matrix 
	 */ 
	public void structure(){		
		System.out.println("用邻接矩阵表示连通性和网络拓扑结构(67108863即INF，意为两节点之间不可达)：\nThe result of the network(expressd by adjacent matrix): ");
		for(int i = 1; i < size; i++){
			for(int j = 1; j < size; j++){
				if(i == j){
					G[i][j] = 0;
				}
				else if(G[i][j] == 0){
					G[i][j] = INF;
				}
				
				if(G[i][j] == INF){
					System.out.print(G[i][j] + " ");			//TODO TODO
				}
				else{
					System.out.print(G[i][j] + "        ");
				}
			}
			System.out.println();							//TODO TODO
		}
	}


	
}























