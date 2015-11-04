import java.util.LinkedList;


public class Queue {
	LinkedList<Request> list = new LinkedList<Request>();
	
	public void creatList(){
		Request request = new Request();				//
		while(request.readRequest() != false){
			try{
				list.add(request);
			}catch(Exception e){
				System.out.println("Add too many requests!");
				System.exit(1);
			}
			request = new Request();					//
		}
	}
	
	public boolean checkList(){
		long time = -1;
		int flag = 1;
		for(Request req : list){
			if(req.equals("$")){
				return true;
			}
			if(req.getInOut() != null){
				if(req.getInOut().equals("F_R") == true){
					if(req.getUpDown().equals("UP") && req.getOnFloor() == 10
							|| req.getUpDown().equals("DOWN") && req.getOnFloor() == 1){
						System.out.println("Input wrong in F_R!");
						return false;
					}
				}
			}
			if(flag == 1){
				if(req.getReqTime() != 0){
					System.out.println("The time of the first request must be zero!");
					return false;
				}
				flag = 0;
				time = 0;
			}
			else if(req.getReqTime() <= time){
				System.out.println("Wrong sequence of time!");
				return false;
			}
			else{
				time = req.getReqTime();
			}
		}
		return true;
	}
}
