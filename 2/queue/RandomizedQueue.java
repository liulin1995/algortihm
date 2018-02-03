import edu.princeton.cs.algs4.StdRandom;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;
import java.lang.Object;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int head,tail,st;
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       s = (Item [] )  new Object[1];
       head = 0;
       tail = -1;
       st = 0;
   }
   private void resize(int capacity)
   {
       Item [] copy = (Item []) new Object[capacity];
       for(int i=0;i<st;i++)
       {
           int index = head + i;
           if(index >=s.length) index = index %s.length;
           copy[i]=s[index];
       }
       s=copy;
       head = 0;
       tail = st-1;
   }
   public boolean isEmpty()                 // is the randomized queue empty?
   {
       return st==0;
   }
   public int size()                        // return the number of items on the randomized queue
   {
       return st;
   }
   public void enqueue(Item item)           // add the item
   {
        if(item == null ){throw new IllegalArgumentException("illegal Argument");}
       if(st == s.length ) resize(2 * s.length);
       if(tail == s.length - 1) tail = -1;
       s[++tail]=item;
       st++;
   }
   public Item dequeue()                    // remove and return a random item
   {
       if(st == 0 ) {throw new NoSuchElementException ("NoSuchElement");}
       int i=StdRandom.uniform(0,st);
       int ix = head +i;
       if(ix>=s.length) ix = ix %s.length;
       swap(ix,head);
       Item item = s[head];
       s[head]=null;
       ++head;
       if(head == s.length ) head = 0;
       --st;
       if(st >0 && st == s.length / 4) resize(s.length /2);
       
       return item;
   }
   private void swap(int i,int head)
   {
       Item temp = s[i];
       s[i]=s[tail];
       s[tail]=temp;
   }
   public Item sample()                     // return a random item (but do not remove it)
   {
       if(st==0) {throw new NoSuchElementException ("NoSuchElement");}
        int i=StdRandom.uniform(0,st);
        int ix = head +i;
        if(ix>=s.length) ix = ix %s.length;
        Item item = s[ix];
        return item ;
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomizedQueueIterator();
   }
   private class RandomizedQueueIterator implements Iterator<Item>
   {
       private Item[]  ShuffleArray  ;
       private int current;
       public RandomizedQueueIterator()
       {
           ShuffleArray =( Item[] )new Object [st];
           for(int i=0;i<st;i++)
           {
                int index = head + i;
                if(index >=s.length) index = index %s.length;
                ShuffleArray[i]=s[index];
           }
            StdRandom.shuffle( ShuffleArray);
            current = 0;
       }
       public boolean hasNext() { 
         return current != st;
       }
       public void remove(){throw new UnsupportedOperationException("UnsupportedOperation");}
       public Item next()
       {
           if(!hasNext()) { throw new NoSuchElementException("NoSuchElementException"); }
           Item item = ShuffleArray[current];
           ++current ;
           return item;
       }
   }
   public static void main(String[] args)   // unit testing (optional)
   {
       RandomizedQueue<Integer> que = new RandomizedQueue<Integer>();
       que.enqueue(1);
       que.enqueue(2);
       que.enqueue(3);
       que.enqueue(4);
       que.enqueue(5);
       que.enqueue(6);
       System.out.println(que.dequeue());
       System.out.println(que.dequeue());
       System.out.println(que.dequeue());
       System.out.println(que.dequeue());
       System.out.println(que.dequeue());
       System.out.println(que.dequeue());
       System.out.println(que.dequeue());
       Iterator<Integer> i  = que.iterator();
       while(i.hasNext())
       {
           int k = i.next();
           System.out.println(k);
       }
   }
}