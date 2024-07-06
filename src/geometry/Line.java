/*
Barak Davidovitch
211604350
OOP ex2
 */

package geometry;

import java.util.List;
import game_util.Util;

/**
 * This Line class is class of line-segments that starts in one point in
 * 2-D space and ends in other point.
 * We also wrote methode that helps us check mutuality between Lines.
 */
public class Line {

    private final Point start;
    private final Point end;

    /**
     * constructor when we get the start and end points like Point class.
     * Notice that we duplicated the Points since encapsulation principle.
     * @param start The first point if the line-segment.
     * @param end THe last point of the line-segment.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * constructor when we enter all 4 coordinates separately.
     * @param x1 first point x cord.
     * @param y1 first point y cord.
     * @param x2 second point x cord.
     * @param y2 second point y cord.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * This method return the length of the line, that is the distance between
     * start Point and end Point.
     * @return The length of the line-segment.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     * @return the middle point as Point object.
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2,
                        (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * Returns the start point of the line.
     * @return start Point as Point class.
     */
    public Point start() {
        return new Point(this.start.getX(), this.start.getY());
    }

    /**
     * Returns the end point of the line.
     * @return end Point as Point class.
     */
    public Point end() {
        return new Point(this.end.getX(), this.end.getY());
    }

    /**
     * checks if point and line has intersection.
     * @param line The line we will check
     * @param point The point we will check
     * @return null if there isn't intersection, it there is return the
     * intersection point.
     */
    public static Point pointAndLineIntersect(Line line, Point point) {
        // check if the point x is between the 2 x's of start and end points.
        if ((Util.isBigger(line.start().getX(), point.getX())
                    && Util.isBigger(line.end().getX(), point.getX()))
                || (Util.isSmaller(line.start().getX(), point.getX())
                    && Util.isSmaller(line.end().getX(), point.getX()))) {
            return null;
        }
        // placement the point in line equation y - y1 = m(x- x1).
        if (Util.isEqual(Math.abs((point.getY() - line.start().getY())
                    * (line.end().getX() - line.start().getX())
                - (line.end().getY() - line.start().getY())
                    * (point.getX() - line.start().getX())), 0)) {
            return new Point(point.getX(), point.getY());
        }
        return null;
    }

    /**
     * The method checks if 2 line-segments intersect and where.
     * @param other The second line.
     * @return if there is infinite intersection points return point (inf, inf)
     * if there is 1 return this point, if there is none return null.
     */
    public Point lineAndLineIntersect(Line other) {
        // if the 2 lines are actually points we check if the points are equal.
        if (this.start.equals(this.end)
                && other.start().equals(other.end())) {
            return (this.start.equals(other.end()) ? this.start : null);
        }

        // The cases where we have actually Line and Point.
        if (this.start.equals(this.end)) {
            return pointAndLineIntersect(other, this.start);
        }
        if (other.start().equals(other.end())) {
            return pointAndLineIntersect(this, other.start());
        }

        /*
        find the intersection between 2 vectors, when we define each vector
        start in start Point and ends in end Point.
        The solution is based on:
        https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect#565282
         */
        Point p = this.start;
        Point q = other.start();
        Point r = this.end.minus(this.start);
        Point s = other.end().minus(other.start());
        double rCrossS = r.cross(s);
        double startPointsDist = (q.minus(p)).cross(r);

        // When the two lines are collinear and can overlap.
        if (Util.isEqual(rCrossS, 0)
                && Util.isEqual(startPointsDist, 0)) {
            double t0 = (q.minus(p)).dot(r) / (r.dot(r));
            double t1 = t0 + s.dot(r) / (r.dot(r));
            double max = Math.max(t0, t1);
            double min = Math.min(t0, t1);

            if (Util.isSmaller(max, 0) || Util.isBigger(min, 1)) {
                return null;
            }
            if (Util.isEqual(max, 0)) {
                return p;
            }
            if (Util.isEqual(min, 1)) {
                return p.plus(r);
            }
            return new Point(Double.POSITIVE_INFINITY,
                            Double.POSITIVE_INFINITY);
        }

        // parallel lines with no intersect.
        if (Util.isEqual(rCrossS, 0)
                && !Util.isEqual(startPointsDist, 0)) {
            return null;
        }

        // intersect of 2 not parallel vectors.
        double t = (q.minus(p)).cross(s) / rCrossS;
        double u = startPointsDist / rCrossS;
        if (Util.isBiggerOrEqual(t, 0) && Util.isSmallerOrEqual(t, 1)
                && (Util.isBiggerOrEqual(u, 0)
                && Util.isSmallerOrEqual(u, 1))) {
            return p.plus(r.scalarMultiply(t));
        }
        return null;
    }

    /**
     * Checks if 2 lines are intersecting.
     * @param other THe second line.
     * @return false if there is no intersect,
     * true if there is inf or 1 intersect
     */
    public boolean isIntersecting(Line other) {
        Point intersectionPoint = this.lineAndLineIntersect(other);
        return intersectionPoint != null;
    }

    /**
     * Checks if 2 lines are intersecting with "this" line.
     * @param other1 The first other line
     * @param other2 The second other line
     * @return true if this 2 lines intersect with this line, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        Point intersectionPoint1 = this.lineAndLineIntersect(other1);
        if (intersectionPoint1 == null) {
            return false;
        }
        Point intersectionPoint2 = this.lineAndLineIntersect(other2);
        return intersectionPoint2 != null;
    }

    /**
     * give the point of intersection of 2 lines.
     * @param other The other line in addition to "this"
     * @return the intersection point if there is, null if there is
     * no intersection, or there is infinite.
     */
    public Point intersectionWith(Line other) {
        Point intersectionPoint = this.lineAndLineIntersect(other);
        if ((intersectionPoint == null)
                || (Util.isEqual(intersectionPoint.getX(),
                    Double.POSITIVE_INFINITY))) {
            return null;
        }
        return intersectionPoint;
    }

    /**
     * check if 2 lines are equals, start and end
     * point of each line are similar.
     * @param other The second line.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        double startX1 = this.start.getX();
        double startY1 = this.start.getY();
        double endX1 = this.end.getX();
        double endY1 = this.end.getY();
        double startX2 = other.start().getX();
        double startY2 = other.start().getY();
        double endX2 = other.end().getX();
        double endY2 = other.end().getY();

        // need to check both cases when start==end, and start==start.
        if (Util.isEqual(startX1, startX2) && Util.isEqual(startY1, startY2)
                && Util.isEqual(endX1, endX2) && Util.isEqual(endY1, endY2)) {
            return true;
        }
        return Util.isEqual(startX1, endX2) && Util.isEqual(startY1, endY2)
                && Util.isEqual(endX1, startX2) && Util.isEqual(endY1, startY2);
    }

    /**
     * return the closest intersection point of rectangle and this line.
     * @param rect the rectangle we want to find the intersections with him.
     * @return the closest intersection point to the start of the line,
     * if this line does not intersect with the rectangle, return null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }
        Point closestPoint = intersections.get(0);
        // find the closest Point to this.start.
        for (int i = 1; i < intersections.size(); i++) {
            if (Util.isSmaller(intersections.get(i).distance(this.start),
                                closestPoint.distance(this.start))) {
                closestPoint = intersections.get(i);
            }
        }
        return new Point(closestPoint);
    }
}
