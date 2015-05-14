import javax.swing.*;
import java.awt.*;

public class BaffleGrid extends JPanel {

    private ClickableArea[][] grid;


    public BaffleGrid(int rows, int cols) {

        setPreferredSize(new Dimension(getWidth(), getHeight()));

        setLayout(new GridLayout(rows + 2, cols + 2));

        grid = new ClickableArea[rows + 2][cols + 2];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {

                ClickableArea temp;

                if (row == 0) {
                    if (col == 0 || col == grid[0].length - 1) {
                        temp = null;
                    } else {
                        temp = new NumberBox(rows + col - 1);
                    }
                } else if (row == grid.length - 1) {
                    if (col == 0 || col == grid[0].length - 1) {
                        temp = null;
                    } else {
                        temp = new NumberBox((2 * rows) + (2 * cols) - col);
                    }
                } else {
                    if (col == 0) {
                        temp = new NumberBox(rows - row + 1);
                    } else if (col == grid[0].length - 1) {
                        temp = new NumberBox(cols + rows + row - 1);
                    } else {
                        temp = new ClickableArea();
                    }
                }

                grid[row][col] = temp;
                add(grid[row][col] != null ? grid[row][col] : new JPanel());
            }
        }
    }
    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/
}
