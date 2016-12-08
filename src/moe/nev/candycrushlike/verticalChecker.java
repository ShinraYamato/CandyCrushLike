package moe.nev.candycrushlike;

import javax.swing.*;

/**
 * Created by Shinra on 28.11.16.
 */
public class verticalChecker extends checker{

    public verticalChecker(Integer scoreP, candyButtons[] btnP, String[] LetterP, Integer TailleHP, Integer TailleVP, int rowIDP, gravityPower destroyerP) {
        super(scoreP, btnP, LetterP, TailleHP, TailleVP, rowIDP, destroyerP);
    }

    public synchronized void run(){
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
                while (destroyer.getIsDeleting()){}
                for (int i = 0; i < this.getHeight(); i++) {
                    if (toDelete.isEmpty()) {
                        toDelete.add(btn[i * this.getWidth() + this.getRowID()]);
                        setFirstDetected(i);
                    } else {
                        //System.out.println("toDelete size: " + toDelete.size() + "access at:" + (rowID * this.getWidth() + (i-1)));
                        if (toDelete.get(toDelete.size() - 1).getButtonType().equals(btn[i * this.getWidth() + this.getRowID()].getButtonType())) {
                            toDelete.add(btn[i * this.getWidth() + this.getRowID()]);

                        } else {
                            if (toDelete.size() >= 3) {
                                setLastDetected(i - 1);
                                System.out.println("checker detected");
                                destroyer.setToDelete(this);

                                System.out.println("checker applied");
                            }
                            toDelete.removeAll(toDelete);//a vérifier
                            toDelete.add(btn[i * this.getWidth() + this.getRowID()]);
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
