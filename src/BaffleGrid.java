import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BaffleGrid extends JPanel {

    public static final short BEAM_UP = 0;
    public static final short BEAM_RIGHT = 1;
    public static final short BEAM_DOWN = 2;
    public static final short BEAM_LEFT = 3;

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
                        temp = new NumberBox(rows - row);
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

    private ArrayList<Integer[]> getPerimIndexes() {

        ArrayList<Integer[]> perimIndexes = new ArrayList<Integer[]>();

        for (int row = grid.length - 2; row > 0; row--)
            perimIndexes.add(new Integer[] {row, 0});

        for (int col = 1; col < grid[0].length - 1; col++)
            perimIndexes.add(new Integer[] {0, col});

        for (int row = 1; row < grid.length - 1; row++)
            perimIndexes.add(new Integer[] {row, grid[0].length - 1});

        for (int col = grid[0].length - 2; col > 0; col--)
            perimIndexes.add(new Integer[] {grid.length - 1, col});

        return perimIndexes;
    }

    private short getBeamDir(int perimVal) throws NoSuchFieldException {

        for (Integer[] loc : getPerimIndexes()) {
            if (((NumberBox)(grid[loc[0]][loc[1]])).getVal() == perimVal) {
                if (loc[0] == 0)
                    return BEAM_DOWN;
                if (loc[0] == grid.length - 1)
                    return BEAM_UP;
                if (loc[1] == 0)
                    return BEAM_RIGHT;
                if (loc[1] == grid[0].length - 1)
                    return BEAM_LEFT;

                throw new NoSuchFieldException("That box is not on the perimeter!");
            }
        }

        throw new NoSuchFieldException("No NumberBox with that value exists.");
    }
}
