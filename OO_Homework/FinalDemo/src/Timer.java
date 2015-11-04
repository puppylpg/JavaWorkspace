
public class Timer implements Runnable{
	private static int timeUnit = 0;
	private static boolean goExit = true;
	private static int EXITTIME = 55;
	
	public static int getTimeUnit(){
		return timeUnit;
	}
	
	public static boolean getGoExit(){
		return goExit;
	}
	
	public static void setExitTime(int t){
		EXITTIME = t;
	}
	
	public void run(){
		while(goExit){
			if(GlobFlag.getInstance().getTimerFlag() == 1){
				timeUnit += 1 ;
				GlobFlag.getInstance().setTimerFlag(2);
				
				if(timeUnit == EXITTIME + 20){		//比最后的时间多输出10s
					goExit = false;
				}
			}
			else 
				Thread.yield();
		}
	}
}
