import java.util.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
	
	private ArrayList<LineSegment> segs;
	private Point[] pnt;

	public FastCollinearPoints(Point[] points) {
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

		for (int p = 0; p < size; p++) {
			Point[] buf = new Point[size-1];
			int cur1 = 0;
			int cur2 = 0;
			while (cur1 < size && cur2 < size-1) {
				if (cur1 != p) {
					buf[cur2] = pnt[cur1];
					cur2++;
				}
				cur1++;
			}	
			Arrays.sort(buf, pnt[p].slopeOrder());

			int cur = 0;
			int cnt = 0;
			boolean found = true;
			while (cur < buf.length) {
				double s1 = pnt[p].slopeTo(buf[cur]);
				if (pnt[p].compareTo(buf[cur]) > 0)
					found = false;
				int next = cur + 1;
			    while (next < buf.length) {
					double s2 = pnt[p].slopeTo(buf[next]);
					if (Math.abs(s1 - s2) < 0.000000000000001) {
						if (pnt[p].compareTo(buf[next]) > 0)
							found = false;
						next++;
						cnt++;
					}
					else if (s1 == Double.POSITIVE_INFINITY && s2 == Double.POSITIVE_INFINITY) {
						if (pnt[p].compareTo(buf[next]) > 0)
							found = false;
						next++;
						cnt++;
					}
					else
						break;
				}
				if (cnt >= 2 && found == true) {
					LineSegment line = new LineSegment(pnt[p], buf[next-1]);
					segs.add(line);
				}	
				cur = next;
				cnt = 0;
				found = true;
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
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		StdOut.println(collinear.numberOfSegments());
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}	    	
	}
}	
