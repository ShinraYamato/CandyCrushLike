package moe.nev.candycrushlike;

/**
 * Created by Shinra on 28.11.16.
 */
public class horizontalChecker extends checker {

    public horizontalChecker(Integer scoreP, candyButtons[] btnP, String[] LetterP, Integer TailleHP, Integer TailleVP, int rowIDP, gravityPower destroyerP, Integer lockP) {
        super(scoreP, btnP, LetterP, TailleHP, TailleVP, rowIDP, destroyerP, lockP);
    }

    public int idGen(int id){
        return (this.getRowID() * this.getWidth() + id);
    }

    public int dimLen(){
        return this.getWidth();
    }
}
