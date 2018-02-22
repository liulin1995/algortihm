//import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;
import java.lang.Object;
public class Board {
    private int [][] myBlocks;
    private int dimension;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks                                      
    {                                      // (where blocks[i][j] = block in row i, column j)
         this.dimension = blocks.length;
         this.myBlocks = new int [this.dimension][this.dimension];
         for(int i=0;i<this.dimension;i++)
         for(int j=0;j<this.dimension;j++)
         {
             myBlocks[i][j]=blocks[i][j];
         }    
    }
    public int dimension()                 // board dimension n
    {
        return this.dimension;
    }
    private int getGoalValue(int x,int y)
    {
        if(x == this.dimension-1 && y == this.dimension-1)
        {
            return 0;
        }else
        {
            return (x)*this.dimension + y +1;
        }   
    }
    public int hamming()                   // number of blocks out of place
    {
        int cnt =0;
        for(int i=0;i<this.dimension;i++)
        for(int j=0;j<this.dimension;j++)
        {
            if(myBlocks[i][j] !=0)
            {
              if(myBlocks[i][j] != getGoalValue(i,j))
                  cnt++;
            }
        }
        return cnt;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int cnt =0;
        for(int i=0;i<this.dimension;i++)
        for(int j=0;j<this.dimension;j++)
        {
            if(myBlocks[i][j] != 0 && myBlocks[i][j]!=getGoalValue(i,j))
            {
               int goalX = (myBlocks[i][j]-1 )/ this.dimension ;
               int goalY = (myBlocks[i][j]-1 )% this.dimension;
               int disX = goalX - i;
               int disY = goalY - j;
               if(disX <0) disX = 0 -disX;
               if(disY <0) disY = 0 -disY;   
               cnt +=disX;
               cnt +=disY;
            }
        }
        return cnt;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        boolean isGoal=true;
        for(int i=0;i<this.dimension;i++)
        for(int j=0;j<this.dimension;j++)
        {
            if(myBlocks[i][j] != getGoalValue(i,j))
            {
                isGoal = false;
                break;
            } 
        }
        return isGoal;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        Board twinBoard;
        int posX=0,posY=0;
        if(myBlocks[posX][posY]==0)
            ++posX;
        for(int i=0;i<this.dimension;i++)
        for(int j=0;j<this.dimension;j++)
        {
            if(myBlocks[posX][posY]!=myBlocks[i][j] &&  myBlocks[i][j]!=0)
            {
                exch( posX , posY, i, j);
                twinBoard=new Board(myBlocks);
                exch( posX , posY, i, j);
                return twinBoard;
            }
        }
        twinBoard=null;
        return twinBoard;
    }
    private void exch(int x1 ,int y1,int x2,int y2)
    {   
        int temp = myBlocks[x1][y1];
        myBlocks[x1][y1] = myBlocks[x2][y2];
        myBlocks[x2][y2] = temp;   
    }
        
    public boolean equals(Object y)        // does this board equal y?
    {
        if(y==null) return false;
        if(y == this) return true;
        if(y.getClass()!= Board.class)  return false;
        
        Board b=(Board) y;
        if(this.dimension != b.dimension()  ) return false;
        for(int i=0;i<this.dimension;i++)
        for(int j=0;j<this.dimension;j++)
        {
             if(b.myBlocks[i][j] != this.myBlocks[i][j])
                return false; 
        }
        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        return new Neighbors();
    }
    private class Neighbors  implements Iterable<Board>
    {    
         private Board[]  neighborsBoards  ;
         private int totalNeighbors;
         public Neighbors()
         {
           neighborsBoards = new Board [4];
           int posX=0 , posY=0;
           for(int i=0;i<dimension;i++)//find 0
           for(int j=0;j<dimension;j++)
           {
                if(myBlocks[i][j] == 0) 
                {
                    posX = i;
                    posY = j;
                    break;
                }     
           }
           this.totalNeighbors = 0; //4 moves 
           if( posX -1 >=0 ) 
           {
               Board b;
               exch(posX -1,posY,posX,posY);
               b = new Board(myBlocks);
               exch(posX -1,posY,posX,posY);
               neighborsBoards[this. totalNeighbors]=b;
               ++this.totalNeighbors;             
           }
           if(posX +1 <dimension )
           {
               Board b;
               exch(posX + 1,posY,posX,posY);
               b = new Board(myBlocks);
               exch(posX + 1,posY,posX,posY);
               neighborsBoards[this. totalNeighbors]=b;
               ++this.totalNeighbors;     
           }
           if(posY -1 >=0)
           {
               Board b;
               exch(posX ,posY - 1,posX,posY);
               b = new Board(myBlocks);
               exch(posX ,posY - 1,posX,posY);
               neighborsBoards[this. totalNeighbors]=b;
               ++this.totalNeighbors;    
           }
           if(posY + 1<dimension)
           {
               Board b;
               exch(posX ,posY + 1,posX,posY);
               b = new Board(myBlocks);
               exch(posX ,posY + 1,posX,posY);
               neighborsBoards[this. totalNeighbors]=b;
               ++this.totalNeighbors; 
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
                 return current != (totalNeighbors);
             }
             public void remove(){throw new UnsupportedOperationException("UnsupportedOperation");}
             public Board next()
             {
                 if(!hasNext()) { throw new NoSuchElementException("NoSuchElementException"); }
                 Board item = neighborsBoards[current];
                 ++current ;
                 return item;
             }
    }
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
      //  myBlocks = goalBlocks;
        StringBuilder str=new StringBuilder();
        str.append(this.dimension);
        str.append("\n");
        for(int i=0;i<this.dimension;i++)
        for(int j=0;j<this.dimension;j++)
        { 
            str.append(String.format("%2d ", myBlocks[i][j]));
            if(j != dimension -1) str.append("  ");
            else str.append("\n");       
        }
        return str.toString();
    }
    public static void main(String[] args) // unit tests (not graded)
    {
        int b[][]={{8,1,3},{4,0,2},{7,6,5}};
        Board bTest = new Board(b);
        Board bTest2 = new Board(b);
        String str=" ";
        System.out.println(bTest.dimension());
        System.out.print(bTest.toString());
        for(Board bb : bTest.neighbors())
        {
            System.out.println(bb.toString());
        }
        System.out.println(bTest.manhattan());
        System.out.println(bTest.hamming());
        System.out.println(bTest.twin());
        System.out.println(bTest.isGoal());
        System.out.println(bTest.equals(str));
    }
}