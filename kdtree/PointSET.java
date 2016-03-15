import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import jave.util.*;

public class PointSET {

	private final int    size;
	private final TreeSet<Point2D> points;

	// construct an empty set of points
	public	PointSET() {
		size = 0;
		points = new TreeSet<Point2D>();
	}

	// is the set empty
	public boolean isEmpty() {
		return (size == 0);	
	}

	// number of points in the set
	public int size() {
		return size;	
	}

	// add the point to the set (if it is not already int the set)
	public void insert(Point2D p) {
		if (p == null)
			throw new NullPointerExceptio();

		if (!square.contains(p)) {
			points.add(p);	
			size++;
		}
		return;
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerExceptio();

		return points.contains(p);	
	}

	// draw all points to stand draw
	public void draw() {
		for (Point2D p : points) 
			p.draw();	
		return;
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerExceptio();

		Stack<Point2D> res = new Stack<Point2D>();
		for (Point2D p : points) {
			if (rect.contains(p))
				res.push(p);
		}	
		return res;
	}

	// a nearest neighbor in the set to point p
	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerExceptio();

		double mindist = Double.POSITIVE_INFINITY;
		Point2D res = null;

		for (Point2D q: points) {
			double dist = p.distanceSquaredTo(q);
			if (dist < mindist) {
				mindist = dist;
				res = q;
			}
		}
		return res;
	}

	public static void main(String[] args) {
	
	}
}
