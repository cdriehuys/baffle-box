import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawCanvas extends JPanel {

    /**
     * Constructor with the panel's preferred dimensions.
     * @param width The preferred width for the panel.
     * @param height The preferred height for the panel.
     */
    public DrawCanvas(int width, int height) {

        // set the preferred size of the panel
        setPreferredSize(new Dimension(width, height));

        setLayout(new GridLayout(2, 3));

        add(new ClickableArea(50, 50));
        add(new ClickableArea(50, 50));
        add(new ClickableArea(50, 50));
        add(new ClickableArea(50, 50));
        add(new ClickableArea(50, 50));
        add(new ClickableArea(50, 50));

        //System.out.format("ClickableArea at (%d, %d) with dimensions %dx%d%n", ca.getX(), ca.getY(), ca.getWidth(), ca.getHeight());

        Timer drawTimer = new Timer(20, new DrawTimerListener());
        drawTimer.start();
    }

    /************************** Overridden Methods **************************/

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    /**************************** Helper Classes ****************************/

    private class DrawTimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
