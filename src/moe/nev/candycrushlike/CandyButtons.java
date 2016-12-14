package moe.nev.candycrushlike;

import javax.swing.*;

import static java.lang.Math.abs;

/**
 * Created by Shinra on 03.12.16.
 */
public class CandyButtons extends JButton {
    private String buttonType;
    private int buttonID;

    public CandyButtons(Icon icon, int buttonIDP, String buttonTypeP) {
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

    public void exchangeButtonsData(CandyButtons b1){
        String TMP = this.getButtonType();
        Icon TMPIcon = this.getIcon();
        this.setButtonType(b1.getButtonType());
        this.setIcon(b1.getIcon());
        b1.setButtonType(TMP);
        b1.setIcon(TMPIcon);

        /*b1.setIcon(new ImageIcon(new
                ImageIcon(getClass().getClassLoader().getResource(b1.getButtonType())).getImage().
                getScaledInstance(60, 60, Image.SCALE_DEFAULT)));*/
    }
}
