import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JButton setLeft, setRight, previewMorph;
    private JSlider ControlPointSize;
    private JPanel buttonPanel, imagePanel;
    private ControlPanel leftPanel, rightPanel;

    public void JMorphView(){


        //This panel is used to hold our buttons
        buttonPanel = new JPanel();

        imagePanel = new JPanel();
        FlowLayout experimentLayout = new FlowLayout();



        imagePanel.setLayout(experimentLayout);

        leftPanel = new ControlPanel(Color.DARK_GRAY);
        rightPanel = new ControlPanel(Color.LIGHT_GRAY);

        leftPanel.setPreferredSize(new Dimension(400, 300));
        rightPanel.setPreferredSize(new Dimension(400, 300));

        imagePanel.add(leftPanel);
        imagePanel.add(rightPanel);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        //Allows the user to set the left and the right images
        setLeft = new JButton("Set Left Image");
        setRight = new JButton("Set Right Image");

        //This allows the user to preview the image morph
        previewMorph = new JButton("Preview Morph");

        buttonPanel.add(setLeft);
        buttonPanel.add(setRight);
        buttonPanel.add(previewMorph);
        add(imagePanel);
        add(buttonPanel);

        setSize(1000,1000);
        setVisible(true);
    }
}