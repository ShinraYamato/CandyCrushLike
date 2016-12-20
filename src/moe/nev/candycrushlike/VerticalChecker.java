package moe.nev.candycrushlike;

/**
 * Created by Shinra on 28.11.16.
 */
public class VerticalChecker extends Checker {

    /**
     * constructeur d'un checker horizontal
     * @param btnP array of buttons
     * @param LetterP array containing names of file for the buttons
     * @param TailleHP width of the game
     * @param TailleVP height of the game
     * @param rowIDP UID of the checker
     * @param destroyerP Link to the destroyer object
     * @param lockP Lock used for synchronizing purpose
     */
    public VerticalChecker(CandyButtons[] btnP, String[] LetterP, Integer TailleHP, Integer TailleVP, int rowIDP, GravityPower destroyerP, Integer lockP) {
        super(btnP, LetterP, TailleHP, TailleVP, rowIDP, destroyerP, lockP);
    }

    /**
     * return a btn ID built from checker UID and checker type
     * @param id
     * @return
     */
    public int idGen(int id){
        return (id * this.getWidth() + this.getRowID());
    }

    /**
     * return the height from the checker
     * @return
     */
    public int dimLen(){
        return this.getHeight();
    }
}
