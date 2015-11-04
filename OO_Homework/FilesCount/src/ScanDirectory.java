import java.io.File;
import java.util.Arrays;


public class ScanDirectory implements Runnable{
	private String path = null;
	private Directory dir;
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: assign two values
	ScanDirectory(String path, Directory childDir){		// TODO: When first initialize, put Input.getPath() into it!
		this.path = path;
		this.dir = childDir;
	}

	
	//REQUIRES: A thread
	//MODIFIES: none
	//EFFECTS: if the thread starts, run here
	@Override
	public void run() {								//TODO: don't forget to try
		// TODO Auto-generated method stub
		try{
			countCurrentDir(path);
		}catch(Exception e){
			System.out.println("Error!!! Please input again!");
		}
		
	}
	
	
	//REQUIRES: none
	//MODIFIES: change the values of priorities of this class
	//EFFECTS: count in current directory
	public void countCurrentDir(String path) {
		File f = null;
		try{
			f = new File(path);
			if( !f.exists() ){
				System.out.println("The file you input doesn't exist! \nPlease try again!");
				System.exit(1);
			}
		}
		catch(Exception e){
			System.out.println("Invalid path is input!!!");
			System.exit(1);
		}
		File [] fList = f.listFiles();
		
		if(fList != null){
			Arrays.sort(fList);
			for(File tmp : fList){
				if(tmp.isFile()){
					dir.totalNum++; 						//count the number
					dir.dirTotal += tmp.length(); 			//count part of the directory size
					
					dir.counting(dir.exeNum, dir.exeTotal, dir.exeMax, dir.exeMin, tmp,
							".exe", ".dll", ".bin", ".jar", ".bat");
					dir.exeNum = Directory.yNum; dir.exeTotal = Directory.yTotal; dir.exeMax = Directory.yMax; dir.exeMin = Directory.yMin;
					
					dir.counting(dir.mNum, dir.mTotal, dir.mMax, dir.mMin, tmp,
							"mp3", "wma", "wav", "ogg", "mpeg");
					dir.mNum = Directory.yNum; dir.mTotal = Directory.yTotal; dir.mMax = Directory.yMax; dir.mMin = Directory.yMin;
					dir.counting(dir.mNum, dir.mTotal, dir.mMax, dir.mMin, tmp,
							"mp4", "avi", "rmvb", "wmv", "mkv");
					dir.mNum = Directory.yNum; dir.mTotal = Directory.yTotal; dir.mMax = Directory.yMax; dir.mMin = Directory.yMin;
					dir.counting(dir.mNum, dir.mTotal, dir.mMax, dir.mMin, tmp,
							".jpg", ".jpeg", ".gif", ".png", ".bmp");
					dir.mNum = Directory.yNum; dir.mTotal = Directory.yTotal; dir.mMax = Directory.yMax; dir.mMin = Directory.yMin;
					
					dir.counting(dir.textNum, dir.textTotal, dir.textMax, dir.textMin, tmp,
							".txt", ".rtf", ".doc", ".docx", ".ppt");
					dir.textNum = Directory.yNum; dir.textTotal = Directory.yTotal; dir.textMax = Directory.yMax; dir.textMin = Directory.yMin;
					dir.counting(dir.textNum, dir.textTotal, dir.textMax, dir.textMin, tmp,
							".pptx", ".xls", ".xlsx", ".pdf", ".lrc");
					dir.textNum = Directory.yNum; dir.textTotal = Directory.yTotal; dir.textMax = Directory.yMax; dir.textMin = Directory.yMin;
				}
				else if(tmp.isDirectory()){
					dir.dirNum++;
					
					Directory childDir = new Directory();
					ScanDirectory sd = new ScanDirectory(tmp.getAbsolutePath(), childDir);//(tmp.getName(), childDir);
					Thread child = new Thread(sd);
					child.start();
					try {
						child.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dir.dirMax = (dir.dirMax > childDir.dirTotal) ? dir.dirMax : childDir.dirTotal;		//TODO:
					dir.dirMin = (dir.dirMin < childDir.dirTotal) ? dir.dirMin : childDir.dirTotal;		//TODO:
					addSubDir(dir, childDir);								//add its sub-directory
					
				}
			}
		}
		System.out.println("-------------------- -------------------- --------------------");
		System.out.println("==> ==> ==> " + new File(path).getAbsolutePath());	//output current path
		outputCurrentDir(dir);									//output the numbers
		
		System.out.println("Sort by " + Input.getMode() + " ==> ==>");				//output mode, then the file-list
		
		if(fList != null){
			this.sortList(fList);									//sort the array according to the mode
			for(File tmp : fList){									//output the sorted files
				System.out.println(tmp.getName());
			}
		}
	}
	
	//REQUIRES: specific values to output
	//MODIFIES: System.out
	//EFFECTS: output the values on the console
	public void outputCurrentDir(Directory dir){
//		long totalNum = 0, dirNum = 0, exeNum = 0, mNum = 0, textNum = 0;
//		long dirMax = 0, exeMax = 0, mMax = 0, textMax = 0;
//		long dirMin = INF, exeMin = INF, mMin = INF, textMin = INF;
//		long dirTotal = 0, exeTotal = 0, mTotal = 0, textTotal = 0;
		System.out.println("Total Number: " + dir.totalNum);
		System.out.println("Directory Number: " + dir.dirNum);
		System.out.println("Execution Number: " + dir.exeNum);
		System.out.println("M Number: " + dir.mNum);
		System.out.println("Text Number: " + dir.textNum);
		System.out.println(" ");
		
		System.out.println("Directory MaxSize: " + dir.dirMax);
		System.out.println("Execcution MaxSize: " + dir.exeMax);
		System.out.println("M MaxSize: " + dir.mMax);
		System.out.println("Text MaxSize: " + dir.textMax);
		System.out.println(" ");
		
		System.out.println("Directory MinSize: " + ((dir.dirMin == Directory.INF) ? 0 : dir.dirMin));
		System.out.println("Execution MinSize: " + ((dir.exeMin == Directory.INF) ? 0 : dir.exeMin));
		System.out.println("M MinSize: " + ((dir.mMin == Directory.INF) ? 0 : dir.mMin));
		System.out.println("Text MinSize: " + ((dir.textMin == Directory.INF) ? 0 : dir.textMin));
		System.out.println(" ");
		
		System.out.println("Directory Average Size: " + ((dir.dirNum == 0) ? 0 : dir.dirTotal / dir.dirNum));
		System.out.println("Execution Average Size: " + ((dir.exeNum == 0) ? 0 : dir.exeTotal / dir.exeNum));
		System.out.println("M Average Size: " + ((dir.mNum == 0 ) ? 0 : dir.mTotal / dir.mNum));
		System.out.println("Text Average Size: " + ((dir.textNum == 0) ? 0 : dir.textTotal / dir.textNum));
		System.out.println(" ");
		
	}

	//REQUIRES: some specific values
	//MODIFIES: add
	//EFFECTS: change the father values
	public void addSubDir(Directory dir, Directory childDir) {
		dir.totalNum += childDir.totalNum;
		dir.dirNum += childDir.dirNum;
		dir.exeNum += childDir.exeNum;
		dir.mNum = childDir.mNum;
		dir.textNum += childDir.textNum;
		
		dir.dirMax += childDir.dirMax;
		dir.exeMax += childDir.exeMax;
		dir.mMax += childDir.mMax;
		dir.textMax += childDir.textMax;
		
		dir.dirMin += ((childDir.dirMin == Directory.INF) ? 0 : childDir.dirMin);
		dir.exeMin += ((childDir.exeMin == Directory.INF) ? 0 : childDir.exeMin);
		dir.mMin += ((childDir.mMin == Directory.INF) ? 0 : childDir.mMin);
		dir.textMin += ((childDir.textMin == Directory.INF) ? 0 : childDir.textMin);
		
		dir.dirTotal += childDir.dirTotal;
		dir.exeTotal += childDir.exeTotal;
		dir.mTotal += childDir.mTotal;
		dir.textTotal += childDir.textTotal;
	}
	
	//REQUIRES: a array list
	//MODIFIES: none
	//EFFECTS: sort the list by specific standards
	public void sortList(File [] flist){
		String mode = Input.getMode();
		if(mode.equals("name")){
			Arrays.sort(flist);
		}
		else if(mode.equals("size")){
			Arrays.sort(flist, new SizeComparator());
		}
		else if(mode.equals("type")){
			Arrays.sort(flist, new TypeComparator());
		}
		else{
			System.out.println("The input of \"mode\" is INVALID!!!");
			System.exit(1);
		}
	}
}











