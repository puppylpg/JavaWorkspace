import java.util.Comparator;


public class FrequencyComparator implements Comparator<WordInfo>{
//Overview: a rule to compare two Objects
	//REQUIRES: two WordInfo objects
	//MODIFIES: none
	//EFFECTS: set a rule to resort a array according to frequency
	@Override
	public int compare(WordInfo o1, WordInfo o2) {
		// TODO Auto-generated method stub
		double freq1 = (double)o1.getFileWordTimes() / o1.getWordTimes();
		double freq2 = (double)o2.getFileWordTimes() / o2.getWordTimes();
		int num = new Double(freq2).compareTo(new Double(freq1));
		if(num == 0){
			int number = o1.getFileName().compareTo(o2.getFileName());
			if(number == 0){
//				return o1.getF().compareTo(o2.getF());    		// NullPointerException
				return 1;
			}
			return number;
		}
		return num;
	}
	
	public boolean repOK(){
		return true;
	}

}