import java.util.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] s;
	private int nums;
	private int N;
	private int tail;

	// construct an empty randomized queue
	public RandomizedQueue() {
		s = (Item[]) new Object[1];
		nums = 0;
		N = 1;
		tail = 0;
	}

	// is the queue empty
	public boolean isEmpty() {
		return nums == 0;
	}
	
	// return the number of items on the queue
	public int size() {
		return nums;
	}
	
	// add the item
	public void enqueue(Item item) {
		if(item == null)
			throw new NullPointerException();

		if(nums == s.length)
			resize(2*nums);
		s[nums] = item;
		nums++;
		tail = nums-1;
	}

	// resize array
	private void resize(int capacity) {
		Item[] back = (Item[]) new Object[capacity];
		for (int i = 0; i < nums; i++)
			back[i] = s[i];
		s = back;
		N = capacity;
	}

	// remove and return a random item
	public Item dequeue() {
		if(nums == 0)	
			throw new NoSuchElementException();

		int index = StdRandom.uniform(0, nums);
		Item res = s[index];
		if(index != tail){
			s[index] = s[tail];
		}
		s[tail] = null;
		tail--;
		nums--;
		if(nums > 0 && nums == N/4)
			resize(s.length/2);
		return res;
	}

	// return a random item
	public Item sample() {
		if(nums == 0)
			throw new NoSuchElementException();

		int index = StdRandom.uniform(0, nums);
		Item res = s[index]; 
		return res;
	
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomQueueIterator();	
	}

	private class RandomQueueIterator implements Iterator<Item> {
		private int iter_nums;
		private int iter_tail;
		private Item[] iter;

		public RandomQueueIterator() {
			iter = (Item[]) new Object[nums];	
			for(int i = 0; i < nums; i++)
				iter[i] = s[i];
			iter_nums = nums;
			iter_tail = tail;
		}
		
		public boolean hasNext(){
			return iter_nums != 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if(iter_nums == 0)
				throw new NoSuchElementException();

			int cur = StdRandom.uniform(0,iter_nums);
			Item res = iter[cur];
			if(cur != iter_tail){
				iter[cur]  = iter[iter_tail];
			}
			iter[tail] = null;
			iter_nums--;
			iter_tail--;
			return res; 
		}
	}

	public static void main(String[] args) {
	
	}
}
