/**
 * Author: Dayvison Cordeiro
 * Date: 24-Mar-2024
 * Last Updated: 24-Mar-2024
 *
 * Description: A program to estimate the value of the percolation threshold via Monte Carlo simulation.
 *
 * Compilation: javac -cp path_to_algs4 PercolationStats.java
 * Execution: java-algs4 PercolationStats 200 100
 *
 * Purpose of the program: This program implements a Monte Carlo simulation to estimate the percolation threshold
 * in a given grid using the Percolation class.
 *
 * Assignment link: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 * Score: 92/100
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] results;
    private final int n;
    private final int trials;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(0 >= n || 0 >= trials){
            throw new IllegalArgumentException("n or trials must be grater than 0");
        }
        this.n = n;
        this.trials = trials;
        results = new double[trials];

        for(int i = 0; i < trials; i ++){
            results[i] = runTrial(this.n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(this.trials));
    }

    private double runTrial(int n){
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()){
            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);
            if (!percolation.isOpen(row, col)){
                percolation.open(row,col);
            }
        }
        return (double) percolation.numberOfOpenSites() / (this.n * this.n);
    }

    // test client
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

}