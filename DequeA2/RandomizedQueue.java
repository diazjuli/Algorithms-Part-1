import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int N;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void adjustSize(int newLength)
    {
        Item[] newQueue = (Item[]) new Object[newLength];
        for( int x = 0; x < N; x++)
            newQueue[x] = queue[x];
        queue = newQueue;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        else {
            queue[N] = item;
            N++;
            if (N == queue.length)
                adjustSize(2 * N);
        }
    }

    public Item dequeue()  {
        int r;
        Item temp;

        if (N == 0)
            throw new NoSuchElementException();
        else {
            r = StdRandom.uniform(N);
            temp = queue[r];
            queue[r] = queue[N - 1];
            N--;
            queue[N] = null;
            if (N == queue.length / 4 && N > 1)
                adjustSize( queue.length / 2);
            return temp;
        }
    }

    public Item sample(){
        int r;

        if (N == 0)
            throw new NoSuchElementException();
        else {
            r = StdRandom.uniform(N);
            return queue[r];
        }
    }

    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private int current;
        private Item[] it;

        public RandomQueueIterator(){
            int r;

            current = 0;
            it = (Item[]) new Object[N];
            for (int x = N - 1; x >= 0; x--){
                if (x != 0)
                    r = StdRandom.uniform(x);
                else
                    r = 0;
                it[x] = queue[r];
                queue[r] = queue[x];
                queue[x] = it[x];
                current++;
            }
        }
        public boolean hasNext() {
            return current != 0;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            else{
                current--;
                return it[current];
            }
        }
    }

    public static void main(String[] args) {

    }
}