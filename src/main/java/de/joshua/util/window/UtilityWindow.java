package de.joshua.util.window;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class UtilityWindow extends Window{
    public UtilityWindow(JFrame mainFrame, int height, int width) throws IOException {
        super(mainFrame, height, width);

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.DARK_GRAY);

        JButton jButton = new JButton("Start Game!");
        jButton.setBackground(Color.RED);
        jButton.setForeground(Color.BLACK);
        jButton.setPreferredSize(new Dimension(300, 100));
        jButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jButton.setFont(jButton.getFont().deriveFont(30f));
        jButton.setFont(jButton.getFont().deriveFont(Font.BOLD));

        jButton.addActionListener(e -> {
            GameWindow gameWindow;
            try {
                removeThis();
                gameWindow = new GameWindow(mainFrame, height, width);
                mainFrame.add(gameWindow);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        this.setLayout( new GridBagLayout() );
        this.add(jButton, new GridBagConstraints());
        mainFrame.add(this);
        mainFrame.pack();
    }

}


