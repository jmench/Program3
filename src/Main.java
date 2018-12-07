/*
Program 3
CS335
Partners: Jordan Menchen & Malik Conner
*/

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

        Controller c = new Controller();
        View v = new View(c);
        //v.JMorph();
        v.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
    }

}
