import javax.swing.*;


public class Polygons extends JComponent {
    //These are used to track the coordinates of each polygon
    private int x_array[], y_array[];
    public Polygons(int x_array[], int y_array[], int points){

        this.x_array = x_array;
        this.y_array = y_array;
    }

    //Returns an array of X coordinates
    public int[] getXarray(){
        return x_array;
    }

    //Returns an array of Y coordinates
    public int[] getYarray(){
        return y_array;
    }


}
