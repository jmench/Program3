import java.awt.*;

public class ControlPoint {
    private int PosX, PosY, radius;
    private ControlPoint CP;
    private Color color;
    private boolean isPrevious = false;
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

    public void setControlPoint(ControlPoint CP){this.CP = CP;}

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

    public int getRadius(){
        return radius;
    }
}
