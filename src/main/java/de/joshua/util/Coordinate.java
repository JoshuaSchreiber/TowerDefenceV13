package de.joshua.util;

import java.awt.Point;

public class Coordinate {

    private double x;
    private double y;

    private final Point point;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
        this.point = new Point((int) x, (int) y);
    }

    public Point getPoint() {
        return point;
    }

    public double myGetX() {
        return x;
    }

    public void mySetX(double x) {
        this.x = x;
        point.setLocation(x, y);
    }

    public double myGetY() {
        return y;
    }

    public void mySetY(double y) {
        this.y = y;
        point.setLocation(x, y);
    }

    public boolean equals(Coordinate coordinate) {
        return this == coordinate;
    }

    public boolean equals(Point coordinate) {
        return this.getPoint() == coordinate;
    }
}
