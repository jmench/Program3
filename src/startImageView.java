import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;
import java.awt.event.MouseEvent;
public class startImageView extends JLabel {

    private static ControlPoint[][] CPArray= new ControlPoint[20][20];
    private static Polygons[][] polygons = new Polygons[20][20];
    private boolean redrawGrid = true;
    private boolean isDragged = false;
    private int gridSize;
    private startImageGrid SIG = new startImageGrid();


    // instance variable to hold the buffered image
    private BufferedImage bim=null;
    private BufferedImage filteredbim=null;

    private int vertex_x_coord[];
    private int vertex_y_coord[];

    //  tell the paintcomponent method what to draw
    private boolean showfiltered=false;

    // Default constructor
    public startImageView() {
    }

    // This constructor stores a buffered image passed in as a parameter
    public startImageView(BufferedImage img) {
        bim = img;
        filteredbim = new BufferedImage
                (bim.getWidth(), bim.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(bim.getWidth(), bim.getHeight()));


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

            for(int i=0; i<gridSize; i++){
                for(int j=0; j<gridSize; j++){
                    g.setColor(Color.BLACK);
                    //Draws the diagonal connections between the points
                    if (i != gridSize-1 && j != gridSize-1) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i + 1][j + 1].getPosX(), CPArray[i + 1][j + 1].getPosY());
                    }

                    //Draws the vertical connections between the points
                    if (j != gridSize-1) {
                        g.drawLine(CPArray[i][j].getPosX() + 2, CPArray[i][j].getPosY(), CPArray[i][j + 1].getPosX() + 2, CPArray[i][j + 1].getPosY());
                    }

                    //Draws the horizontal connections between the points
                    if (i != gridSize-1) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY() + 2, CPArray[i + 1][j].getPosX(), CPArray[i + 1][j].getPosY() + 2);
                    }
                    if (i != 0 && j != 0 && i != gridSize-1 && j != gridSize-1) {
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
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {

                    g.setColor(Color.BLACK);

                    //Draws the diagonal connections between the points
                    if (i != gridSize-1 && j != gridSize-1) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i + 1][j + 1].getPosX(), CPArray[i + 1][j + 1].getPosY());
                    }

                    //Draws the vertical connections between the points
                    if (j != gridSize-1) {
                        g.drawLine(CPArray[i][j].getPosX() + 2, CPArray[i][j].getPosY(), CPArray[i][j + 1].getPosX() + 2, CPArray[i][j + 1].getPosY());
                    }

                    //Draws the horizontal connections between the points
                    if (i != gridSize-1) {
                        g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY() + 2, CPArray[i + 1][j].getPosX(), CPArray[i + 1][j].getPosY() + 2);
                    }
                    //This draws our dots but ensures that the dots on the border are not drawn
                    if (i != 0 && j != 0 && i != gridSize-1 && j != gridSize-1) {
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
        SIG.setCPArray(CPArray);
        SIG.setPolygons(polygons);
        SIG.setGridSize(gridSize);
    }




    /**
     * TODO: Add a gridsize parameter for when we want to change the gridsize
     */
    public void addGrid(int gridSize) {


        this.gridSize = gridSize;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                //We first create a 2D array of our control points

                if(gridSize==10) {
                    CPArray[i][j] = new ControlPoint(i * (bim.getWidth() / 9), j * (bim.getHeight() / 9), 5);
                }
                else if(gridSize==5) {
                    CPArray[i][j] = new ControlPoint(i * (bim.getWidth() / 4)-3, j * (bim.getHeight() / 4), 5);
                }
                else if(gridSize==20) {
                    CPArray[i][j] = new ControlPoint(i * (bim.getWidth() / 18)-20, j * (bim.getHeight() / 19), 5);
                }
            }
        }

        this.addMouseListener(new MouseAdapter() {
            //When the mouse is released, we set the last dragged point
            //as the "previous point"
            public void mouseReleased(MouseEvent e){
                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < gridSize; j++) {
                        if(CPArray[i][j].isCurrent()){
                            CPArray[i][j].setPrevious(true);
                            CPArray[i][j].setCurrent(false);
                        }
                    }
                }
                isDragged= false;
            }

            //This determines if we can drag a point
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
                //Here, we find which point was selected, and animate it as dragging
                if(isDragged) {
                    for(int i=0; i<gridSize; i++){
                        for(int j=0; j<gridSize; j++){
                            if(polygons[i][j]!=null) {
                                int arr[] = polygons[i][j].getXarray();
                                int arr2[] = polygons[i][j].getYarray();
                                if (e.getX() >= arr[0] && e.getX() <= arr[2] && e.getY() >= arr2[0] && e.getY() <= arr2[2]) {
                                    if(i!=0 && j!=0 && i!=gridSize-1 && j!=gridSize-1) {

                                        //Prevents a dot from horizontally crossing a neighbor's path
                                        if(CPArray[i][j].getPosX()<CPArray[i-1][j].getPosX()+5
                                        || CPArray[i][j].getPosX()<CPArray[i-1][j-1].getPosX()+5
                                        || CPArray[i][j].getPosX()<CPArray[i-1][j+1].getPosX()+5){
                                            CPArray[i][j].setPosX(e.getX()+7);
                                            return;
                                        }
                                        if(CPArray[i][j].getPosX()>CPArray[i+1][j].getPosX()-5
                                        || CPArray[i][j].getPosX()>CPArray[i+1][j-1].getPosX()-5
                                        || CPArray[i][j].getPosX()>CPArray[i+1][j+1].getPosX()-5){
                                            CPArray[i][j].setPosX(e.getX()-7);
                                            return;
                                        }

                                        //Prevents a dot from vertically crossing a neighbor's path
                                        if(CPArray[i][j].getPosY()<CPArray[i][j-1].getPosY()+5
                                        || CPArray[i][j].getPosY()<CPArray[i-1][j-1].getPosY()+5
                                        || CPArray[i][j].getPosY()<CPArray[i+1][j-1].getPosY()+5){
                                            CPArray[i][j].setPosY(e.getY()+7);
                                            return;
                                        }
                                        if(CPArray[i][j].getPosY()>CPArray[i][j+1].getPosY()-5
                                        || CPArray[i][j].getPosY()>CPArray[i-1][j+1].getPosY()-5
                                        || CPArray[i][j].getPosY()>CPArray[i+1][j+1].getPosY()-5){
                                            CPArray[i][j].setPosY(e.getY()-7);
                                            return;
                                        }

                                        /** IN-PROGRESS*/
                                        //Prevents a dot from diagonally crossing a neighbor's path
                                        if(CPArray[i][j].getPosY()<((CPArray[i][j-1].getPosY()+5)+(CPArray[i-1][j].getPosY()-5))/2
                                        || CPArray[i][j].getPosY()<((CPArray[i][j-1].getPosY()+5)+(CPArray[i+1][j].getPosY()-5))/2){
                                            CPArray[i][j].setPosY(e.getY()+7);
                                            return;
                                        }


                                        CPArray[i][j].setPosX(e.getX());
                                        CPArray[i][j].setPosY(e.getY());
                                        CPArray[i][j].setColor(Color.RED);
                                        CPArray[i][j].setCurrent(true);


                                        redrawGrid = true;
                                        SIG.setCPArray(CPArray);
                                        SIG.setPolygons(polygons);
                                        SIG.setGridSize(gridSize);

                                    }
                                }
                            }
                        }
                    }
                    repaint();
                    startImageView.super.revalidate();
                }
            }

        });
        this.repaint();
    }





}