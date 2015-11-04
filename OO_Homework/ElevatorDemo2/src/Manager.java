
public class Manager {
	Queue queue = new Queue();
	Elevator ele = new Elevator(1, 0, "STAY", 'S');
	
	public void order(){
		this.queue.creatList();
		if(this.queue.checkList()!= true){
			System.exit(1);
		}

		for(Request req : this.queue.list){
			if(req.equals("$")){
				break;
			}
						
			if(req.getInOut().equals("F_R")){
				move(req.getOnFloor(), req);
			}
			else{
				move(req.getToFloor(), req);
			}
		}
	}	
	
	//merge the request by floor. The elevator only check whether to merge the requests when it achieves a new floor
	public void carryRequest(int toFloor, Request req){
		for(Request r : this.queue.list){
			
			if(r.getReqTime() > ele.getTimeUnit() * 0.5 || (ele.getP_S() == 'S' && r.getReqTime() + 0.5 == ele.getTimeUnit() * 0.5) ){
				break;		//if the request time beyond the time when the elevator achieves this floor, no need to check more
			}
			else if(r.getValid() == false){
				continue;
			}
			else if(r.getValid() == true){
				if( ( r.getOnFloor() == ele.getFloor() && r.getUpDown().equals(ele.getState()) ) //SHOULDN'T BE "==" r.getUpDown() == ele.getState()
						|| r.getToFloor() == ele.getFloor() ){
					if(ele.getP_S() == 'P'){
						ele.setTimeUnit(ele.getTimeUnit() + 2);
						ele.setState("STAY");
						ele.setP_S('S');
						System.out.println(ele.toString());
					}
					else if(ele.getP_S() == 'S'){
						// do nothing
					}
					r.setValid(false);
					if(r.getInOut().equals("F_R")){
						System.out.println("---------------(" + r.getInOut() + "," + r.getOnFloor() + "," + r.getUpDown() + "," + r.getReqTime() + ")---------------");
					}
					else{
						System.out.println("---------------(" + r.getInOut() + "," + r.getToFloor() + "," + r.getReqTime() + ")---------------");
					}
				}
			}
		}
	}
	
	void move(int toFloor, Request req){
		while(ele.getTimeUnit() < 2 * req.getReqTime()){
			ele.setTimeUnit(ele.getTimeUnit() + 1);
			ele.setState("STAY");
			ele.setP_S('S');
			System.out.println(ele.toString());				//no need to write toString
		}
		
		while((ele.getFloor() < toFloor)){
			ele.moveUp();
			if(ele.getFloor() < toFloor){
				ele.setTimeUnit(ele.getTimeUnit() + 1);
				judgeOverflow();
				ele.setState("UP");
				ele.setP_S('P');
				System.out.println(ele.toString());
				carryRequest(toFloor, req);
			}
			else{
				ele.setTimeUnit( ele.getTimeUnit() + 1 );
				judgeOverflow();
				ele.setState("UP");
				ele.setP_S('S');
				System.out.println(ele.toString());
			}
		}

		while(ele.getFloor() > toFloor){
			ele.moveDown();
			if(ele.getFloor() > toFloor){
				ele.setTimeUnit(ele.getTimeUnit() + 1);
				judgeOverflow();
				ele.setState("DOWN");
				ele.setP_S('P');
				System.out.println(ele.toString());
				carryRequest(toFloor, req);
			}
			else{
				ele.setTimeUnit( ele.getTimeUnit() + 1 );
				judgeOverflow();
				ele.setState("DOWN");
				ele.setP_S('S');
				System.out.println(ele.toString());
			}
		}

		if(ele.getFloor() == toFloor){
			ele.setTimeUnit(ele.getTimeUnit() + 2);
			judgeOverflow();
			ele.setState("STAY");
			ele.setP_S('S');
			System.out.println(ele.toString());
			carryRequest(toFloor, req);
		}
	}
	
		
	private void judgeOverflow(){
		if(ele.getTimeUnit() / 2 > Elevator.OVERFLOW){
			System.out.println("Time range is out of int!");
			System.exit(1);
		}
	}

}
