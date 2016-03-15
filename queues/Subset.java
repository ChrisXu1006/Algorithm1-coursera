import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);

		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while(!StdIn.isEmpty()){
			String tmp = StdIn.readString();
			q.enqueue(tmp);
			//System.out.println(tmp);
		}
		
		for(int i = 0; i < k; i++){
			System.out.println(q.dequeue());
		}
	}
}
