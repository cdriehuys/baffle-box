import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickableArea extends JComponent {

    public static final Color INACTIVE_COLOR = Color.LIGHT_GRAY;


    private boolean mouse, active;

    private Border defaultBorder, hoverBorder;

    private Color inactiveColor;

    public ClickableArea() {

        active = true;

        inactiveColor = INACTIVE_COLOR;

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
                if (active && mouse)
                    onClick();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (active) {
                    setBorder(hoverBorder);
                    mouse = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (active) {
                    setBorder(defaultBorder);
                    mouse = false;
                    repaint();
                }
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

    public BaffleGrid getBaffleGrid() { return (BaffleGrid)getParent(); }

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

    public void activate() {
        active = true;
        setBackground(UIManager.getColor("Panel.background"));
    }

    public void deactivate() {
        active = false;
        setBackground(inactiveColor);
    }

    public void setInactiveColor(Color c) { inactiveColor = c; }

    /**************************** Other Methods *****************************/

    public void onClick() {
        deactivate();
    }

    public short getBeamDir(short originalDir) {
        return originalDir;
    }
}
