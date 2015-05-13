import java.awt.*;

public class Box {

    private boolean baffle;
    private boolean baffleVisible;
    private boolean forwardBaffle;


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

        forwardBaffle = Math.random() < .5;
    }

    public void draw(Graphics g, int x, int y, int boxSize) {

        if (baffle && baffleVisible) {

            if (forwardBaffle)
                g.drawLine(x, y + boxSize, x + boxSize, y);
            else
                g.drawLine(x, y, x + boxSize, y + boxSize);
        }
    }
}
