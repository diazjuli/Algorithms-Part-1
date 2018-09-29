import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private ArrayList<LineSegment> lines;
    private ArrayList<Point> added;

    public FastCollinearPoints(Point[] points){
        Point[] pointscopy = Arrays.copyOf(points, points.length);
        Point[] slopes = Arrays.copyOf(points, points.length);
        lines = new ArrayList<LineSegment>();
        added = new ArrayList<Point>();
        int count;

        for (int x = 0; x < pointscopy.length; x++){
            Arrays.sort(slopes, pointscopy[x].slopeOrder());

            double curr_slope = pointscopy[x].slopeTo(pointscopy[x]);

            for (int y = 0; y < slopes.length; y++){
                curr_slope = pointscopy[x].slopeTo(slopes[y]);
                count = 1;
                ArrayList<Point> pointsToAdd = new ArrayList<Point>();

                pointsToAdd.add(slopes[y]);

                while (y < slopes.length - 1 && pointscopy[x].slopeTo(slopes[y + 1]) == curr_slope){
                    pointsToAdd.add(slopes[y + 1]);
                    count++;
                    y++;
                }
                if (count >= 3){
                    addLineSegment(pointscopy[x], pointsToAdd);
                }
            }
            added.add(pointscopy[x]);

        }

    }  // finds all line segments containing 4 or more points

    private Point[] mergeSort(Point[] slopes){
        return null;
    }

    private void addLineSegment(Point origin, ArrayList<Point> toAdd){

        //check that no points in toadd have already been added
        Point min;
        Point max;
        boolean add = true;
        LineSegment line;

        min = origin;
        max = origin;
        for(Point point: toAdd){
            if (added.contains(point))
                add = false;
            if (min.compareTo(point) > 0)
                min = point;
            if (max.compareTo(point) < 0)
                max = point;
        }
        if (add) {
            line = new LineSegment(min, max);
            lines.add(line);
        }
    }


    public           int numberOfSegments()  {
        return lines.size();
    }      // the number of line segments


    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {

        Point[] points = new Point[8];

        Point p0 = new Point(10000, 0);
        Point p1 = new Point(0, 10000);
        Point p2 = new Point(3000, 7000);
        Point p3 = new Point(7000, 3000);
        Point p4 = new Point(20000, 21000);
        Point p5 = new Point(3000, 4000);
        Point p6 = new Point(14000, 15000);
        Point p7 = new Point(6000, 7000);

        points[0] = p0;
        points[1] = p1;
        points[2] = p2;
        points[3] = p3;
        points[4] = p4;
        points[5] = p5;
        points[6] = p6;
        points[7] = p7;

        FastCollinearPoints fast = new FastCollinearPoints(points);
        System.out.println(fast.lines);
        System.out.println();

     /*  // read the n points from a file
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
        */
    }
}