/*
Program 3
CS335
Partners: Jordan Menchen & Malik Connor
*/

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        View v = new View();
        v.JMorphView();
        v.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
    }

}
