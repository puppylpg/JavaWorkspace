
public class Tray {
	
	private int x,y;      
	private boolean available = false; 

    public synchronized int get() {
        while (available == false) {
            try {  
            	wait();       
            } catch (InterruptedException e) { 
            	
            }        
        }
        available = false;  // 此时available为真，确保所有其他消费者等待
        notifyAll(); 
        return x+y;  
    }
    
    public synchronized void put(int a, int b) {
        while (available == true) {
            try {  
            	wait();   
            } catch (InterruptedException e) { 
            	
            }        
        }
        available = true;  // 唤醒等待队列中的其他消费者或生产者
        x= a; 
        y = b;
        notifyAll(); 
    }
}