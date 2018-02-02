public class LinkedStackOfString {
    
    private Node first = null;
    private class Node 
    {
        String item;
        Node next ;
    }
    public boolean isEmpty()
    {
        return first == null;
    }
    public void push ( String item )
    {
        Node oldFirst = first;
        Node first = new Node();
        first.item = item;
        first.next = oldFirst;
    }
    public String pop()
    {
        String item = first.item;
        first = first.next;
        return item;
    }
             
}