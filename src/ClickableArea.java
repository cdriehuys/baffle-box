import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickableArea extends JComponent {

    public ClickableArea(int width, int height) {

        Dimension size = new Dimension(width, height);

        setSize(size);
        setPreferredSize(size);
        setBounds(getX(), getY(), width, height);

        setBorder(LineBorder.createBlackLineBorder());

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(LineBorder.createBlackLineBorder());
                repaint();
            }
        });
    }

    /************************** Overridden Methods **************************/

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        //System.out.println("Painting ClickableArea");
    }

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/
}
