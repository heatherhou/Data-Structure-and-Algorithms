/** @author Yanan Hou 
 */
/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
	private final int OUT_BOUNDS = -1;
	private boolean[][] myGrid;
	private IUnionFind myFinder;
	private int numberOpen;
	private final int VTOP;
	private final int VBOTTOM; 
	private int size;
	/**
	 * Constructs a Percolation object for a nxn grid that that creates
	 * a IUnionFind object to determine whether cells are full
	 */
	public PercolationUF(int n, IUnionFind finder) {
		
		myFinder = finder;
		myFinder.initialize(n*n + 2);
		size = n;
		myGrid = new boolean [n][n];
		VTOP = size * size;
		VBOTTOM = size * size + 1;
	}

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 * @param row specifies row
	 * @param col specifies column
	 */
	private int getIndex(int row, int col) {
		if (!inBounds(row,col))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else 
			return row * size + col;
	}

	public void open(int i, int j) {
		if (!inBounds(i,j))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else
		{
			if (myGrid[i][j] != false)
				return;
			myGrid[i][j] = true;
			updateOnOpen(i,j);
		}
		
	}

	public boolean isOpen(int i, int j) {
		if (!inBounds(i,j))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else
			return myGrid[i][j];
	}

	public boolean isFull(int i, int j) {
		if (!inBounds(i,j))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else
		{
			int index = getIndex(i,j);
			return myFinder.connected(index, VTOP);
		}
	}

	/** 
	 * Return the number of open sites
	 */
	public int numberOfOpenSites() {
		int count = 0;
		for (int i =0; i< size; i++)
			for (int j = 0; j<size ;j++)
			{
				if (inBounds(i,j) && isOpen(i,j))
					count++;
			}
		numberOpen = count;
		return count;
	}

	public boolean percolates() {
		return (myFinder.connected(VTOP, VBOTTOM));
	}

	/**
	 * Connect new site (row, col) to all adjacent open sites
	 * if site is on the first/last row, connect it to VTOP/VBOTTOM
	 * @param row specifies row
	 * @param col specifies column
	 */

	private void updateOnOpen(int row, int col)
	{


		if (!inBounds(row,col))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else 
		{
			int index = getIndex(row,col);
			
			if (row == 0)
				myFinder.union(index, VTOP);
			if (row == size-1)
				myFinder.union(index, VBOTTOM);
			
			if (inBounds(row -1, col) && isOpen(row -1,col))
			{
				int up = getIndex(row -1, col);
				myFinder.union(index, up);
			}
			
			if (inBounds(row, col -1) && isOpen(row, col -1))
			{
				int left = getIndex(row, col -1);
				myFinder.union(index, left);	
			}
			
			if (inBounds(row,col + 1) && isOpen(row,col +1))
			{
				int right = getIndex(row, col +1);
				myFinder.union(index, right);
			}
			
			
			if(inBounds(row+ 1, col) && isOpen(row + 1, col))
			{
				int down = getIndex(row+1,col);
				myFinder.union(index,down);
			}
			
			
		}
		
	}
	
	private boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
}
