package de.joshua.util.window;

import javax.swing.*;
import java.io.IOException;

public class WindowManger {
    private GameWindow gameWindow;
    private UtilityWindow utilityWindow;
    private JFrame mainFrame;
    private int height;
    private int width;
    private boolean firstRoundG;
    public WindowManger(JFrame mainFrame, int height, int width) throws IOException {
        this.mainFrame = mainFrame;
        this.height = height;
        this.width = width;

        utilityWindow = new UtilityWindow(mainFrame, height, width);
        addUtilityWindow();
    }

    public void addGameWindow() throws IOException {
        if(firstRoundG){
            gameWindow = new GameWindow(mainFrame, height, width);
        }
        firstRoundG = false;
        mainFrame.add(gameWindow);
    }

    public void removeGameWindow(){
        mainFrame.remove(gameWindow);
    }

    public void addUtilityWindow(){
        mainFrame.add(utilityWindow);
    }
    public void removeUtilityWindow(){
        mainFrame.remove(utilityWindow);
    }
}
