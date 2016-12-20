package moe.nev.candycrushlike;

import javax.swing.*;

import static java.lang.Math.abs;

/**
 * Created by Shinra on 03.12.16.
 * extends JButton adding parameters for CandyCrush like a type or a UID
 */

public class CandyButtons extends JButton {
    private String buttonType;
    private int buttonID;

    /**
     * constructor, create a candyButton
     * @param icon
     * @param buttonIDP
     * @param buttonTypeP
     */
    public CandyButtons(Icon icon, int buttonIDP, String buttonTypeP) {
        super(null, icon);
        this.buttonType = buttonTypeP;
        this.buttonID = buttonIDP;
    }

    /**
     * return UID from button
     * @return
     */
    public int getButtonID() {
        return buttonID;
    }

    /**
     * return type from button (his picture)
     * @return
     */
    public String getButtonType() {
        return buttonType;
    }

    /**
     * set type from button (his picture)
     * @param buttonType
     */
    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    /**
     * exchange picture and type from 2 buttons
     * @param b1
     */
    public void exchangeButtonsData(CandyButtons b1){
        String TMP = this.getButtonType();
        Icon TMPIcon = this.getIcon();
        this.setButtonType(b1.getButtonType());
        this.setIcon(b1.getIcon());
        b1.setButtonType(TMP);
        b1.setIcon(TMPIcon);
    }
}
