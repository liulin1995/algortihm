import java.util.Arrays;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.util.Collections;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
   private Point[] points ;
   private int numberOfSegments;
   private ArrayList<LineSegment> lineSeg = new ArrayList<LineSegment>();
   private ArrayList<SegmentRecord> lineRecords =new ArrayList<SegmentRecord>();
   
   private class SegmentRecord implements Comparable<SegmentRecord>{
       private Point p1;
       private Point p2;
       private double slope;
       public SegmentRecord(Point p1,Point p2)
       {
           this.p1 = p1;
           this.p2 = p2;
           this.slope = p1.slopeTo(p2);
       }
       public Point getP1()
       {
           return this.p1;
       } 
       public Point getP2()
       {
           return this.p2;
       }
       public double getSlope()
       {
           return this.slope;
       }
       public int compareTo(SegmentRecord that)
       {
           if(this.slope > that.slope) return 1;
           else if(this.slope<that.slope) return -1;
           return 0;
       }
   }
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
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
   private void actionBeforeSegments()
   {
       for(int i=0;i<points.length;i++)
       {
           Point [] copy = new Point[points.length-1];
           int cnt= 0;
           for( int j=0;j<points.length;j++)
           {
               if(i!=j)
               {
                 copy[cnt++]=points[j];
               } 
           }
           Arrays.sort(copy,points[i].slopeOrder());
           double [] slopes=new double[copy.length];
           
           for(int j=0;j<copy.length;j++)
           {
               slopes[j]= points[i].slopeTo(copy[j]); 
           }
           int bef=0;
           int j;
           for( j=1;j<copy.length;j++)
           {
                if(slopes[j]!=slopes[bef])
                {
                    if((j-bef) > 2) 
                    {   
                        int flag = 1;
                        int ccc = 0;
                        Collections.sort(lineRecords);
                        for(int k=0;k<lineRecords.size();k++)
                        {   
                            if(lineRecords.get(k).getSlope() == slopes[bef] ) 
                            {
                               if(points[i].compareTo(lineRecords.get(k).getP1())==0) ++ccc;
                               if(points[i].compareTo(lineRecords.get(k).getP2())==0) ++ccc;
                               for(int w=bef;w<j;w++)
                               { 
                                  if(copy[w].compareTo( lineRecords.get(k).getP1())== 0 ) 
                                  {  ++ccc;
                                  }
                                  if(copy[w].compareTo(lineRecords.get(k).getP2())==0) 
                                  {   ++ccc;
                                  }
                                  if(ccc>=2)
                                  {
                                      flag = 0;
                                      break;
                                  }
                               }
                             
                            }else if(lineRecords.get(k).getSlope() > slopes[bef] )
                                break;
                        }         
                        if(flag==1)
                        {
                            lineSeg.add(new LineSegment(points[i],copy[j-1]));
                            lineRecords.add(new SegmentRecord(points[i],copy[j-1]));
                        }
                    }
                    bef = j;
                }
           }
           if((j-bef) > 2)
           {
                        int flag = 1;
                        int ccc = 0;
                        Collections.sort(lineRecords);
                        for(int k=0;k<lineRecords.size();k++)
                        {   
                            if(lineRecords.get(k).getSlope() == slopes[bef] ) 
                            {
                               if(points[i].compareTo(lineRecords.get(k).getP1())==0) ++ccc;
                               if(points[i].compareTo(lineRecords.get(k).getP2())==0) ++ccc;
                               for(int w=bef;w<j;w++)
                               { 
                                  if(copy[w].compareTo( lineRecords.get(k).getP1())== 0 ) 
                                  {  ++ccc;
                                  }
                                  if(copy[w].compareTo(lineRecords.get(k).getP2())==0) 
                                  {   ++ccc;
                                  }
                                  if(ccc>=2)
                                  {
                                      flag = 0;
                                      break;
                                  }
                               }
                             
                            }else if(lineRecords.get(k).getSlope() > slopes[bef]) break;
                        }         
                        if(flag==1)
                        {
                            lineSeg.add(new LineSegment(points[i],copy[j-1]));
                            lineRecords.add(new SegmentRecord(points[i],copy[j-1]));
                        }            
           }
       }
       this.numberOfSegments = lineSeg.size();
   }
   public  int numberOfSegments()        // the number of line segments
   {
       return this.numberOfSegments;
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}