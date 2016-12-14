package moe.nev.candycrushlike;

/**
 * Created by Shinra on 28.11.16.
 */
public class HorizontalChecker extends Checker {

    public HorizontalChecker(CandyButtons[] btnP, String[] LetterP, Integer TailleHP, Integer TailleVP, int rowIDP, GravityPower destroyerP, Integer lockP) {
        super(btnP, LetterP, TailleHP, TailleVP, rowIDP, destroyerP, lockP);
    }

    public int idGen(int id){
        return (this.getRowID() * this.getWidth() + id);
    }

    public int dimLen(){
        return this.getWidth();
    }
}
