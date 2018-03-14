
/** @author Yanan Hou 
 *  Implement PercolationDFS in more efficient way
 *  by only checking the cell being opened to see 
 *  if it results in more  FULL  cells,
 *  rather than checking every cell reachable from the top row.
 */
public class PercolationDFSFast extends PercolationDFS{

	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationDFSFast(int n) {
		super(n);

	}

	@Override
	public void open (int row, int col)
	{
		if(!inBounds(row,col))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else 
			super.open(row, col);
	}
	
	@Override 
	public boolean isOpen(int row, int col)
	{
		if(!inBounds(row,col))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else 
			return super.isOpen(row, col);
	}
	
	@Override 
	public boolean isFull(int row, int col)
	{
		if(!inBounds(row,col))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		else 
			return super.isFull(row, col);
	}
	
	/**
	 * Update open grid and connect with neighbor grid
	 * @param row specifies row
	 * @param col specifies column
	 */
	@Override 
	protected void updateOnOpen(int row, int col)
	{
		if(!inBounds(row,col))
			throw new IndexOutOfBoundsException("The row and column is out of bounds");
		
		
		if ( inBounds(row,col) && isFull(row, col) 
				|| inBounds(row-1,col) &&isFull(row-1, col) 
				|| inBounds(row, col -1) && isFull(row, col -1) 
				|| inBounds(row, col +1)&& isFull(row, col +1) 
				|| inBounds(row +1, col)&& isFull (row +1, col) ) 
		{
			myGrid[row][col] = FULL;
			dfs(row -1, col);
			dfs(row,col -1);
			dfs(row, col +1);
			dfs(row +1, col);
		}

		
	}
}
