package hw2;
import  edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private Percolation p1;
    private double[] threshold;
    public PercolationStats(int N, int T, PercolationFactory pf) throws java.lang.IllegalArgumentException {   // perform T independent experiments on an N-by-N grid
        if (T < 1){
            throw new java.lang.IllegalArgumentException("Error:Time must be at least 1");
        }
        this.N = N;
        this.T = T;
        threshold = new double[T];
        for (int i = 0; i < T; i++) {
            p1 = pf.make(N);
            while(!p1.percolates()) {
                int siteIndex = StdRandom.uniform(N * N);
                p1.open(siteIndex / N, siteIndex % N);
            }
            threshold[i] = (double)p1.numberOfOpenSites() / (N * N);
        }
    }

    public double mean(){     // sample mean of percolation threshold
        return StdStats.mean(threshold);
    }
    public double stddev() {   // sample standard deviation of percolation threshold
        return StdStats.stddev(threshold);
    }
    public double confidenceLow() { // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceHigh() {      // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
