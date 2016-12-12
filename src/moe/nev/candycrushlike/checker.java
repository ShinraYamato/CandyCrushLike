package moe.nev.candycrushlike;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Shinra on 28.11.16.
 */
public abstract class checker implements Runnable {
    protected Integer score;
    protected candyButtons[] btn;
    protected String[] Letter;
    private int width, height;
    private int rowID;
    protected ArrayList<candyButtons> toDelete;
    protected gravityPower destroyer;
    private int firstDetected;
    private int lastDetected;
    public Integer lock;

    public checker(Integer scoreP, candyButtons[] btnP, String[] LetterP, Integer widthP, Integer heightP, int rowIDP, gravityPower destroyerP, Integer lockP) {
        this.score = scoreP;
        this.btn = btnP;
        this.Letter = LetterP;
        this.width = widthP;
        this.height = heightP;
        this.rowID = rowIDP;
        this.toDelete = null;
        this.destroyer = destroyerP;
        this.lock = lockP;
        toDelete = new ArrayList<>();
        resetDetection();
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public int getRowID() {
        return rowID;
    }

    public void resetDetection() {
        firstDetected = -1;
        lastDetected = -1;
    }

    public int getFirstDetected() {
        return firstDetected;
    }

    public int getLastDetected() {
        return lastDetected;
    }

    public void setFirstDetected(int firstDetected) {
        this.firstDetected = firstDetected;
    }

    public void setLastDetected(int lastDetected) {
        this.lastDetected = lastDetected;
    }

    public abstract int idGen(int id);

    public abstract int dimLen();

    public void tryDelete(int id){
        if (toDelete.size() >= 3) {
            setLastDetected(id - 1);
            System.out.println("checker detected");
            addScore();
            destroyer.setToDelete(this);
            System.out.println("checker applied");
        }
    }

    public void addScore(){
        int diff = getFirstDetected()-lastDetected+1;
        switch (diff){
            case 3:
                score+=50;
                break;
            case 4:
                score+=150;
                break;
            case 5:
                score+=400;
                break;
        }
    }

    public boolean sameType(int id){
        return toDelete.get(toDelete.size() - 1).getButtonType().equals(btn[idGen(id)].getButtonType());
    }

    public void run() {
        System.out.println("thread :" + getRowID() + " lancé: status: " + destroyer.isRunning());
        while (destroyer.isRunning()) {
            synchronized (lock) {
                toDelete.removeAll(toDelete);
                resetDetection();
                for (int i = 0; i < this.dimLen(); i++) {
                    //si buffer vide on l'initialise avec la case courante
                    if (toDelete.isEmpty()) {
                        toDelete.add(btn[idGen(i)]);
                        setFirstDetected(i);
                    } else {
                        //si la case courante est de même type que la dernière
                        if (sameType(i)) {
                            toDelete.add(btn[idGen(i)]);
                        } else {
                            //si la case courante est différente on regarde sa taille, si > 3 il va il y avoir une suppression
                            tryDelete(i);
                            //dans tous les cas on efface le buffer
                            toDelete.removeAll(toDelete);
                            toDelete.add(btn[idGen(i)]);
                            setFirstDetected(i);
                        }
                    }
                }
                tryDelete(this.dimLen() - 1);
            }
        }
        System.out.println("thread :" + getRowID() + " annulé: status: " + destroyer.isRunning());
    }
}
