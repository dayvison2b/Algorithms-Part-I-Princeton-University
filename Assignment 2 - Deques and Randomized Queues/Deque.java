import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    // Construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    // Is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the number of items on the deque
    public int size() {
        return size;
    }

    // Add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node newNode = new Node(item);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node newNode = new Node(item);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = head.item;
        head = head.next;
        size--;

        if (isEmpty()) tail = null;
        else head.prev = null;
        return item;
    }

    // Remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = tail.item;
        tail = tail.prev;
        size--;
        if (isEmpty()) head = null;
        else tail.next = null;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // Unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        // Test construction
        if (!deque.isEmpty() || deque.size() != 0) {
            System.out.println("Error: Deque constructor failed");
        }

        // Test addFirst and addLast
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(4);

        // Test size
        if (deque.size() != 4) {
            System.out.println("Error: size() returned incorrect value");
        }

        // Test iterator
        Iterator<Integer> iterator = deque.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(" ");
        }
        if (!sb.toString().trim().equals("3 1 2 4")) {
            System.out.println("Error: iterator returned incorrect order");
        }

        // Test removeFirst
        if (deque.removeFirst() != 3) {
            System.out.println("Error: removeFirst returned incorrect value");
        }

        // Test removeLast
        if (deque.removeLast() != 4) {
            System.out.println("Error: removeLast returned incorrect value");
        }

        // Test isEmpty and size after removals
        if (deque.isEmpty() || deque.size() != 2) {
            System.out.println("Error: isEmpty or size returned incorrect value after removals");
        }

        // Test exception handling for removeFirst and removeLast on empty deque
        deque = new Deque<>();
        try {
            deque.removeFirst();
            System.out.println("Error: removeFirst did not throw NoSuchElementException on empty deque");
        } catch (NoSuchElementException e) {
            // Expected
        }

        try {
            deque.removeLast();
            System.out.println("Error: removeLast did not throw NoSuchElementException on empty deque");
        } catch (NoSuchElementException e) {
            // Expected
        }

        // Test exception handling for addFirst and addLast with null argument
        try {
            deque.addFirst(null);
            System.out.println("Error: addFirst did not throw IllegalArgumentException for null argument");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            deque.addLast(null);
            System.out.println("Error: addLast did not throw IllegalArgumentException for null argument");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test exception handling for iterator.next() on empty deque
        try {
            Iterator<Integer> emptyIterator = deque.iterator();
            emptyIterator.next();
            System.out.println("Error: iterator did not throw NoSuchElementException on empty deque");
        } catch (NoSuchElementException e) {
            // Expected
        }

        // Test exception handling for iterator.remove()
        deque.addFirst(5);
        iterator = deque.iterator();
        iterator.next();
        try {
            iterator.remove();
            System.out.println("Error: iterator did not throw UnsupportedOperationException for remove");
        } catch (UnsupportedOperationException e) {
            // Expected
        }

        System.out.println("All tests passed");
    }
}