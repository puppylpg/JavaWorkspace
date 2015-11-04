public class Producer extends Thread {
      private Tray tray;             
      private int id;
      
      public Producer(Tray t, int id) {
    	  tray = t;            
    	  this.id = id;            
      }
      
      public void run() {
           for (int i = 0; i < 10; i++) 
               for(int j =0; j < 10; j++ ) {
                  tray.put(i, j);
                  System.out.println("Producer #" + this.id   + " put: ("+i +","+j + ").");
                  try { 
                	  sleep((int)(Math.random() * 100));  
                  }catch (InterruptedException e) { }
               };
     }
}