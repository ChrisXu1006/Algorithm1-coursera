import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ; 

public class Solver {
	
	private boolean solveable;
	private int moves;
	private final Stack<Board> sol;

	private class Node implements Comparable<Node> {
		private Node  prev;
	    private Board board;
		private int moves;
		private int distance = -1;

		public Node(Node prev, Board board, int moves) {
			this.prev = prev;
			this.board = board;
			this.moves = moves;
		}	

		private int dist() {
			if (distance == -1) 
				distance = moves + board.manhattan();
			return distance;
		}	

		public int compareTo(Node that) {
			if (this.dist() < that.dist())
				return -1;
			else if (this.dist() > that.dist())
				return 1;
			else
				return 0;
		}
	}

	public Solver(Board initial) {
		if (initial == null)
			throw new NullPointerException();

		MinPQ<Node> main = new MinPQ<Node>();
		MinPQ<Node> maintwin = new MinPQ<Node>();
		sol = new Stack<Board>();
		
		moves = 0;
		Board board = initial;
		Board boardtwin = initial.twin();
		Node root = new Node(null, board, 0);
		Node roottwin = new Node(null, boardtwin, 0);
		main.insert(root);
		maintwin.insert(roottwin);

		Node cur;
		Node curtwin;
		Node minmoves = null;
		while (true) {
			cur = main.delMin();
			curtwin = maintwin.delMin();
			board = cur.board;
			boardtwin = curtwin.board;
			if (boardtwin.isGoal()) {
				solveable = false;
				return;
			}
			if (board.isGoal()) {
				if (!solveable) {
					solveable = true;
					moves = cur.moves;
					minmoves = cur;
				}
				else if (cur.moves < moves) {
					moves = cur.moves;
					Node temp = cur;
					minmoves = cur;
				}
			}

			int nmove = cur.moves+1;
			int nmovetwin = curtwin.moves+1; 
			Iterable<Board> neighbors = board.neighbors();
			for (Board neighbor : neighbors) {
				if (cur.prev != null && neighbor.equals(cur.prev.board))
					continue;
				Node newnode = new Node(cur, neighbor, nmove);
				main.insert(newnode);
			}
			Iterable<Board> neighborstwin = boardtwin.neighbors();
			for (Board neighbortwin : neighborstwin) {
				if (curtwin.prev != null && neighbortwin.equals(curtwin.prev.board))
					continue;
				Node newnodetwin = new Node(curtwin, neighbortwin, nmovetwin);
				maintwin.insert(newnodetwin);
			}
				
			if (cur.moves > 100 || solveable)
				break;
		}	

		Stack<Board> helper = new Stack<Board>();
		if (solveable) {
			while (minmoves != null) {
				helper.push(minmoves.board);
				minmoves = minmoves.prev;
			}
			while (!helper.empty())
				sol.push(helper.pop());
		}
	}

	public boolean isSolvable() {
		return solveable;	
	}

	public int moves() {
		if (solveable)
			return moves;
		else
			return -1;
	}

	public Iterable<Board> solution() {
		if (solveable)
			return sol;
		else
			return null;	
	}

	public static void main(String[] args) {
		// for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

			// solve the slider puzzle
			Board initial = new Board(tiles);
            StdOut.println(initial.toString());
			Solver sol = new Solver(initial);
			StdOut.println(filename + sol.moves());
			StdOut.println("Solution");
		}	
	}
}

