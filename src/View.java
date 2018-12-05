import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JFrame {
    private JButton setLeft, setRight, previewMorph, genMorph;
    private BufferedImage image;
    private JSlider ControlPointSize;
    private JPanel buttonPanel, imagePanel, setPanel, sliderPanel, resPanel, morphPanel;
    private startImageView startImage;
    private endImageView  endImage;
    private ControlPoint CPArray[][];
    private Polygons PolyArray[][] = new Polygons[10][10];
    private int gridSize = 10;
    private JSlider startIntensity, endIntensity;
    private BufferedImage bim;

    public void JMorphView(){



        //This panel is used to hold our buttons
        buttonPanel = new JPanel();

        //This holds our setLeft and setRight buttons
        setPanel = new JPanel();

        //This panel is used to hold our morph buttons
        morphPanel = new JPanel();

        //This holds our sliders
        sliderPanel = new JPanel();

        //This holds our resolution
        resPanel = new JPanel();

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
        endImage.addGrid(gridSize);

        //Add individual panels to the image panel
        imagePanel.add(startImage);
        imagePanel.add(endImage);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        //Allows the user to set the left and the right images
        setLeft = new JButton("Set Left Image");
        setRight = new JButton("Set Right Image");

        JLabel startIntLabel = new JLabel("Left Image Intensity:");
        startIntensity = new JSlider(JSlider.HORIZONTAL, 1, 200, 100);

        startIntensity.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {

                startImage.changeIntensity((float)(startIntensity.getValue()/100.0));
            }
        });

        JLabel endIntLabel = new JLabel("Right Image Intensity:");
        endIntensity = new JSlider(JSlider.HORIZONTAL, 1, 200, 100);

        endIntensity.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {

                endImage.changeIntensity((float)(endIntensity.getValue()/100.0));
            }
        });

        JLabel gridRes = new JLabel("Grid Resolution");
        String[] gridStrings= {"5x5", "10x10", "20x20"};
        JComboBox gridSizes = new JComboBox(gridStrings);
        gridSizes.setSelectedIndex(1);


        //This allows the user to start the morph generation
        genMorph = new JButton("Generate Morph");

        //This allows the user to preview the image morph
        previewMorph = new JButton("Preview Morph");
        previewMorph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                morphWindow gui = new morphWindow(View.this, startImage.getImage());
                gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Not exit, exit would close
                gui.setSize(700, 700);
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
                gui.setResizable(false);
            }
        });

        setPanel.add(setLeft);
        setPanel.add(setRight);


        sliderPanel.add(startIntLabel);
        sliderPanel.add(startIntensity);
        sliderPanel.add(endIntLabel);
        sliderPanel.add(endIntensity);

        resPanel.add(gridRes);
        resPanel.add(gridSizes);

        morphPanel.add(previewMorph);
        morphPanel.add(genMorph);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        buttonPanel.add(setPanel);
        buttonPanel.add(sliderPanel);
        buttonPanel.add(resPanel);
        buttonPanel.add(morphPanel);


        //Adds the image and button panels to our JFrame
        add(imagePanel);
        add(buttonPanel);

        setSize(1000,1000);
        setVisible(true);





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
                                            endImage.addGrid(gridSize);
                                            startImage.repaint();
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
                                //If the image dimensions are too large, then we use a resizing function
                                if(image.getHeight() >400 || image.getWidth()>600){
                                    double x = 600.0/image.getWidth();
                                    double y = 400.0/image.getHeight();
                                    System.out.println("Dimensions: "+x+" "+y);
                                    image = resize(image,x,y);
                                }
                            } catch (IOException e1){};

                            //Here we display the image in it's panel
                            startImage.setOrigImage(image);
                           //startImage.setImage(image);
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
                                if(image.getHeight() >400 || image.getWidth()>600){
                                    double x = 600.0/image.getWidth();
                                    double y = 400.0/image.getHeight();
                                    System.out.println("Dimensions: "+x+" "+y);
                                    image = resize(image,x,y);
                                }

                            } catch (IOException e1){};
                            endImage.setImage(image);
                            endImage.showImage();
                            endImage.addGrid(gridSize);
                        }
                    }
                }
        );
    }
    //A function used to resize the buffered image
    //Source: https://stackoverflow.com/questions/9417356/bufferedimage-resize
    public static BufferedImage resize(BufferedImage img, double newW, double newH) {
        System.out.println("Rescaled "+newW+" "+newH );
        Image tmp = img.getScaledInstance((int) (img.getWidth()*newW), (int) (img.getHeight()*newH), Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage((int) (img.getWidth()*newW), (int) (img.getHeight()*newH), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
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