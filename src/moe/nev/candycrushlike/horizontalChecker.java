package moe.nev.candycrushlike;

import javax.swing.*;

/**
 * Created by Shinra on 28.11.16.
 */
public class horizontalChecker extends checker{

    public horizontalChecker(Integer scoreP, candyButtons[] btnP, String[] LetterP, Integer TailleHP, Integer TailleVP, int rowIDP, gravityPower destroyerP) {
        super(scoreP, btnP, LetterP, TailleHP, TailleVP, rowIDP, destroyerP);
    }

    public void run(){
        System.out.println("thread :"+getRowID() + " lancé: status: " +destroyer.isRunning());
        while (destroyer.isRunning()){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (true) {
                toDelete.removeAll(toDelete);
                setFirstDetected(-1);
                setLastDetected(-1);

                for (int i = 0; i < this.getWidth(); i++) {
                    if (toDelete.isEmpty()) {
                        toDelete.add(btn[this.getRowID() * this.getWidth() + i]);
                        setFirstDetected(i);
                    } else {
                        //System.out.println("toDelete size: " + toDelete.size() + "access at:" + (rowID * this.getWidth() + (i-1)));
                        if (toDelete.get(toDelete.size() - 1).getButtonType().equals(btn[this.getRowID() * this.getWidth() + i].getButtonType())) {
                            toDelete.add(btn[this.getRowID() * this.getWidth() + i]);

                        } else {
                            if (toDelete.size() >= 3) {
                                setLastDetected(i - 1);
                                System.out.println("checker detected");
                                destroyer.setToDelete(this);

                                System.out.println("checker applied");
                            }
                            toDelete.removeAll(toDelete);//a vérifier
                            toDelete.add(btn[this.getRowID() * this.getWidth() + i]);
                            setFirstDetected(i);
                        }
                    }
                }
                if (toDelete.size() >= 3) {
                    setLastDetected(this.getWidth() - 1);
                    System.out.println("checker end line detected");
                    destroyer.setToDelete(this);

                    System.out.println("checker end line applied");
                }
            }
        }
        System.out.println("thread :"+getRowID() + " annulé: status: " +destroyer.isRunning());
    }
}
