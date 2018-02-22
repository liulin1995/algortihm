import edu.princeton.cs.algs4.MinPQ;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;
import java.lang.IllegalArgumentException;
import java.util.Comparator;

public class Solver {
    private MinPQ<Node> nodePq=new MinPQ<Node>(new Comparator<Node>()
        {
            public int compare(Node node1,Node node2)
            {
                if(node1.getCost() <node2.getCost()) return -1;
                else if(node1.getCost() > node2.getCost() ) return 1;
                else return 0;
            }
        });
    private boolean Solvable;
    private Node goal;
    private Node initNode ,twinNode;
    private class Node implements Comparable<Node>
    {   
        private Board board;
        private int moves;
        private Node predecessor;
        private int cost;
        private boolean isTwin;
        public Node(Board b1,int m,Node b2,boolean Twin)
        {
            board = b1;
            moves = m;
            predecessor = b2;
            cost = moves + board.manhattan();  //manhattan(),hamming()
            this.isTwin = Twin;
        }
        public int getMoves()
        {
            return this.moves;
        }
        public int getCost()
        {
            return this.cost;   
        }
        public Node getPredecessor()
        {
            return this.predecessor;
        } 
        public Board  getBoard()
        {
            return this.board;
        }  
        public int compareTo(Node b)
        {
            int res = 0;
            if(this.cost < b.cost ) res= -1;
            if(this.cost == b.cost) res= 0;
            if(this.cost > b.cost) res= 1;
            return res;
        }   
        public boolean isTwin()
        {
            return this.isTwin;
        }       
    }
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if(initial == null ) throw new IllegalArgumentException("IllegalArgumentException");
        
        this.initNode =new  Node(initial,0,null,false);
        this.twinNode = new Node(initial.twin(),0,null,true);
        nodePq.insert(initNode);
        nodePq.insert(twinNode);
        while(true)
        {
            Node minNode =nodePq.delMin(); 
            if(minNode.getBoard().isGoal() )
            {
                if(minNode.isTwin())
                { 
                    this.Solvable =false;
                    this.goal = null;
                }
                else {
                    this.Solvable=true;
                    this.goal = minNode;
                }
                break;
            }else
            {
                for(Board board :minNode.getBoard().neighbors())
                {
                    Node neighNode  = new Node(board,minNode.getMoves()+1,minNode,minNode.isTwin());
                    Node pre = minNode.getPredecessor();
                    if(pre == null )
                        nodePq.insert(neighNode);
                    else if(null != pre&&!pre.getBoard().equals(board))
                        nodePq.insert(neighNode);  
                }  
            }   
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return this.Solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if(this.isSolvable())  
        return  this.goal.getMoves();
        else return -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(this.isSolvable())  
        return new Solution();
        else return null;
    }
    private class Solution implements Iterable<Board>
    {    private Board[] boards;
         private int totalNum;
         public Solution()
         {
             totalNum = moves() + 1;
             boards = new Board[totalNum];
             int cnt = totalNum - 1;
             Node now = goal;
             while(now != null)
             {
                 boards[cnt--]= now.getBoard();
                 now = now.getPredecessor();
             } 
         }
         public Iterator<Board> iterator()         // return an independent iterator over items in random order
         {
             return new BoardIterator();
         } 
         private class BoardIterator implements Iterator<Board>
         {
             private int current;
             public BoardIterator()
             {
                 current = 0;
             }
             public boolean hasNext() { 
                 return current != (totalNum);
             }
             public void remove(){throw new UnsupportedOperationException("UnsupportedOperation");}
             public Board next()
             {
                 if(!hasNext()) { throw new NoSuchElementException("NoSuchElementException"); }
                 Board item = boards[current];
                 ++current ;
                 return item;
             }
         }    
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
      //  int b[][]={{8,1,3},{4,0,2},{7,6,5}};
        int b[][]={{0,1,3},{4,2,5},{7,8,6}};
        int a[][]={{1,2,3},{4,5,6},{8,7,0}};
        int c[][]={{0,1,3},{4,2,5},{7,8,6}};
        Board bTest = new Board(a);
        Solver solver = new Solver(bTest);
        System.out.println(solver.moves());
        System.out.println("-------------");
        if(solver.isSolvable())
        for(Board bb :solver.solution())
        {
            System.out.print(bb.toString());
            System.out.println("-------------");
        }
    }
    
}