package drinker;

public class Point2D {

    public final int x;
    public final int y;

    public Point2D() {
        this(0, 0);
    }

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isZero() {
        return this.x == 0 && this.y == 0;
    }

    public boolean equals(Point2D b) {
        return this.x == b.x && this.y == b.y;
    }

}
