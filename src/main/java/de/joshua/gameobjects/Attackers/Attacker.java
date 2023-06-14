package de.joshua.gameobjects.Attackers;

import de.joshua.gameobjects.GameObject;
import de.joshua.util.Coordinate;

import java.awt.*;


public abstract class Attacker extends GameObject {
    double radiusToMainPlayerTower = 1200;
    double speed;
    String colour;
    public Attacker(double healthPoints, double degreeToMainPlayerTower, double speed, String colour) {
        super();

        this.speed = speed;
        this.colour = colour;

        setDegreeToMainPlayerTower(degreeToMainPlayerTower);
        setHealthPoints(healthPoints);

        setObjectCoordinate(rotatePointDegrees(getDegreeToMainPlayerTower(), radiusToMainPlayerTower, getRadius()));
        mySetWidth(getMainPlayerTowerWidth() * 0.8);
        mySetHeight(getMainPlayerTowerHeight() * 0.8);
        setRadius(myGetWidth() /2);
    }

    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintAttacker(g2d);
        shortenDistanceToTower();
    }

    public void paintAttacker(Graphics g) {

    }

    public void shortenDistanceToTower(){
        radiusToMainPlayerTower = radiusToMainPlayerTower - speed;
        setObjectCoordinate(rotatePointDegrees(getDegreeToMainPlayerTower(), radiusToMainPlayerTower, getRadius()));
    }
}
