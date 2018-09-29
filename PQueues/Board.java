import edu.princeton.cs.algs4.StdOut;
import java.lang.Math;
import java.util.ArrayList;

public class Board {

    private int n;
    private short[][] board;
    private int blankRow;
    private int blankCol;

    public Board(int[][] blocks){
        n = blocks.length;
        board = new short[n][n];

        for (int x = 0; x < n; x++){
            for (int y = 0; y < n; y++){
                if (blocks[x][y] == 0){
                    blankRow = x;
                    blankCol = y;
                }
                board[x][y] = (short)blocks[x][y];
            }
        }
    }           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)

    private Board(short[][] blocks){
        n = blocks.length;
        board = new short[n][n];

        for (int x = 0; x < n; x++){
            for (int y = 0; y < n; y++){
                if (blocks[x][y] == 0){
                    blankRow = x;
                    blankCol = y;
                }
                board[x][y] = blocks[x][y];
            }
        }
    }           // construct a board from an n-by-n array of blocks
    public int dimension(){
        return this.n;
    }
    public int hamming(){
        int count;

        count = 0;
        for (int x = 1; x < n * n;x++){
            if (board[(x - 1)/ n][(x - 1) % n] != x)
                count++;
        }
        return count;
    }                // number of blocks out of place
    public int manhattan(){
        int count;
        int row;
        int col;

        count = 0;
        for (int x = 0; x < n; x++){
            for (int y = 0; y < n; y++){
                if (board[x][y] != 0){
                    row = (board[x][y] - 1) / n;
                    col = (board[x][y] - 1) % n;
                    count += Math.abs(row - x);
                    count += Math.abs(col - y);
                }
            }
        }
        return count;
    }               // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        if (this.hamming() == 0)
            return true;
        return false;
    }               // is this board the goal board?
    public Board twin(){
        int[][] twin_board;
        int temp;
        Board twin;

        twin_board = new int[n][n];
        for (int x = 0; x < n; x++){
            for (int y = 0; y < n; y++){
                twin_board[x][y] = board[x][y];
            }
        }
        if (blankRow == 0) {
            temp = twin_board[1][0];
            twin_board[1][0] = twin_board[1][1];
            twin_board[1][1] = temp;
        }
        else{
            temp = twin_board[0][0];
            twin_board[0][0] = twin_board[0][1];
            twin_board[0][1] = temp;
        }
        twin = new Board(twin_board);
        return twin;
    }                 // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y){
        if (this.getClass() != y.getClass())
            return false;

        Board compare = (Board) y;

        if (this.n != compare.n)
            return false;
        for (int x = 0; x < n; x++){
            for (int z = 0; z < n; z++){
                if (board[x][z] != compare.board[x][z])
                    return false;
            }
        }
        return true;
    }       // does this board equal y?
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();


        if (blankRow != 0){
            // blank not in first row => can swap blank with piece on top
            //swap blank piece with piece on top, create a new board, then swap back
            this.board[blankRow][blankCol] = this.board[blankRow - 1][blankCol];
            this.board[blankRow - 1][blankCol] = 0;
            Board btop = new Board(this.board);
            neighbors.add(btop);

            //swap back to normal
            this.board[blankRow - 1][blankCol] = this.board[blankRow][blankCol];
            this.board[blankRow][blankCol] = 0;
        }
        if (blankRow != n - 1)
        {
            // blank not in last row => can swap blank with piece below
            this.board[blankRow][blankCol] = this.board[blankRow + 1][blankCol];
            this.board[blankRow + 1][blankCol] = 0;
            Board bbot = new Board(this.board);
            neighbors.add(bbot);

            //swap back to normal
            this.board[blankRow + 1][blankCol] = this.board[blankRow][blankCol];
            this.board[blankRow][blankCol] = 0;
        }
        if (blankCol != n - 1){
            // blank not in first column => can swap blank with piece to the right
            this.board[blankRow][blankCol] = this.board[blankRow][blankCol + 1];
            this.board[blankRow][blankCol + 1] = 0;
            Board bRight = new Board(this.board);
            neighbors.add(bRight);

            //swap back to normal
            this.board[blankRow][blankCol + 1] = this.board[blankRow][blankCol];
            this.board[blankRow][blankCol] = 0;
        }

        if (blankCol != 0)
        {
            // blank not in last column => can swap blank with piece to the left
            this.board[blankRow][blankCol] = this.board[blankRow][blankCol - 1];
            this.board[blankRow][blankCol - 1] = 0;
            Board bLeft = new Board(this.board);
            neighbors.add(bLeft);

            //swap back to normal
            this.board[blankRow][blankCol - 1] = this.board[blankRow][blankCol];
            this.board[blankRow][blankCol] = 0;
        }
        return neighbors;
    }    // all neighboring boards
    public String toString() {
        String str = "\n";
        str += this.dimension() + "\n";
        for (int x = 0; x < n; x++){
            for(int y = 0; y < n;y++){
                str += " " + board[x][y] + " ";
            }
            str += "\n";
        }
        return str;
    }              // string representation of this board (in the output format specified below)

    public static void main(String[] args){
        int[][] puzz3 = new int[3][3];
        int x = 8;
        for (int y = 0; y < 3; y++){
            for (int z = 0; z < 3; z++){
                puzz3[y][z] = x;
                x--;
            }
        }
        Board test3 = new Board(puzz3);
        StdOut.println("dimension is:" + test3.dimension());
        StdOut.println("hamming distance is: " + test3.hamming());
        StdOut.println("manhattan distance is: " + test3.manhattan());
        StdOut.println("is goal: " + test3.isGoal());
        StdOut.println("twin board: " + test3.twin());
        StdOut.println("is equal: " + test3.equals(test3.twin()));
        for(Board b: test3.neighbors()){
            StdOut.println("neighbour: " + b);
        }

    } // unit tests (not graded)
}