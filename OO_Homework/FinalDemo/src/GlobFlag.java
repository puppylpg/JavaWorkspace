
public class GlobFlag {
	private int timerFlag = 1;		
	
	private GlobFlag(){}		//私有化构造函数，防止在外部实例化
	
	private static GlobFlag instance = new GlobFlag();		//私有静态对象instance
	//私有：防止外部改动此对象
	//静态：下面的静态方法getInstance()只能操作静态对象，所以instance定义成static
	
	//静态方法返回instance对象：外部无法实例化，所以无法调用，因此变成static可以直接用类名调用
	public static GlobFlag getInstance(){
		return instance;
	}

	public int getTimerFlag() {				
		return timerFlag;
	}

	public void setTimerFlag(int timerFlag) {	
		this.timerFlag = timerFlag;
	}
	
}
