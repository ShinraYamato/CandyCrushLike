package moe.nev.candycrushlike;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by Shinra on 28.11.16.
 */
public class GravityPower implements Runnable {
    private boolean isRunning;
    private Checker toDelete;
    private Semaphore antiCollider;
    private Random rnd;
    private int score;

    public GravityPower(Random rndP) {
        this.isRunning = true;
        this.antiCollider = new Semaphore(0);
        this.toDelete = null;
        this.rnd = rndP;
        score = 0;
    }

    /**
     * add score points
     * @param toAdd
     */
    public void addScore(int toAdd){
        this.score+=toAdd;
    }

    /**
     * retourne actual points
     * @return points
     */
    public int getScore() {
        return score;
    }

    /**
     * test is programm should continue to run
     * @return true or false
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * switch the running indicator to false
     */
    public void stop(){
        this.isRunning = false;
    }

    /**
     * try to add a new checker who has 3 or more identical buttons
     * @param toDelete the checker
     */
    public void setToDelete(Checker toDelete) {
        this.toDelete = toDelete;
        try {
            //attend la fin de la suppression des données
            antiCollider.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Destroy and apply gravity to buttons
     */
    public void run() {
        while (isRunning()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (toDelete != null) {
                if (toDelete instanceof VerticalChecker) {
                    suppressionV();
                }
                if (toDelete instanceof HorizontalChecker) {
                    suppressionH();
                }
                toDelete = null;
                antiCollider.release();
            }
        }
    }

    /**
     * supression + gravity routine dor horizontal checkers
     */
    private void suppressionH() {
        for (int i = toDelete.getFirstDetected(); i <= toDelete.getLastDetected(); i++) {
            for (int j = toDelete.getRowID(); j > 0; j--) {
                toDelete.btn[idFromXY(i, j)].exchangeButtonsData(toDelete.btn[idFromXY(i, j - 1)]);
            }
            toDelete.btn[idFromXY(i, 0)].setButtonType(toDelete.Letter[rnd.nextInt(toDelete.Letter.length)]);
            toDelete.btn[idFromXY(i, 0)].setIcon(new ImageIcon
                    (new ImageIcon(getClass().getClassLoader().getResource(toDelete.btn[idFromXY(i, 0)].getButtonType())).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        }
    }

    /**
     * supression + gravity routine dor vertical checkers
     */
    private void suppressionV() {
        for (int j = toDelete.getLastDetected(); j > (toDelete.getLastDetected() - toDelete.getFirstDetected()); j--) {
            toDelete.btn[idFromXY(toDelete.getRowID(), j)].exchangeButtonsData(toDelete.btn[idFromXY(toDelete.getRowID(), j - (toDelete.getLastDetected() - toDelete.getFirstDetected() + 1))]);
        }
        for (int j = (toDelete.getLastDetected() - toDelete.getFirstDetected()); j >= 0; j--) {
            toDelete.btn[idFromXY(toDelete.getRowID(), j)].setButtonType(toDelete.Letter[rnd.nextInt(toDelete.Letter.length)]);

            toDelete.btn[idFromXY(toDelete.getRowID(), j)].setIcon(new ImageIcon(new
                    ImageIcon(getClass().getClassLoader().getResource(toDelete.btn[idFromXY(toDelete.getRowID(), j)].getButtonType())).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        }
    }

    private int idFromXY(int x, int y) {
        return y * this.toDelete.getWidth() + x;
    }
}
