import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utility.BackRoom;
import Utility.Receiver;
import Utility.Sender;
import Utility.Transfer;

//Overview: read the configuration file to form the machines
//AF(c) = (int) where int = getTotalmachine + getSender + getReceiver + getTransfer;
public class ReadConfiguration implements Runnable{
	private static int totalMachine = 0;
	private static int sender = 0;
	private static int receiver = 0;
	private static int transfer = 0;
	
	private ArrayList<Sender> send = null;
	private ArrayList<Receiver> receive = null;
	private ArrayList<Transfer> trans = null;
	
	private static Scanner sc = new Scanner(System.in);

	public ArrayList<Sender> getSend(){
		return this.send;
	}
	
	/*  requires: some values
	 *  modifiers: send, receive, trans
	 *  effects: change the values of the variables above 
	 */ 
	public void init(ArrayList<Sender> send, ArrayList<Receiver> receive, ArrayList<Transfer> trans){
		this.send = send;
		this.receive = receive;
		this.trans = trans;
	}
	
	/**
	 * @return the totalMachine
	 */
	public static int getTotalMachine() {
		return totalMachine;
	}
	
	
	/**
	 * @param totalMachine the totalMachine to set
	 */
	public static void setTotalMachine(int totalMachine) {
		ReadConfiguration.totalMachine = totalMachine;
	}

	/**
	 * @param sender the sender to set
	 */
	public static void setSender(int sender) {
		ReadConfiguration.sender = sender;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public static void setReceiver(int receiver) {
		ReadConfiguration.receiver = receiver;
	}

	/**
	 * @param transfer the transfer to set
	 */
	public static void setTransfer(int transfer) {
		ReadConfiguration.transfer = transfer;
	}

	/**
	 * @return the transfer
	 */
	public static int getTransfer() {
		return transfer;
	}

	/**
	 * @return the sender
	 */
	public static int getSender() {
		return sender;
	}

	/**
	 * @return the receiver
	 */
	public static int getReceiver() {
		return receiver;
	}
	
	/*  requires: none
	 *  modifiers: send, receive, trans
	 *  effects:  change some values above
	 */ 
	public void find(){
		ArrayList<Sender> Send = this.send;
		ArrayList<Receiver> Receive = this.receive;
		ArrayList<Transfer> Trans = this.trans;
	}

	//effects: repOK != null
	public boolean repOK(){
		return (send != null && receive != null && trans != null);
	}

	/*  requires: none
	 *  modifiers: sender etc.
	 *  effects: modify the values according to the configuration file 
	 */ 
	public void readConfigurationFile(){
		String str = null;

		String filePath = null;
		FileReader fr = null;
		System.out.println("Input the abstract path of Configuration File please: ");
		try{
			filePath = sc.nextLine();			//read a file path
		}catch(Exception e){
			System.out.println("Path input error! Please restart the program and input again!");
			System.exit(1);
		}
		try {
			fr = new FileReader(filePath);	//open the file
		} catch (FileNotFoundException e1) {
			System.out.println("No such file exists! Please restart the program and input again!");
			System.exit(1);
		}
		
		String regex1 = "^Sender:([1-3])$";
		String regex2 = "^Receiver:([1-5])$";
		String regex3 = "^\\d+$";
		String regex4 = "(Sender|Receiver):( true| false)+";
		Pattern p1 = Pattern.compile(regex1);
		Pattern p2 = Pattern.compile(regex2);
		Pattern p3 = Pattern.compile(regex3);
		Pattern p4 = Pattern.compile(regex4);
		
		BufferedReader br = null;  
		try{  
			br = new BufferedReader(fr);  	//buffer for the file
		}catch(Exception e){  
			System.out.println("Error! Opening new buffer for file fails!");  
		}  
		
		try {		//TODO in case of xjb input lines 
			while( ( str = br.readLine() ) != null ){		//read a line from the file
				
				if( !( str.matches(regex1) || str.matches(regex2) //if not matches the regex
						|| str.matches(regex3) || str.matches(regex4) ) ){
					System.out.println("Illegal information in file!");
					System.exit(1);
				}
				else{										//if matches the regex
					Matcher m1 = p1.matcher(str);
					Matcher m2 = p2.matcher(str);
					Matcher m3 = p3.matcher(str);
					Matcher m4 = p4.matcher(str);
					if(m1.find()){							//if matches 1
						sender = Integer.parseInt(m1.group(1));
						for(int i = 1; i <= sender; i++){
							Sender e = new Sender();
							e.setID(i);
							this.send.add(e);
						}
					}
					else if(m2.find()){						//if matches 2
						receiver = Integer.parseInt(m2.group(1));
						for(int i = 1; i <= receiver; i++){
							Receiver e =new Receiver();
							e.setID(i);
							this.receive.add(e);
						}
					}
					else if(m3.find()){						//if matches 3
						transfer = Integer.parseInt(str);
						for(int i = 1; i <= transfer; i++){
							Transfer e = new Transfer();
							e.setID(i);
							this.trans.add(e);
						}
					}
					else if(m4.find()){						//if matches 4
						if(m4.group(1).equals("Sender")){		//if is ability of sender
							boolean b = false;
							if(m4.groupCount() == sender + 1){		//if appearing time is right
								for(int i = 2; i <= m4.groupCount(); i++){	
									b = Boolean.parseBoolean(m4.group(i).trim());
									this.send.get(i-2).setAbility(b);	//set ability
								}
							}
						}
						else if(m4.group(1).equals("Receiver")){	//if is ability of receiver
							boolean b = false;
							if(m4.groupCount() == receiver + 1){	//if appearing time is right
								for(int i = 2; i <= m4.groupCount(); i++){
									b = Boolean.parseBoolean(m4.group(i).trim());
									this.receive.get(i-2).setAbility(b);	//set ability
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("The configuration file is not correct! Reatart the program please!");
			System.exit(1);
		}
	
		BackRoom.setReceive(this.receive);			//set the changed value to BackRoom's machine
		BackRoom.setSend(this.send);
		BackRoom.setTrans(this.trans);
		
		totalMachine = receiver + sender + transfer;
	}

	/*  requires: none
	 *  modifiers: none
	 *  effects: to start a thread to run  
	 */ 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int size = 0;
		int [][] G;
		try {
			this.find();
			size = ReadConfiguration.getTotalMachine() + 1;
			G = new int [size][size];
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					G[i][j] = 0;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error! Please restart again!");
			System.exit(1);
		}
	}
	
}
















