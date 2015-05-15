import java.awt.*;

public class Baffle extends ClickableArea {

    public static final Image FLAG;

    private boolean hasBaffle, forwardBaffle, baffleVisible, flagged;


    static {
        FLAG = Toolkit.getDefaultToolkit().createImage(Baffle.class.getResource("images/flag.png"));
    }

    /** Default constructor **/
    public Baffle() {

        // initialize all booleans to false
        hasBaffle = forwardBaffle = baffleVisible = flagged = false;
    }

    /************************** Overridden Methods **************************/

    /**
     * Given an incoming beam's direction, find which direction it will exit the baffle in.
     * @param enterDir the beam's initial direction
     * @return the beam's new direction
     */
    @Override
    public short getBeamDir(short enterDir) {

        // determine if entry direction is valid
        if (enterDir != BaffleGrid.BEAM_DOWN
                && enterDir != BaffleGrid.BEAM_LEFT
                && enterDir != BaffleGrid.BEAM_RIGHT
                && enterDir != BaffleGrid.BEAM_UP) {

            throw new IllegalArgumentException("enterDir must be one of the predefined BaffleGrid.BEAM_--- constants.");
        }

        // determine if there is a baffle to bounce off of
        if (hasBaffle) {

            // reflection logic
            if (forwardBaffle) {

                switch (enterDir) {

                    case BaffleGrid.BEAM_RIGHT:
                        return BaffleGrid.BEAM_UP;

                    case BaffleGrid.BEAM_DOWN:
                        return BaffleGrid.BEAM_LEFT;

                    case BaffleGrid.BEAM_LEFT:
                        return BaffleGrid.BEAM_DOWN;
                }

                return BaffleGrid.BEAM_RIGHT;

            } else {

                switch (enterDir) {

                    case BaffleGrid.BEAM_RIGHT:
                        return BaffleGrid.BEAM_DOWN;

                    case BaffleGrid.BEAM_DOWN:
                        return BaffleGrid.BEAM_RIGHT;

                    case BaffleGrid.BEAM_LEFT:
                        return BaffleGrid.BEAM_UP;
                }

                return BaffleGrid.BEAM_LEFT;
            }

        } else {

            // no baffle, return original direction
            return enterDir;
        }
    }

    /**
     * Draw the baffle to the screen.
     * @param g the graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // if the box has been flagged, draw a flag
        if (flagged) {
            g.drawImage(FLAG, 0, 0, getWidth(), getHeight(), null);
        }

        // if the box has a baffle and it's been discovered, draw the baffle
        if (hasBaffle && baffleVisible) {
            g.setColor(Color.BLACK);
            g.drawLine(0, forwardBaffle ? getHeight() : 0, getWidth(), forwardBaffle ? 0 : getHeight());
        }
    }

    /**
     * The function that is called when the box is left-clicked
     */
    @Override
    public void onLeftClick() {

        // if it's not flagged
        if (!flagged) {
            super.onLeftClick();    // parent's left-click function

            // if box has a baffle
            if (hasBaffle) {
                baffleVisible = true;   // make the baffle visible
                getBaffleGrid().getGame().getScorePanel().foundBaffle();    // add to score
            } else {
                getBaffleGrid().getGame().getScorePanel().addScore(Game.WRONG_GUESS);   // wrong guess, subtract score
            }
        }
    }

    /**
     * The function that is called when the box is right-clicked
     */
    @Override
    public void onRightClick() {

        // toggle the flag
        flagged = !flagged;
    }


    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    /**
     * Generates a baffle with a random direction.
     */
    public void generateBaffle() {

        hasBaffle = true;
        forwardBaffle = Math.random() < .5;
        setInactiveColor(Color.GRAY);
    }
}
