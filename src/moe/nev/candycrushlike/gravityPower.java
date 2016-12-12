package moe.nev.candycrushlike;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by Shinra on 28.11.16.
 */
public class gravityPower implements Runnable {
    private Integer isRunning;
    private checker toDelete;
    private Semaphore antiCollider;
    private Random rnd;

    public gravityPower(Integer isRunningP, Random rndP) {
        System.out.println("destroyer init");
        this.isRunning = isRunningP;
        this.antiCollider = new Semaphore(0);
        this.toDelete = null;
        this.rnd = rndP;
    }

    public boolean isRunning() {
        return (isRunning > 0);
    }

    public void setToDelete(checker toDelete) {

        System.out.println("destroyer record data");
        this.toDelete = toDelete;
        try {
            //attend la fin de la suppression des données
            antiCollider.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        System.out.println("destroyer is running");
        while (isRunning == 1) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (toDelete != null) {
                if (toDelete instanceof verticalChecker) {
                    System.out.println("destroyer sup v");
                    suppressionV();
                }
                if (toDelete instanceof horizontalChecker) {
                    System.out.println("destroyer sup h");
                    suppressionH();
                }

                System.out.println("destroyer released data");
                toDelete = null;
                antiCollider.release();
            }
        }
        System.out.println("destroyer is running");
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
