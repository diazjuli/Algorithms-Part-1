import java.util.ArrayList;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private ArrayList<Board> solution;
    private int moves;

    public Solver(Board initial){
        MinPQ<Node> queue = new MinPQ<Node>();
        MinPQ<Node> twinQueue = new MinPQ<Node>();
        solution = new ArrayList<Board>();
        moves = 0;
        boolean finished = false;

        Node first = new Node(initial, 0, null);
        Node twinFirst = new Node(initial.twin(), 0, null);

        queue.insert(first);
        twinQueue.insert(twinFirst);
        //change to a while loop instead of recursion to save memory

        while (!finished){
            Node min = queue.delMin();
            Node twinMin = twinQueue.delMin();
            if (min.board.isGoal()) {
                getPath(min);
                moves = min.num_moves;
                finished = true;
            }
            else if (twinMin.board.isGoal()){
                moves = -1;
                solution = null;
                finished = true;
            }
            for (Board b: min.board.neighbors()){
                if (min.prev == null || !b.equals(min.prev.board)) {
                    Node toAdd = new Node(b,min.num_moves + 1, min);
                    queue.insert(toAdd);
                }
            }
            for (Board twinB: twinMin.board.neighbors()){
                if (twinMin.prev == null || !twinB.equals(twinMin.prev.board)) {
                    Node toAdd = new Node(twinB,twinMin.num_moves + 1, twinMin);
                    twinQueue.insert(toAdd);
                }
            }
        }
    }           // find a solution to the initial board (using the A* algorithm)

    private void getPath(Node solved){
        if (solved.prev == null)
            solution.add(solved.board);
        else{
            getPath(solved.prev);
            solution.add(solved.board);
        }
    }
    private class Node implements Comparable<Node>{
        private Board board;
        private int num_moves;
        private Node prev;

        public Node(Board board, int moves, Node prev){
            this.board = board;
            this.num_moves = moves;
            this.prev = prev;
        }

        public int priority(){
            return this.num_moves + this.board.manhattan();
        }


        public int compareTo(Node other)
        {
            return (this.priority() - other.priority());
        }
    }

    public boolean isSolvable(){
        if (moves == -1)
            return false;
        else
            return true;
    }        // is the initial board solvable?
    public int moves()  {
        return this.moves;
    }                   // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()   {
        return solution;
    }   // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args){
        int[][] puzz3 = new int[3][3];
        int x = 1;
        for (int y = 0; y < 3; y++){
            for (int z = 0; z < 3; z++){
                puzz3[y][z] = x;
                x++;
            }
        }
        puzz3[2][2] = 8;
        puzz3[2][1] = 0;

        Board test3 = new Board(puzz3);

        Solver solver = new Solver(test3);
        System.out.println(solver.moves);
        System.out.println(solver.solution);
    } // solve a slider puzzle (given below)
}