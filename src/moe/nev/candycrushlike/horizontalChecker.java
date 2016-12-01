package moe.nev.candycrushlike;

import javax.swing.*;

/**
 * Created by Shinra on 28.11.16.
 */
public class horizontalChecker extends checker{

    public horizontalChecker(Integer isRunningP, Integer scoreP, JButton[] btnP, String[] LetterP, Integer TailleHP, Integer TailleVP, int rowIDP, gravityPower destroyerP, Integer isDeletingP) {
        super(isRunningP, scoreP, btnP, LetterP, TailleHP, TailleVP, rowIDP, true, destroyerP, isDeletingP);
    }

    public void run(){
        while (isRunning==1){
            for (int i = 0; i < width; i++) {
                if (toDelete.isEmpty()){
                    toDelete.add(btn[rowID*width+i]);
                }
                if (toDelete.get(toDelete.size()).getIcon().equals(btn[rowID*width+i].getIcon())){
                    toDelete.add(btn[rowID*width+i]);
                    if (toDelete.size()>=2){
                        isDeleting=1;
                        destroyer.setToDelete(this);
                    }
                } else {
                    toDelete.removeAll(toDelete);//a vérifier
                    toDelete.add(btn[rowID*width+i]);
                }
            }
        }
    }
}
