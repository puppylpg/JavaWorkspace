
public class Simulator implements Runnable{
	private int timeUnit;
	Queue queue = new Queue();
	
	Queue queuing = null;				//新建一个Queuing类的引用

	void creatQueue(){					//创建原始命令队列
		this.queue.creatList();
		if(this.queue.checkList() != true){
			System.exit(1);
		}
	}
	 
	Simulator(){						//在新建Simulator时新建一个Queue队列
		creatQueue();
	}
	
	void setQueuing(Queue t){			//TODO：将Queuing传进来给到这个引用，主函数里别忘了调用这个
		queuing = t;
	}
	
	
	public void run(){
		while(Timer.getGoExit()){
			if(GlobFlag.getInstance().getTimerFlag() == 2){
				this.timeUnit = Timer.getTimeUnit();
			
				addToQueuing(timeUnit);		//根据时间，将原始队列的命令加入到queuing中
				
				GlobFlag.getInstance().setTimerFlag(3);	//退出之前修改标志位
			}
			else
				Thread.yield();
		}
	}
	
	void addToQueuing(int tu){			//tu : timeUnit of Simulator(also Timer's)
		for(Request req : queue.list){
			if(req.getReqTime() * 2 > tu){	//大于当前时间的就不管了
				break;
			}
			
			if(req.getValid() == true){	//如果没被删除，拷贝一份，删之，并将拷贝添加到Queuing里面
				Request tmp = req.cloneAnObj();		//克隆一个新的对象
				req.setValid(false);
				queuing.list.add(tmp);	//可以用引用对Queuing进行操作
			}
		}
	}
	
}
 







