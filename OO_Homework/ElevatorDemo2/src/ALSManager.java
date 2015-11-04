
public class ALSManager extends Manager{
	
	public void order(){
		super.queue.creatList();
		if(super.queue.checkList() != true){
			System.exit(1);
		}
		
		for(Request req : super.queue.list){
			if(req.getValid() == false){
				continue;
			}
			else{
				req.setValid(false);
				if(req.getInOut().equals("F_R")){
					move(req.getOnFloor(), req);
				}
				else{
					move(req.getToFloor(), req);
				}
			}
		}
	}

}
