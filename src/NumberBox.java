import javax.swing.*;
import java.awt.*;

public class NumberBox extends ClickableArea {

    private int val;


    public NumberBox(int val) {
        super();

        this.val = val;

        setDefaultBorder(null);
    }
    /************************** Overridden Methods **************************/

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(val), getWidth() / 3, getHeight() / 2);
    }

    @Override
    public void onClick() {
        JOptionPane.showMessageDialog(getParent(), "Would you like to fire a beam from box " + val + "?", "Fire?",
                JOptionPane.QUESTION_MESSAGE);
    }

    /****************************** Accessors *******************************/

    public int getVal() { return val; }

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/
}
