package moe.nev.candycrushlike;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by Shinra on 28.11.16.
 */
public class gravityPower implements Runnable {
    private boolean isRunning;
    private checker toDelete;
    private Semaphore antiCollider;
    private Random rnd;
    private int score;

    public gravityPower(Random rndP) {
        this.isRunning = true;
        this.antiCollider = new Semaphore(0);
        this.toDelete = null;
        this.rnd = rndP;
        score = 0;
    }

    public void addScore(int toAdd){
        this.score+=toAdd;
    }

    public int getScore() {
        return score;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop(){
        this.isRunning = false;
    }

    public void setToDelete(checker toDelete) {
        this.toDelete = toDelete;
        try {
            //attend la fin de la suppression des données
            antiCollider.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        while (isRunning()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (toDelete != null) {
                if (toDelete instanceof verticalChecker) {
                    suppressionV();
                }
                if (toDelete instanceof horizontalChecker) {
                    suppressionH();
                }
                toDelete = null;
                antiCollider.release();
            }
        }
    }

    private void suppressionH() {
        for (int i = toDelete.getFirstDetected(); i <= toDelete.getLastDetected(); i++) {
            for (int j = toDelete.getRowID(); j > 0; j--) {
                toDelete.btn[idFromXY(i, j)].exchangeButtonsData(toDelete.btn[idFromXY(i, j - 1)]);
            }
            toDelete.btn[idFromXY(i, 0)].setButtonType(toDelete.Letter[rnd.nextInt(toDelete.Letter.length)]);
            toDelete.btn[idFromXY(i, 0)].setIcon(new ImageIcon(new
                    ImageIcon("images/" + toDelete.btn[idFromXY(i, 0)].getButtonType()).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        }
    }

    private void suppressionV() {
        for (int j = toDelete.getLastDetected(); j > (toDelete.getLastDetected() - toDelete.getFirstDetected()); j--) {
            toDelete.btn[idFromXY(toDelete.getRowID(), j)].exchangeButtonsData(toDelete.btn[idFromXY(toDelete.getRowID(), j - (toDelete.getLastDetected() - toDelete.getFirstDetected() + 1))]);
        }
        for (int j = (toDelete.getLastDetected() - toDelete.getFirstDetected()); j >= 0; j--) {
            toDelete.btn[idFromXY(toDelete.getRowID(), j)].setButtonType(toDelete.Letter[rnd.nextInt(toDelete.Letter.length)]);
            toDelete.btn[idFromXY(toDelete.getRowID(), j)].setIcon(new ImageIcon(new
                    ImageIcon("images/" + toDelete.btn[idFromXY(toDelete.getRowID(), j)].getButtonType()).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        }
    }

    private int idFromXY(int x, int y) {
        return y * this.toDelete.getWidth() + x;
    }
}
