import java.io.File;
import java.util.Comparator;


public class TypeComparator implements Comparator<File>{

	//REQUIRES: Thread
	//MODIFIES: none
	//EFFECTS: return a number to control the comparison by type	
	@Override
	public int compare(File f1, File f2) {
		// TODO Auto-generated method stub
		int num = 0;
		if(f1.isDirectory() && f2.isDirectory()){				//dir vs. dir		先考虑文件和文件夹
			num = 0;
		}
		else if(f1.isDirectory() && !f2.isDirectory()){			//dir vs. file
			num = -1;
		}
		else if(!f1.isDirectory() && f2.isDirectory()){			//file vs. dir
			num = 1;
		}
		else{													//file vs. file
			String name1 = f1.getName();
			String name2 = f2.getName();
			String prefix1 = name1.substring(name1.lastIndexOf(".") + 1);
			String prefix2 = name2.substring(name2.lastIndexOf(".") + 1);
			if(prefix1 == null && prefix2 == null){			//					再考虑有后缀文件和无后缀文件
				num = 0;
			}
			else if(prefix1 == null && prefix2 != null){
				num = -1;
			}
			else if(prefix1 != null && prefix2 == null){
				num = 1;
			}
			else{
				num = prefix1.compareTo(prefix2);									//最后是两个有后缀文件
			}
		}
		
		if(num == 0){																//后缀共同则按名字比
			return f1.getName().compareTo(f2.getName());
		}
		
		return num;
	}
	
}