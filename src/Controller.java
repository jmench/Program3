import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Controller  {
    private int frameCtr =0;
    private int seconds =0;
    private int fps =0;
    private int time = 0;
    private Timer timer;
    private View view;
    private  ControlPoint[][] endCPArr ;
    private  ControlPoint[][] morphCPArr;
    private  ControlPoint[][] origCPArr;
    private startImageView SV;
    private boolean runMorph =false;


    public Controller(){
            fps=0;
            time=0;

        /**The value was originally left at '1' (instead of 1000), so
         * this we need to be readjusted for a consisten FPS rate
         */
        timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameCtr = view.getFPS();
                seconds = view.getSeconds();
                if(time% (1000/frameCtr)==0){
                    fps++;

                    view.startMorph(fps, frameCtr);

                   view.showMorph(fps, seconds*frameCtr);
                   redraw();
                   // origGridRedraw();

                }

                if(fps== seconds*frameCtr){
                  //  timer.stop();
                }


              /*  if(flag){
                    origGridRedraw();
                }
                else {
                    redraw();
                }*/
                time++;
            }
        });

      //  timer.start();






    }

    public void setArrays(ControlPoint startArr[][], ControlPoint endArr[][], startImageView SV){
        this.SV =SV;
        this.endCPArr=endArr;
        this.morphCPArr= startArr;
        this.origCPArr = startArr;
    }

    public void startTimer(){
        timer.start();
        runMorph=true;
    }

    public void redraw(){
        for(int i=1; i<SV.getGridSize(); i++){
            for(int j=1; j<SV.getGridSize(); j++){
                morphCPArr[i][j].setPreviewX(morphCPArr[i][j].getPosX());
                morphCPArr[i][j].setPreviewY(morphCPArr[i][j].getPosY());
                int dx,dy;
                int x1 = morphCPArr[i][j].getPosX();
                int x2 = endCPArr[i][j].getPosX();
                int y1 = morphCPArr[i][j].getPosY();
                int y2 =endCPArr[i][j].getPosY();
                if(x1!=x2 && y1!=y2){
                    dx = x1 +(10*(x2-x1)/40);
                    dy = y1 + (10*(y2-y1)/40);
                    this.morphCPArr[i][j] = new ControlPoint(dx,dy,5);
                }
                SV.repaint();

            }
        }

    }

    public void origGridRedraw(){
        for(int i=1; i<SV.getGridSize(); i++){
            for(int j=1; j<SV.getGridSize(); j++){
                int dx,dy;
                int x1 = morphCPArr[i][j].getPosX();
                int x2 = origCPArr[i][j].getPosX();
                int y1 = morphCPArr[i][j].getPosY();
                int y2 =origCPArr[i][j].getPosY();
                if(x1!=x2 && y1!=y2){
                    dx = x1 +(10*(x2-x1)/40);
                    dy = y1 + (10*(y2-y1)/40);
                    this.morphCPArr[i][j] = new ControlPoint(dx,dy,5);
                }
                SV.repaint();
            }
        }

    }

    public void setView(View view){
        this.view = view;
    }

    public void stopTimer(){
        timer.stop();
        runMorph=false;
        time=0;
        fps=0;
    }
}