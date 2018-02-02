public class FixedCapacityStackOfString{
    
    private String [] s;
    private int N = 0;
    
    public FixedCapacityStackOfString(int capacity)
    {
        S=new String[capacity];
    }
    public boolean isEmpty()
    {
        return N == 0;
    }
    public void push(String item)
    {
        S[N++]=item;
    }
    
    public String pop()
    {
        return [--N];
    }
}