package Utility;
import java.util.ArrayList;

/* Overview: BackRoom manages communication tools. 
 * AF(c) = (boolean)  the send + receive + trans, is connected.
 */
public class BackRoom {
	private static ArrayList<Sender> send = new ArrayList<Sender>();
	private static ArrayList<Receiver> receive = new ArrayList<Receiver>();
	private static ArrayList<Transfer> trans = new ArrayList<Transfer>();
	
	/*  requires: none
	 *  modifiers: send, receive, trans
	 *  effects: reset these variables 
	 */ 
	public static void init(){
		send = new ArrayList<Sender>();
		receive = new ArrayList<Receiver>();
		trans = new ArrayList<Transfer>();
	}
	/**
	 * @return the send
	 */
	public static ArrayList<Sender> getSend() {
		return send;
	}
	/**
	 * @param send the send to set
	 */
	public static void setSend(ArrayList<Sender> send) {
		BackRoom.send = send;
	}
	/**
	 * @return the receive
	 */
	public static ArrayList<Receiver> getReceive() {
		return receive;
	}
	/**
	 * @param receive the receive to set
	 */
	public static void setReceive(ArrayList<Receiver> receive) {
		BackRoom.receive = receive;
	}
	/**
	 * @return the trans
	 */
	public static ArrayList<Transfer> getTrans() {
		return trans;
	}
	/**
	 * @param trans the trans to set
	 */
	public static void setTrans(ArrayList<Transfer> trans) {
		BackRoom.trans = trans;
	}
	
	/* repOK != null */
	public boolean repOK() {
		return (send != null) && (receive != null) && (trans!= null);
	}
}
