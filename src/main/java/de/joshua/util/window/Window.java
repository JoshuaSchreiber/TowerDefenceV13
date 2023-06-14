package de.joshua.util.window;

import de.joshua.configuration.file.YamlConfiguration;
import de.joshua.util.NamedImage;
import de.joshua.util.handler.JMenuItemHandler;
import de.joshua.util.handler.ResourceImageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import static java.awt.event.KeyEvent.VK_F11;

public abstract class Window extends JPanel {
    File file = new File("config/config.yml");
    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
    public static double height;
    public static double width;
    final JFrame mainFrame;
    Image backgroundImage;
    NamedImage[] images;
    JMenuBar mainMenuBar;
    JMenu settings;
    public Window(JFrame mainFrame, int height, int width) throws IOException {
        super();
        GameWindow.height = height;
        GameWindow.width = width;
        this.mainFrame = mainFrame;

        gameWindowInitialisation();
        mainFrame.setFocusable(true);
    }

    public void gameWindowInitialisation() {
        addF11KeyListener();
        registerWindowListenerExit();
        createMenuBar();

        mainFrame.setJMenuBar(mainMenuBar);
        mainFrame.validate();
        mainFrame.repaint();
    }
    public void registerWindowListenerExit(){
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e){
                System.exit(0);
            }
        });
    }

    public void createMenuBar() {
        mainMenuBar = new JMenuBar();
        mainFrame.setJMenuBar(mainMenuBar);

        mainMenuBar.add(createSettingsItem(mainMenuBar));
    }

    public JMenuItem createSettingsItem(JMenuBar mainMenuBar) {
        settings = new JMenu("Game Menu");

        settings.add(new JMenuItemHandler("Close Game", () -> System.exit(0)));

        JMenu fullGameWindow = new JMenu("Full Game Window");
        JMenu infoItem = new JMenu("Important: To get the Menu back, press F11!!!");
        fullGameWindow.add(infoItem);
        infoItem.add(new JMenuItemHandler("  X", () -> {
            mainMenuBar.setVisible(false);
            mainFrame.dispose();
            mainFrame.setUndecorated(true);
            mainFrame.setVisible(true);
        }));
        settings.add(fullGameWindow);

        JMenu background = new JMenu("Background");
        images = ResourceImageHandler.getImagesFromResources("images/");
        for (NamedImage image : images) {
            background.add(new JMenuItemHandler(image.getName(), () -> {
                backgroundImage = image.getImage();
                configuration.set("defaults.backgroundimagename", "images/" + image.getName());
                try {
                    configuration.save(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                repaint();
            }));
        }
        settings.add(background);

        return settings;
    }

    public void addF11KeyListener() {
        mainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == VK_F11) {
                    if (mainMenuBar.isVisible()) {
                        mainMenuBar.setVisible(false);
                        mainFrame.dispose();
                        mainFrame.setUndecorated(true);
                        mainFrame.setVisible(true);
                    } else {
                        mainFrame.dispose();
                        mainFrame.setUndecorated(false);
                        mainFrame.setVisible(true);
                        mainMenuBar.setVisible(true);
                    }
                }
            }
        });

    }

    public void removeThis(){
        mainFrame.remove(this);
    }
}
