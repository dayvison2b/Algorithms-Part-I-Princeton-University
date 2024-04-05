import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Point is null");
            }
        }

        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points");
            }
        }

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        double slope1 = points[p].slopeTo(points[q]);
                        double slope2 = points[p].slopeTo(points[r]);
                        double slope3 = points[p].slopeTo(points[s]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            segments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() { // the line segments
        return segments.toArray(new LineSegment[segments.size()]);
    }
}