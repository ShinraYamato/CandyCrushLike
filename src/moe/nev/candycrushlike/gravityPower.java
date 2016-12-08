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
    private checker toDelete, toDeleteOld;
    private Semaphore antiCollider, release, release2;
    private Random rnd;
    private Integer isDeleting;

    public gravityPower(Integer isRunningP, Random rndP, Integer isDeletingP){
        System.out.println("destroyer init");
        this.isRunning = isRunningP;
        this.antiCollider = new Semaphore(1);
        this.release = new Semaphore(0);
        this.release2 = new Semaphore(0);
        this.toDelete = null;
        this.toDeleteOld = toDelete;
        this.rnd = rndP;
        this.isDeleting = isDeletingP;
    }

    public boolean getIsDeleting() {
        return (isDeleting > 1);
    }

    public void setIsDeleting(Boolean isDeleting) {
        if (isDeleting){
            this.isDeleting = 1;
        } else {
            this.isDeleting = 0;
        }
    }

    public void isRunning(int isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return (isRunning > 0);
    }

    public checker getToDelete() {
        return toDelete;
    }

    public void setToDelete(checker toDelete) {
        try {
            System.out.println("destroyer received data");
            this.antiCollider.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("destroyer record data");
        if (canScan(toDelete)){
            this.toDelete = toDelete;
        } else {
            antiCollider.release();
            return;
        }
        try {
            release.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean canScan(checker toDelete){
        if (toDeleteOld == null){
            return true;
        } else {
            if ((toDeleteOld instanceof verticalChecker && toDelete instanceof horizontalChecker)||(toDeleteOld instanceof horizontalChecker && toDelete instanceof verticalChecker)){
                if (toDelete.getRowID()>toDeleteOld.getFirstDetected()&&toDelete.getRowID()<toDeleteOld.getFirstDetected()){
                    return false;
                }
            }
        }
        return true;
    }

    public synchronized void run(){
        System.out.println("destroyer is running");
        while (isRunning==1){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (toDelete!=null){
                if (toDelete instanceof verticalChecker){
                    System.out.println("destroyer sup v");
                    suppressionV();
                }
                if (toDelete instanceof horizontalChecker){
                    System.out.println("destroyer sup h");
                    suppressionH();
                }

                System.out.println("destroyer released data");
                toDeleteOld = toDelete;
                toDelete = null;
                release.release();
                antiCollider.release();
            }
            //System.out.println("destroyer inside");
        }
        System.out.println("destroyer is running");
    }

    private synchronized void suppressionH(){
        for (int i=toDelete.getFirstDetected();i<=toDelete.getLastDetected();i++){
            for (int j=toDelete.getRowID();j>0;j--){
                toDelete.btn[idFromXY(i,j)].setIcon(toDelete.btn[idFromXY(i,j-1)].getIcon());
                toDelete.btn[idFromXY(i,j)].setButtonType(toDelete.btn[idFromXY(i,j-1)].getButtonType());
            }
            toDelete.btn[idFromXY(i,0)].setButtonType(toDelete.Letter[rnd.nextInt(toDelete.Letter.length)]);
            toDelete.btn[idFromXY(i,0)].setIcon(new ImageIcon(new
                    ImageIcon("images/"+toDelete.btn[idFromXY(i,0)].getButtonType()).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        }
    }

    private synchronized void suppressionV(){
            for (int j=toDelete.getLastDetected();j>(toDelete.getLastDetected()-toDelete.getFirstDetected());j--){
                toDelete.btn[idFromXY(toDelete.getRowID(),j)].setIcon(toDelete.btn[idFromXY(toDelete.getRowID(),j-(toDelete.getLastDetected()-toDelete.getFirstDetected()+1))].getIcon());
                toDelete.btn[idFromXY(toDelete.getRowID(),j)].setButtonType(toDelete.btn[idFromXY(toDelete.getRowID(),j-(toDelete.getLastDetected()-toDelete.getFirstDetected()+1))].getButtonType());
            }
            for (int j=(toDelete.getLastDetected()-toDelete.getFirstDetected());j>=0;j--) {
                toDelete.btn[idFromXY(toDelete.getRowID(), j)].setButtonType(toDelete.Letter[rnd.nextInt(toDelete.Letter.length)]);
                toDelete.btn[idFromXY(toDelete.getRowID(), j)].setIcon(new ImageIcon(new
                        ImageIcon("images/" + toDelete.btn[idFromXY(toDelete.getRowID(), j)].getButtonType()).getImage().
                        getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
            }
    }


    private int idFromXY(int x, int y){
        return y * this.toDelete.getWidth() + x;
    }
}
