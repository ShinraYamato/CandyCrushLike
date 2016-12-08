package moe.nev.candycrushlike;

/**
 * Created by pierre.buffo on 21.11.16.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import static java.lang.Math.abs;

public class VueCrush extends JPanel implements ActionListener {
    private static final String[] Letter = { "bird.png" , "cricket.png"
            ,"elephant.png", "penguin.png", "dolphin.png", "cat.png", "jelly_fish.png",
            "gnome_panel_fish.png","pig.png", "kbugbuster.png"};
    private int lastClickedID = -1;
    private static Integer Taille = 10;
    private candyButtons[] btn = new candyButtons[Taille*Taille];
    Random rnd;
    private Integer isRunning;
    private Integer isDeleting;
    private Integer score;
    private Thread[] vChecker = new Thread[Taille*Taille];
    private Thread[] hChecker = new Thread[Taille*Taille];
    private Thread destroyerT = new Thread();
    private gravityPower destroyer;

    public VueCrush(Random rnd) {
    // utilisation d'un GridLayout comme "layout"
        super(new GridLayout(Taille, Taille));
        int dim = Taille*Taille;
        this.rnd = rnd;
        for(int j=0;j<dim;j++) { // boucle d'ajout des boutons
            btn[j] = new candyButtons(null, j, Letter[rnd.nextInt(Letter.length)]);
            btn[j].setIcon(new ImageIcon(new
                    ImageIcon("images/"+btn[j].getButtonType()).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
            btn[j].addActionListener(this);
             // enregistrement de l'ecouteur
            this.add(btn[j]); // ajout du bouton a ce JPanel
        }
        isRunning = 1;
        isDeleting = 0;
        score = 0;
        destroyer = new gravityPower(isRunning, rnd, isDeleting);
        destroyerT = new Thread(destroyer);
        for (int j=0;j<Taille;j++){
            vChecker[j] = new Thread(new verticalChecker(score, btn, Letter, Taille, Taille, j, destroyer));
            hChecker[j] = new Thread(new horizontalChecker(score, btn, Letter, Taille, Taille, j, destroyer));
            //vChecker[j].start();
            hChecker[j].start();
        }
        //hChecker[1] = new Thread(new horizontalChecker(isRunning, score, btn, Letter, Taille, Taille, 1, destroyer));
        //hChecker[1].start();
        destroyerT.start();
    }
    public void actionPerformed(ActionEvent e) {
        if (lastClickedID < 0){
            lastClickedID = ((candyButtons) e.getSource()).getButtonID();
        } else if (neighbourByName(btn[lastClickedID], (candyButtons)e.getSource())){
            ((candyButtons) e.getSource()).exchangeButtonsData(btn[lastClickedID]);
            lastClickedID = -1;
        } else {
            lastClickedID = -1;
        }
        isRunning = 0;
    }

    /**
     * vérifie si deux bouttons sont côte à côte, nécéssite la variable globale taille définie
     * @param a
     * @param b
     * @return oui ou non
     */
    public boolean neighbourByName(candyButtons a, candyButtons b){
        return ((abs(a.getButtonID()-b.getButtonID())==(1))||abs(a.getButtonID()-b.getButtonID())==(Taille));
    }
}
