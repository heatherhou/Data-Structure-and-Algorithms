import java.util.*;
/** @author Yanan Hou 
 */
/**
 * Compute statistics on Percolation after performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	
	private double[] result;
	
	
	/** 
	 * Generate statistics for Percolation execution
	 * Store the ratio of numbers opened in the result for T times
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T)
	{
		
		if (N <= 0 || T<= 0) // check if N and T are in bounds
			throw new IllegalArgumentException("N or T are out of bounds");
		result = new double[T];
		for ( int i = 0; i< T; i++)
		{
			IUnionFind find = new QuickUWPC(N);
			IPercolate each = new PercolationUF(N,find);
			//IPercolate each = new PercolationDFS(N);
			ArrayList <int[]> list = new ArrayList<>(N*N);
			for ( int m = 0; m< N; m++)
				for ( int n = 0 ; n < N; n++)
				{
					list.add(new int[]{m,n});
				}
			Collections.shuffle(list,ourRandom); 
			for (int j = 0 ; !each.percolates() && (j < N* N);j++)
			{
				each.open(list.get(j)[0], list.get(j)[1]);
			}
			result[i] = each.numberOfOpenSites() /(N*N*1.0);
		}	

	}
	
	public double mean ()
	{
		return StdStats.mean(result);
	}
	
	public double stddev()
	{
		return StdStats.stddev(result);
	}
	
	public double confidenceLow()
	{
		return (mean() - ((1.96 * stddev())/Math.sqrt(result.length)));
	}
	public double confidenceHigh()
	{
		return (mean() + (1.96 * stddev())/Math.sqrt(result.length));
	}
	public static void main(String[] args) {
//		PercolationStats  ps  =  new  PercolationStats(10,10); 
//		System. out .println( ps .mean());
//		ps  =  new  PercolationStats(20,10);
//		System. out .println( ps.confidenceLow());
//		
		int[] number = { 50, 100, 200,400};
		for ( int i : number)
		{
			double start =    System.nanoTime();
			PercolationStats  ps  = new PercolationStats(i,100); 
			double end =    System.nanoTime();
			double time =    (end-start)/1e9;
			//System.out.printf("n = %d, mean: %1.4f, time: %1.4f\n",i, ps.mean(),time);
		}

	}
}
