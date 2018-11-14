public class ControlPoint {
    private int PosX, PosY, radius;
    public ControlPoint(int x, int y, int radius){
        this.PosX = x;
        this.PosY = y;
        this.radius = radius;
    }

    public int getPosX(){
        return PosX;
    }
    public int getPosY(){
        return PosY;
    }

    public int getRadius(){
        return radius;
    }
}
