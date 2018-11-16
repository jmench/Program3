import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class View extends JFrame {
    private JButton setLeft, setRight, previewMorph;
    private JSlider ControlPointSize;
    private JPanel buttonPanel, imagePanel;
    private myImageView startImage, endImage;
    private ControlPoint CPArray[][] = new ControlPoint[10][10];
    private myImageView MIV; // = new myImageView();


    public void JMorphView(){



        //This panel is used to hold our buttons
        buttonPanel = new JPanel();

        //Panel for the images
        imagePanel = new JPanel();
        FlowLayout experimentLayout = new FlowLayout();
        imagePanel.setLayout(experimentLayout);


        /*
        TODO: Try to make myImageView first and then add that to the panel
         */

        startImage = new myImageView(readImage("./src/boat_resized.gif"));
        endImage = new myImageView(readImage("./src/boat_resized.gif"));
        //startImage.addGrid();
        //endImage.addGrid();



        //Add individual panels to the image panel
        imagePanel.add(startImage);
        imagePanel.add(endImage);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        //Allows the user to set the left and the right images
        setLeft = new JButton("Set Left Image");
        setRight = new JButton("Set Right Image");

        //This allows the user to preview the image morph
        previewMorph = new JButton("Preview Morph");

        buttonPanel.add(setLeft);
        buttonPanel.add(setRight);
        buttonPanel.add(previewMorph);

        //Adds to the
        add(imagePanel);
        add(buttonPanel);

        setSize(1000,1000);
        setVisible(true);
        myImageView MIV = new myImageView();
        CPArray = MIV.getCPArray();
        System.out.println("Final "+CPArray[3][3].getPosX());
        startImage.addMouseListener(new MouseListener(){
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX() + " "+ e.getY());
            }
        });


    }

    // This method reads an Image object from a file indicated by
    // the string provided as the parameter.  The image is converted
    // here to a BufferedImage object, and that new object is the returned
    // value of this method.
    // The mediatracker in this method can throw an exception

    public BufferedImage readImage (String file) {

        Image image = Toolkit.getDefaultToolkit().getImage(file);
        MediaTracker tracker = new MediaTracker (new Component () {});
        tracker.addImage(image, 0);
        try { tracker.waitForID (0); }
        catch (InterruptedException e) {}
        BufferedImage bim = new BufferedImage
                (image.getWidth(this), image.getHeight(this),
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bim.createGraphics();
        big.drawImage (image, 0, 0, this);
        return bim;

    }


}