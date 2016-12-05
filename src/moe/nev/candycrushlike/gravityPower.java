package moe.nev.candycrushlike;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by Shinra on 28.11.16.
 */
public class gravityPower implements Runnable{
    private Integer isRunning;
    private checker toDelete;
    private Semaphore antiCollider;
    private Random rnd;

    public gravityPower(Integer isRunningP, Random rndP){
        System.out.println("derpC");
        this.isRunning = isRunningP;
        this.antiCollider = new Semaphore(1);
        this.toDelete = null;
        this.rnd = rndP;
    }

    public void setToDelete(checker toDelete) {
        try {
            System.out.println("derpData");
            this.antiCollider.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("derpDataA");
        this.toDelete = toDelete;
    }

    public checker getToDelete() {
        return toDelete;
    }

    public void run(){
        System.out.println("derp");
        while (isRunning==1){
            if (toDelete!=null){
                if (toDelete instanceof verticalChecker){
                    System.out.println("sup v");
                }
                if (toDelete instanceof horizontalChecker){
                    System.out.println("sup h");
                    suppressionH();
                }
                toDelete = null;
                antiCollider.release();
                System.out.println("releaseD");
            }
        }
    }

    private void suppressionH(){
        for (int i=toDelete.getFirstDetected();i<toDelete.getLastDetected();i++){
            for (int j=toDelete.getRowID();j>1;j--){
                toDelete.btn[idFromXY(i,j)].setIcon(toDelete.btn[idFromXY(i,j-1)].getIcon());
            }
            toDelete.btn[idFromXY(i,0)].setButtonType(toDelete.Letter[rnd.nextInt(toDelete.Letter.length)]);
            toDelete.btn[idFromXY(i,0)].setIcon(new ImageIcon(new
                    ImageIcon("images/"+toDelete.btn[idFromXY(i,0)].getButtonType()).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        }
    }

    private int idFromXY(int x, int y){
        return y * this.toDelete.getWidth() + x;
    }
}
