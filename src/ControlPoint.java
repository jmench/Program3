import java.awt.*;

public class ControlPoint {
    //Tracks the location and size of each point
    private int PosX, PosY, radius, previewX, previewY;
    private ControlPoint CP;
    private Color color;
    //States if the control point was the last selected point
    private boolean isPrevious = false;

    //States if the control point is the currently selected point
    private boolean isCurrent = false;
    public ControlPoint(int x, int y, int radius){
        this.PosX = x;
        this.PosY = y;
        this.radius = radius;
    }

    public boolean isCurrent(){
        return isCurrent;
    }

    //Determines if a used controlpoints was the previously used point
    public void setPrevious(boolean isPrevious){
        this.isPrevious = isPrevious;
    }

    //Determines if a controlpoint is the one that is currently being used
    public void setCurrent(boolean isCurrent){
        this.isCurrent = isCurrent;
    }

    //Sets the color of our controlpoints
    public void setColor(Color color){
        this.color = color;
    }

    //Returns the color of our controlpoints
    public Color getColor(){
        return color;
    }

    //Gets the x position of our point
    public int getPosX(){
        return PosX;
    }

    //Gets the y position of our point
    public int getPosY(){
        return PosY;
    }

    //Sets the x position of our point
    public void setPosX(int x){
        this.PosX = x;
        this.previewX=x;
    }

    //Sets the y position of our point
    public void setPosY(int y){
        this.PosY = y;
        this.previewY =y;
    }

    //Sets the X preview coordinate of our point
    public void setPreviewX(int x){
        this.previewX = x;
    }

    //Sets the Y preview coordinate of our point
    public void setPreviewY(int y){
        this.previewY =y;
    }

    //Returns the X preview coordinate
    public int getPreviewX(){
        return previewX;
    }

    //Returns the Y preview coordinate
    public int getPreviewY(){
        return previewY;
    }

    //Sets the preview coordinates of the controlpoints
    public void setNewPreview(int x, int y){
        this.previewX=x;
        this.previewY=y;
    }

    //Returns the radius of the controlpoints
    public int getRadius(){
        return radius;
    }
}
