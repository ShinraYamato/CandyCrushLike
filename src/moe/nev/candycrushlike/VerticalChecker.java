package moe.nev.candycrushlike;

/**
 * Created by Shinra on 28.11.16.
 */
public class VerticalChecker extends Checker {

    public VerticalChecker(CandyButtons[] btnP, String[] LetterP, Integer TailleHP, Integer TailleVP, int rowIDP, GravityPower destroyerP, Integer lockP) {
        super(btnP, LetterP, TailleHP, TailleVP, rowIDP, destroyerP, lockP);
    }

    public int idGen(int id){
        return (id * this.getWidth() + this.getRowID());
    }

    public int dimLen(){
        return this.getHeight();
    }
}
