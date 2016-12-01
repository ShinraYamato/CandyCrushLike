package moe.nev.candycrushlike;

/**
 * Created by Shinra on 28.11.16.
 */
public class gravityPower implements Runnable{
    private Integer isRunning;
    private Integer isDeleting;
    private checker toDelete;

    public gravityPower(Integer isRunningP, Integer isDeletingP){
        this.isRunning = isRunningP;
        this.isDeleting = isDeletingP;
    }

    public void setToDelete(checker toDelete) {
        this.toDelete = toDelete;
    }

    public checker getToDelete() {
        return toDelete;
    }

    public void run(){
        while (isRunning==1){
            if (toDelete!=null){
//Suppresion

                toDelete == null;
                isDeleting = 0;
            }
        }
    }
}
