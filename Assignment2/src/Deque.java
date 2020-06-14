import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.previous = first;
        }
        n++;
        if (n == 1) {
            last = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node(item);
        if (oldLast != null) {
            oldLast.next = last;
            last.previous = oldLast;
        }
        n++;
        if (n == 1) {
            first = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.previous = null;
        }
        n--;
        if (n == 0) {
            last = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null;
        }
        n--;
        if (n == 0) {
            first = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        assert deque.isEmpty();
        assert deque.size() == 0;
        System.out.println("-------------- enqueue ----------------");
        deque.addLast("Onur");
        assert !deque.isEmpty();
        assert deque.size() == 1;
        deque.addFirst("Bengu");
        assert deque.size() == 2;
        deque.addLast("Ayfer");
        assert deque.size() == 3;
        deque.addLast("Cevat");
        assert deque.size() == 4;
        deque.addFirst("Kamil");
        assert deque.size() == 5;
        for (String s : deque) {
            System.out.println(s);
        }
        String removed = deque.removeFirst();
        assert removed.equals("Kamil");
        assert deque.size() == 4;
        removed = deque.removeLast();
        assert removed.equals("Cevat");
        assert deque.size() == 3;
        System.out.println("---------");
        for (String s : deque) {
            System.out.println(s);
        }
        removed = deque.removeLast();
        assert removed.equals("Ayfer");
        removed = deque.removeFirst();
        assert removed.equals("Bengu");
        removed = deque.removeLast();
        assert removed.equals("Onur");
        System.out.println("---------");
        for (String s : deque) {
            System.out.println(s);
        }
    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        Node(Item item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return String.format("Item: {%s}", item);
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        DequeIterator(Node first) {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
