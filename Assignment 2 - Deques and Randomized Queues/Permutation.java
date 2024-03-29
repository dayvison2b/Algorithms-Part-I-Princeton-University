import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // Read all strings from standard input and add them to the queue
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        // Dequeue and print k random strings from the queue
        while (k > 0) {
            StdOut.println(queue.dequeue());
            k--;
        }
    }
}