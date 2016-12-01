package moe.nev.candycrushlike;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Shinra on 28.11.16.
 */
public abstract class checker implements Runnable {
    protected Integer isRunning;
    protected Integer score;
    protected JButton[] btn;
    protected String[] Letter;
    protected Integer width, height;
    protected int rowID;
    protected boolean line;
    protected ArrayList<JButton> toDelete;
    protected gravityPower destroyer;
    protected Integer isDeleting;

    public checker(Integer isRunningP, Integer scoreP, JButton[] btnP, String[] LetterP, Integer widthP, Integer heightP, int rowIDP, boolean lineP, gravityPower destroyerP,Integer isDeletingP){
        this.isRunning = isRunningP;
        this.score = scoreP;
        this.btn = btnP;
        this.Letter = LetterP;
        this.width = widthP;
        this.height = heightP;
        this.rowID = rowIDP;
        this.line = lineP;
        this.toDelete = null;
        this.destroyer = destroyerP;
        toDelete = new ArrayList<>();
        this.isDeleting = isDeletingP;
    }
}
