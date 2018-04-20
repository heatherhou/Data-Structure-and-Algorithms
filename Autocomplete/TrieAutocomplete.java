import java.util.*;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * General trie/priority queue algorithm for implementing Autocompletor
 * 
 * @author Austin Lu
 * @author Jeff Forbes
 */
public class TrieAutocomplete implements Autocompletor {

	/**
	 * Root of entire trie
	 */
	protected Node myRoot;

	/**
	 * Constructor method for TrieAutocomplete. Should initialize the trie rooted at
	 * myRoot, as well as add all nodes necessary to represent the words in terms.
	 * 
	 * @param terms
	 *            - The words we will autocomplete from
	 * @param weights
	 *            - Their weights, such that terms[i] has weight weights[i].
	 * @throws NullPointerException
	 *             if either argument is null
	 * @throws IllegalArgumentException
	 *             if terms and weights are different length
	 */
	public TrieAutocomplete(String[] terms, double[] weights) {
		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		// Represent the root as a dummy/placeholder node
		myRoot = new Node('-', null, 0);
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < terms.length; i++) {
			if (weights[i] < 0) 
				throw new IllegalArgumentException("Negative weight " + weights[i]);
			add(terms[i], weights[i]);
			list.add(terms[i]);
		}
		if (list.size() != terms.length)
			throw new IllegalArgumentException("Duplicate input terms");
	}

	/**
	 * Add the word with given weight to the trie. If word already exists in the
	 * trie, no new nodes should be created, but the weight of word should be
	 * updated.
	 * 
	 * In adding a word, this method should do the following: Create any necessary
	 * intermediate nodes if they do not exist. Update the subtreeMaxWeight of all
	 * nodes in the path from root to the node representing word. Set the value of
	 * myWord, myWeight, isWord, and mySubtreeMaxWeight of the node corresponding to
	 * the added word to the correct values
	 * 
	 * @throws a
	 *             NullPointerException if word is null
	 * @throws an
	 *             IllegalArgumentException if weight is negative.
	 */
	private void add(String word, double weight) {
		// TODO: Implement add
		Node current = myRoot;
		for (int k =0; k<word.length();k++)
		{
			char ch = word.charAt(k);
			if (current.children.get(ch) == null)
				current.children.put(ch, new Node(ch,current,weight));
			if(current.mySubtreeMaxWeight < weight)
				current.mySubtreeMaxWeight = weight;
			current = current.children.get(ch);
			
		}
		current.isWord = true;
		current.myWord = word;
		current.myWeight = weight;
	}

	/**
	 * Required by the Autocompletor interface. Returns an array containing the k
	 * words in the trie with the largest weight which match the given prefix, in
	 * descending weight order. If less than k words exist matching the given prefix
	 * (including if no words exist), then the array instead contains all those
	 * words. e.g. If terms is {air:3, bat:2, bell:4, boy:1}, then topKMatches("b",
	 * 2) should return {"bell", "bat"}, but topKMatches("a", 2) should return
	 * {"air"}
	 * 
	 * @param prefix
	 *            - A prefix which all returned words must start with
	 * @param k
	 *            - The (maximum) number of words to be returned
	 * @return An Iterable of the k words with the largest weights among all words
	 *         starting with prefix, in descending weight order. If less than k such
	 *         words exist, return all those words. If no such words exist, return
	 *         an empty Iterable
	 * @throws a
	 *             NullPointerException if prefix is null
	 */
	public Iterable<String> topMatches(String prefix, int k) {
		Node current = myRoot;
		if(prefix == null)
			throw new NullPointerException();
		if (k < 0) {
			throw new IllegalArgumentException("Illegal value of k:"+k);
		}
		if (k == 0) {
			return new LinkedList<>();
		}

		for (int i =0; i< prefix.length();i++)
		{
			if (!current.children.containsKey(prefix.charAt(i)))
				return new LinkedList<>();
			current = current.children.get(prefix.charAt(i));
			
		}
		PriorityQueue<Node> nodePQ = new PriorityQueue<Node>(new Node.ReverseSubtreeMaxWeightComparator());
		PriorityQueue<Term> termPQ = new PriorityQueue<Term>(k, new Term.WeightOrder());
		
		nodePQ.add(current);
		LinkedList<String> list = new LinkedList<>();
		while (nodePQ.size()>0)
		{
			current= nodePQ.remove();
			if(current.isWord)
			{
				termPQ.add(new Term(current.getWord(),current.getWeight()));
			}
			if(termPQ.size()>k)
				termPQ.remove();
			if(!nodePQ.isEmpty() && termPQ.size()==k)
				if (termPQ.peek().getWeight()>nodePQ.peek().getWeight())
					break;
			for (Node n:current.children.values())
			{
				nodePQ.add(n);
			}
		}
		while(termPQ.size()>0)
		{
			list.addFirst(termPQ.remove().getWord());
		}
		
		return list;
	}

	/**
	 * Given a prefix, returns the largest-weight word in the trie starting with
	 * that prefix.
	 * 
	 * @param prefix
	 *            - the prefix the returned word should start with
	 * @return The word from with the largest weight starting with prefix, or an
	 *         empty string if none exists
	 * @throws a
	 *             NullPointerException if the prefix is null
	 */
	public String topMatch(String prefix) {
		Node current = myRoot;
		if(prefix == null)
			throw new NullPointerException();

		for (int i =0; i< prefix.length();i++)
		{
			if (!current.children.containsKey(prefix.charAt(i)))
				return "";
			current = current.children.get(prefix.charAt(i));
			
		}
		while(current.mySubtreeMaxWeight!= current.myWeight)
		{
//			for (Node child: current.children.values())
//			{
//				if (child.myWeight==current.mySubtreeMaxWeight)
//					current = child;
//			}
				
			current = Collections.min(current.children.values(), new Node.ReverseSubtreeMaxWeightComparator());
		}
			
		return current.myWord;
	}

	/**
	 * Return the weight of a given term. If term is not in the dictionary, return
	 * 0.0
	 */
	public double weightOf(String term) {
		Node current = myRoot;
		for (int i =0; i< term.length();i++)
		{
			if (!current.children.containsKey(term.charAt(i)))
				return 0;
			current = current.children.get(term.charAt(i));
			
		}
		if (current.isWord)
			if (current.myWord.equals(term))
				return current.getWeight();
		return 0;
	}
}
