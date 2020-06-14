import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int n = 0;
    private int last = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        arr[last++] = item;
        n++;
        if (last == arr.length) {
            resize(arr.length * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int random = 0;
        Item item = null;
        while (item == null) {
            random = StdRandom.uniform(last);
            item = arr[random];
        }
        arr[random] = null;
        n--;
        if (n == arr.length / 4) {
            resize(arr.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int random = 0;
        Item item = null;
        while (item == null) {
            random = StdRandom.uniform(last);
            item = arr[random];
        }
        return arr[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(arr);
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                temp[index] = arr[i];
                index++;
            }
        }
        arr = temp;
        last = n;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        assert queue.isEmpty();
        assert queue.size() == 0;
        queue.enqueue("Onur");
        assert !queue.isEmpty();
        assert queue.size() == 1;
        queue.enqueue("Bengü");
        assert queue.size() == 2;
        queue.enqueue("Kamil");
        assert queue.size() == 3;
        queue.enqueue("Cevat");
        assert queue.size() == 4;
        queue.enqueue("Ayfer");
        assert queue.size() == 5;
        System.out.println("-------------- iterator ----------------");
        for (String item : queue) {
            System.out.println(item);
        }
        System.out.println("-------------- dequeue ----------------");
        int size = queue.size();
        for (int i = 0; i < 5; i++) {
            size--;
            System.out.println(queue.dequeue());
            assert queue.size() == size;
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

    private class RandomizedQueueIterator implements Iterator<Item> {
        int len = n;
        boolean end = false;
        Item[] copyArr;

        public RandomizedQueueIterator(Item[] arr) {
            copyArr = arr.clone();
        }

        @Override
        public boolean hasNext() {
            return len != 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = 0;
            Item item = null;
            while (item == null) {
                index = StdRandom.uniform(last);
                item = copyArr[index];
            }
            len--;
            copyArr[index] = null;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
