import java.util.Iterator;
public class testDeque{
    
    public static void main(String[] args)
    {
        
        Deque<Integer> deque = new Deque<Integer>();
       deque.addFirst(1);
       deque.addFirst(2);
       deque.addFirst(3);
       deque.addLast(1);
       deque.addLast(2);
       deque.addLast(3);
//        System.out.println(deque. removeLast() );
//        System.out.println(deque. removeLast() );
//        System.out.println(deque. removeLast() );
//        System.out.println(deque. removeLast() );
//        System.out.println(deque. removeLast() );

       Iterator<Integer> i  = deque.iterator();
       while(i.hasNext())
       {
           int k = i.next();
           System.out.println(k);
       }
        
    }
}