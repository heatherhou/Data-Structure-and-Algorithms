import java.util.HashSet;
import java.util.Set;

public class WordGram implements Comparable<WordGram>{
	
	private int myHash;
	private String[] myWords ;
	
	/** A constructor store a size string from a 
	 * given string array words from start index 
	 * @param words
	 * @param start
	 * @param size
	 */
	
	public WordGram(String[] words, int start, int size) {
		// complete this constructor
		myWords = new String[size];
		for (int i = start; i< start + size; i++) //store the string from start index 
		{
			myWords[i-start] = words[i];
		}
	
	}
	
	/** Calculate the hash code, 
	 * store it in my Hash 
	 * and return it 
	 * @return
	 */
	@Override
	public int hashCode() {
		if (myHash == 0) //Check if myHash already exist
		{
			int hash = 0;
			for (int i = 0; i< myWords.length;i++)
			{
				hash += Math.pow(myWords[i].hashCode(),i);
			}
			myHash = hash;
			return myHash;
		}else 
			return myHash; // if myHash already exist, return it directly
		
	}
	
	/** Convert myWords array to a string **/
	@Override
	public String toString() {
		String result = new String();
		if (myWords != null)
			result = String.join(" ", myWords);
		else 
			result = "";
		return result;
	}
	
	/** Check if a given object
	 *  equals to the WordGram object. 
	 *  @param other
	 *  @return
	 *  */
	
	@Override
	public boolean equals(Object other) {
		if (other == null || ! (other instanceof WordGram)) {
			return false;
		}
		WordGram wg = (WordGram) other;
		if (compareTo(wg) == 0)
			return true;
		else 
			return false; 
	}
	
	/** Compare with WordGram other,
	 *  return positive value if it is larger
	 *  negative if it is smaller
	 *  0 if they are the same
	 *  @param other
	 *  @return
	 */
	@Override
	public int compareTo(WordGram other) {
	
		if (myWords.length == other.myWords.length)
		{
			for (int k = 0; k< myWords.length; k++)
			{
				int cmp = myWords[k].compareTo(other.myWords[k]);
				if (cmp != 0 ) {
					return cmp;
				}
			}	
		}else if (myWords.length > other.myWords.length)
			return 1;
		else 
			return -1;
			 
		return 0;
		
		
	}
	
	/**return the length of myWords string
	 * @return
	 */
	public int length() {
		return myWords.length;
	}
	
	/** Create a new WordGram object
	 * store part of myWords in it
	 * and add the given string last to the last of the myWords
	 * @param last
	 * @return
	 */
	public WordGram shiftAdd(String last) {
		WordGram wg = new WordGram(this.myWords,0, this.length());
		//Store all the strings in this into a new WordGram object except the last one
		for ( int i = 0; i < wg.length()-1;i++)
		{
			wg.myWords[i] = this.myWords[i+1];
		}
		if (length()!= 0)
			wg.myWords[length()-1] = last; //Separately store the parameter last to wg
		else 
			wg.myWords=null;
		return wg;
	}
	
	public static void main(String[] args)
	{
		String str = "aa bb cc aa bb cc aa bb cc aa bb dd ee ff gg hh ii jj";
		String[] array = str.split("\\s+");
		WordGram[] myGrams = new WordGram[array.length-2];
		for(int k=0; k < array.length-2; k++){
			myGrams[k] = new WordGram(array,k,3);
		}
		
		
		//test shiftadd
		String add = "test";
		WordGram[] testShiftAdd = new WordGram[array.length-2];
		for (int i = 0; i<myGrams.length;i++)
		{
			testShiftAdd[i] = myGrams[i].shiftAdd(add);
		}
		
		for (WordGram elem: testShiftAdd)
		{
			System.out.println(elem.toString());
		}
		
		Set<Integer> set = new HashSet<Integer>();
		for(WordGram w : myGrams) {
			set.add(w.hashCode());
		}
		System.out.println(set.size());
		//test toString
		String[] words = {"apple", "zebra", "mongoose", "hat","cat"};
		WordGram a = new WordGram (words, 0,4);
		
		String as = a.toString();
		System.out.print(as);
	}
}
