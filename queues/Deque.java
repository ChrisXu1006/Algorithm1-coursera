import java.util.*;

public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node tail;
	private int nums;

    private class Node {
        Item item;
        Node next = null;
		Node prev = null;
    }

    // construct an empty deque
    public Deque() {
	    first = null;
		tail = null;
		nums = 0;
	}

	public boolean isEmpty() {
		return first == null;
	}

	// is the deque empty
	public int size() {
	    return nums;		
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();

	    if(first == null) {
		    first = new Node();
			first.item = item;
			tail = first;
		}
		else if(nums == 1) {
			first = new Node();
			first.item = item;
			tail.prev = first;
			first.next = tail;
		}
		else {
			Node oldfirst = first;
			first = new Node();
			first.item = item;
			oldfirst.prev = first;
			first.next = oldfirst;
		}
		nums++;
	}
    
	// add the item to the end
	public void addLast(Item item) {
	    if (item == null)
			throw new NullPointerException();
		
		if(tail == null) {
		    tail = new Node();
			tail.item = item;
			first = tail;
		}
		else if(nums == 1) {
		    tail = new Node();
			tail.item = item;
		    first.next = tail;
			tail.prev = first;	
		}
		else {
			Node oldtail = tail;
		    tail = new Node();
			tail.item = item;
			tail.prev = oldtail;
		    oldtail.next = tail;
		}
		nums++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
	    if(nums == 0)
			throw new NoSuchElementException();

		Item res = first.item;
		first = first.next;
		nums--;
		if(nums == 0)
			tail = null;
		if(first != null)
			first.prev = null;
		return res;
	}

	// remove and return the item from the end
	public Item removeLast() {
		if(nums == 0)
			throw new NoSuchElementException();
		
		Item res = tail.item;
		tail = tail.prev;
		nums--;
		if(nums == 0)
			first = null;
		if(tail != null)
			tail.next = null;
		return res;
	}

	// return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
	    private Node cur = first;

		public boolean hasNext() {
			return cur != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();	
		}

		public Item next() {
			if(cur == null)
				throw new NoSuchElementException();
		    Item res = cur.item;
			cur = cur.next;
			return res;
		}
	}

	// unit testing
	public static void main(String[] args) {
		int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	    Deque<Integer> q = new Deque<Integer>();
		//for(int i = 0; i < 6; i++)
		//	q.addFirst(a[i]);
		//System.out.println(q.size());
		q.addLast(1);
		//System.out.println(q.removeFirst());
		q.addFirst(2);
		q.addFirst(3);
		q.addFirst(4);
		//q.addFirst(5);
		System.out.println(q.removeLast());
		//System.out.println(q.removeFirst());
		System.out.println(q.size());
		Iterator<Integer> i = q.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
	}
}
