import java.util.*;
public class EfficientMarkov extends MarkovModel{
	 Map <String, ArrayList<String>> myMap;
	/**Constructor with parameter order 
	 * Create a hash map for myMap
	 * @param order
	 */
	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap <String, ArrayList <String>>();
	}
	
	/**Get the value of specific arraylist
	 * associated with given key in parameter
	 * @param key
	 * @return the new ArrayList of the following value associated with key
	 */
	@Override
	public ArrayList <String> getFollows (String key)
	{
		if (myMap.containsKey(key))
			return myMap.get(key);
		else 
			throw new NoSuchElementException(); //if key not found, throw an exception
	}
	
	/** Build the map with string text
	 * @param text
	 */
	@Override 
	public void setTraining (String text)
	{
		super.setTraining(text); //set the text in the super class
		myMap.clear();
		
		//read from each index of text and store the key and the character on next index
		for ( int i = 0; i <= text.length()-myOrder; i++)
		{
			String each = text.substring(i,i+myOrder);
			String next = new String();
			if (i != text.length()-myOrder) 
			{
				next = Character.toString(text.charAt (i+myOrder));
			}else  //if the one at i index is the last k-order string, use the random text
				next = PSEUDO_EOS;
			
			if (myMap.containsKey(each)) // Check if the key already exists.
			{
				ArrayList<String> update = myMap.get(each);
				update.add(next);
				myMap.put(each, update);
			}else  // key doesn't exist, create one
			{
				ArrayList <String> update = new ArrayList <String> (Arrays.asList(next));
				myMap.put(each, update );
			}
		}
	}
	
	
}
