import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class morphWindow extends JDialog {

    //The button to start our morph
    private JButton startMorph;

    //Shows the morphing image
    private JPanel morphingImage;

    private static ControlPoint[][] CPArray= new ControlPoint[20][20];
    private static Polygons[][] polygons = new Polygons[20][20];
    private JLabel grid;
    private int gridSize;
    private int vertex_x_coord[];
    private int vertex_y_coord[];
    private startImageGrid SIG = new startImageGrid();
    private JPanel previewPanel;

    // instance variable to hold the buffered image
    private BufferedImage bim=null;

    //This opens a new window to start our morphing
    public morphWindow(JFrame frame, BufferedImage img) {

        super(frame, "Preview Morph", true);
        setLayout(new FlowLayout());
        System.out.println(img.getWidth());
        setImage(img);
        showImage();
        previewPanel = new JPanel();

        startMorph = new JButton("Start Morph");
        morphingImage = new JPanel();


        CPArray = SIG.getCPArray();
        polygons = SIG.getPolygons();
        gridSize = SIG.getGridSize();


        previewPanel.add(morphingImage);
        previewPanel.add(startMorph);
        add(previewPanel);


    }

    public void setImage(BufferedImage img) {
        if (img == null) return;


        //////CHANGE FUNCTION NAME
        bim = img;
        // origBim = img;
        //filteredbim = new BufferedImage
        // (bim.getWidth(), bim.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(bim.getWidth(), bim.getHeight()));
        //  showfiltered=false;
        this.repaint();
    }

    public void showImage() {
        if (bim == null) return;
     //   showfiltered=false;
        this.repaint();
    }

/** TODO: This needs to properly display the Start Grid in the morph window. A function for transitioning to
  the End Image grid will be added later*/
    public void paint(Graphics g) {
        super.paint(g);
        g.translate(55,150);


        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bim, 0, 0, this);
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

                       // g.drawPolygon(polygons[i][j].getXarray(), polygons[i][j].getYarray(), 4);

                    }
                }
            }


    }



}