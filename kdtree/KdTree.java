import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import jave.util.*;

public class KdTree {

	private final int size;
	private Node root;
	private Node   min_point;
	private double min_dist;

	private static class Node {
		private Point2D p;
		private RectHV r;
		private Node lb;
		private Node rt;

		public Node(Point2D p, RectHV r) {
			this.p = p;
			this.r = r;
			this.lb = null;
			this.rt = null;
		}
	}

	// construct an empty set of points
	public	KdTree() {
		root = null;	
		size = 0;
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
		
		root = put(root, p, 1, 0.0, 0.0, 1.0, 1.0);	
		size++;
		return;
	}

	private Node put(Node r, Point2D p, int h, double xmin, double ymin, double xmax, double ymax) {
		if (r == null) {
			RectHV square = RectHV(xmin, ymin, xmax, ymax);	
			Node n = new Node(p, square);
			r = n;
		}
		else {
			if (h % 2 == 1) {
				if (p.x() < r.p.x()) {
					r.lb = put(r.lb, p, h+1, xmin, ymin, r.p.x(), ymax); 
				}
				else {
					r.rt = put(r.rt, p, h+1, r.p.x(), ymin, xmax, ymax);
				}
			}
			else {
				if (p.y() < r.p.y()) {
					r.lb = put(r.lb, p, h+1, xmin, ymin, xmax, r.p.y());
				}
				else {
					r.rt = put(r.rt, p, h+1, xmin, r.p.y(), xmax, ymax);
				}
			}
		}
		return r;
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null)
			throw new NullPointerExceptio();
		return contain(r, p, 0);
	}

	private boolean contain(Node r, Point2D p, int h) {
		if (r == null)
			return false;
		if (p.equals(r.p))
			return true;
		if (h % 2 == 1) {
			if (p.x() < r.p.x()) 
				return (r.lb, p, h+1);
			else
				return (r.rt, p, h+1);
		}
		else {
			if (p.y() < r.p.y()) 
				return (r.lb, p, h+1);
			else
				return (r.rt, p, h+1);
		}
	}

	// draw all points to stand draw
	public void draw() {
		draw_rec(r, 0);
	}

	private void draw_rec(Node r, int h) {
		if (r == null)
			return;
		StdDraw.setPenColor(StdDraw.BLACK);
		r.p.draw();
		if (h % 2 == 1) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(r.p.x(), 0.0, r.p.x(), 1.0);
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(0.0, r.p.y(), 1.0, r.p.y());
		}
		draw_rec(r.lb, h+1);
		draw_rec(r.rt, h+1);
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerExceptio();

		Stack<Point2D> res = new Stack<Point2D>();
		find(r, rec, res);
		return res;
	}

	private void find(Node r, RectHV rect, Stack<Point2D> res) {
		if (r == null)
			return;
		if (rect.intersects(r.r) == false)
			return;
		if (rect.contains(r.p))
			res.push(p);
		find(r.lb, rect, res);
		find(r.rt, rect, res);
		return;
	}

	// a nearest neighbor in the set to point p
	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerExceptio();

		if (r == null)
			return r;

		min_point = r;
		min_dist = p.distanceSquareTo(r.p);
		near_rec(r.lb, p);	
		near_rec(r.rt, p);
		return min_point;
	}
	
	private void near_rec(Node r, Point2D p) {
		if (r == null)
			return;
		if (r.r.distanceSquaredTo(p) >= min_dist)
			return;
		if (r.p.distanceSquareTo(p) < min_dist) {
			min_dist = r.p.distanceSquareTo(p);
			min_point = r;
		}
		near_rec(r.lb, p);	
		near_rec(r.rt, p);
		return;
	}

	public static void main(String[] args) {
	
	}
}
