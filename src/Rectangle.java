/*
Barak Davidovitch
211604350
oop biu 2024
*/
import java.util.List;
import java.util.ArrayList;

/**
 * This class representing Rectangle that made of the upper left Point and
 * the sizes of the width and height.
 * The Rectangle is aligned with the axes.
 */
public class Rectangle {

    private Point upperLeft;
    private final double width;
    private final double height;
    private final List<Line> edges;

    /**
     * The constructor.
     * @param upperLeft THe upper left point of the rectangle.
     * @param width the width of rectangle (parallel to x-axis).
     * @param height the height of rectangle (parallel to y-axis).
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;

        // add all edges to edges list.
        edges = new ArrayList<>();
        Point upperRight = new Point(this.upperLeft.getX() + this.width,
                                    this.upperLeft.getY());
        Point lowerRight = new Point(upperRight.getX(),
                                    upperRight.getY() + this.height);
        Point lowerLeft = new Point(this.upperLeft.getX(), lowerRight.getY());

        edges.add(new Line(this.upperLeft, upperRight));
        edges.add(new Line(upperRight, lowerRight));
        edges.add(new Line(lowerRight, lowerLeft));
        edges.add(new Line(lowerLeft, this.upperLeft));
    }

    /**
     * copy constructor.
     * @param rectangle the Rectangle we want to copy.
     */
    public Rectangle(Rectangle rectangle) {
        this(rectangle.getUpperLeft(), rectangle.getWidth(),
                rectangle.getHeight());
    }

    /**
     * this method make the list of intersection points between the rectangle.
     * and the line.
     * @param line the line we want to intersect with.
     * @return a (possibly empty) List of intersection points.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();
        for (Line edge : this.edges) {
            Point tempPoint = line.intersectionWith(edge);
            if (tempPoint == null) {
                continue;
            }
            // check if there is point like is in the list already.
            boolean equalFlag = false;
            for (Point intersectionPoint : intersections) {
                if (tempPoint.equals(intersectionPoint)) {
                    equalFlag = true;
                }
            }
            if (!equalFlag) {
                intersections.add(tempPoint);
            }
        }
        return intersections;
    }

    /**
     * get the Rectangle width.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * get the Rectangle height.
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * get the upper-Left point.
     * @return a copy of the upper-Left point of rectangle.
     */
    public Point getUpperLeft() {
        return new Point(this.upperLeft);
    }

    /**
     * give the min X value in the rectangle.
     * @return min X of the upper left point, that is the smallest X in rectangle.
     */
    public double getMinX() {
        return this.upperLeft.getX();
    }

    /**
     * give the min y value in the rectangle.
     * @return min y of the upper left point, that is the smallest y in rectangle.
     */
    public double getMinY() {
        return this.upperLeft.getY();
    }

    /**
     * give the max X value in the rectangle.
     * @return max X of the lower right point, that is the biggest X in rectangle.
     */
    public double getMaxX() {
        return this.upperLeft.getX() + this.width;
    }

    /**
     * give the max y value in the rectangle.
     * @return max y of the lower right point, that is the biggest y in rectangle.
     */
    public double getMaxY() {
        return this.upperLeft.getY() + this.height;
    }

    /**
     * method that set the upperLeft point to newPoint.
     * @param newPoint The point we want to switch to.
     */
    public void setUpperLeft(Point newPoint) {
        this.upperLeft = new Point(newPoint);
    }
}
