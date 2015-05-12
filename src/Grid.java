import java.awt.*;

public class Grid {

    private static final int BOX_SIZE = 20;
    private static final int BORDER_WIDTH = 1;


    private Box[][] grid;


    public Grid(int width, int height, int numBaffles) {

        grid = new Box[height][width];

        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                grid[row][col] = new Box(false);
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    public int getWidth() {
        return (BOX_SIZE + BORDER_WIDTH) * grid[0].length + BORDER_WIDTH;
    }

    public int getHeight() {
        return (BOX_SIZE + BORDER_WIDTH) * grid.length + BORDER_WIDTH;
    }

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public void draw(Graphics g, int x, int y) {

        g.drawLine(x, y, x + getWidth(), y);
        g.drawLine(x, y, x, y + getHeight());

        for (int row = 0; row < grid.length; row++) {

            for (int col = 0; col < grid[0].length; col++) {

                int colX = (BOX_SIZE + BORDER_WIDTH) * (col + 1) + BORDER_WIDTH + x;
                int topRightY = (BOX_SIZE + BORDER_WIDTH) * row + BORDER_WIDTH + y;
                int bottomRightY = topRightY + BOX_SIZE + BORDER_WIDTH;

                g.drawLine(colX, topRightY, colX, bottomRightY);
            }

            int borderY = (BOX_SIZE + BORDER_WIDTH) * (row + 1) + BORDER_WIDTH + y;

            g.drawLine(x, borderY, x + getWidth(), borderY);
        }
    }
}
