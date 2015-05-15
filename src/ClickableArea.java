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

    /** Default Constructor **/
    public ClickableArea() {

        // initialize values
        active = true;
        mouse = false;

        inactiveColor = INACTIVE_COLOR;

        defaultBorder = LineBorder.createBlackLineBorder();
        hoverBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

        setBorder(defaultBorder);

        // add a mouse listener
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            /**
             * Called when a mouse button is released
             * @param e the MouseEvent
             */
            @Override
            public void mouseReleased(MouseEvent e) {

                if (active && mouse) {      // determine if the baffle is active and the mouse is within the baffle

                    if (e.getButton() == 1)     // left click
                        onLeftClick();
                    else if (e.getButton() == 3)    // right click
                        onRightClick();
                }
            }

            /**
             * Called when mouse enters the baffle
             * @param e the MouseEvent
             */
            @Override
            public void mouseEntered(MouseEvent e) {

                if (active) {        // determine if baffle is active
                    setBorder(hoverBorder);     // change the border
                    mouse = true;               // set mouse to true
                    repaint();                      // call a repaint because the border changed
                }
            }

            /**
             * Called when mouse exits the baffle
             * @param e the MouseEvent
             */
            @Override
            public void mouseExited(MouseEvent e) {

                if (active) {       // determine if the baffle is active
                    setBorder(defaultBorder);   // change the border
                    mouse = false;              // set mouse to false
                    repaint();                  // call a repaint because the border changed
                }
            }
        });
    }

    /************************** Overridden Methods **************************/

    /**
     * Paints the component onto the given graphics object.
     * @param g the graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // fill in the component's background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /****************************** Accessors *******************************/

    /**
     * Gets the ClickableObject's parent BaffleGrid
     * @return the ClickableObject's parent BaffleGrid
     */
    public BaffleGrid getBaffleGrid() { return (BaffleGrid)getParent(); }

    /****************************** Mutators ********************************/

    /**
     * Set's the default border for when the mouse is not hovering over the area.
     * @param b the border to use
     */
    public void setDefaultBorder(Border b) {
        defaultBorder = b;
        if (!mouse)
            setBorder(b);
        repaint();
    }

    /**
     * Set's the border for when the mouse is hovering over the area.
     * @param b the border to use
     */
    public void setHoverBorder(Border b) {
        hoverBorder = b;
        if (mouse)
            setBorder(b);
        repaint();
    }

    /**
     * Activate's the area so that it can be interacted with.
     */
    public void activate() {
        active = true;
        setBackground(UIManager.getColor("Panel.background"));
    }

    /**
     * Deactivate's the area so it cannot be interacted with.
     */
    public void deactivate() {
        active = false;
        setBackground(inactiveColor);
    }

    /**
     * Set's the background color for when the area is not active.
     * @param c the new background color
     */
    public void setInactiveColor(Color c) { inactiveColor = c; }

    /**************************** Other Methods *****************************/

    /**
     * Called when the MouseListener receives a left-click event.
     */
    public void onLeftClick() {
        deactivate();
    }

    /**
     * Called when the MouseListener receives a right-click event.
     */
    public void onRightClick() {}

    /**
     * Finds the direction a beam would leave in.
     * @param originalDir the beam's original direction
     * @return the beam's new direction
     */
    public short getBeamDir(short originalDir) {
        return originalDir;
    }
}
