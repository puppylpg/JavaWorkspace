
public class Consumer extends Thread {
    private Tray tray;
    private int id;
    
    public Consumer(Tray t, int id) {
        tray = t;         
        this.id = id;    
    }
    
    public void run() {
        int value = 0;
        for (int i = 0; i < 10; i++) {
            value = tray.get();
            System.out.println("Consumer #" + this.id + " got: " + value);
        }   
    } 
}