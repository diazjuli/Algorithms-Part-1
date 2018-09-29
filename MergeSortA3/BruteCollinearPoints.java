import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> lines;
    private int count;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        this.lines = new ArrayList<LineSegment>();
        if (checkPoints(points))
            throw new IllegalArgumentException();
        Point[] copyPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(copyPoints);

        for (int x = 0; x < copyPoints.length - 3; x++){
            for (int y = x + 1; y < copyPoints.length - 2; y++){
                for (int w = y + 1; w < copyPoints.length - 1; w++){
                    for (int z = w + 1; z < copyPoints.length; z++){
                        if (sameSlope(copyPoints[x], copyPoints[y], copyPoints[w],copyPoints[z])){
                            count++;
                            lines.add(new LineSegment(copyPoints[x], copyPoints[z]));
                        }
                    }
                }
            }
        }
    }

    private boolean checkPoints(Point[] points){
        for (int x = 0; x < points.length; x++){
            if (points[x] == null)
                return true;
            for (int y = x + 1; y < points.length; y++){
                if (points[y] == null)
                    return true;
                if (points[x].compareTo(points[y]) == 0)
                    return true;
            }
        }
        return false;
    }
    private boolean sameSlope(Point a, Point b, Point c, Point d){
        if (a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(b) == a.slopeTo(d))
            return true;
        return false;
    }


    private boolean checkEqual(Point a, Point b, Point c, Point d){
        if (a.compareTo(b) == 0 || a.compareTo(c) == 0 || a.compareTo(d) == 0 || b.compareTo(c) == 0 || b.compareTo(d) == 0 || c.compareTo(d) == 0)
            return true;
        return false;
    }

    public           int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }

    public static void  main(String[] args){
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

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}