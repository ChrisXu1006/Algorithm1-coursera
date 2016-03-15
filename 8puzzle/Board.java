import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

	private int[][] board;
	private int N;

	// construct a board from an N-by-N array of blocks
	// where blocks[i][j] = block in row i, column j
	public Board(int[][] blocks) {
		N = blocks.length;	
		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = blocks[i][j];
			}
		}
	}

	// board dimension N
	public int dimension() {
		return N;	
	}

	// number of blocks out of place
	public int hamming() {
		int res = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 0)
					continue;
				if (board[i][j] != (N*i+j+1))
					res++;
			}
		}	
		return res;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int res = 0;	
		int dst_i = 0;
		int dst_j = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 0)
					continue;
				dst_i = (board[i][j]-1) / N;
				dst_j = (board[i][j]-1) % N;
				res = res + Math.abs(i-dst_i) + Math.abs(j-dst_j);
			}
		}
		return res;
	}

	// is this board the goal board
	public boolean isGoal() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 0)
					continue;
				if (board[i][j] != (N*i+j+1))
					return false;
			}
		}	
		return true;
	}

	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		Board res = new Board(board);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0) {
                    swap(res.board, i, j, i, j + 1);
                    return res;
                }
            }
        }
        return res;	
	}

	// does this board equal y
	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		
		Board that = (Board) y;
		if (this.N != that.N)
			return false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (this.board[i][j] != that.board[i][j])
					return false;
			}
		}
		return true;	
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		int i0 = 0;
		int j0 = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 0) {
					i0 = i;
					j0 = j;
					break;
				}
			}
		}	

		Stack<Board> neighbors = new Stack<Board>();
		Board neighbor = new Board(board);
		if (i0 > 0) {
			swap(neighbor.board, i0, j0, i0-1, j0);
			neighbors.push(neighbor);
		}
		neighbor = new Board(board);
		if (i0 < N-1) {
			swap(neighbor.board, i0, j0, i0+1, j0);
			neighbors.push(neighbor);
		}
		neighbor = new Board(board);
		if (j0 > 0) {
			swap(neighbor.board, i0, j0, i0, j0-1);
			neighbors.push(neighbor);
		}
		neighbor = new Board(board);
		if (j0 < N-1) {
			swap(neighbor.board, i0, j0, i0, j0+1);
			neighbors.push(neighbor);
		}
		return neighbors;

	}

	private void swap(int[][] b, int i, int j, int p, int q) {
		int temp = b[i][j];
		b[i][j] = b[p][q];
		b[p][q] = temp;
	}

	// string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", board[i][j]));
			}
			s.append("\n");
		}
		return s.toString();		
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
            StdOut.println(initial.dimension());
            StdOut.println(initial.hamming());
            StdOut.println(initial.manhattan());
            StdOut.println(initial.isGoal());

			Board twin = initial.twin();
			StdOut.println(twin.toString());
			Iterable<Board> n = initial.neighbors();
			for (Board neighbor : n) {
				StdOut.println(neighbor.toString());
			}
        }
	}
}
