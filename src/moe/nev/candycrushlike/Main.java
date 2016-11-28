package moe.nev.candycrushlike;

/**
 * Created by pierre.buffo on 21.11.16 at 21.11.16.
 * ${PACKAGE_NAME}
 * CandyCrushLike
 */
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Main {
    public static void main(String[] args) {
        final Random rnd = new Random(System.currentTimeMillis());
        VueCrush vue = new VueCrush(rnd);
        JFrame f = new JFrame();
        f.getContentPane().add(vue);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800,800);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }
}