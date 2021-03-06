import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;


public class morphWindow extends JDialog {

    //The button to start our morph
    private JButton startMorph;

    //Shows the morphing image
    private JPanel morphingImage;

    private static ControlPoint[][] startArr= new ControlPoint[20][20];
    private static ControlPoint[][] endArr= new ControlPoint[20][20];
    private static Polygons[][] polygons = new Polygons[20][20];
    private JLabel grid;
    private int gridSize;
    private int vertex_x_coord[];
    private int vertex_y_coord[];
    private startImageGrid SIG = new startImageGrid();
    //private endImageGrid EIG = new endImageGrid();
    private startImageGrid EIG = new startImageGrid();
    private JPanel previewPanel;
    private View view;


    // instance variable to hold the buffered image
    private BufferedImage bim=null;

    //This opens a new window to start our morphing
    public morphWindow(JFrame frame, BufferedImage img) {

        super(frame,  "Morph Window", true);

        setLayout(new FlowLayout());

        setImage(img);
        showImage();

        previewPanel = new JPanel();


        morphingImage = new JPanel();

        previewPanel.add(morphingImage);

        add(previewPanel);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                view.resetFinalImage();

            }
        });
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

    }

    public BufferedImage getImage() {
        return bim;
    }

    public void setView(View view){
      this.view = view;
    }
}