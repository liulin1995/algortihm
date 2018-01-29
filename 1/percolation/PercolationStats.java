import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.IllegalArgumentException;

public class PercolationStats {
    private double [] record;
    private Percolation perc;
    private int Itrials;
    
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
       if(n<=0||trials<=0)
       {    throw new IllegalArgumentException("illegal Argument"); }
       else
       {
           record=new double[trials];
           Itrials=trials;

           for(int i=0;i<trials;i++)
           { 
              perc =new Percolation(n);
              double cnt=0;
              while(!perc.percolates())
              {
                  int row = StdRandom.uniform(1,n+1);
                  int col = StdRandom.uniform(1,n+1);
                  if(!perc.isOpen(row,col)){
                       perc.open(row,col);
                       cnt=cnt+1;
                  }
                 
              }
              record[i]=cnt/(double)(n*n);
           }
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(record);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(record);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double m=mean();
        double stdd=stddev();
        return m-1.96*stdd/Math.sqrt(Itrials);
         
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double m=mean();
        double stdd=stddev();
        return m+1.96*stdd/Math.sqrt(Itrials);
    }

    public static void main(String[] args)        // test client (described below)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps=new PercolationStats(N,T);
        System.out.printf("mean                    = %f\n",ps.mean());
        System.out.printf("stddev                  = %f\n",ps.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n",ps.confidenceLo(),ps.confidenceHi());
    }
}