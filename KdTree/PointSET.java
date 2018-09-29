import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> bst;

    public         PointSET(){
        bst = new TreeSet<Point2D>();
    }                           // construct an empty set of points

    public           boolean isEmpty(){
        return bst.isEmpty();
    }                  // is the set empty?

    public               int size(){
        return bst.size();
    }                       // number of points in the set

    public              void insert(Point2D p){
        if (p == null)
            throw new IllegalArgumentException();
        else
            bst.add(p);
    }            // add the point to the set (if it is not already in the set)

    public           boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        else
            return bst.contains(p);
    }           // does the set contain point p?

    public              void draw() {
        for (Point2D p: bst)
            p.draw();
    }                       // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect){
        ArrayList<Point2D> points = new ArrayList<Point2D>();

        if (rect == null)
            throw new IllegalArgumentException();
        else
        {
            for (Point2D p : bst)
            {
                if (rect.contains(p))
                    points.add(p);
            }
        }
        return points;
    }            // all points that are inside the rectangle (or on the boundary)

    public           Point2D nearest(Point2D p){
        double distance;
        Point2D closest;

        if (bst.isEmpty())
            return null;

        closest =  bst.first();
        distance = p.distanceSquaredTo(closest);
        for (Point2D point : bst){
            if (p.distanceSquaredTo(point) < distance) {
                distance = p.distanceSquaredTo(point);
                closest = point;
            }
        }
        return closest;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args)   {

        PointSET test = new PointSET();
        Point2D a = new Point2D(0.0, 0.0);
        Point2D b = new Point2D(1.1, 1.1);
        Point2D c = new Point2D(1.0, 1.0);
        test.insert(a);
        test.insert(b);
        test.insert(c);
        Point2D d = new Point2D(0.3, 0.3);
        System.out.println(test.nearest(d));


    }               // unit testing of the methods (optional)
}