import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class EfficientWordMarkov extends WordMarkovModel {
	Map <WordGram, ArrayList <String>> myMap;
	
	public EfficientWordMarkov()
	{
		super(2);
	}
	public EfficientWordMarkov(int order)
	{
		super(order);
		myMap = new TreeMap <WordGram,ArrayList <String>>();
	}
	/** Convert string text to wordGram 
	 *  Build the map with wordGram key
	 *  @param text
	 */
	@Override
	public void setTraining (String text)
	{
		super.setTraining(text);
		myMap.clear();
		for (int i = 0 ;i <= myWords.length - myOrder; i++)
		{
			WordGram key = new WordGram (myWords,i,myOrder);
			String next = new String();
			if ( i!= myWords.length - myOrder)
			{ 
				next = myWords[i + myOrder];
				
			}
			else 
				next = PSEUDO_EOS;
			
			if (myMap.containsKey(key))
			{
				ArrayList <String> update = new ArrayList <String> (myMap.get(key));
				update.add(next);
				myMap.put(key,update);
			}else 
			{
				ArrayList <String> update = new ArrayList <String> (Arrays.asList(next));
				myMap.put(key, update);
			}			
		}
	}

	@Override 
	public ArrayList <String> getFollows (WordGram key)
	{
		if (myMap.containsKey(key))
			return myMap.get(key);
		else
			throw new NoSuchElementException();
		
	}
}
