package org.abhishek.gui;

import org.abhishek.credentials.Configuration;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class GUI {

    public GUI() {
        new MainGUI();
    }

    class MainGUI{
        JFrame mainFrame;
        final static String title = "Budgety";
        final static int HEIGHT = 800;
        final static int WIDTH = HEIGHT/12 * 9;
        private Connection connect = null;

        public MainGUI() {
            mainFrame = new JFrame(title);
            mainFrame.setSize(WIDTH,HEIGHT);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            new IndexFrame(mainFrame);
            mainFrame.setVisible(true);
            connect = Configuration.connectToDatabase();
            if(connect == null) {
                String msg = "\nMake sure you have Postgresql 10 or higher installed OR \nif you do have then please run the budgety_database.sql in your psql shell and then try again";
                JOptionPane.showMessageDialog(mainFrame, "Couldn't connect to the database :( " + msg,"Database Connection Error",JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
}
