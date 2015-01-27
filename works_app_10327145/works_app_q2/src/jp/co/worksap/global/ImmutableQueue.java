package jp.co.worksap.global;
import java.util.NoSuchElementException;
import java.util.*;
public class ImmutableQueue<E> {

    private InternalStorage<E> storage_enqueue;
    private InternalStorage<E> storage_dequeue;

    public ImmutableQueue() {
    	storage_enqueue = new InternalStorage<E>();
        storage_dequeue = new InternalStorage<E>();
    }

    public ImmutableQueue(InternalStorage<E> enq, InternalStorage<E> deq) {
        storage_enqueue = enq;
        storage_dequeue = deq;
    }
   
    public static void main(String[] args) {
    	/*
    	 * sample implementation 
    	 */
        ImmutableQueue<Integer> queue = new ImmutableQueue<Integer>();
        System.out.println(queue);
        ImmutableQueue<Integer> one = queue.enqueue(7);
        System.out.println(queue);
        System.out.println(one); 
        ImmutableQueue<Integer> two = queue.enqueue(1);
        ImmutableQueue<Integer> three = one.enqueue(1);
        System.out.println(queue);
        System.out.println(one);
        System.out.println(two);
        System.out.println(three.enqueue(3).enqueue(5));
    }

    public ImmutableQueue<E> enqueue(E e) {
        if (e == null)
            throw new IllegalArgumentException();

        return new ImmutableQueue<E>(storage_enqueue.push(e), storage_dequeue);
    }

    public ImmutableQueue<E> dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (!storage_dequeue.isEmpty()) {
            return new ImmutableQueue<E>(storage_enqueue, storage_dequeue.rest);
        } else {
            return new ImmutableQueue<E>(new InternalStorage<E>(),
                    storage_enqueue.reversed().rest);
        }
    }

    public int size() {
        boolean a = (storage_enqueue == null);
        boolean b = (storage_dequeue == null);
        if (a && b) {
            return 0;
        } else if (a) {
            return storage_dequeue.size;
        } else if (b) {
            return storage_enqueue.size;
        } else
            return storage_dequeue.size + storage_enqueue.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (storage_dequeue.isEmpty()) {
            storage_dequeue = storage_enqueue.reversed();
            storage_enqueue = new InternalStorage<E>();
        }
        return storage_dequeue.head;
    }
    
    @Override // inbuilt toString function in java needs to be overridden for our implementation
    public String toString() {
        String string = "Queue: ";
        if (isEmpty()) {
            return "Empty Queue";
        }
        ImmutableQueue<E> current = this;
        while (!current.isEmpty()) {
            string += current.peek();
            string += " ";
            current = current.dequeue();
        }
        return string;
    }

    private static class InternalStorage<E> {
        private E head = null;
        private InternalStorage<E> rest = null;
        private int size = 0;

        public InternalStorage(E head, InternalStorage<E> rest) {
            this.head = head;
            this.rest = rest;
            this.size = rest.size + 1;
        }

        public InternalStorage() {
        }

        public boolean isEmpty() {
        	if(size==0) return true;
        	else return false;
        }

        public InternalStorage<E> push(E item) {
            return new InternalStorage<E>(item, this);
        }

        public InternalStorage<E> reversed() {
            InternalStorage<E> r = new InternalStorage<E>();
            InternalStorage<E> cur = this;
            while (!cur.isEmpty()) {
                r = r.push(cur.head);
                cur = cur.rest;
            }
            return r;
        }
    }
}