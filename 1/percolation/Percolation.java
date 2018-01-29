// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class Percolation {
   private WeightedQuickUnionUF uf;
   private int N;
   private int opened;
   private boolean [] mat;
   public Percolation(int n)                // create n-by-n grid, with all sites blocked
   {
       if(n<=0)
       {    throw new IllegalArgumentException("illegal Argument"); }
       else
       {
           N=n;
           opened=0;
           mat = new boolean[N*N+1];
           uf=new WeightedQuickUnionUF(n*n+2);
        }
   }
   private int getIndex(int row ,int col)
   {
       if(row<=N &&col<=N&&row>=1&&col>=1)
       {
           return (row-1)*N+col;
       }else
       {
           return -1;
       }
   }
   public    void open(int row, int col)    // open site (row, col) if it is not open already
   {
       int id = getIndex(row,col);
       
       if(id>0 && mat[id]==false)
       {
           mat[id]=true;
           opened++;
           if(row==1) uf.union(0,id);
           if(row == N) uf.union(N*N+1,id);
           for(int i=0;i<4;i++)
           {
               int ii=0;
               if(i==0) 
               {
                   ii = getIndex(row-1,col);
                   if(ii>0 && mat[ii]) uf.union(ii,id);
               }else if(i==1)
               {
                   ii = getIndex(row+1,col);
                   if(ii>0 && mat[ii]) uf.union(ii,id);
               }else if(i==2)
               {
                   ii = getIndex(row,col-1);
                   if(ii>0 && mat[ii]) uf.union(ii,id);
               }else if(i==3){
                   ii = getIndex(row,col+1);
                   if(ii>0 && mat[ii]) uf.union(ii,id);
               }
               
           }
       }else if(id<0)
       {
           throw new IllegalArgumentException("illegal Argument");
       }
       
       
   }
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
       int id = getIndex(row,col);
       if(id>0)
           return mat[id];
       else if(id<0)
       {
           throw new IllegalArgumentException("illegal Argument");
       }
        return false;
   }
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
       
       int id = getIndex(row,col);
       if(id>0)
          return uf.connected(id,0);
       else if(id<0)
       {
           throw new IllegalArgumentException("illegal Argument");
       }
       return false;
   }
   public     int numberOfOpenSites()       // number of open sites
   {
       return opened;
   }
   public boolean percolates()              // does the system percolate?
   {
       return uf.connected(0,N*N+1);
   }

   public static void main(String[] args)   // test client (optional)
   {
       Percolation perc=new Percolation(4);
       perc.open(1,1);
       perc.open(2,1);
       perc.open(3,1);
       perc.open(2,1);
       
       System.out.println("is precolates():"+""+perc.percolates());
       
   }
}