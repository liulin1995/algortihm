import java.util.Arrays;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
public class BruteCollinearPoints {
   private Point[] points ;
   private int numberOfSegments;
   private ArrayList<LineSegment> lineSeg = new ArrayList<LineSegment>();
   
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
       if(points == null)  throw new IllegalArgumentException("IllegalArgumentException");
       this.points = new Point[points.length];
       for(int i=0;i<points.length;i++)
           this.points[i]=points[i];
       for(int i=0;i<this.points.length;i++)
           if(this.points[i] == null) throw new IllegalArgumentException("IllegalArgumentException");
      
       Arrays.sort(this.points);
       for(int i=1;i<this.points.length;i++)
           if(this.points[i].compareTo(this.points[i-1])==0) throw new IllegalArgumentException("IllegalArgumentException");
              
       this.numberOfSegments = 0;
       actionBeforeSegments();
   }
   public int numberOfSegments()        // the number of line segments
   {
       return this.numberOfSegments;
   }
   private void actionBeforeSegments()
   {
       int len = this.points.length;
       for(int p=  0;p<len-3;p++)
       for(int q=p+1;q<len-2;q++)
       {
           double s1=points[p].slopeTo(points[q]);
           for(int r=q+1;r<len-1;r++)
           {
               double s2=points[q].slopeTo(points[r]);
               if(s1 != s2) continue;
               for(int s=r+1;s<len;s++)
               {           
                  double s3=points[r].slopeTo(points[s]);
                  if(s1 == s2 && s2 == s3) 
                   {
                      lineSeg.add(new LineSegment(points[p],points[s]));
                 
                    }
               }
           }
       }
      this.numberOfSegments = this.lineSeg.size();
   }
   public LineSegment[] segments()                // the line segments
   {
       LineSegment[] lineSegment = new LineSegment[this.numberOfSegments];
       for(int i=0;i<this.numberOfSegments;i++)
           lineSegment[i]=this.lineSeg.get(i);
       return lineSegment;
   }
   public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}