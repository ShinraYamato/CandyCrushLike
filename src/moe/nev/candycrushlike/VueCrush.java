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
    private static final String[] Letter = /*{"m1.png","m2.png","m3.png","m4.png","m5.png","m6.png","m7.png","m8.png","m9.png","m10.png","m11.png","m12.png","m13.png","m14.png"};*/
            { "bird.png" , "cricket.png"
            ,"elephant.png", "penguin.png", "dolphin.png", "cat.png", "jelly_fish.png",
            "gnome_panel_fish.png","pig.png", "kbugbuster.png", "dragon_fly.png"};

    private int lastClickedID = -1;
    private static Integer Taille = 10;
    private CandyButtons[] btn = new CandyButtons[Taille*Taille];
    Random rnd;
    private Thread[] vChecker = new Thread[Taille*Taille];
    private Thread[] hChecker = new Thread[Taille*Taille];
    private Thread destroyerT = new Thread();
    private GravityPower destroyer;
    public Integer lock = 0;
    private int timeToPlay = 3;//temps en minutes de jeu

    public VueCrush(Random rnd) {
    // utilisation d'un GridLayout comme "layout"
        super(new GridLayout(Taille, Taille));
        int dim = Taille*Taille;
        this.rnd = rnd;
        for(int j=0;j<dim;j++) { // boucle d'ajout des boutons
            btn[j] = new CandyButtons(null, j, Letter[rnd.nextInt(Letter.length)]);

            btn[j].setIcon(new ImageIcon(new
                    ImageIcon(getClass().getClassLoader().getResource(btn[j].getButtonType())).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
            btn[j].addActionListener(this);
             // enregistrement de l'ecouteur
            this.add(btn[j]); // ajout du bouton a ce JPanel
        }
        destroyer = new GravityPower(rnd);
        destroyerT = new Thread(destroyer);
        for (int j=0;j<Taille;j++){
            vChecker[j] = new Thread(new VerticalChecker(btn, Letter, Taille, Taille, j, destroyer, lock));
            hChecker[j] = new Thread(new HorizontalChecker(btn, Letter, Taille, Taille, j, destroyer, lock));
            vChecker[j].setName("vertical "+j);
            hChecker[j].setName("horizontal "+j);
            vChecker[j].start();
            hChecker[j].start();
        }
        destroyerT.start();
        stop();
    }

    /**
     * tâches à effectuer à la fin du jeu
     */
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

    /**
     * effectue les changements lors de clics sur des boutons
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (lastClickedID < 0){
            lastClickedID = ((CandyButtons) e.getSource()).getButtonID();
        } else if (neighbourByID(btn[lastClickedID], (CandyButtons)e.getSource())){
            ((CandyButtons) e.getSource()).exchangeButtonsData(btn[lastClickedID]);
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
    public boolean neighbourByID(CandyButtons a, CandyButtons b){
        int ax, bx, ay, by;
        ax = a.getButtonID() / Taille;
        bx = b.getButtonID() / Taille;
        ay = a.getButtonID() % Taille;
        by = b.getButtonID() % Taille;
        return ((abs(ax-bx) == 1 && (abs(ay-by)) == 0)||(abs(ax-bx) == 0 && (abs(ay-by)) == 1));
    }
}
