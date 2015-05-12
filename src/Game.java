import java.awt.*;

public class Game {

    private Grid grid;


    public Game() {

        grid = new Grid(10, 10, 3);
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public void draw(Graphics g) {
        grid.draw(g, 50, 50);
    }
}
