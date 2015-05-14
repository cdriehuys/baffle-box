import java.awt.*;

public class Baffle extends ClickableArea {

    private boolean hasBaffle, forwardBaffle, baffleVisible;


    public Baffle() {

        hasBaffle = forwardBaffle = baffleVisible = false;
    }

    /************************** Overridden Methods **************************/

    @Override
    public short getBeamDir(short enterDir) {

        if (enterDir != BaffleGrid.BEAM_DOWN
                && enterDir != BaffleGrid.BEAM_LEFT
                && enterDir != BaffleGrid.BEAM_RIGHT
                && enterDir != BaffleGrid.BEAM_UP) {

            throw new IllegalArgumentException("enterDir must be one of the predefined BaffleGrid.BEAM_--- constants.");
        }

        if (hasBaffle) {

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

            return enterDir;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (hasBaffle && baffleVisible) {
            g.setColor(Color.BLACK);
            g.drawLine(0, forwardBaffle ? getHeight() : 0, getWidth(), forwardBaffle ? 0 : getHeight());
        }
    }

    @Override
    public void onClick() {
        super.onClick();

        if (hasBaffle) {
            baffleVisible = true;
            getBaffleGrid().getGame().getScorePanel().addScore(Game.BAFFLE_PRIZE);
            getBaffleGrid().getGame().getScorePanel().foundBaffle();
        } else {
            getBaffleGrid().getGame().getScorePanel().addScore(Game.WRONG_GUESS);
        }
    }


    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public void generateBaffle() {

        hasBaffle = true;
        forwardBaffle = Math.random() < .5;
    }
}
