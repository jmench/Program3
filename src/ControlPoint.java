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


    public boolean isPrevious(){
        return isPrevious;
    }

    public boolean isCurrent(){
        return isCurrent;
    }

    public void setPrevious(boolean isPrevious){
        this.isPrevious = isPrevious;
    }
    public void setCurrent(boolean isCurrent){
        this.isCurrent = isCurrent;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public int getPosX(){
        return PosX;
    }
    public int getPosY(){
        return PosY;
    }

    public void setPosX(int x){
        this.PosX = x;
    }
    public void setPosY(int y){
        this.PosY = y;
    }

    public void setPreviewX(int x){
        this.previewX = x;
    }

    public void setPreviewY(int y){
        this.previewY =y;
    }

    public int getRadius(){
        return radius;
    }
}
