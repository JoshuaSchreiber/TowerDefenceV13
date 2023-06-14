package de.joshua.gameobjects;

import de.joshua.util.Coordinate;
import de.joshua.util.window.GameWindow;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public abstract class GameObject {
    private Coordinate objectCoordinate;
    private double width;
    private double height;
    private double movingAngle = 0;
    private double movingDistance = 0;
    private final static Coordinate mainPlayerTowerCoordinate = new Coordinate(GameWindow.width / 2 - 50, GameWindow.height / 2 - 50);
    private final static double mainPlayerTowerWidth = 80;
    private final static double mainPlayerTowerHeight = 80;
    private final static double mainPlayerTowerRadius = 40;
    private double healthPoints;

    private double startHealthPoints;
    private double radius;
    private double degreeToMainPlayerTower;

    public double getStartHealthPoints() {
        return startHealthPoints;
    }

    public void setStartHealthPoints(double startHealthPoints) {
        this.startHealthPoints = startHealthPoints;
    }

    public double getDegreeToMainPlayerTower() {
        return degreeToMainPlayerTower;
    }

    public void setDegreeToMainPlayerTower(double degreeToMainPlayerTower) {
        this.degreeToMainPlayerTower = degreeToMainPlayerTower;
    }


    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public GameObject() {
    }

    public double getHealthPoints() {
        return healthPoints;
    }
    public void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }
    public Coordinate getMainPlayerTowerCoordinate(){return mainPlayerTowerCoordinate;}
    public double getMainPlayerTowerWidth(){
        return mainPlayerTowerWidth;
    }
    public double getMainPlayerTowerHeight(){
        return mainPlayerTowerHeight;
    }

    public double getMainPlayerTowerRadius() { return mainPlayerTowerRadius; }
    public Coordinate getObjectCoordinate() {
        return objectCoordinate;
    }

    public void setObjectCoordinate(Coordinate objectCoordinate) {
        this.objectCoordinate = objectCoordinate;
    }

    public double myGetWidth() {
        return width;
    }

    public void mySetWidth(double width) {
        this.width = width;
    }

    public double myGetHeight() {
        return height;
    }

    public void mySetHeight(double height) {
        this.height = height;
    }

    public double getMovingAngle() {
        return movingAngle;
    }

    public void setMovingAngle(double movingAngle) {
        this.movingAngle = movingAngle;
    }

    public double getMovingDistance() {
        return movingDistance;
    }

    public void setMovingDistance(double movingDistance) {
        this.movingDistance = movingDistance;
    }

    public boolean touches(GameObject one, GameObject two){
        Coordinate centeredOne = new Coordinate(one.getObjectCoordinate().myGetX() + one.getRadius(), one.getObjectCoordinate().myGetY() + one.getRadius());
        Coordinate centeredTwo = new Coordinate(two.getObjectCoordinate().myGetX() + two.getRadius(), two.getObjectCoordinate().myGetY() + two.getRadius());
        double temp = Math.sqrt(Math.pow((centeredOne.myGetX() - centeredTwo.myGetX()), 2) +
                Math.pow((centeredOne.myGetY() - centeredTwo.myGetY()), 2));
        if(temp < 0){
            temp = - temp;
        }
        if((one.getRadius() + two.getRadius()) > temp){
            return true;
        }
        return false;
    }

    public Coordinate rotatePointDegrees(double degree, double radius, double radiusOfTheRotatedObject){
        Coordinate newPoint;
        newPoint = new Coordinate(mainPlayerTowerCoordinate.myGetX()+mainPlayerTowerRadius + (radius) * Math.cos(degree),
                mainPlayerTowerCoordinate.myGetY()+mainPlayerTowerRadius + (radius) * Math.sin(degree) );
        newPoint.mySetX(newPoint.myGetX()-radiusOfTheRotatedObject);
        newPoint.mySetY(newPoint.myGetY()-radiusOfTheRotatedObject);
        return newPoint;
    }

    public void paintStatusBar(Graphics2D g2d) {

        double barOffsetY = myGetHeight() * 0.3;

        // paint Tank Energy Bar
        g2d.setColor(Color.DARK_GRAY);
        RoundRectangle2D tankEnergyBarFrame = new RoundRectangle2D.Double(Math.round(getObjectCoordinate().myGetX()) - 1,
                Math.round(getObjectCoordinate().myGetY() - barOffsetY) - 1,
                myGetWidth() + 1, 6, 0, 0);
        g2d.draw(tankEnergyBarFrame);
        if (getHealthPoints() > (getStartHealthPoints()/2)) {
            g2d.setColor(Color.decode("#00BF00"));
        } else {
            g2d.setColor(Color.RED);
        }
        RoundRectangle2D tankEnergyBar = new RoundRectangle2D.Double(Math.round(getObjectCoordinate().myGetX()),
                Math.round(getObjectCoordinate().myGetY() - barOffsetY),
                myGetWidth() / getStartHealthPoints() * (getHealthPoints()), 5, 0, 0);
        g2d.fill(tankEnergyBar);
    }


    protected abstract void paintMe(java.awt.Graphics g);
}