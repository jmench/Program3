import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.event.MouseEvent;
public class myImageView extends JLabel {

    private static ControlPoint[][] CPArray= new ControlPoint[10][10];
    private static Polygons[][] polygons = new Polygons[10][10];
    private boolean redrawGrid = true;
    private boolean isDragged = false;


    // instance variable to hold the buffered image
    private BufferedImage bim=null;
    private BufferedImage filteredbim=null;

    private int vertex_x_coord[];
    private int vertex_y_coord[];

    //  tell the paintcomponent method what to draw
    private boolean showfiltered=false;

    // Default constructor
    public myImageView() {
    }

    // This constructor stores a buffered image passed in as a parameter
    public myImageView(BufferedImage img) {
        bim = img;
        filteredbim = new BufferedImage
                (bim.getWidth(), bim.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(bim.getWidth(), bim.getHeight()));
        this.addGrid();
        this.repaint();

    }

    // This mutator changes the image by resetting what is stored
    // The input parameter img is the new image;  it gets stored as an
    //     instance variable
    public void setImage(BufferedImage img) {
        if (img == null) return;
        bim = img;
        filteredbim = new BufferedImage
                (bim.getWidth(), bim.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(bim.getWidth(), bim.getHeight()));
        showfiltered=false;
        this.repaint();
    }

    // accessor to get a handle to the bufferedimage object stored here
    public BufferedImage getImage() {
        return bim;
    }

    //  show current image by a scheduled call to paint()
    public void showImage() {
        if (bim == null) return;
        showfiltered=false;
        this.repaint();
    }

    public void setRedrawGrid(boolean flag){
        this.redrawGrid =flag;
    }

    //  get a graphics context and show either filtered image or
    //  regular image
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D big = (Graphics2D) g;
        if (showfiltered)
            big.drawImage(filteredbim, 0, 0, this);
        else
            big.drawImage(bim, 0, 0, this);

        if(redrawGrid){

            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    g.setColor(Color.BLACK);
                    //Draws the diagonal connections between the points
                    if (i != 9 && j != 9) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i + 1][j + 1].getPosX(), CPArray[i + 1][j + 1].getPosY());
                    }

                    //Draws the vertical connections between the points
                    if (j != 9) {
                        g.drawLine(CPArray[i][j].getPosX() + 2, CPArray[i][j].getPosY(), CPArray[i][j + 1].getPosX() + 2, CPArray[i][j + 1].getPosY());
                    }

                    //Draws the horizontal connections between the points
                    if (i != 9) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY() + 2, CPArray[i + 1][j].getPosX(), CPArray[i + 1][j].getPosY() + 2);
                    }
                    if (i != 0 && j != 0 && i != 9 && j != 9) {
                        if(CPArray[i][j].isCurrent()){
                            g.setColor(Color.RED);
                        }
                        else{
                            g.setColor(Color.BLACK);
                        }
                        g.fillOval(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i][j].getRadius(), CPArray[i][j].getRadius());
                        vertex_x_coord = new int[]{CPArray[i][j].getPosX() - 5, CPArray[i][j].getPosX() + 7, CPArray[i][j].getPosX() + 7, CPArray[i][j].getPosX() - 5};
                        vertex_y_coord = new int[]{CPArray[i][j].getPosY() - 5, CPArray[i][j].getPosY() - 5, CPArray[i][j].getPosY() + 7, CPArray[i][j].getPosY() + 7};
                        polygons[i][j] = new Polygons(vertex_x_coord, vertex_y_coord, 4);

                        /**
                         * NOTE: Draw polygon is only used for testing. It should be deleted for the final product
                         */

                        g.drawPolygon(polygons[i][j].getXarray(), polygons[i][j].getYarray(), 4);

                    }
                }
            }
            redrawGrid = false;
        }

        else{
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {

                    g.setColor(Color.BLACK);

                    //Draws the diagonal connections between the points
                    if (i != 9 && j != 9) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i + 1][j + 1].getPosX(), CPArray[i + 1][j + 1].getPosY());
                    }

                    //Draws the vertical connections between the points
                    if (j != 9) {
                        g.drawLine(CPArray[i][j].getPosX() + 2, CPArray[i][j].getPosY(), CPArray[i][j + 1].getPosX() + 2, CPArray[i][j + 1].getPosY());
                    }

                    //Draws the horizontal connections between the points
                    if (i != 9) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY() + 2, CPArray[i + 1][j].getPosX(), CPArray[i + 1][j].getPosY() + 2);
                    }
                    //This draws our dots but ensures that the dots on the border are not drawn
                    if (i != 0 && j != 0 && i != 9 && j != 9) {
                        g.fillOval(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i][j].getRadius(), CPArray[i][j].getRadius());
                        vertex_x_coord = new int[]{CPArray[i][j].getPosX() - 5, CPArray[i][j].getPosX() + 7, CPArray[i][j].getPosX() + 7, CPArray[i][j].getPosX() - 5};
                        vertex_y_coord = new int[]{CPArray[i][j].getPosY() - 5, CPArray[i][j].getPosY() - 5, CPArray[i][j].getPosY() + 7, CPArray[i][j].getPosY() + 7};
                        polygons[i][j] = new Polygons(vertex_x_coord, vertex_y_coord, 4);

                        /**
                         * NOTE: Draw polygon is only used for testing. It should be deleted for the final product
                         */

                        g.drawPolygon(polygons[i][j].getXarray(), polygons[i][j].getYarray(), 4);
                    }
                }
            }
        }

    }




    /**
     * TODO: Add a gridsize parameter for when we want to change the gridsize
     */
    public void addGrid() {



            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    //We first create a 2D array of our control points
                    CPArray[i][j] = new ControlPoint(i * (bim.getWidth() / 9), j * (bim.getHeight() / 9), 5);
                }
            }



            this.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent e){
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            if(CPArray[i][j].isCurrent()){
                                CPArray[i][j].setPrevious(true);
                                CPArray[i][j].setCurrent(false);
                            }
                        }
                    }
                    isDragged= false;
                }
                public void mousePressed(MouseEvent e) {
                    if(contains(e.getPoint())){
                        isDragged=true;
                    }
                    repaint();
                }
            });

            this.addMouseMotionListener(new MouseMotionListener(){
                public void mouseMoved(MouseEvent e) {}



            public void mouseDragged(MouseEvent e){

                if(isDragged) {
                    for(int i=1; i<polygons.length; i++){
                        for(int j=1; j<polygons.length; j++){
                            if(polygons[i][j]!=null) {
                                int arr[] = polygons[i][j].getXarray();
                                int arr2[] = polygons[i][j].getYarray();
                                if (e.getX() >= arr[0] && e.getX() <= arr[2] && e.getY() >= arr2[0] && e.getY() <= arr2[2]) {
                                    CPArray[i][j].setPosX(e.getX());
                                    CPArray[i][j].setPosY(e.getY());
                                    CPArray[i][j].setColor(Color.RED);
                                    CPArray[i][j].setCurrent(true);

                                    redrawGrid = true;


                                }
                            }
                        }
                    }
                    repaint();
                    myImageView.super.revalidate();
                }
                }

        });

    }



    public static Polygons[][] getPolyArray(){
        return polygons;
    }

    public static ControlPoint[][] getCPArray(){
        return CPArray;
    }

}