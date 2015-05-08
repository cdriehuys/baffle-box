import javax.swing.*;
import java.awt.*;

public class DrawCanvas extends JPanel {

    /**
     * Constructor with the panel's preferred dimensions.
     * @param width The preferred width for the panel.
     * @param height The preferred height for the panel.
     */
    public DrawCanvas(int width, int height) {

        // set the preferred size of the panel
        setPreferredSize(new Dimension(width, height));
    }

    /************************** Overridden Methods **************************/

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/
}
