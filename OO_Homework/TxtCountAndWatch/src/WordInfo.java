
public class WordInfo {
//Overview: to get some properties from the class
	/* AF(c) = (word, fileName, ff, f, b, bb, wordTimes, fileWordTimes) where
	 * 		word = word,
	 * 		fileName = fileName,
	 * 		ff = ff,
	 * 		f = f,
	 * 		b = b,
	 * 		bb = bb,
	 * 		wordTimes = wordTimes,
	 * 		fileWordTimes = fileWordTimes}
	 */
	private String word = null;
	
	private String fileName = null;
	private String ff = null;				// forward forward
	private String f = null;				// forward
	private String b = null;				// backward
	private String bb = null;				// backward backward
	private int wordTimes = 0;
	private int fileWordTimes = 0;
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return word
	public String getWord() {
		return word;
	}
	
	//REQUIRES: a string
	//MODIFIES: word
	//EFFECTS: set the value of word
	public void setWord(String word) {
		this.word = word;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return fileName
	public String getFileName() {
		return fileName;
	}
	
	//REQUIRES: a string
	//MODIFIES: fileName
	//EFFECTS: set the value of fileName
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return forward forward
	public String getFf() {
		return ff;
	}
	
	//REQUIRES: a string
	//MODIFIES: ff
	//EFFECTS: set the value of ff
	public void setFf(String ff) {
		this.ff = ff;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return f
	public String getF() {
		return f;
	}
	
	//REQUIRES: a string
	//MODIFIES: f
	//EFFECTS: set the value of f
	public void setF(String f) {
		this.f = f;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return b
	public String getB() {
		return b;
	}
	
	//REQUIRES: a string
	//MODIFIES: b
	//EFFECTS: set the value of b
	public void setB(String b) {
		this.b = b;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return bb
	public String getBb() {
		return bb;
	}
	
	//REQUIRES: a string
	//MODIFIES: bb
	//EFFECTS: set the value of bb
	public void setBb(String bb) {
		this.bb = bb;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return wordTimes
	public int getWordTimes() {
		return wordTimes;
	}
	
	//REQUIRES: a integer that is not less than 0
	//MODIFIES: wordTimes
	//EFFECTS: set the value of wordTimes
	public void setWordTimes(int wordTimes) {
		this.wordTimes = wordTimes;
	}
	
	//REQUIRES: none
	//MODIFIES: none
	//EFFECTS: return fileWordTimes
	public int getFileWordTimes() {
		return fileWordTimes;
	}
	
	//REQUIRES: a integer that is not less than 0
	//MODIFIES: fileWordTimes
	//EFFECTS: set the value of fileWordTimes
	public void setFileWordTimes(int fileWordTimes) {
		this.fileWordTimes = fileWordTimes;
	}
	
	public boolean repOK(){
		if(this.wordTimes < 0 || this.fileWordTimes < 0){
			return false;
		}
		return true;
	}

}
