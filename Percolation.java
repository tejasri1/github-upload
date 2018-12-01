/* *****************************************************************************
 *  Name: Thota Tejasri
 *  Date: 12/1/2018
 *  Description: Assignment - 1
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int len;
    private int openCount;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new java.lang.IllegalArgumentException();

        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF((n * n) + 2);
        len = n;
        openCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        if (row < 1 || col < 1 || row > len || col > len) {
            throw new java.lang.IllegalArgumentException();
        }

        int index = (len * (row - 1)) + (col - 1) + 1;

        if (row == 1) {
            uf.union(0, index);
        }
        if (row == len) {
            uf.union((len * len) + 1, index);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(index, index - len);
        }
        if (row < len && isOpen(row + 1, col)) {
            uf.union(index, index + len);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(index, index - 1);
        }
        if (col < len && isOpen(row, col + 1)) {
            uf.union(index, index + 1);
        }


        grid[row - 1][col - 1] = true;
        openCount++;

    }


    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > len || col > len) {
            throw new java.lang.IllegalArgumentException();
        }

        return grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > len || col > len) {
            throw new java.lang.IllegalArgumentException();
        }
        int index = (len * (row - 1)) + (col - 1) + 1;
        return uf.connected(0, index);

    }

    // number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, (len * len) + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(StdIn.readInt());
        // p.open(1, 1);
        StdOut.print(p.isFull(1, 1));
        // StdOut.print(p.isOpen(1, 1));
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            // StdOut.println("row: " + row + " col : " + col);
            p.open(row, col);
        }

        StdOut.print(p.numberOfOpenSites());
    }

}
