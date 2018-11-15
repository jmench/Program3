import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControlPoint implements MouseListener {
    private int PosX, PosY, radius;
    public ControlPoint(int x, int y, int radius){
        this.PosX = x;
        this.PosY = y;
        this.radius = radius;
    }

    public void mouseClicked(MouseEvent e){};
    public void mouseEntered(MouseEvent e){};
    public void mouseExited(MouseEvent e){};
    public void  mousePressed(MouseEvent e){};
    public void mouseReleased(MouseEvent e){};

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
