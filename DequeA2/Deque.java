import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Deque(){
        first = new Node();
        last = new Node();
        count = 0;
    }

    public boolean isEmpty(){             // is the deque empty?
        return count == 0;
    }

    public int size() {                       // return the number of items on the deque
        return count;
    }

    public void addFirst(Item item) {          // add the item to the front

        if (item == null)
            throw new IllegalArgumentException();
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            if (count == 0) {
                last = first;
            } else {
                first.next = oldFirst;
                oldFirst.previous = first;
            }
            count++;
        }
    }

    public void addLast(Item item){           // add the item to the end
        if (item == null)
            throw new IllegalArgumentException();
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            if (count == 0)
                first = last;
            else {
                last.previous = oldLast;
                oldLast.next = last;
            }
            count++;
        }
    }

    public Item removeFirst(){                // remove and return the item from the front
        if (count == 0)
            throw new NoSuchElementException();
        else {
            Node oldFirst = first;
            first = oldFirst.next;
            if (count == 1)
                last = null;
            else
                first.previous = null;
            count--;
            return oldFirst.item;
        }
    }

    public Item removeLast(){                 // remove and return the item from the end
        if (count == 0)
            throw new NoSuchElementException();
        else {
            Node oldLast = last;
            last = oldLast.previous;
            if (count == 1)
                first = null;
            else
                last.next = null;
            count--;
            return oldLast.item;
        }
    }

    public Iterator<Item> iterator(){         // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }

    public static void main(String[] args) {  // unit testing (optional)

    }
}