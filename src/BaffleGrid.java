import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BaffleGrid extends JPanel {

    public static final short BEAM_UP = 0;
    public static final short BEAM_RIGHT = 1;
    public static final short BEAM_DOWN = 2;
    public static final short BEAM_LEFT = 3;

    private ClickableArea[][] grid;


    /**
     * Constructs a BaffleGrid with the given dimensions and number of baffles.
     * @param rows the number of rows in the grid
     * @param cols the number of columns in the grid
     * @param numBaffles the number of baffles to generate
     */
    public BaffleGrid(int rows, int cols, int numBaffles) {

        // create a grid layout with room for all the components
        setLayout(new GridLayout(rows + 2, cols + 2));

        // make a new grid that's big enough for all the boxes and the perimeter labels
        grid = new ClickableArea[rows + 2][cols + 2];

        // loop through each row
        for (int row = 0; row < grid.length; row++) {

            // loop through each column
            for (int col = 0; col < grid[0].length; col++) {

                ClickableArea temp;

                if (row == 0) {     // if row == 0, we need to create the top number labels

                    if (col == 0 || col == grid[0].length - 1) {    // in the corners, no label is needed

                        temp = null;

                    } else {        // make a new label

                        temp = new NumberBox(rows + col - 1);
                    }

                } else if (row == grid.length - 1) {    // we need to make the bottom labels

                    if (col == 0 || col == grid[0].length - 1) {    // in the corners, no label is needed

                        temp = null;

                    } else {    // make a new label

                        temp = new NumberBox((2 * rows) + (2 * cols) - col);

                    }

                } else {    // in the middle rows, we need to generate both labels and boxes

                    if (col == 0) {     // in the first column, generate a label
                        temp = new NumberBox(rows - row);

                    } else if (col == grid[0].length - 1) {     // generate a label in the last column too

                        temp = new NumberBox(cols + rows + row - 1);

                    } else {    // otherwise create a new baffle

                        temp = new Baffle();
                    }
                }

                // assign the temp variable;
                grid[row][col] = temp;

                // if the area is null, insert a JPanel as a filler
                add(getAt(row, col) != null ? getAt(row, col) : new JPanel());
            }
        }

        // generate the correct number of baffles
        createBaffles(numBaffles);
    }
    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /**
     * Gets the parent Game object.
     * @return The parent Game object of this BaffleGrid
     */
    public Game getGame() { return (Game)getParent(); }

    /**
     * Gets the <code>ClickableArea</code> at the given row and column.
     * @param row the row index
     * @param col the column index
     * @return a <code>ClickableArea</code> reference
     */
    public ClickableArea getAt(int row, int col) {

        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length)
            throw new IllegalArgumentException(String.format("Index of row: %d, col: %d is out of bounds.", row, col));

        return grid[row][col];
    }

    /**
     * Gets the <code>ClickableArea</code> at the row and column specified in the given integer array.
     * @param loc an integer array where <code>loc[0]</code> is the row index and <code>loc[1]</code> is the
     *            column index
     * @return a reference to a <code>ClickableArea</code>
     */
    public ClickableArea getAt(Integer[] loc) {

        if (loc.length != 2)
            throw new IllegalArgumentException("Argument must be an integer array with 2 values of the form {row, col}");

        return getAt(loc[0], loc[1]);
    }

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    /**
     * Fires a beam from the specified perimeter value.
     * @param perimVal the perimeter value to fire from
     */
    public void fireBeam(int perimVal) {

        // the row and column of the specified perimeter value
        Integer[] loc = getPerimValLocation(perimVal);

        if (loc != null) {  // make sure the value was found

            try {

                // get the direction of the beam coming from the perimeter
                short beamDir = getBeamDir(perimVal);

                // propagate the beam once so it's in a Baffle object
                loc = propagateBeam(loc, beamDir);

                // continue to propagate the beam while the beam is in a Baffle
                while (getAt(loc) instanceof Baffle) {

                    ClickableArea area = getAt(loc);
                    beamDir = area.getBeamDir(beamDir);
                    loc = propagateBeam(loc, beamDir);
                }

                // if the beam reaches a perimeter box, finish firing
                if (getAt(loc) instanceof NumberBox) {

                    // get the ending box
                    NumberBox box = (NumberBox)getAt(loc);
                    // highlight the starting and ending perimeter boxes
                    highlightVal(perimVal, box.getVal());
                    // log the shot in the history
                    getGame().logMove(String.format("Fired from %d, ended up at %d", perimVal, box.getVal()));
                    // assess point penalty for firing shots
                    getGame().getScorePanel().addScore(Game.FIRE_PENALTY);
                }

            } catch (NoSuchFieldException e) {

                // there was an error finding one of the perimeter values' locations
                System.out.println("Error finding beam direction.");
                e.printStackTrace();
            }
        } else {

            // the initial perimeter value was not located
            System.out.println("Error firing beam.");
        }
    }

    /**
     * Get the row and column of each perimeter value
     * @return An ArrayList containing the row and column of each perimeter value
     */
    private ArrayList<Integer[]> getPerimIndexes() {

        // array list for all the locations
        ArrayList<Integer[]> perimIndexes = new ArrayList<Integer[]>();

        // get left edge
        for (int row = grid.length - 2; row > 0; row--)
            perimIndexes.add(new Integer[] {row, 0});

        // get top edge
        for (int col = 1; col < grid[0].length - 1; col++)
            perimIndexes.add(new Integer[] {0, col});

        // get right edge
        for (int row = 1; row < grid.length - 1; row++)
            perimIndexes.add(new Integer[] {row, grid[0].length - 1});

        // get bottom edge
        for (int col = grid[0].length - 2; col > 0; col--)
            perimIndexes.add(new Integer[] {grid.length - 1, col});

        return perimIndexes;
    }

    /**
     * Gets the location of the perimeter box with the specified value.
     * @param val the value to search for
     * @return an Integer[] <code>loc</code> where <code>loc[0]</code> is the row and <code>loc[1]</code> is
     *         the column.
     */
    private Integer[] getPerimValLocation(int val) {

        for (Integer[] loc : getPerimIndexes())
            if (((NumberBox)(getAt(loc))).getVal() == val)
                return loc;

        return null;
    }

    /**
     * Get's the initial beam direction when a shot is fired from the perimeter
     * @param perimVal the perimeter value to fire from
     * @return an Integer[] <code>loc</code> where <code>loc[0]</code> is the row and <code>loc[1]</code> is
     *         the column.
     * @throws NoSuchFieldException if the perimeter value cannot be found
     */
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

    /**
     * Moves a beam through the grid.
     * @param initialLoc the starting position of the beam
     * @param beamDir the beam's direction
     * @return the new location of the beam
     */
    private Integer[] propagateBeam(Integer[] initialLoc, short beamDir) {

        // make sure direction is valid
        if (beamDir != BEAM_DOWN
                && beamDir != BEAM_LEFT
                && beamDir != BEAM_RIGHT
                && beamDir != BEAM_UP) {

            throw new IllegalArgumentException("beam dir must be one of the predefined beam direction constants.");
        }

        // determine the new location of the beam
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

    /**
     * Changes the color of the locations where the beam was shot from and where it ended up.
     * @param startVal the value of the starting location
     * @param endVal the value of the ending location
     */
    private void highlightVal(int startVal, int endVal) {

        for (Integer[] loc : getPerimIndexes()) {
            NumberBox box = (NumberBox)getAt(loc);
            if (box.getVal() == startVal)
                box.setHighlighted(true, false);
            else if (box.getVal() == endVal)
                box.setHighlighted(true, true);
            else
                box.clearHighlight();
        }
    }

    /**
     * Generates random baffles.
     * @param numBaffles the number of baffles to generate.
     */
    private void createBaffles(int numBaffles) {

        // create an array list to hold all possible baffle locations
        ArrayList<Integer[]> possibleLocations = new ArrayList<Integer[]>();

        // populate the list with all the possible locations
        for (int row = 1; row < grid.length - 1; row++)
            for (int col = 1; col < grid[0].length - 1; col++)
                possibleLocations.add(new Integer[] {row, col});

        // loop for each baffle to be created
        for (int i = 0; i < numBaffles; i++) {

            // pick a random index in the range of the list of possible locations
            int index = (int)(Math.random() * possibleLocations.size());

            // generate a baffle at the location reference by the index
            Integer[] loc = possibleLocations.get(index);
            ((Baffle)(getAt(loc))).generateBaffle();

            // remove that location from the list
            possibleLocations.remove(index);
        }
    }
}
