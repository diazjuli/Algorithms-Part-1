import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    
    private WeightedQuickUnionUF weightedQu;
    private boolean[][] value;
    private int len;
    private int num_open_sites;
    
    public Percolation(int n){
        if (n < 1){
            throw new IllegalArgumentException("n must be greater than 0");
        }
        weightedQu = new WeightedQuickUnionUF(n * n + 2);
        value = new boolean[n][n];
        len = n;
        num_open_sites = 0;
    }

    //This method opens a site if it is not open already.
    // It also creates a connection with any adjacent, open sites
    public void open(int row, int col){
        if (row < 1 || row > this.len || col < 1 || col > this.len)
            throw new IllegalArgumentException("row or column are outside prescribed range");

        //if site was closed, increase number of open sites by 1
        if (!value[row - 1][col- 1])
            num_open_sites += 1;
        //change value of site to open
        value[row - 1][col - 1] = true;
        //if site to open is in top row, make connection to 1st node, else connect to site above if it is open
        if (row != 1){
            if (isOpen(row - 1,col))
                this.weightedQu.union((row - 1) * this.len + col, (row - 2) * this.len + col);
        }
        else
            this.weightedQu.union(0, col);
        //if site to open is in bottom row, make connection to last node, else connect to site below if it is open
        if (row != this.len){
            if (isOpen(row + 1,col))
                this.weightedQu.union((row - 1) * this.len + col, (row) * this.len + col);
        }
        else
            this.weightedQu.union(this.len * this.len + 1, (row - 1) * this.len + col);

        //if site to open is not in first column, make connection to left site if it is open
        if (col != 1) {
            if (this.isOpen(row, col - 1))
                this.weightedQu.union((row - 1) * this.len + col, (row - 1) * this.len + col - 1);
        }

        //if site to open is not in last column, make connection to right site if it is open
        if (col != this.len) {
            if (this.isOpen(row, col + 1))
                this.weightedQu.union( (row - 1) * this.len + col,(row - 1) * this.len + col + 1);
        }
    }

    public boolean isOpen(int row, int col){
        if (row < 1 || row > this.len || col < 1 || col > this.len)
            throw new IllegalArgumentException("row or column are outside prescribed range");
        return value[row - 1][col - 1];
    }
    public boolean isFull(int row, int col){
        if (row < 1 || row > this.len || col < 1 || col > this.len)
            throw new IllegalArgumentException("row or column are outside prescribed range");
        return this.weightedQu.connected(0, (row - 1) * len + col);
    }
    public int numberOfOpenSites(){
        return this.num_open_sites;
    }
    public boolean percolates(){
        return this.weightedQu.connected(0, this.len * this.len + 1);
    }
    
     public static void main(String[] args){
        Percolation perc = new Percolation(6);
         System.out.println(perc.isOpen(1,1));
         System.out.println(perc.isFull(1,1));
        System.out.println(perc.percolates());
    }
}



