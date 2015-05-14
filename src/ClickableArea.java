import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickableArea extends JComponent {

    private boolean mouse;

    private Border defaultBorder, hoverBorder;

    public ClickableArea() {

        defaultBorder = LineBorder.createBlackLineBorder();
        hoverBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

        setBorder(defaultBorder);

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
                setBorder(hoverBorder);
                mouse = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(defaultBorder);
                mouse = false;
                repaint();
            }
        });

        mouse = false;
    }

    /************************** Overridden Methods **************************/

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    public void setDefaultBorder(Border b) {
        defaultBorder = b;
        if (!mouse)
            setBorder(b);
        repaint();
    }

    public void setHoverBorder(Border b) {
        hoverBorder = b;
        if (mouse)
            setBorder(b);
        repaint();
    }

    /**************************** Other Methods *****************************/
}
