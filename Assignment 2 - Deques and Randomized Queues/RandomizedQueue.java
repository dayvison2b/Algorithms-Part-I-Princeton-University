import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to the queue");
        }
        if (size == queue.length) {
            resize(queue.length * 2);
        }
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item item = queue[randomIndex];
        queue[randomIndex] = queue[--size];
        queue[size] = null;
        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int newSize) {
        Item[] temp = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iteratorQueue;
        private int currentIndex;
        public RandomizedQueueIterator() {
            iteratorQueue = queue.clone();
            StdRandom.shuffle(iteratorQueue, 0, size);
            currentIndex = 0;
        }

        public boolean hasNext() {
            return currentIndex < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            return iteratorQueue[currentIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        System.out.println("Is the queue empty? " + queue.isEmpty()); // true

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Queue size: " + queue.size()); // 3

        System.out.println("Dequeued item: " + queue.dequeue());
        System.out.println("Sampled item: " + queue.sample());

        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println("Iterator item: " + iterator.next());
        }
    }
}