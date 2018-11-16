import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Polygons extends JComponent {
    //These are used to track the coordinates of each polygon
    private int x1, x2, x3, x4;
    private int y1, y2, y3, y4;
    private int x_array[], y_array[];
    public Polygons(int x_array[], int y_array[], int points){

        this.x_array = x_array;
        this.y_array = y_array;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("I've been pressed");
            }
        });
    }

    public int[] getXarray(){
        return x_array;
    }

    public int[] getYarray(){
        return y_array;
    }


}
