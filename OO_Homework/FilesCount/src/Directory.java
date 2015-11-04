import java.io.File;


public class Directory {
	static final long INF = 99999999L;
	long totalNum = 0, dirNum = 0, exeNum = 0, mNum = 0, textNum = 0;
	long dirMax = 0, exeMax = 0, mMax = 0, textMax = 0;
	long dirMin = INF, exeMin = INF, mMin = INF, textMin = INF;
	long dirTotal = 0, exeTotal = 0, mTotal = 0, textTotal = 0;
	static long yNum, yTotal, yMax, yMin;
	//多媒体文件：音频文件(>=5类 mp3、wma、wav、ogg、mpeg)、视频文件(>=5类 mp4 avi rmvb wmv mkv) 、图片文件(>=5类.jpg .jpeg .gif .png .bmp )
	//text：txt文件、rtf文件、MS office文件
	//可执行文件：exe文件、dll文件、bin文件、jar文件（针对windows平台）

	//REQUIRES: some specific values
	//MODIFIES: add
	//EFFECTS: recount the values of these priorities
	public void counting(long xNum, long xTotal, long xMax, long xMin, File tmp, 
			String s1, String s2, String s3, String s4, String s5){
		String tmpName = tmp.getName().toLowerCase();
		
		yNum = xNum; yTotal = xTotal; yMax = xMax; yMin = xMin;
		if(tmpName.endsWith(s1) || tmpName.endsWith(s2) || tmpName.endsWith(s3) 
				|| tmpName.endsWith(s4) || tmpName.endsWith(s5)){
			xNum++;													//count x number
			xTotal += tmp.length();									//count x size
			xMax = (xMax > tmp.length()) ? xMax : tmp.length();		//find x max size
			xMin = (xMin < tmp.length()) ? xMin : tmp.length();		//find x min size
			
			yNum = xNum; yTotal = xTotal; yMax = xMax; yMin = xMin;
		}
	}
}
