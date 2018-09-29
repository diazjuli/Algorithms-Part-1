import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] results;
    private int num_trial;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int row;
        int col;
        int count;
        Percolation perc;

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trials must be greater than 0");

        results = new double[trials];
        num_trial = trials;

        for (int i = 0; i < trials; i++){
            perc = new Percolation(n);
            count = 0;

            while (!perc.percolates()){
                row = StdRandom.uniform(1, n+ 1);
                col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col))
                {
                    count++;
                    perc.open(row, col);
                }
            }
            results[i] = count/ (n * n);
        }
    }
    public double mean(){
        return StdStats.mean(results);
    }
    public double stddev(){
        return StdStats.stddev(results);
    }
    public double confidenceLo(){
        return (this.mean() - 1.96 * this.stddev() / Math.sqrt((double)this.num_trial));
    }
    public double confidenceHi(){
        return (this.mean() + 1.96 * this.stddev() / Math.sqrt((double)this.num_trial));
    }

    public static void main(String[] args) {
        int n;
        int trials;
        PercolationStats percStats;

        n = Integer.parseInt(args[0]);
        trials = Integer.parseInt(args[1]);
        percStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = " + percStats.confidenceLo() + ", " + percStats.confidenceHi());
    }
}
