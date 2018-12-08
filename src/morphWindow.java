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


    //Shows the morphing image
    private JPanel morphingImage;

    private myImageGrid SIG = new myImageGrid();

    private myImageGrid EIG = new myImageGrid();
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

    //Sets the current image in the morphwindow
    public void setImage(BufferedImage img) {
        if (img == null) return;
        bim = img;
        setPreferredSize(new Dimension(bim.getWidth(), bim.getHeight()));
        this.repaint();
    }

    //Shows the current image in the morphwindow
    public void showImage() {
        if (bim == null) return;
        this.repaint();

    }

//Repaints the the current image in the morphwindow
  public void paint(Graphics g) {
        super.paint(g);
        g.translate(55,150);


        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bim, 0, 0, this);

    }

    public void setView(View view){
      this.view = view;
    }
}