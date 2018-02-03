import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;
public class Deque<Item> implements Iterable<Item> {
    private Node head,tail;
    private int st;
    private class Node {
        Item item;
        Node next,before;
    }
   public Deque()                           // construct an empty deque
   {
       head = null;
       tail = null;
       st = 0;
   }
   public boolean isEmpty()                 // is the deque empty?
   {
       if(head == null && tail ==null ) return true ;
       else return false;
   }
   public int size()                        // return the number of items on the deque
   {
       return st;
   }
   public void addFirst(Item item)          // add the item to the front
   {
       if(item == null ){throw new IllegalArgumentException("illegal Argument");}
       if(head ==null) // no element 
       {
           Node first = new Node ();  // is the only element ,and the first one and the last one .
           first.item = item ;
           first.before = null;
           first.next = null;
           head = first;
           tail = first;
       }else
       {
          Node first = new Node();  // add in the head .
          first.item = item;
          first.before = null;
          first.next = head ;
          head.before = first;
          head = first;
       }
       st++;
   }
   public void addLast(Item item)           // add the item to the end
   {
       if(item == null ){throw new IllegalArgumentException("illegal Argument");}
       if(tail == null)   // the only ,will add to tail .
       {
           Node last =new Node();
           last.item = item;
           last.before = null;
           last .next = null;
           head = last ; // update the tail and head .
           tail = last;
       }else
       {
           Node last = new Node(); // add to tail.
           last.item = item;
           last.before = tail;
           tail.next  =last ;
           last .next = null;
           tail = last;
       }
       st++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
       if(isEmpty()) {throw new NoSuchElementException ("NoSuchElement");}
       Node oldNext = head.next;
       Item oldItem = head.item;
       head .next = null;
       if(oldNext !=null ) oldNext . before = null;
       head = oldNext;
       if(head ==null) tail = null;
          st--;
       return oldItem;   
   }
   public Item removeLast()                 // remove and return the item from the end
   {
        if(isEmpty()) {throw new NoSuchElementException ("NoSuchElement");}
       Node oldBefore = tail.before;
       Item oldItem = tail.item;
        tail.before = null;
       if(oldBefore!=null) oldBefore .next =null;
       tail= oldBefore ;
       if(tail == null ) head = null;
       st--;
       return oldItem;
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator();
   }
   private class DequeIterator implements Iterator<Item>
   {
       private Node current = head ;
       public boolean hasNext() { 
         return current != null;
       }
       public void remove(){throw new UnsupportedOperationException("UnsupportedOperation");}
       public Item next()
       {
           if(!hasNext()) { throw new NoSuchElementException("NoSuchElementException"); }
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
   public static void main(String[] args)   // unit testing (optional)
   {
       Deque<Integer> deque = new Deque<Integer>();
       deque.addFirst(1);
       deque.addFirst(2);
       deque.addFirst(3);
       deque.addLast(1);
       deque.addLast(2);
       deque.addLast(3);
       
       Iterator<Integer> i  = deque.iterator();
       while(i.hasNext())
       {
           int k = i.next();
           System.out.println(k);
       }
       
   }
}