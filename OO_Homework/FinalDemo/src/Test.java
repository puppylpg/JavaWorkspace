
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Timer timer = new Timer();
			Simulator simulator = new Simulator();
			Queuing waitingQueue = new Queuing();
			Elevator ele1 = new Elevator(1, true, 1, "STAY", 'S'); 	//int ID, boolean exist, int floor, String state, char P_S
			Elevator ele2 = new Elevator(2, true, 1, "STAY", 'S');
			Elevator ele3 = new Elevator(3, true, 1, "STAY", 'S');
			Manager manager = new Manager();
			
			Thread t1 = new Thread(timer);
			Thread t2 = new Thread(simulator);
			Thread t3 = new Thread(manager);
			Thread t4 = new Thread(ele1);
			Thread t5 = new Thread(ele2);
			Thread t6 = new Thread(ele3);
			
			
			simulator.setQueuing(waitingQueue.getQueuing());
			manager.setQueuing(waitingQueue.getQueuing());
			manager.setEle1(ele1);
			manager.setEle2(ele2);
			manager.setEle3(ele3);
			
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
		}
		catch(Exception e){
			System.out.println("Error! Please input again!");
		}
		
	}

}
