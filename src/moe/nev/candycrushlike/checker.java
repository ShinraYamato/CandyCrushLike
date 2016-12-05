package moe.nev.candycrushlike;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Shinra on 28.11.16.
 */
public abstract class checker implements Runnable {
    private Integer isRunning;
    protected Integer score;
    protected candyButtons[] btn;
    protected String[] Letter;
    private Integer width, height;
    private int rowID;
    protected ArrayList<candyButtons> toDelete;
    protected gravityPower destroyer;
    private int firstDetected;
    private int lastDetected;

    public checker(Integer isRunningP, Integer scoreP, candyButtons[] btnP, String[] LetterP, Integer widthP, Integer heightP, int rowIDP, gravityPower destroyerP){
        this.isRunning = isRunningP;
        this.score = scoreP;
        this.btn = btnP;
        this.Letter = LetterP;
        this.width = widthP;
        this.height = heightP;
        this.rowID = rowIDP;
        this.toDelete = null;
        this.destroyer = destroyerP;
        toDelete = new ArrayList<>();
        resetDetection();
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public boolean isRunning() {
        return (isRunning > 0);
    }

    public int getRowID() {
        return rowID;
    }

    public void resetDetection(){
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
}
