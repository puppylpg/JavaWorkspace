import java.io.File;
import java.util.Comparator;


public class SizeComparator implements Comparator<File>{

	//REQUIRES: Thread
	//MODIFIES: none
	//EFFECTS: return a number to control the comparison by size
	@Override
	public int compare(File f1, File f2) {
		// TODO Auto-generated method stub
		int num = 0;
		if(f1.isDirectory() && f2.isDirectory()){				//dir vs. dir
			num = 0;
		}
		else if(f1.isDirectory() && !f2.isDirectory()){			//dir vs. file
			num = -1;
		}
		else if(!f1.isDirectory() && f2.isDirectory()){			//file vs. dir
			num = 1;
		}
		else{													//file vs. file
			num = new Long(f1.length()).compareTo(new Long(f2.length()));
		}
		
		if(num == 0){											//compare by name
			return f1.getName().compareTo(f2.getName());
		}
		return num;
	}
}
