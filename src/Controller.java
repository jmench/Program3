import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Controller  {
    private int frames =10;
    private int fps =0;
    private int time = 0;
    private Timer timer;
    private View view;
    private  ControlPoint[][] endCPArr ;
    private  ControlPoint[][] morphCPArr;
    private  ControlPoint[][] origArr;
    private startImageView SV;
    private boolean flag =false;


    public Controller(){


        JButton stopPreview = new JButton("Stop Preview");
            //view  = new View()
            fps=0;
            time=0;


        /**The value was originally left at '1' (instead of 1000), so
         * this we need to be readjusted for a consisten FPS rate
         */
        timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(time% (1000/frames)==0){
                    fps++;

                    /**NEEDS TO BE frames*seconds
                     * ALSO: CHANGE THE FOR LOOP SIZES TO THE CORRECT MEASUREMENTS */
                   view.showMorph(fps, frames);
                    redraw();
                  //  view.startMorph(fps, frames);
                }

                if(fps== frames*3){
                    timer.stop();
                }

              //  System.out.println("Time "+time);
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


        stopPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                time =0;
            }});




    }

    public void setArrays(ControlPoint startArr[][], ControlPoint endArr[][], startImageView SV){
        this.SV =SV;
        this.endCPArr=endArr;
        this.morphCPArr= startArr;
        this.origArr = startArr;
    }

    public void startTimer(){
        timer.start();
    }

    public void redraw(){
        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                int dx,dy;
                int x1 = morphCPArr[i][j].getPosX();
                int x2 = endCPArr[i][j].getPosX();
                int y1 = morphCPArr[i][j].getPosY();
                int y2 =endCPArr[i][j].getPosY();
                if(x1!=x2 && y1!=y2){
                    dx = x1 +(10*(x2-x1)/40);
                    dy = y1 + (10*(y2-y1)/40);
                    System.out.println(dx +" "+dy);
                    System.out.println(x1 +" "+x2+ " "+y1+" "+y2);
                    this.morphCPArr[i][j] = new ControlPoint(dx,dy,5);
                }
                SV.repaint();

            }
        }

    }

    public void origGridRedraw(){
        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                int dx,dy;
                int x1 = morphCPArr[i][j].getPosX();
                int x2 = origArr[i][j].getPosX();
                int y1 = morphCPArr[i][j].getPosY();
                int y2 =origArr[i][j].getPosY();
                if(x1!=x2 && y1!=y2){
                    dx = x1 +(10*(x2-x1)/40);
                    dy = y1 + (10*(y2-y1)/40);
                    System.out.println(dx +" "+dy);
                    System.out.println(x1 +" "+x2+ " "+y1+" "+y2);
                    this.morphCPArr[i][j] = new ControlPoint(dx,dy,5);
                }
            }
        }

        flag =true;
    }

    public void setView(View view){
        this.view = view;
    }

    public void stopTimer(){
        timer.stop();
        time=0;
        fps=0;
    }
}