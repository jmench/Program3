import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel{

    private ControlPoint CPArray[][] = new ControlPoint[10][10];
    public ControlPanel(Color c){
        setBackground (c);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                CPArray[i][j] = new ControlPoint(i*(400/9), j*(300/9), 5);
                System.out.println(CPArray[i][j].getPosX() + " "+ CPArray[i][j].getPosY());
                g.fillOval(CPArray[i][j].getPosX(), CPArray[i][j].getPosY(), CPArray[i][j].getRadius(), CPArray[i][j].getRadius());
            }
        }

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){

                //Draws the diagonal connections between the points
                if(i!=9 &&  j!=9){
                    g.drawLine(CPArray[i][j].getPosX()+2, CPArray[i][j].getPosY()+2, CPArray[i+1][j+1].getPosX()+2, CPArray[i+1][j+1].getPosY()+2);
                }

                //Draws the vertical connections between the points
                if(j!=9){
                    g.drawLine(CPArray[i][j].getPosX()+2, CPArray[i][j].getPosY(), CPArray[i][j+1].getPosX()+2, CPArray[i][j+1].getPosY());
                }

                //Draws the horizontal connections between the points
                if(i!=9){
                    g.drawLine(CPArray[i][j].getPosX(), CPArray[i][j].getPosY()+2, CPArray[i+1][j].getPosX(), CPArray[i+1][j].getPosY()+2);
                }
            }
        }
    }
}
