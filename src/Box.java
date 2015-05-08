import java.awt.*;

public class Box {

    private static final int FORWARD_BAFFLE = 1;
    private static final int BACK_BAFFLE = 2;

    private boolean baffle;
    private boolean baffleVisible;

    private int baffleDir;


    public Box(boolean baffle) {

        if (baffle)
            generateBaffle();

        baffleVisible = false;
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    public boolean hasBaffle() { return baffle; }

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public void generateBaffle() {

        baffle = true;

        if (Math.random() < .5)
            baffleDir = FORWARD_BAFFLE;
        else
            baffleDir = BACK_BAFFLE;
    }

    public void draw(Graphics g, int x, int y, int boxSize) {

        if (baffle && baffleVisible) {

            if (baffleDir == FORWARD_BAFFLE)
                g.drawLine(x, y + boxSize, x + boxSize, y);
            else
                g.drawLine(x, y, x + boxSize, y + boxSize);
        }
    }
}
