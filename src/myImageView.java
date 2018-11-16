import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.event.MouseEvent;
public class myImageView extends JLabel {

    private static ControlPoint[][] CPArray= new ControlPoint[10][10];

    // instance variable to hold the buffered image
    private BufferedImage bim=null;
    private BufferedImage filteredbim=null;

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

    //  get a graphics context and show either filtered image or
    //  regular image
    public void paintComponent(Graphics g) {
        Graphics2D big = (Graphics2D) g;
        if (showfiltered)
            big.drawImage(filteredbim, 0, 0, this);
        else
            big.drawImage(bim, 0, 0, this);
    }

    public void addGrid() {

        Graphics g = bim.getGraphics();
        g.setColor(Color.BLACK);
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                //We first create a 2D array of our control points
                CPArray[i][j] = new ControlPoint(i * (bim.getWidth() / 9), j * (bim.getHeight() / 9), 5);
            }
        }

        for(int i=0; i<10; i++) {
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
                }
                System.out.println(CPArray[i][j].getPosX());
            }
        }
    }


    public static ControlPoint[][] getCPArray(){
        return CPArray;
    }

}