import java.util.LinkedList;


public class Elevator implements Runnable{
	private int ID;
	private boolean exist;
	private int floor;
	private String state;
	private char P_S;					
	private int distance = 0;
	static final long OVERFLOW = 9999999999L;
	private static final int DOOROPEN = 1;
	private static final int DOORCLOSE = 2;
	private int currentstate = DOORCLOSE;
	private int nextstate = DOORCLOSE;
	
	LinkedList<Request> list = new LinkedList<Request>();
	
	public void addRequest(Request req){			//Manager调用此函数向电梯的命令队列中添加命令
		list.add(req);
	}
	
	public Elevator(int ID, boolean exist, int floor, String state, char P_S){
		this.ID = ID;
		this.exist = exist;
		this.floor = floor;
		this.state = state;
		this.P_S = P_S;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public boolean getExist() {
		return exist;
	}
	
	public void moveUp(){
		this.floor++;
		this.distance++;			//运动距离增加
	}
	
	public void moveDown(){
		this.floor--;
		this.distance++;			//运动距离增加
	}
	
	public int getDistance(){
		return this.distance;
	}
	
	public String toString(){
		if(this.exist == true)
			return "(Elevator_" + this.ID + "," + floor + "," + state + "," + P_S + "," + Timer.getTimeUnit() * 0.5 + ")";
		else
			return "(Elevator_" + this.ID + "," + "Missing" + "," + Timer.getTimeUnit() * 0.5 + ")";
	}
	
	public void moveItself(){
		Request req = null;
		for(Request r : list){						//寻找第一个尚未执行完的命令
			if(r.getValid() != false){
				req = r;
				break;
			}
		}
		
		if(req != null){							//找到了，则开始执行
			this.currentstate = this.nextstate;
			
			int aimFloor = 0;
			
			if(req.getLeave() == true){				//碰到leave
				this.exist = false;
				req.setValid(false);		//将leave指令干掉，否则永远轮不到下一条指令！！！！！！！
			}
			else if(req.getJoin() == true){				//碰到join
				this.exist = true;
				req.setValid(false);		//将join指令干掉，否则永远轮不到下一条指令！！！！！！！
			}
			else if(this.exist == true){				//想运动，楼梯必须的存在
				
				if(req.getInOut().equals("F_R")){		//获取指令的目标楼层
					aimFloor = req.getOnFloor();
				}
				else if(req.getInOut().equals("E_R")){
					aimFloor = req.getToFloor();
				}
				
				if(this.currentstate == DOOROPEN){		//根据当前楼层做出动作，并判断下一次的运动状态
					this.nextstate = DOORCLOSE;
					this.state = "STAY";
					this.P_S = 'S';
				}
				else if(this.floor < aimFloor){
					this.nextstate = DOORCLOSE;
					this.moveUp();
					this.state = "UP";
					this.P_S = 'P';
					if(this.instructionCarried(this.floor) != null){		//检查是否有顺路，有则删之，下一状态设置为开门
						this.nextstate = DOOROPEN;
						this.instructionCarried(this.floor).setValid(false);
					}
				}
				else if(this.floor > aimFloor){
					this.nextstate = DOORCLOSE;
					this.moveDown();
					this.state = "DOWN";
					this.P_S = 'P';
					if(this.instructionCarried(this.floor) != null){		//检查是否有顺路，有则删之，下一状态设置为开门
						this.nextstate = DOOROPEN;
						this.instructionCarried(this.floor).setValid(false);
					}
				}
				else if(this.floor == aimFloor){
					this.nextstate = DOOROPEN;
					req.setValid(false);			//删除这条指令！！！坑死爹了！（但是只能删除F_R、E_R指令，不能删除leave、join）
					this.state = "STAY";
					this.P_S = 'S';
					if(this.instructionCarried(this.floor) != null){		//检查是否有顺路，有则删之，下一状态设置为开门
						this.nextstate = DOOROPEN;
						this.instructionCarried(this.floor).setValid(false);
					}
				}
			}
			else if(this.exist == false){		//如果此时电梯处于Missing状态
				if(req.getInOut() != null)		//此时碰到了F_R、E_R，直接删除
					req.setValid(false);
			}
		}
	}

	
	public Request instructionCarried(int floor){	//电梯内部的指令队列要实现捎带
		int aimFloor = 0;
		
		for(Request req : list){
			
			if(req.getInOut() != null){				//只有是F_R或E_R才能捎带
				if(req.getInOut().equals("F_R")){			//获取指令的目标楼层
					aimFloor = req.getOnFloor();
				}
				else if(req.getInOut().equals("E_R")){
					aimFloor = req.getToFloor();
				}
				
				//指令有效，且，指令的目标楼层和电梯当前楼层相同，则此命令就是要顺带的，返回之
				if(req.getValid() == true && aimFloor == floor){		
					return req;
				}
				
			}
		}
		return null;		//否则，返回null
	}
	
	public void run(){
		while(Timer.getGoExit()){
			if(GlobFlag.getInstance().getTimerFlag() == this.ID + 3){
				this.moveItself();											//进行动作
				System.out.println(this);									//打印当前状态
				if(this.ID != 3){
					GlobFlag.getInstance().setTimerFlag(this.ID + 3 + 1);
				}
				else{
					GlobFlag.getInstance().setTimerFlag(1);
				}
			}
			else
				Thread.yield();
		}
	}
	
}







