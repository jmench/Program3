import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel{

    public ControlPanel(Color c){
        setBackground (c);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawLine(100, 100, 400, 400);
    }
}
