import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private final List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("The input points array is null");
        }

        // Check for any null points
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("The input points array contains a null point");
            }
        }

        // Sort the points array
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        // Check for duplicate points
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException("The input points array contains duplicate points");
            }
        }

        segments = new ArrayList<>();

        // Iterate over each point as the origin
        for (int i = 0; i < sortedPoints.length; i++) {
            Point origin = sortedPoints[i];

            Arrays.sort(sortedPoints, origin.slopeOrder());

            int count = 1;
            double currentSlope = origin.slopeTo(sortedPoints[0]);

            // Find collinear points
            for (int j = 1; j < sortedPoints.length; j++) {
                double slope = origin.slopeTo(sortedPoints[j]);
                if (Double.compare(slope, currentSlope) == 0) {
                    count++;
                } else {
                    if (count >= 3) {
                        addSegment(origin, sortedPoints, j - count, j - 1);
                    }
                    count = 1;
                    currentSlope = slope;
                }
            }

            if (count >= 3) {
                addSegment(origin, sortedPoints, sortedPoints.length - count, sortedPoints.length - 1);
            }

            Arrays.sort(sortedPoints);
        }
    }

    private void addSegment(Point origin, Point[] points, int startIndex, int endIndex) {
        if (origin.compareTo(points[startIndex]) < 0) {
            segments.add(new LineSegment(origin, points[endIndex]));
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }
}
