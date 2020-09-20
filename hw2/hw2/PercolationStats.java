package hw2;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int n;
    private double[] threshold;

    public PercolationStats(int N, int T, PercolationFactory pf) { // DONE: perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = N;
        this.threshold = new double[T];

        for (int t = 0; t < T; t++) {
            Percolation test = pf.make(N);                        // generate a new system
            while (!test.percolates()) {
                test.openSite();
            }
            this.threshold[t] = 1.0 * test.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {                                         // DONE: sample mean of percolation threshold
        return StdStats.mean(this.threshold);
    }

    public double stddev() {                                       // DONE: sample standard deviation of percolation threshold
        return StdStats.stddev(this.threshold);
    }

    public double confidenceLow() {                                // DONE: low endpoint of 95% confidence interval
        double mean = this.mean();
        double stddev = this.stddev();
        return mean - 1.96 * stddev / Math.sqrt(1.0 * this.threshold.length);
    }

    public double confidenceHigh() {                               // DONE: high endpoint of 95% confidence interval
        double mean = this.mean();
        double stddev = this.stddev();
        return mean + 1.96 * stddev / Math.sqrt(1.0 * this.threshold.length);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats pstat = new PercolationStats(20, 5000, pf);
        System.out.println("mean: " + pstat.mean());
        System.out.println("std: " + pstat.stddev());
        System.out.println("CI: [" + pstat.confidenceLow() + ", " + pstat.confidenceHigh() + "]");
    }
}
