import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BaffleGrid extends JPanel {

    public static final short BEAM_UP = 0;
    public static final short BEAM_RIGHT = 1;
    public static final short BEAM_DOWN = 2;
    public static final short BEAM_LEFT = 3;

    private ClickableArea[][] grid;


    public BaffleGrid(int rows, int cols, int numBaffles) {

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
                        temp = new Baffle();
                    }
                }

                grid[row][col] = temp;
                add(grid[row][col] != null ? grid[row][col] : new JPanel());
            }
        }

        createBaffles(numBaffles);
    }
    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    public Game getGame() { return (Game)getParent(); }

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public void fireBeam(int perimVal) {

            Integer[] loc = getPerimValLocation(perimVal);

            if (loc != null) {

                try {

                    short beamDir = getBeamDir(perimVal);

                    loc = propagateBeam(loc, beamDir);

                    while (grid[loc[0]][loc[1]] instanceof Baffle) {

                        ClickableArea area = grid[loc[0]][loc[1]];
                        beamDir = area.getBeamDir(beamDir);
                        loc = propagateBeam(loc, beamDir);
                    }

                    if (grid[loc[0]][loc[1]] instanceof NumberBox) {
                        NumberBox box = (NumberBox) grid[loc[0]][loc[1]];
                        highlightVal(perimVal, box.getVal());
                    }

                } catch (NoSuchFieldException e) {

                    System.out.println("Error finding beam direction.");
                    e.printStackTrace();
                }
            } else {

                System.out.println("Error firing beam.");
            }
    }

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

    private Integer[] getPerimValLocation(int val) {

        for (Integer[] loc : getPerimIndexes())
            if (((NumberBox)(grid[loc[0]][loc[1]])).getVal() == val)
                return loc;

        return null;
    }

    private short getBeamDir(int perimVal) throws NoSuchFieldException {

        Integer[] loc = getPerimValLocation(perimVal);

        if (loc != null) {
            if (loc[0] == 0)
                return BEAM_DOWN;
            if (loc[0] == grid.length - 1)
                return BEAM_UP;
            if (loc[1] == 0)
                return BEAM_RIGHT;
            if (loc[1] == grid[0].length - 1)
                return BEAM_LEFT;
        }

        throw new NoSuchFieldException("No NumberBox with that value exists.");
    }

    private Integer[] propagateBeam(Integer[] initialLoc, short beamDir) {

        if (beamDir != BEAM_DOWN
                && beamDir != BEAM_LEFT
                && beamDir != BEAM_RIGHT
                && beamDir != BEAM_UP) {

            throw new IllegalArgumentException("beam dir must be one of the predefined beam direction constants.");
        }

        switch (beamDir) {

            case BEAM_DOWN:
                return new Integer[] {initialLoc[0] + 1, initialLoc[1]};

            case BEAM_UP:
                return new Integer[] {initialLoc[0] - 1, initialLoc[1]};

            case BEAM_LEFT:
                return new Integer[] {initialLoc[0], initialLoc[1] - 1};
        }

        return new Integer[] {initialLoc[0], initialLoc[1] + 1};
    }

    private void highlightVal(int startVal, int endVal) {

        for (Integer[] loc : getPerimIndexes()) {
            NumberBox box = (NumberBox)grid[loc[0]][loc[1]];
            if (box.getVal() == startVal)
                box.setHighlighted(true, false);
            else if (box.getVal() == endVal)
                box.setHighlighted(true, true);
            else
                box.clearHighlight();
        }
    }

    private void createBaffles(int numBaffles) {

        ArrayList<int[]> possibleLocations = new ArrayList<int[]>();

        for (int row = 1; row < grid.length - 1; row++)
            for (int col = 1; col < grid[0].length - 1; col++)
                possibleLocations.add(new int[] {row, col});

        for (int i = 0; i < numBaffles; i++) {

            int index = (int)(Math.random() * possibleLocations.size());

            int[] loc = possibleLocations.get(index);

            ((Baffle)(grid[loc[0]][loc[1]])).generateBaffle();

            possibleLocations.remove(index);
        }
    }
}
