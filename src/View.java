import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JButton setLeft, setRight, previewMorph;
    private JSlider ControlPointSize;
    private JPanel buttonPanel, imagePanel;
    private ControlPanel cpanel;

    public void JMorphView(){


        //This panel is used to hold our buttons
        buttonPanel = new JPanel();

        cpanel = new ControlPanel();

        //Allows the user to set the left and the right images
        setLeft = new JButton("Set Left Image");
        setRight = new JButton("Set Right Image");

        //This allows the user to preview the image morph
        previewMorph = new JButton("Preview Morph");

        buttonPanel.add(setLeft);
        buttonPanel.add(setRight);
        buttonPanel.add(previewMorph);
        add(cpanel);
        add(buttonPanel);
        setSize(1000,1000);
        setVisible(true);
    }
}