package de.joshua.gameobjects;

import de.joshua.gameobjects.Attackers.Attacker;
import de.joshua.gameobjects.Attackers.Circle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

public class Tower extends GameObject {
    private ArrayList<Missile> missile = new ArrayList<>();
    private ArrayList<Attacker> attacker = new ArrayList<>();

    int removedMissiles = 0;
    boolean stopGame = false;

    public Tower() {
        super();
        setObjectCoordinate(getMainPlayerTowerCoordinate());
        mySetWidth(getMainPlayerTowerWidth());
        mySetHeight(getMainPlayerTowerHeight());

        setRadius(myGetWidth() /2);
        setHealthPoints(10);
        setStartHealthPoints(getHealthPoints());

        for(int i = 0; i != 50; i++){
            missile.add(new Missile(getMovingAngle()));
        }
    }

    public ArrayList<Missile> getMissile() {
        return missile;
    }

    public void setMissile(ArrayList<Missile> missile) {
        this.missile = missile;
    }

    public ArrayList<Attacker> getAttacker() {
        return attacker;
    }

    public void setAttacker(ArrayList<Attacker> attacker) {
        this.attacker = attacker;
    }

    public boolean getStopGame() {
        return stopGame;
    }

    @Override
    public void paintMe(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintTower(g2d);
    }

    public void setDeltaMovingAngle(String direction, double changeMovingAngle){
        if(direction.equals("+")){
            setMovingAngle(getMovingAngle() + changeMovingAngle);
        }else{
            setMovingAngle(getMovingAngle() - changeMovingAngle);
        }
    }

    private void paintTower(Graphics2D g2d) {
        paintStatusBar(g2d);

        Ellipse2D towerBody = new Ellipse2D.Double(getObjectCoordinate().myGetX(), getObjectCoordinate().myGetY(), myGetWidth(), myGetHeight());

        RoundRectangle2D tankCannonPipe = new RoundRectangle2D.Double(getObjectCoordinate().myGetX()+40, // + tower width
                getObjectCoordinate().myGetY()+40-8, // + tower width - this. width/2
                75, 16, 5, 5);

        RoundRectangle2D tankTurret = new RoundRectangle2D.Double(getObjectCoordinate().myGetX()+20,
                getObjectCoordinate().myGetY()+20,
                40, 40, 15, 8);

        AffineTransform transform = new AffineTransform();
        transform.rotate(getMovingAngle(), towerBody.getCenterX(), towerBody.getCenterY());

        g2d.setColor(Color.decode("#000000"));
        Shape transformed = transform.createTransformedShape(towerBody);
        g2d.fill(transformed);
        g2d.setColor(Color.DARK_GRAY);
        transformed = transform.createTransformedShape(tankCannonPipe);
        g2d.fill(transformed);
        transformed = transform.createTransformedShape(tankTurret);
        g2d.fill(transformed);

        if (missile.size() > 0) {
            missile.get(0).paintMe(g2d);
        }
        for(int i = 0; i < missile.size()-1; i++){
            if(missile.get(i).getMovingDistance() >= 5) {
                missile.get(i+1).paintMe(g2d);
            }
        }

        for (Attacker value : attacker) {
            value.paintMe(g2d);
        }

        attackerTouchesTower();
    }

    public void shoot() {
        if (getMovingAngle() > 2 * Math.PI) {
            setMovingAngle(getMovingAngle() - 2 * Math.PI);
        }

        for(int i = 0; i < missile.size(); i++){
            if(missile.get(i).missileRotate){
                missile.get(i).degree = getMovingAngle();
            }
        }

        for(int i = 0; i < missile.size()-1; i++){
            if(missile.get(i).getMovingDistance() > 0){      // If the missile starts to fly away it stops rotating
                missile.get(i).missileRotate = false;
            }
        }

        if (missile.size() > 0) {       // ShootRange increasing, missile[0], needs to start before the others so that the loop can work
            missile.get(0).setMovingDistance(missile.get(0).getMovingDistance() + 1);
            missile.get(0).setMovingAngle(getMovingAngle());
        }
        for(int i = 0; i < missile.size()-1; i++){
            if(missile.get(i).getMovingDistance() >= 5){         // Increasing the ShootRange for everything other than null
                missile.get(i+1).setMovingDistance(missile.get(i + 1).getMovingDistance() + 1);
                missile.get(i+1).setMovingAngle(getMovingAngle());
            }
        }


        for(int i = 0; i < missile.size(); i++){
            if(missile.get(i).getMovingDistance() == 100){       // If the missile is out of the window it gets removed, and a new Missile gets added
                missile.remove(i);
                missile.add(new Missile(getMovingAngle()));
            }
            if(removedMissiles > 0){        // If missiles got removed because of the attackers, new ones get added here
                missile.add(new Missile(getMovingAngle()));
                removedMissiles--;
            }
        }
    }

    public void createAttacker(String form, double healthPoints, double speed){
        Random rn = new Random();
        double degreeToMainPlayerTower = rn.nextDouble() * (2 * Math.PI);
        if(form.equals("Circle")){
            attacker.add(new Circle(healthPoints, degreeToMainPlayerTower, speed, "#000000"));
        }
    }

    public void attackerTouchesCannonball(){
        for(int i = 0; i < missile.size(); i++) {
            for (int z = 0; z < attacker.size(); z++) {
                if(touches(missile.get(i), attacker.get(z))){
                    missile.get(i).setHealthPoints(missile.get(i).getHealthPoints()-1);
                    attacker.get(z).setHealthPoints(attacker.get(z).getHealthPoints()-1);
                    removedMissiles++;
                }
            }
        }
    }

    public void attackerTouchesTower(){
        for (int z = 0; z < attacker.size(); z++) {
            if(touches(this, attacker.get(z))) {
                setHealthPoints(getHealthPoints()-attacker.get(z).getHealthPoints());
                attacker.get(z).setHealthPoints(0);
                if(getHealthPoints() < 0){
                    stopGame = true;
                }
            }
        }
    }

    public void removeThingsWithHealthNullOrLess(){
        for(int i = 0; i < missile.size(); i++){
            if(missile.get(i).getHealthPoints() < 1){
                missile.remove(i);
            }
        }
        for (int z = 0; z < attacker.size(); z++) {
            if (attacker.get(z).getHealthPoints() < 1){
                attacker.remove(z);
            }
        }
    }

}
