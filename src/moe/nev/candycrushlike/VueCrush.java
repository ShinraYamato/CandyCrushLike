package moe.nev.candycrushlike;

/**
 * Created by pierre.buffo on 21.11.16.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import moe.nev.candycrushlike.*;

import static java.lang.Math.abs;

public class VueCrush extends JPanel implements ActionListener {
    private static final String[] Letter = { "bird.png", "cricket.png",
            "elephant.png", "penguin.png", "dolphin.png", "cat.png", "jelly_fish.png",
            "gnome_panel_fish.png","pig.png", "kbugbuster.png"};
    private int lastClickedID = -1;
    private static Integer Taille = 10;
    private JButton[] btn = new JButton[Taille*Taille];
    Random rnd;

    private int lastID = -1;
    private Integer isRunning;
    private Integer score;
    private verticalChecker[] vChecker;
    private horizontalChecker[] hChecker;

    public VueCrush(Random rnd) {
// utilisation d'un GridLayout comme "layout"
        super(new GridLayout(Taille, Taille));
        int dim = Taille*Taille;
        this.rnd = rnd;
        for(int j=0;j<dim;j++) { // boucle d'ajout des boutons
            btn[j] = new JButton(new ImageIcon(new
                    ImageIcon("images/"+Letter[rnd.nextInt(Letter.length)]).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
            btn[j].setName(String.valueOf(j));
            btn[j].addActionListener(this);
            // enregistrement de l'ecouteur
            this.add(btn[j]); // ajout du bouton a ce JPanel
        }
        for (int j=0;j<Taille;j++){
            vChecker[j] = new verticalChecker(1,score, btn, Letter, Taille, Taille, j);
            hChecker[j] = new horizontalChecker(1,score, btn, Letter, Taille, Taille, j);
        }
    }
    public void actionPerformed(ActionEvent e) {
        Icon tmp;
        if (lastClickedID < 0){
            lastClickedID = Integer.valueOf(((JButton) e.getSource()).getName());
        } else if (neighbourByName(btn[lastClickedID], (JButton)e.getSource())){
            swapIcon(btn[lastClickedID], (JButton)e.getSource());
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
    public boolean neighbourByName(JButton a, JButton b){
        return (abs(Integer.valueOf(a.getName())-Integer.valueOf(b.getName()))==(1))||(abs(Integer.valueOf(a.getName())-Integer.valueOf(b.getName()))==(Taille));
    }

    public void swapIcon(JButton a, JButton b){
        Icon tmp = a.getIcon();
        a.setIcon(b.getIcon());
        b.setIcon(tmp);
    }
}
