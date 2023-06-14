package de.joshua.util.window;

import de.joshua.configuration.file.YamlConfiguration;
import de.joshua.gameobjects.Tower;
import de.joshua.util.Coordinate;
import de.joshua.util.NamedImage;
import de.joshua.util.handler.JMenuItemHandler;
import de.joshua.util.handler.ResourceImageHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static java.awt.event.KeyEvent.VK_F11;




public class GameWindow extends Window {
    public JMenuItem points;
    public Tower mainPlayerTower;
    public String movingDirection = "+";
    double spinningSpeed = 0.05;
    int attackerSpurn = 0;
    double attackerSpurn2 = 200;
    int attackerHealth = 2;
    JMenuItem startItem = new JMenuItem("Continue Game");
    JMenuItem stopItem = new JMenuItem("Stop Game");
    public static Timer timer;
    public static double tick;

    public GameWindow(JFrame mainFrame, int height, int width) throws IOException {
        super(mainFrame, height, width);

        gamePanelInitialisation();
        registerWindowListener();
        addSpaceButtonListener();

        mainMenuBar.add(addPointsItem(mainMenuBar));
        addStopAndContinueItems(settings);
    }

    public void gamePanelInitialisation() {
        initGame();
        mainFrame.add(this);
        repaint();
    }

    public void addSpaceButtonListener(){
        mainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (movingDirection.equals("+")) {
                        movingDirection = "-";
                    } else {
                        movingDirection = "+";
                    }
                }
            }
        });
    }

    private void registerWindowListener() {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                stopGame();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                continueGame();
            }
        });
    }
    public JMenuItem addPointsItem(JMenuBar mainMenuBar) {
        points = new JMenuItem("Points: " + tick);
        mainMenuBar.add(points);
        return points;
    }

    public void addStopAndContinueItems(JMenu settings){
        startItem = new JMenuItem("Continue Game");
        stopItem = new JMenuItem("Stop Game");
        startItem.setVisible(false);

        startItem.addActionListener(e -> {
            continueGame();
            startItem.setVisible(false);
            stopItem.setVisible(true);
        });
        settings.add(startItem);

        stopItem.addActionListener(e -> {
            stopGame();
            stopItem.setVisible(false);
            startItem.setVisible(true);
        });
        settings.add(stopItem);
    }
    private void initGame() {
        createGameGameObjects();
        tick = 0;
        timer = new Timer(5, e -> {
            try {
                doOnTick();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        timer.start();
    }

    public void doOnTick() throws IOException {
        tick++;
        points.setText("Points: " + tick);
        mainPlayerTower.setDeltaMovingAngle(movingDirection, spinningSpeed);
        mainPlayerTower.shoot();
        mainPlayerTower.attackerTouchesCannonball();
        mainPlayerTower.removeThingsWithHealthNullOrLess();

        attackerSpurn++;
        if (attackerSpurn > attackerSpurn2){
            mainPlayerTower.createAttacker("Circle", attackerHealth, 2);
            attackerSpurn = 0;
            attackerHealth++;
            attackerSpurn2 = attackerSpurn2 * 0.98;
        }

        if(mainPlayerTower.getStopGame()){
            removeThis();
            stopTimer();
            UtilityWindow utilityWindow = new UtilityWindow(mainFrame, (int) Window.height, (int) Window.width);
            mainFrame.add(utilityWindow);
        }

        repaint();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void continueTimer() {
        try {
            timer.start();
        } catch (Exception ignored) {
        }
    }

    public void stopGame() {
        stopTimer();
    }

    public void continueGame() {
        continueTimer();
    }

    public void createGameGameObjects() {
        mainPlayerTower = new Tower();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!configuration.isSet("defaults.backgroundimagename")) {
            configuration.set("defaults.backgroundimagename", "images/" + images[0].getName());
            backgroundImage = images[0].getImage();
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                backgroundImage = ImageIO.read(getClass().getClassLoader().getResource(configuration.getString("defaults.backgroundimagename")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int heightImage = backgroundImage.getHeight(null);
        int widthImage = backgroundImage.getWidth(null);
        for (int x = 0; x < GameWindow.width; x += widthImage) {
            for (int y = 0; y < GameWindow.height; y += heightImage) {
                g2d.drawImage(backgroundImage, x, y, null);
            }
        }
        mainPlayerTower.paintMe(g);
    }
}
