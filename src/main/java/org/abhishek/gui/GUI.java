package org.abhishek.gui;

import javax.swing.JFrame;
import java.awt.*;

public class GUI {

    public GUI() {
        new MainGUI();
    }

    class MainGUI{
        JFrame mainFrame;
        final static String title = "Budgety";
        final static int HEIGHT = 800;
        final static int WIDTH = HEIGHT/12 * 9;

        public MainGUI() {
            mainFrame = new JFrame(title);
            mainFrame.setSize(WIDTH,HEIGHT);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            new IndexFrame(mainFrame);
            mainFrame.setVisible(true);
        }
    }
}
