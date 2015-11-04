import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class CountTxt implements Runnable{
//Overview: count the directories pointed and list the words information
	/* AF(c) = (path, pathHasBeenReadFlag) where
	 * 		path = sc.nextLine(),
	 * 		pathHashBeenReadFlag = pathHashBeenReadFlag}
	 */
	private static String path = null;
	private static String word = null;
	private static Scanner sc = new Scanner(System.in);
	private static int txtNum = 0;
	private int wordNum = 0;
	private static int validNum = 0;
	 
	
	private static HashMap< String, ArrayList<WordInfo> > hashMap;
	//< a word, an arrayList which contains the word's location and surrounding info>
	private HashMap<String, HashMap<String, Integer>> timesMap = new HashMap<String, HashMap<String, Integer>>();
	//< file name, <a word, word appear time> >
	
	public boolean repOK(){
		if(txtNum < 0 || this.wordNum < 0 || validNum < 0)
			return false;
		return true;
	}

	//REQUIRES: A hashMap
	//MODIFIES: CountTxt.hashMap
	//EFFECTS: set hashMap to a specific one
	public void setHashMap(HashMap< String, ArrayList<WordInfo> > hashMap){
		CountTxt.hashMap = hashMap;
	}

	private boolean flag = true;			// only at the first time output the counting info
	private static boolean pathHasBeenReadFlag = false;		// used by Thread Timer
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return CountTxt.path
	public String getPath(){
		return CountTxt.path;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return pathHasBeenReadFlag
	public boolean getPathHasBeenReadFlag(){
		return CountTxt.pathHasBeenReadFlag;
	}
	
	//REQUIRES: System.in
	//MODIFIES: path, word
	//EFFECTS: read path&word, and output information
	public void inputString(){
		try{													//TODO:
			System.out.print("Input a valid path please:");
			CountTxt.path = sc.nextLine();
			
			CountTxt.pathHasBeenReadFlag = true;
			scanDirectory(CountTxt.path);						// begin to scan .txt files in Directory
			
			if(flag == true){									// output, only at the first time
				System.out.println("The number of text files are: " + CountTxt.txtNum);
				System.out.println("The number of words extracted are :" + CountTxt.validNum);
				flag = false;
			}
			
			while(true){
				CountTxt.word = sc.nextLine();
				if( hashMap.containsKey(CountTxt.word) == false){
					System.out.println("No such word exists!\n Try again------");
				}
				else{
					ArrayList<WordInfo> al = CountTxt.hashMap.get(CountTxt.word);
					al.sort(new FrequencyComparator());
//					Arrays.sort(al, new FrequencyComparator());
					for(WordInfo wi : al){
//						System.out.println(wi.getFileName() + "(" + wi.getWordTimes() + ")");	// output filename & the times of the word
						System.out.println(wi.getFileName() + "(" + (double)wi.getWordTimes()/wi.getFileWordTimes() + ")" + ": ");	//filename & frequency
						if(wi.getFf() != null){
							System.out.print(wi.getFf() + " ");				//ff
						}
						if(wi.getF() != null){								
							System.out.print(wi.getF() + " ");				//f
						}
						System.out.print(CountTxt.word + " ");				//word itself
						if(wi.getB() != null){
							System.out.print(wi.getB() + " ");				//b
						}
						if(wi.getBb() != null){
							System.out.print(wi.getBb() + " ");				//bb
						}
						System.out.println("");
						System.out.println("");
					}
				}
			}
			
		}catch(Exception e){
			System.out.println("Invalid input!");
			System.exit(1);
		}
		
	}
	
	//REQUIRES: a String path
	//MODIFIES: hashMap
	//EFFECTS: Scan the directories recursively, and put the information into hashMap
	public void scanDirectory(String path){
		if(path.equals(CountTxt.path)){		//ONLY when TIMER start scanning, clear. this judge is IMPORTANT
			CountTxt.hashMap.clear();		//Every time scanning begins, clear the hashMap
		}
		this.timesMap.clear();
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
			for(File tmp : fList){
				if(tmp.getName().toLowerCase().endsWith(".txt")){
					CountTxt.txtNum++;
					extractWords(tmp.getAbsolutePath());					//TODO: count the text file
				}
				else if(tmp.isDirectory()){
					scanDirectory(tmp.getAbsolutePath());
				}
			}
		}
	}
	
	//REQUIRES: a String fileName
	//MODIFIES: wordTimes
	//EFFECTS: Scan the directories recursively, and put the information into hashMap & wordTimes
	public void extractWords(String fileName){
		HashMap<String, Integer> wordTimes = new HashMap<String, Integer>();		// <a word, appear times>
		this.timesMap.put(fileName, wordTimes);				// connect the fileName and wordTimes map
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(fileName));
		}catch(Exception e){
			System.out.println("Error! Opening new buffer for file fails!");
		}
		String tmpString = null;							// A good way to read a file and convert to a string
        StringBuffer sb = new StringBuffer();				//sb means StringBuffer
        try {
			while ((tmpString = br.readLine()) != null) {
			    sb.append(tmpString);						//read the file context into stringbuffer
			    sb.append('\n');							//readLine will not read '\n'
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for(char c : sb.toString().toCharArray()){			// examine whether the text file is valid
        	if( !(c >= 'A' && c <='Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' ||
        			c == ',' || c == '.' || c == '?' || c == ':' || c =='!' || 
        			c == '\n' || c == '\t' || c == ' ')){
        		System.out.println("A fake text file is found!");
        		System.out.println("Unexpected characters are found in " + fileName);
        		System.exit(1);
        	}
        }
        
        String [] words = sb.toString().split(",|\\.|\\?|:|!|\\n|\\t| ");		// result in blank string when appear together
//      String [] test = sb.toString().split("(,|\\.|\\?|:|!|\\n|\\t| )+");		//TODO: won't appear so many blank string
        List<String> l = new ArrayList<String>();		// A good way to delete special element in array
        for(String s : words){
        	l.add(s);
        }
        while(l.contains("")){
        	l.remove("");
        }
        words = l.toArray(new String [l.size()]);
        
        this.wordNum = words.length;					// calculate the total number of this file
        
        
        for(int i = 0; i < words.length; i++){			// calculate the context word by word
        	String tmp = words[i];
        	
        	if(wordTimes.containsKey(tmp)){				// count everyword's frequency
        		wordTimes.put(tmp, wordTimes.get(tmp) + 1);
        	}
        	else{
        		wordTimes.put(tmp, 1);
        	}
        	
        	WordInfo wordInfo = new WordInfo();			//new a wordinfo to memorize the word-information
        	wordInfo.setWord(tmp);
        	wordInfo.setFileName(fileName);
        	if(i >= 2){
        		wordInfo.setFf(words[i-2]);
        	}
        	if(i >= 1){
        		wordInfo.setF(words[i-1]);
        	}
        	if(i + 1 < words.length){
        		wordInfo.setB(words[i+1]);
        	}
        	if(i + 2 < words.length){
        		wordInfo.setBb(words[i+2]);
        	}
        	
        	if( !hashMap.containsKey(tmp) ){			// add word into hashMap
        		ArrayList<WordInfo> al = new ArrayList<WordInfo>();
        		al.add(wordInfo);		
        		hashMap.put(tmp, al);
        		CountTxt.validNum++;					// count the number of what is put into hashMap
        	}
        	else{
        		hashMap.get(tmp).add(wordInfo);
        	}
        }
        
        for(int i = 0; i < words.length; i++){			// put the word times information into WordInfo
        	String tmp = words[i];
        	
        	int hehe = wordTimes.get(tmp);
        	ArrayList<WordInfo> al = hashMap.get(tmp);
        	for(WordInfo wi : al){
        		if(wi.getFileName().equals(fileName)){
        			wi.setWordTimes(hehe);
        			wi.setFileWordTimes(this.wordNum);
        		}
        	}
        }
        
	}

	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: override run() so that can start a thread
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{											//TODO:
			inputString();
		}catch(Exception e){
			System.out.println("Error!!! Please input again!");
		}
	}
	
}
