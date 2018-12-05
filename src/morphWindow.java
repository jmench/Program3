import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;



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
    //This opens a new window to start our morphing
    public morphWindow(JFrame frame) {

        super(frame, "Preview Morph", true);
        setLayout(new FlowLayout());

        startMorph = new JButton("Start Morph");
        morphingImage = new JPanel();

        grid = new JLabel();
        CPArray = SIG.getCPArray();
        polygons = SIG.getPolygons();
        gridSize = SIG.getGridSize();

        add(morphingImage);
        add(grid);
        add(startMorph);
        grid.repaint();


    }

/** TODO: This needs to properly display the Start Grid in the morph window. A function for transitioning to
  the End Image grid will be added later*/
    public void paint(Graphics g) {
        super.paint(g);
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