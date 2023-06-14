package de.joshua.gameobjects.Attackers;

import de.joshua.util.Coordinate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Circle extends Attacker {
    public Circle(double healthPoints, double degreeToMainPlayerTower, double speed, String colour) {
        super(healthPoints, degreeToMainPlayerTower, speed, colour);

        setStartHealthPoints(healthPoints);
    }

    public void paintAttacker(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        paintStatusBar(g2d);

        Ellipse2D circle;

        circle = new Ellipse2D.Double(getObjectCoordinate().myGetX(), getObjectCoordinate().myGetY(), myGetWidth(), myGetHeight());

        AffineTransform transform = new AffineTransform();
        g2d.setColor(Color.decode(colour));

        Shape transformed;

        transformed = transform.createTransformedShape(circle);

        g2d.fill(transformed);
    }
}
