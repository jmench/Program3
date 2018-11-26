import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JFrame {
    private JButton setLeft, setRight, previewMorph;
    private BufferedImage image;
    private JSlider ControlPointSize;
    private JPanel buttonPanel, imagePanel;
    private startImageView startImage;
    private endImageView  endImage;
    private ControlPoint CPArray[][];
    private Polygons PolyArray[][] = new Polygons[10][10];
    private int gridSize = 10;
    private BufferedImage bim;

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

        startImage = new startImageView(readImage("./src/boat_resized.gif"));
        endImage = new endImageView(readImage("./src/boat_resized.gif"));

        startImage.addGrid(gridSize);

        //Add individual panels to the image panel
        imagePanel.add(startImage);
        imagePanel.add(endImage);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        //Allows the user to set the left and the right images
        setLeft = new JButton("Set Left Image");
        setRight = new JButton("Set Right Image");

        JLabel gridRes = new JLabel("Grid Resolution");
        String[] gridStrings= {"5x5", "10x10", "20x20"};
        JComboBox gridSizes = new JComboBox(gridStrings);
        gridSizes.setSelectedIndex(1);


                //This allows the user to preview the image morph
                previewMorph = new JButton("Preview Morph");
        previewMorph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                morphWindow gui = new morphWindow(View.this);
                gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Not exit, exit would close
                gui.setSize(400, 275);
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
                gui.setResizable(false);
            }
        });
        buttonPanel.add(setLeft);
        buttonPanel.add(setRight);
        buttonPanel.add(previewMorph);
        buttonPanel.add(gridRes);
        buttonPanel.add(gridSizes);

        //Adds the image and button panels to our JFrame
        add(imagePanel);
        add(buttonPanel);

        setSize(1000,1000);
        setVisible(true);
       /* myImageView MIV = new myImageView();
        CPArray = MIV.getCPArray();
        PolyArray = MIV.getPolyArray();*/




        final JFileChooser fc = new JFileChooser(".");

        gridSizes.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            int v=  gridSizes.getSelectedIndex();
                                            if(v==0){
                                                gridSize=5;
                                            }
                                            else if(v==1){
                                                gridSize=10;
                                            }
                                            else{
                                                gridSize=20;
                                            }
                                            startImage.addGrid(gridSize);
                                            startImage.repaint();
                                           // endImage.addGrid(gridSize);
                                        }
                                    }
        );

        //This allows us to change the images in their respective panels
        setLeft.addActionListener(
                new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        int returnVal = fc.showOpenDialog(View.this);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            try {
                                image = ImageIO.read(file);
                            } catch (IOException e1){};

                            //Here we display the image in it's panel
                           startImage.setImage(image);
                           startImage.showImage();

                           //Then resize and redraw the grid
                            startImage.addGrid(gridSize);

                        }
                    }
                }
        );

        setRight.addActionListener(
                new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        int returnVal = fc.showOpenDialog(View.this);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            try {
                                image = ImageIO.read(file);

                            } catch (IOException e1){};
                            endImage.setImage(image);
                            endImage.showImage();
                            endImage.addGrid();
                        }
                    }
                }
        );
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

    public BufferedImage getImage() {
        return bim;
    }




}