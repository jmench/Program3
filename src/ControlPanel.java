import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel{
    private ArrayList<ControlPoint> CPList = new ArrayList<ControlPoint>();
    private ControlPoint CPArray[][] = new ControlPoint[10][10];
    public ControlPanel(Color c){
        setBackground (c);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                CPArray[i][j] = new ControlPoint(i*(400/9)-1, j*(300/9)-1, 5);
               // CPList.add(new ControlPoint(i*(400/9)-1, j*(300/9)-1, 5));
                g.fillOval(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i][j].getRadius(), CPArray[i][j].getRadius());
            }
        }

        for(int i=0; i<CPList.size(); i++){
            //g.fillOval(CPList.get(i).getPosX(), CPList.get(i).getPosY(), CPList.get(i).getRadius(), CPList.get(i).getRadius());
            //g.drawLine(CPList.get());
        }

    }
}
