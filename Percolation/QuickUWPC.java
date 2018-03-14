/** @author Yanan Hou 
 */
public class QuickUWPC implements IUnionFind {
	 private int[] parent;   // parent[i] = parent of i
	    private int[] size;     // size[i] = number of sites in subtree rooted at i
	    private int count;      // number of components

	public 	QuickUWPC()
	{
		this(10);
	}
	public QuickUWPC(int n)
	{
		initialize(n);
	}

	/**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own 
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
	@Override
	public void initialize(int n) {
		count = n;
		parent = new int[n];
	    size = new int[n];
	    for (int i = 0; i < n; i++) {
	    		parent[i] = i;
	    		size[i] = 1;
	    }
	}
	
	// validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }



    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one object
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
	@Override
	public int find(int p) {
		validate(p);
		int root = p;
		while (root != parent[root])
			root = parent[root];
		while ( p != root)
		{
			int newp = parent[p];
			parent[p] = root;
			p = newp;
		}
		return root;
	}

	/**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
	
	@Override
	public boolean connected(int p, int q) {
		 return find(p) == find(q);
	}

	/**
     * Merge the component containing site {@code p} with the 
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */

	@Override
	public void union(int p, int q) {
		int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
		
	}
	
	/**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
	@Override
	public int components() {
		return count;
	}



}