package de.joshua.gameobjects;

import de.joshua.util.Coordinate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Missile extends GameObject {
    double degree = 0;
    boolean missileRotate = true;
    public Missile(double movingAngel) {
        super();
        setMovingAngle(movingAngel);
        setObjectCoordinate(getMainPlayerTowerCoordinate());
        mySetWidth(10);
        mySetHeight(10);
        setDegreeToMainPlayerTower(getMovingAngle());

        setRadius(myGetWidth() /2);
        setHealthPoints(1);
    }

    @Override
    public void paintMe(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        AffineTransform transform = new AffineTransform();
        setObjectCoordinate(new Coordinate(getMainPlayerTowerCoordinate().myGetX() + getMainPlayerTowerRadius()*2-getRadius() + getMovingDistance()*10,
                getMainPlayerTowerCoordinate().myGetY()+getMainPlayerTowerRadius()-getRadius() ));

        setObjectCoordinate(rotatePointDegrees(degree, getObjectCoordinate().myGetX() - getMainPlayerTowerCoordinate().myGetX(), getRadius()));

        Ellipse2D missileShape = new Ellipse2D.Double(getObjectCoordinate().myGetX(), getObjectCoordinate().myGetY(), myGetWidth(), myGetHeight());
        Shape transformedMissileShape = transform.createTransformedShape(missileShape);
        g2d.fill(transformedMissileShape);
    }
}