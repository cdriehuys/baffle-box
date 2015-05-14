import javax.swing.*;
import java.awt.*;

public class NumberBox extends ClickableArea {

    private static final Color NORMAL_COLOR = Color.BLACK;
    private static final Color HIGHLIGHTED_COLOR = Color.RED;

    private boolean highlighted;

    private int val;


    public NumberBox(int val) {
        super();

        this.val = val;

        highlighted = false;

        setDefaultBorder(null);
    }
    /************************** Overridden Methods **************************/

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(highlighted ? HIGHLIGHTED_COLOR : NORMAL_COLOR);
        g.drawString(String.valueOf(val), getWidth() / 3, getHeight() / 2);
    }

    @Override
    public void onClick() {
        if (JOptionPane.showConfirmDialog(getParent(), "Would you like to fire a beam from box " + val + "?", "Fire?",
                JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {

            getBaffleGrid().fireBeam(val);
        }
    }

    /****************************** Accessors *******************************/

    public int getVal() { return val; }

    /****************************** Mutators ********************************/

    public void setHighlighted(boolean val) { highlighted = val; }

    /**************************** Other Methods *****************************/
}
