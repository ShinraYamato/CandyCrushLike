package moe.nev.candycrushlike;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Shinra on 03.12.16.
 */
public class candyButtons extends JButton {
    private String buttonType;
    private int buttonID;

    public candyButtons(Icon icon, int buttonIDP, String buttonTypeP) {
        super(null, icon);
        this.buttonType = buttonTypeP;
        this.buttonID = buttonIDP;
    }

    public int getButtonID() {
        return buttonID;
    }

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonID(int buttonID) {
        this.buttonID = buttonID;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public void exchangeButtonsData(candyButtons b1){
        String TMP = this.getButtonType();
        this.setButtonType(b1.getButtonType());
        this.setIcon(b1.getIcon());
        b1.setButtonType(TMP);
        b1.setIcon(new ImageIcon(new
                ImageIcon("images/"+b1.getButtonType()).getImage().
                getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
    }
}