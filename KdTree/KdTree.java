import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {

    private int		size;
    private Node	root;

    private static class Node {

        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private final boolean vert;

        public Node(Point2D point, RectHV r, boolean vert)
        {
            this.p = point;
            this.rect = r;
            this.lb = null;
            this.rt = null;
            this.vert = vert;
        }
    }
    public 						KdTree()		// construct an empty set of points 
    {
        this.size = 0;
        this.root = null;
    }

    public boolean				isEmpty()                      // is the set empty? 
    {
        return (this.size == 0);
    }

    public int					size()                         // number of points in the set 
    {
        return (this.size);
    }

    public void                 insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        RectHV firstRec = new RectHV(0,0,1,1);
        root = put(root, p, firstRec, true);
    }

    private Node                put(Node parent, Point2D point, RectHV r, boolean vert)
    {
        double cmp;
        Node newPoint;
        RectHV newRec;

        if (parent == null)
        {
            newPoint = new Node(point, r, vert);
            this.size += 1;
            return (newPoint);
        }
        if (vert) {
            cmp = point.x() - parent.p.x();
            if (cmp == 0.0)
                cmp = point.y() - parent.p.y();
        }
        else {
            cmp = point.y() - parent.p.y();
            cmp = point.y() - parent.p.y();
            if (cmp == 0.0)
                cmp = point.x() - parent.p.x();
        }
        if (cmp == 0)
            return (parent);
        else if (cmp > 0)
        {
            if (vert)
                newRec = new RectHV(parent.p.x(), r.ymin(), r.xmax(), r.ymax());
            else
                newRec = new RectHV(r.xmin(), parent.p.y(), r.xmax(), r.ymax());
            parent.rt = put(parent.rt, point, newRec, !vert);
        }
        else if (cmp < 0)
        {
            if (vert)
                newRec = new RectHV(r.xmin(), r.ymin(), parent.p.x(), r.ymax());
            else
                newRec = new RectHV(r.xmin(), r.ymin(), r.xmax(), parent.p.y());
            parent.lb = put(parent.lb, point, newRec, !vert);
        }
        return (parent);
    }

    public boolean				contains(Point2D p)            // does the set contain point p? 
    {
        Node x = this.root;
        double cmp;

        while (x != null)
        {
            if (x.vert) {
                cmp = p.x() - x.p.x();
                if (cmp == 0.0)
                    cmp = p.y() - x.p.y();
            }
            else {
                cmp = p.y() - x.p.y();
                if (cmp == 0.0)
                    cmp = p.x() - x.p.x();
            }
            if (cmp == 0.0)
                return (true);
            else if (cmp < 0)
                x = x.lb;
            else
                x = x.rt;
        }
        return (false);
    }

    public void					draw()                         // draw all points to standard draw 
    {
        Node x = this.root;
        draw_kd(x);
    }

    private void				draw_kd(Node x)
    {
        if (x != null)
        {
            x.p.draw();
            x.rect.draw();
            draw_kd(x.lb);
            draw_kd(x.rt);
        }
    }

    public Iterable<Point2D>	range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
    {
        SET<Point2D> points;
        points = new SET<Point2D>();
        Node x = root;

        rangeSearch(rect, x, points);
        return (points);
    }

    private void				rangeSearch(RectHV rect, Node x, SET<Point2D> points)
    {
        if (x != null)
        {
            if (rect.intersects(x.rect))
            {
                if (rect.contains(x.p))
                    points.add(x.p);
                rangeSearch(rect, x.lb, points);
                rangeSearch(rect, x.rt, points);
            }
        }
    }

    public Point2D				nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        Node x = root;
        Point2D nearest;
        double cmp;

        if (this.isEmpty())
            return (null);
        nearest = root.p;
        if (p.x() > nearest.x()) {
            nearest = findNearest(p, x.rt, nearest);
            nearest = findNearest(p, x.lb, nearest);
        }
        else
        {
            nearest = findNearest(p, x.lb, nearest);
            nearest = findNearest(p, x.rt, nearest);
        }
        return (nearest);
    }

    private Point2D 			findNearest(Point2D p, Node x, Point2D nearest)
    {
        double cmp;

        if (x == null)
            return (nearest);
        if (nearest.distanceSquaredTo(p) < x.rect.distanceSquaredTo(p))
            return (nearest);
        if (x.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
            nearest = x.p;
        if (x.vert) {
            cmp = p.x() - x.p.x();
        }
        else {
            cmp = p.y() - x.p.y();
        }
        if (cmp < 0)
        {
            nearest = findNearest(p, x.lb, nearest);
            nearest = findNearest(p, x.rt, nearest);
        }
        else {
            nearest = findNearest(p, x.rt, nearest);
            nearest = findNearest(p, x.lb, nearest);
        }
        return (nearest);
    }

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        KdTree test = new KdTree();
        Point2D a = new Point2D(0.7, 0.2);
        Point2D b = new Point2D(0.5, 0.4);
        Point2D c = new Point2D(0.2, 0.3);
        Point2D d = new Point2D(0.4, 0.7);
        Point2D e = new Point2D(0.9, 0.6);
        test.insert(a);
        test.insert(b);
        test.insert(c);
        test.insert(d);
        test.insert(e);
        Point2D find = new Point2D(0.045, 0.577);
        System.out.println(test.nearest(find));
    }
}