import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private final ArrayList<ArrayList<Point>> lineSegmentPoints = new ArrayList<>();
    private final Point[] points;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        this.points = points.clone();
        int n = this.points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                var p = this.points[i];
                var q = this.points[j];
                if (p.compareTo(q) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        Point[] others = new Point[n - 1];
        Arrays.sort(this.points);
        for (int i = 0; i < n; i++) {
            Point p = this.points[i];
            int k = 0;
            for (int j = 0; j < n; j++) {
                Point q = this.points[j];
                if (p.compareTo(q) != 0) {
                    others[k] = q;
                    k++;
                }
            }
            Arrays.sort(others, p.slopeOrder());
            double previousSlope = Double.MAX_VALUE;
            Point previousPoint = null;
            ArrayList<Point> pointsInALine = new ArrayList<Point>(Arrays.asList(p));
            for (Point q : others) {
                double slope = p.slopeTo(q);
                if (previousSlope == Double.MAX_VALUE || previousSlope == slope) {
                    pointsInALine.add(q);
                    if (q == others[others.length - 1]) {
                        if (pointsInALine.size() > 3) {
                            if (!checkExistanceOfPointsInAllLineSegments(pointsInALine)) {
                                lineSegmentPoints.add(pointsInALine);
                                lineSegments.add(new LineSegment(p, q));
                            }
                        }
                    }
                } else {
                    if (pointsInALine.size() > 3) {
                        if (!checkExistanceOfPointsInAllLineSegments(pointsInALine)) {
                            lineSegmentPoints.add(pointsInALine);
                            lineSegments.add(new LineSegment(p, previousPoint));
                        }
                    }
                    pointsInALine = new ArrayList<>(Arrays.asList(p, q));
                }
                previousSlope = slope;
                previousPoint = q;
            }
        }
    }

    private boolean checkExistanceOfPointsInAllLineSegments(ArrayList<Point> points) {
        for (var lineSegmentPoints : lineSegmentPoints) {
            if (lineSegmentPoints.containsAll(points)) {
                return true;
            }
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegments_arr = new LineSegment[numberOfSegments()];
        lineSegments.toArray(lineSegments_arr);
        return lineSegments_arr;
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
