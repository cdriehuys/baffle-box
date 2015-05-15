import java.awt.*;

public class NumberBox extends ClickableArea {

    private static final Color NORMAL_COLOR = Color.BLACK;
    private static final Color START_COLOR = Color.GREEN;
    private static final Color END_COLOR = Color.RED;

    private boolean highlighted, isEnd;

    private int val;


    /**
     * Create a new <code>NumberBox</code> with the given value.
     * @param val the value to display
     */
    public NumberBox(int val) {
        super();

        this.val = val;

        highlighted = isEnd = false;

        setDefaultBorder(null);
    }
    /************************** Overridden Methods **************************/

    /**
     * Paints the object onto the given graphics object
     * @param g the graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // change font if highlighted
        if (highlighted) {
            g.setColor(isEnd ? END_COLOR : START_COLOR);
            g.setFont(g.getFont().deriveFont(Font.BOLD));
        } else {
            g.setColor(NORMAL_COLOR);
            g.setFont(g.getFont().deriveFont(Font.PLAIN));
        }

        // draw the NumberBox's value
        g.drawString(String.valueOf(val), getWidth() / 3, getHeight() / 2);
    }

    /**
     * Fire a beam from this box.
     */
    @Override
    public void onLeftClick() {
        getBaffleGrid().fireBeam(val);
    }

    /****************************** Accessors *******************************/

    /**
     * Get this box's value.
     * @return the box's value
     */
    public int getVal() { return val; }

    /****************************** Mutators ********************************/

    /**
     * Clear the highlighting on the number box.
     */
    public void clearHighlight() { highlighted = false; }

    /**
     * Highlight the <code>NumberBox</code>.
     * @param val if the box should be highlighted
     * @param isEnd determines if the box should be highlighted with the beam end color or the beam start color
     */
    public void setHighlighted(boolean val, boolean isEnd) {
        highlighted = val;
        this.isEnd = isEnd;
    }

    /**************************** Other Methods *****************************/
}
