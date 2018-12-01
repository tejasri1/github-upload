/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] data;
    private Double mean;
    private Double stdDev;
    private final double T;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials < 0 || n < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        data = new double[trials];
        T = trials;
        int index = 0;

        while (trials > 0) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                // StdOut.println(i + " " + j);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                }

            }
            data[index++] = (perc.numberOfOpenSites() / ((double) n * n));
            trials--;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean == null) {
            mean = StdStats.mean(data);
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stdDev == null) {
            stdDev = StdStats.stddev(data);
        }
        return stdDev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(T)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(T)));
    }

    // test client (described below)
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(m, n);
        StdOut.println("mean: " + p.mean());
        StdOut.println("stddev: " + p.stddev());
        StdOut.println("Interval: [" + p.confidenceLo() + " , "
                               + p.confidenceHi() + "]");

    }
}
