package moe.nev.candycrushlike;

import java.util.ArrayList;

/**
 * Created by Shinra on 28.11.16.
 * class used to check button redundancy
 */
public abstract class Checker implements Runnable {
    protected CandyButtons[] btn;
    protected String[] Letter;
    private int width, height;
    private int rowID;
    protected ArrayList<CandyButtons> toDelete;
    protected GravityPower destroyer;
    private int firstDetected;
    private int lastDetected;
    public Integer lock;

    /**
     * constructor used to create a checker
     * @param btnP array of buttons
     * @param LetterP array containing names of file for the buttons
     * @param widthP width of the game
     * @param heightP height of the game
     * @param rowIDP UID of the checker
     * @param destroyerP Link to the destroyer object
     * @param lockP Lock used for synchronizing purpose
     */
    public Checker(CandyButtons[] btnP, String[] LetterP, Integer widthP, Integer heightP, int rowIDP, GravityPower destroyerP, Integer lockP) {
        this.btn = btnP;
        this.Letter = LetterP;
        this.width = widthP;
        this.height = heightP;
        this.rowID = rowIDP;
        this.destroyer = destroyerP;
        this.lock = lockP;
        this.toDelete = new ArrayList<>();
        resetDetection();
    }

    /**
     * return width from the game
     * @return
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * return height from the game
     * @return
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * return UID from the checker
     * @return
     */
    public int getRowID() {
        return rowID;
    }

    /**
     * reset firstDetected and lastDetected
     */
    public void resetDetection() {
        firstDetected = -1;
        lastDetected = -1;
    }

    /**
     * return firstDetected
     * @return
     */
    public int getFirstDetected() {
        return firstDetected;
    }

    /**
     * return lastDetected
     * @return
     */
    public int getLastDetected() {
        return lastDetected;
    }

    /**
     * set firstDetected
     * @param firstDetected
     */
    public void setFirstDetected(int firstDetected) {
        this.firstDetected = firstDetected;
    }

    /**
     * set lastDetected
     * @param lastDetected
     */
    public void setLastDetected(int lastDetected) {
        this.lastDetected = lastDetected;
    }

    /**
     * return a btn ID built from checker UID and checker type
     * @param id
     * @return
     */
    public abstract int idGen(int id);

    /**
     * return the length from the checker's dimension
     * @return
     */
    public abstract int dimLen();

    /**
     * if enough buttons have been detected they will be suppressed
     * @param id
     */
    public void tryDelete(int id) {
        if (toDelete.size() >= 3) {
            setLastDetected(id - 1);
            addScore();
            destroyer.setToDelete(this);
        }
    }

    /**
     * add a certain amount of score depending on how much buttons have been detected
     */
    public void addScore() {
        int diff = lastDetected - firstDetected + 1;
        switch (diff) {
            case 3:
                destroyer.addScore(50);
                break;
            case 4:
                destroyer.addScore(150);
                break;
            case 5:
                destroyer.addScore(400);
                break;
        }
    }

    /**
     * test if the last button into toDelete buffer is from the same type that the button with id passed in parameter
     * @param id
     * @return
     */
    public boolean sameType(int id) {
        return toDelete.get(toDelete.size() - 1).getButtonType().equals(btn[idGen(id)].getButtonType());
    }

    /**
     * point d'entrée du thread
     */
    public void run() {
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
                tryDelete(this.dimLen());
            }
        }
    }
}
