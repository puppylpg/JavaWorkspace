
public class Manager implements Runnable{
	
	Queue queuing = null;
	Elevator ele1 = null;
	Elevator ele2 = null;
	Elevator ele3 = null;
	
	void setQueuing(Queue t){					//TODO:
		this.queuing = t;
	}
	
	void setEle1(Elevator ele){					//TODO
		this.ele1 = ele;
	}
	
	void setEle2(Elevator ele){
		ele2 = ele;
	}
	void setEle3(Elevator ele){
		ele3 = ele;
	}
	
	public Elevator min(Elevator a, Elevator b){			//找出两个电梯里运动量较小的
		if(a.getExist() == false){					//如果有一个不存在了，直接返回另一个
			return b;
		}
		else if(b.getExist() == false){
			return a;
		}
		
		if(a.getDistance() <= b.getDistance()){
			return a;
		}
		else
			return b;
	}
	
	void addToElevator(int tu){
		for(Request req : this.queuing.list){
			//TODO：先不捎带，先把电梯的命令直接给电梯，楼层的命令直接给运动量最小的

			
			if(req.getValid() == true){				//queuing里面有命令
				Request tmp = req.cloneAnObj();		//克隆一份
				req.setValid(false);				//删除queuing里的命令
				switch(req.gete_ID()){				//根据命令的ID将命令传输到不同的电梯
					case 1 : ele1.addRequest(tmp);
						break;
					case 2 : ele2.addRequest(tmp);
						break;
					case 3 : ele3.addRequest(tmp);
						break;
					default : min(ele1, min(ele2, ele3)).addRequest(tmp);
				}

			}
		}
	}
	
	public void run(){
		while(Timer.getGoExit()){
			
			if(GlobFlag.getInstance().getTimerFlag() == 3){
				this.addToElevator(Timer.getTimeUnit());
				
				GlobFlag.getInstance().setTimerFlag(4);
			}
			else
				Thread.yield();
		}
		
	}
}
