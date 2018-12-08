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
    private JSlider speed, fps;
    private JSlider ControlPointSize;
    private JPanel buttonPanel, imagePanel, setPanel, sliderPanel, resPanel, morphPanel, speedPanel;
    private startImageView startImage, morphImage;
    private startImageView endImage;
    private startImageGrid SIG;
    private startImageGrid EIG;

    private ControlPoint previewArr[][];
    private startImageGrid previewGrid = new startImageGrid();

    private int gridSize = 10;
    private Color CPColor = Color.BLACK;
    private JSlider startIntensity, endIntensity;
    private BufferedImage bim;
    private Controller CTR;
    private MorphTools MT =new MorphTools();
    public BufferedImage startFrames[];
    public BufferedImage endFrames[];
    public BufferedImage finalImage;

    public morphWindow gui;

    public View(Controller CTR){

        this.CTR = CTR;
        CTR.setView(this);
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

        //This panel holds the sliders for speed and fps
        speedPanel = new JPanel();

        //Panel for the images
        imagePanel = new JPanel();


        FlowLayout experimentLayout = new FlowLayout();
        imagePanel.setLayout(experimentLayout);


        startImage = new startImageView(readImage("./src/boat_resized.gif"));
        endImage = new startImageView(readImage("./src/lion.jpg"));
       //// endImage = new endImageView(readImage("./src/boat_resized.gif"));
        morphImage = new startImageView(readImage("./src/boat_resized.gif"));

        startImage.setFileName("./src/boat_resized.gif");
        endImage.setFileName("./src/lion.jpg");
        morphImage.setFileName("./src/boat_resized.gif");

        startImage.setSide("Left");
        endImage.setSide("Right");

        startImage.setView(this);
        endImage.setView(this);

        gui = new morphWindow(View.this, startImage.getImage());
        gui.setView(this);

        startImage.addGrid(gridSize);
        endImage.addGrid(gridSize);

        startImage.changeColor(Color.BLACK);
        endImage.changeColor(Color.BLACK);

        SIG = startImage.getStartGrid();
        EIG = endImage.getStartGrid();

        startImage.getCPArray();

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

        JLabel speedLabel = new JLabel("Seconds: 3");
        JLabel fpsLabel = new JLabel("Frames Per Second: 20");
        speed = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
        fps = new JSlider(JSlider.HORIZONTAL, 1, 60, 20);

        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                speedLabel.setText("Seconds: " + speed.getValue());
            }
        });

        fps.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fpsLabel.setText("Frames Per Second: " + fps.getValue());
            }
        });

        JLabel gridRes = new JLabel("Grid Resolution");
        String[] gridStrings= {"5x5", "10x10", "20x20"};
        JComboBox gridSizes = new JComboBox(gridStrings);
        gridSizes.setSelectedIndex(1);


        JLabel gridCol = new JLabel("Grid Color");
        String[] gridcols = {"Black", "Blue", "Green", "White"};
        JComboBox colors = new JComboBox(gridcols);
        colors.setSelectedIndex(0);


        //This allows the user to start the morph generation
        genMorph = new JButton("Generate Morph");

        //This allows the user to preview the image morph
        previewMorph = new JButton("Preview Morph");

        //Stops the morph preview
        JButton stopPreview = new JButton("Stop Preview");

        //Resets the preview grid
        JButton resetPreview = new JButton("Reset Preview");

        previewMorph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CTR.setArrays(SIG.getCPArray(), EIG.getCPArray(), startImage);
                CTR.startTimer();
                previewMorph.setEnabled(false);
                stopPreview.setEnabled(true);
                resetPreview.setEnabled(false);
                genMorph.setEnabled(false);
                gridSizes.setEnabled(false);

            }
        });

        stopPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CTR.stopTimer();
                previewMorph.setEnabled(true);
                stopPreview.setEnabled(false);
                resetPreview.setEnabled(true);
                genMorph.setEnabled(true);
                gridSizes.setEnabled(true);

            }
        });

        resetPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CTR.setArrays(SIG.getCPArray(), EIG.getCPArray(), startImage);
                CTR.origGridRedraw();

            }
        });

        /**We'll use this for the GENERATE MORPH button */
        genMorph.addActionListener(new ActionListener() {
                               @Override
                               public void actionPerformed(ActionEvent e) {

                                   morph();
                                   CTR.setArrays(SIG.getCPArray(), EIG.getCPArray(), startImage);
                                   CTR.startTimer();
                                  // morphWindow gui = new morphWindow(View.this, startImage.getImage());
                                   gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Not exit, exit would close
                                   gui.setSize(700, 700);
                                   gui.setLocationRelativeTo(null);
                                   gui.setVisible(true);
                                   gui.setResizable(false);
                               }});


        setPanel.add(setLeft);
        setPanel.add(setRight);


        sliderPanel.add(startIntLabel);
        sliderPanel.add(startIntensity);
        sliderPanel.add(endIntLabel);
        sliderPanel.add(endIntensity);

        resPanel.add(gridRes);
        resPanel.add(gridSizes);
        resPanel.add(gridCol);
        resPanel.add(colors);

        morphPanel.add(previewMorph);
        morphPanel.add(stopPreview);
        morphPanel.add(resetPreview);
        stopPreview.setEnabled(false);
        morphPanel.add(genMorph);

        speedPanel.add(speedLabel);
        speedPanel.add(speed);
        speedPanel.add(fpsLabel);
        speedPanel.add(fps);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        buttonPanel.add(setPanel);
        buttonPanel.add(sliderPanel);
        buttonPanel.add(resPanel);
        buttonPanel.add(speedPanel);
        buttonPanel.add(morphPanel);


        //Adds the image and button panels to our JFrame
        add(imagePanel);
        add(buttonPanel);

        setSize(1200,1000);
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
                                            endImage.repaint();
                                        }
                                    }
        );

        colors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int v= colors.getSelectedIndex();
                if(v==0){
                    CPColor=Color.BLACK;
                }
                else if(v==1){
                    CPColor=Color.BLUE;
                }
                else if (v==2){
                    CPColor=Color.GREEN;
                }
                else {
                    CPColor=Color.WHITE;
                }

                startImage.changeColor(CPColor);
                endImage.changeColor(CPColor);

                startImage.repaint();
                endImage.repaint();
            }
        });

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
                                    image = resize(image,x,y);
                                }
                            } catch (IOException e1){};

                            //Here we display the image in it's panel
                            startImage.setOrigImage(image);
                            startImage.setFileName(file.toString());
                            morphImage.setOrigImage(image);
                            morphImage.setFileName(file.toString());

                           startImage.showImage();

                           //Then resize and redraw the grid
                            startImage.addGrid(gridSize);

                            gui.setImage(image);
                            setSize(getWidth()+1,getHeight());
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
                                    image = resize(image,x,y);
                                }

                            } catch (IOException e1){};
                            endImage.setOrigImage(image);
                            endImage.setFileName(file.toString());
                            endImage.showImage();
                            endImage.addGrid(gridSize);
                            setSize(getWidth()+1,getHeight());
                        }
                    }
                }
        );
    }


    //A function used to resize the buffered image
    //Source: https://stackoverflow.com/questions/9417356/bufferedimage-resize
    public static BufferedImage resize(BufferedImage img, double newW, double newH) {

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

    public void setPreviewArr(startImageGrid previewGrid){
        this.previewGrid = previewGrid;
    }

    public void compareGrid(String side){
        for(int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++){

                if(side.equals("Left")){
                if(startImage.getCPArray()[i][j].isCurrent()){
                        endImage.getCPArray()[i][j].setCurrent(true);
                        endImage.setNewColor(i, j, Color.RED);
                    }
                    else{
                        endImage.getCPArray()[i][j].setCurrent(false);
                        endImage.setNewColor(i,j, Color.BLACK);
                    }
            }
            else if(side.equals("Right")){
                    if(endImage.getCPArray()[i][j].isCurrent()){
                        startImage.getCPArray()[i][j].setCurrent(true);
                        startImage.setNewColor(i, j, Color.RED);
                    }
                    else{
                        startImage.getCPArray()[i][j].setCurrent(false);
                        startImage.setNewColor(i,j, Color.BLACK);
                    }
                }
            }
        }

      /*  for(int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++) {
                if (endImage.getCPArray()[i][j].isCurrent()) {
                    startImage.getCPArray()[i][j].setCurrent(true);
                    startImage.setNewColor(i, j, Color.RED);
                } else {
                    startImage.getCPArray()[i][j].setCurrent(false);
                    startImage.setNewColor(i, j, Color.BLACK);
                }
            }}*/
    }

    public void startMorph(int fps, int frames){
        startImageGrid start = SIG;
        previewGrid = SIG;
        morphImage.setCPArray(SIG.getCPArray());
        startImageGrid end = EIG;


        for(int x=0; x<gridSize; x++){
            for(int y=0; y<gridSize; y++){
                double x1 = previewGrid.getCPArray()[x][y].getPosX();
                double y1= previewGrid.getCPArray()[x][y].getPosY();
                double x2 = end.getCPArray()[x][y].getPosX();
                double y2 = end.getCPArray()[x][y].getPosY();

                double i = ((fps*((x2-x1)/frames)))+x1;
                double j = ((fps*(y2-y1)/frames))+y1;
                //morphImage.getCPArray()[x][y].setPosX((int)i);
                //morphImage.getCPArray()[x][y].setPosY((int)j);
            }
        }

        for(int x=0; x<gridSize-1; x++){
            for(int y=0; y<gridSize-1; y++){


                int sx1 = start.getCPArray()[x][y].getPosX();
                int sy1 = start.getCPArray()[x][y].getPosY();
                int sx4 = start.getCPArray()[x][y+1].getPosX();
                int sy4 = start.getCPArray()[x][y+1].getPosY();
                int sx2 = start.getCPArray()[x+1][y].getPosX();
                int sy2 = start.getCPArray()[x+1][y].getPosY();
                int sx3 = start.getCPArray()[x+1][y+1].getPosX();
                int sy3 = start.getCPArray()[x+1][y+1].getPosY();

                int dx1 = previewGrid.getCPArray()[x][y].getPosX();
                int dy1 = previewGrid.getCPArray()[x][y].getPosY();
                int dx4 = previewGrid.getCPArray()[x][y+1].getPosX();
                int dy4 = previewGrid.getCPArray()[x][y+1].getPosY();
                int dx2 = previewGrid.getCPArray()[x+1][y].getPosX();
                int dy2 = previewGrid.getCPArray()[x+1][y].getPosY();
                int dx3 = previewGrid.getCPArray()[x+1][y+1].getPosX();
                int dy3 = previewGrid.getCPArray()[x+1][y+1].getPosY();


                if (((x == gridSize-1) && (y == 1)) || ((x == 1) && (y == gridSize-1))) {

                    Triangle S = new Triangle(sx1, sy1, sx2, sy2, sx4, sy4);
                    Triangle D = new Triangle(dx1, dy1, dx2, dy2, dx4, dy4);
                    MT.warpTriangle(startImage.getImage(), morphImage.getImage(), S, D, null, null);

                    S = new Triangle(sx2, sy2, sx3, sy3, sx4, sy4);
                    D = new Triangle(dx2, dy2, dx3, dy3, dx4, dy4);
                    MT.warpTriangle(startImage.getImage(), morphImage.getImage(), S, D, null, null);
                }

                else {
                    Triangle S = new Triangle(sx1, sy1, sx2, sy2, sx3, sy3);
                    Triangle D = new Triangle(dx1, dy1, dx2, dy2, dx3, dy3);
                    MT.warpTriangle(startImage.getImage(), morphImage.getImage(), S, D, null, null);

                    S = new Triangle(sx3, sy3, sx4, sy4, sx1, sy1);
                    D = new Triangle(dx3, dy3, dx4, dy4, dx1, dy1);
                    MT.warpTriangle(startImage.getImage(), morphImage.getImage(), S, D, null, null);
                }
            }
        }


        morphImage.repaint();
       // gui.setImage(morphImage.getImage());
        //gui.repaint();
    }

    public void showMorph(int fps, int frames){
       //BufferedImage startFrame= startImage.getImage();
       //BufferedImage endFrame = endImage.getImage();
         BufferedImage startFrame = startFrames[frames-1];
         BufferedImage endFrame = endFrames[frames - fps];
        BufferedImage finalImage = new BufferedImage(startFrame.getWidth(), startFrame.getHeight(), BufferedImage.TYPE_INT_RGB);


        Graphics g = finalImage.getGraphics();
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setComposite(AlphaComposite.SrcOver.derive(1 - ((float)fps / frames)));
        g2.drawImage(startFrame, 0, 0, this);

        g2.setComposite(AlphaComposite.SrcOver.derive((float) fps / frames));
        g2.drawImage(endFrame, 0, 0, this);

        File outputFile = new File("./MorphResults/Frame" + fps + ".jpg");
        try {
            ImageIO.write(finalImage, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println("Error Saving Morph Frame");
        }
        morphImage.setImage(finalImage);
        gui.setImage(finalImage);
        //gui.repaint();
    }

    public void morph(){
        /**NEEDS TO BE frames*seconds */
        int FPS = fps.getValue()*speed.getValue();

        startFrames = new BufferedImage[FPS];
        endFrames = new BufferedImage[FPS];

        morphImage = copyMorphImage(endImage.getFileName());

        for(int i=0; i<endFrames.length; i++){
            startMorph(i, FPS);
            endFrames[i] = startImageView.deepCopy(morphImage.getImage());

        }

        /** morphImageView.resetPreviewControlPoints(); */

        morphImage = copyMorphImage(startImage.getFileName());

        for(int i=0; i<startFrames.length; i++){
            startMorph(i, FPS);
            startFrames[i] = startImageView.deepCopy(morphImage.getImage());

        }

    }

    public void resetFinalImage(){
        finalImage = new BufferedImage(startImage.getWidth(), startImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        morphImage.setImage(finalImage);
        gui.setImage(finalImage);
        gui.repaint();
        CTR.stopTimer();
    }

    public int getSeconds(){
        return speed.getValue();
    }

    public int getFPS(){
        return fps.getValue();
    }


    private startImageView copyMorphImage(String file) {
        /**CHANGE THIS AND MAKE IT MORE DYNAMIC */////
        return new startImageView(readImage(file));
    }




}