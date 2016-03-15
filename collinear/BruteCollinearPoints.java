import java.util.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	
	private ArrayList<LineSegment> segs;
	private Point[] pnt;

	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new NullPointerException();

		segs = new ArrayList<LineSegment>();
		int size = points.length;
		pnt = new Point[size];
		for (int i = 0; i < size; i++) {
			if(points[i] == null)
				throw new NullPointerException();
			pnt[i] = points[i];	
		}
		Arrays.sort(pnt);

		for (int i = 1; i < size; i++) {
			if (pnt[i].compareTo(pnt[i-1]) == 0)
				throw new IllegalArgumentException(); 
		}

		for (int p = 0; p < size - 3; p++) {
			for (int q = p + 1; q < size - 2; q++) {
				for (int r = q + 1; r < size - 1; r++) {
					for (int s = r + 1; s < size; s++) {
						double s1 = pnt[p].slopeTo(pnt[q]);
						double s2 = pnt[p].slopeTo(pnt[r]);
						double s3 = pnt[p].slopeTo(pnt[s]);
						
						if ((s1 == Double.POSITIVE_INFINITY) && (s2 == Double.POSITIVE_INFINITY) && (s3 == Double.POSITIVE_INFINITY)){
							LineSegment line = new LineSegment(pnt[p], pnt[s]);
							segs.add(line);	
						}
						else if ((Math.abs(s1-s2) <  0.00000000001) && (Math.abs(s1-s3) < 0.00000000001) && (Math.abs(s2-s3) < 0.00000000001)) { 
							LineSegment line = new LineSegment(pnt[p], pnt[s]);
							segs.add(line);	
						}	
					}
				}
			}
		}
	}

	public int numberOfSegments() {
		return segs.size();	
	}

	public LineSegment[] segments() {
		LineSegment[] res = new LineSegment[segs.size()];
		res = segs.toArray(res);
		return res;	
	}

	public static void main(String[] args) {
		
		// read the N points from a file
		In in = new In(args[0]);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.show(0);
		StdDraw.setXscale(-10, 32768);
		StdDraw.setYscale(-10, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}	    	
	}
}
