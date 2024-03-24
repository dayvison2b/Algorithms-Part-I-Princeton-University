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

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    private final int n;
    private final boolean[][] sites;
    private int openSites;
    private final WeightedQuickUnionUF uf;
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0){ throw new IllegalArgumentException("Invalid grid size");}
        this.n = n;
        sites = new boolean[n][n];
        int size = n * n + 2;
        uf = new WeightedQuickUnionUF(size);
        virtualTop = 0;
        virtualBottom = size - 1;
        openSites = 0;

        for(int i = 0; i < n; i++){
            uf.union(getIndex(1, i + 1), virtualTop);
            uf.union(getIndex(n, i + 1), virtualBottom);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        validateIndices(row, col);
        if(!isOpen(row, col)){
            int index = getIndex(row, col);

            sites[row - 1][col - 1] = true;
            openSites ++;

            unionNeighbors(row, col, index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validateIndices(row, col);
        return sites[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validateIndices(row, col);
        if(isOpen(row, col)){
            int index = getIndex(row, col);
            return uf.find(index) == uf.find(virtualTop);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(virtualBottom) == uf.find(virtualTop);
    }

    // Connect the site at row and column with its open neighbors
    private void unionNeighbors(int row, int col, int index){
        // up
        if(1 <= row - 1 && isOpen(row - 1, col)){
            uf.union(index, getIndex(row - 1, col));
        }
        // down
        if(this.n >= row + 1 && isOpen(row + 1, col)){
            uf.union(index, getIndex(row + 1, col));
        }
        // left
        if(1 <= col - 1 && isOpen(row, col - 1)){
            uf.union(index, getIndex(row, col - 1));
        }
        // right
        if(this.n >= col + 1 && isOpen(row, col + 1)){
            uf.union(index, getIndex(row, col + 1));
        }
    }

    // Transform the (row,col) in Union-Find index
    private int getIndex(int row, int col){
        return (row - 1) * n + col;
    }

    // Validate if the row and column indices are within the grid bounds
    private void validateIndices(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row and col indices are not valid!");
        }
    }

     //test client (optional)
    public static void main(String[] args){
        Percolation percolation = new Percolation(2);

        percolation.open(1, 1);
        percolation.open(2, 1);


        System.out.println("Number of open sites: " + percolation.numberOfOpenSites());
        System.out.println("Does it percolate? " + percolation.percolates());
        System.out.println("Is site (2, 1) full? " + percolation.isFull(2, 1));
        System.out.println("Is site (1, 1) full? " + percolation.isFull(1, 1));
    }
}