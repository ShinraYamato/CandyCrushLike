package moe.nev.candycrushlike;

/**
 * Created by pierre.buffo on 21.11.16.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class VueCrush extends JPanel implements ActionListener {
    private static final String[] Letter = { "bird.png", "cricket.png",
            "elephant.png", "penguin.png", "dolphin.png", "cat.png", "jelly_fish.png",
            "gnome_panel_fish.png","pig.png", "kbugbuster.png"};
    private boolean fini;
    private static Integer termine = 0;
    private static Integer Taille = 10;
    private JButton[] btn = new JButton[Taille*Taille];
    Random rnd;
    public VueCrush(Random rnd) {
// utilisation d'un GridLayout comme "layout"
        super(new GridLayout(Taille, Taille));
        int dim = Taille*Taille;
        this.rnd = rnd;
        for(int j=0;j<dim;j++) { // boucle d'ajout des boutons
            btn[j] = new JButton(new ImageIcon(new
                    ImageIcon(Letter[rnd.nextInt(Letter.length)]).getImage().
                    getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
            btn[j].setName(String.valueOf(j));
            btn[j].addActionListener(this);
// enregistrement de l'ecouteur
            this.add(btn[j]); // ajout du bouton a ce JPanel
        }
    }
    public void actionPerformed(ActionEvent e) {
    }
}
