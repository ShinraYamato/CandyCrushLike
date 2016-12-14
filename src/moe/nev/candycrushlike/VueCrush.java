package moe.nev.candycrushlike;

/**
 * Created by pierre.buffo on 21.11.16.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static java.lang.Math.abs;

public class VueCrush extends JPanel implements ActionListener {
    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
    private static final String[] Letter = { "bird.png" , "cricket.png"
            ,"elephant.png", "penguin.png", "dolphin.png", "cat.png", "jelly_fish.png",
            "gnome_panel_fish.png","pig.png", "kbugbuster.png", "dragon_fly.png"};
    private int lastClickedID = -1;
    private static Integer Taille = 10;
    private candyButtons[] btn = new candyButtons[Taille*Taille];
    Random rnd;
    private Thread[] vChecker = new Thread[Taille*Taille];
    private Thread[] hChecker = new Thread[Taille*Taille];
    private Thread destroyerT = new Thread();
    private gravityPower destroyer;
    public Integer lock = 0;
    private int timeToPlay = 5;//temps en secondes de jeu

    public VueCrush(Random rnd) {
    // utilisation d'un GridLayout comme "layout"
        super(new GridLayout(Taille, Taille));
        int dim = Taille*Taille;
        this.rnd = rnd;
        for(int j=0;j<dim;j++) { // boucle d'ajout des boutons
            btn[j] = new candyButtons(null, j, Letter[rnd.nextInt(Letter.length)]);

            btn[j].setIcon(new ImageIcon(new
                    ImageIcon(getClass().getClassLoader().getResource(btn[j].getButtonType())).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
            btn[j].addActionListener(this);
             // enregistrement de l'ecouteur
            this.add(btn[j]); // ajout du bouton a ce JPanel
        }
        destroyer = new gravityPower(rnd);
        destroyerT = new Thread(destroyer);
        for (int j=0;j<Taille;j++){
            vChecker[j] = new Thread(new verticalChecker(btn, Letter, Taille, Taille, j, destroyer, lock));
            hChecker[j] = new Thread(new horizontalChecker(btn, Letter, Taille, Taille, j, destroyer, lock));
            vChecker[j].start();
            hChecker[j].start();
        }
        destroyerT.start();
        stop();
    }

    public void stop(){
        Runnable task = new Runnable() {
            public void run() {
                destroyer.stop();
                JOptionPane.showMessageDialog(null,"Score : "+destroyer.getScore(),"Fin de partie après "+timeToPlay+" minutes",JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        };
        worker.schedule(task, timeToPlay, TimeUnit.MINUTES);
    }

    public void actionPerformed(ActionEvent e) {
        if (lastClickedID < 0){
            lastClickedID = ((candyButtons) e.getSource()).getButtonID();
        } else if (neighbourByID(btn[lastClickedID], (candyButtons)e.getSource())){
            ((candyButtons) e.getSource()).exchangeButtonsData(btn[lastClickedID]);
            lastClickedID = -1;
        } else {
            lastClickedID = -1;
        }
    }

    /**
     * vérifie si deux bouttons sont côte à côte, nécéssite la variable globale taille définie
     * @param a
     * @param b
     * @return oui ou non
     */
    public boolean neighbourByID(candyButtons a, candyButtons b){
        int ax, bx, ay, by;
        ax = a.getButtonID() / Taille;
        bx = b.getButtonID() / Taille;
        ay = a.getButtonID() % Taille;
        by = b.getButtonID() % Taille;
        return ((abs(ax-bx) == 1 && (abs(ay-by)) == 0)||(abs(ax-bx) == 0 && (abs(ay-by)) == 1));
    }
}
