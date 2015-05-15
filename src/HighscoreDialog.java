import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class HighscoreDialog extends JDialog {

    private static final Vector<String> colNames;


    private HighscoreDB db;

    /**
     * Initialize static variables.
     */
    static {

        colNames = new Vector<String>();
        colNames.add("Name");
        colNames.add("Score");
    }

    /**
     * Create a highscore dialog initially showing the scores for the current difficulty.
     * @param parent the dialog's parent <code>Frame</code>
     * @param currentGameMode the game difficulty level. Must be one of <code>Game.EASY</code>, <code>Game.MEDIUM</code>,
     *        or <code>Game.HARD</code>.
     */
    public HighscoreDialog(Frame parent, int currentGameMode) {
        super(parent);

        // create a connection to the high score database
        db = new HighscoreDB();

        // initialize the dialog's UI
        initialize(currentGameMode);
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    /**
     * Create a table of scores for the given difficulty.
     * @param difficulty the game difficulty level. Must be one of <code>Game.EASY</code>, <code>Game.MEDIUM</code>,
     *        or <code>Game.HARD</code>.
     * @return a <code>JTable</code> containing the scores.
     */
    private JTable createScoreTable(int difficulty) {

        // create a new JTable from the database
        JTable t = new JTable(db.getScores(difficulty), colNames);
        // make the table uneditable
        t.setEnabled(false);

        return t;
    }

    /**
     * Create a scrollable container for a score table.
     * @param difficulty the game difficulty level. Must be one of <code>Game.EASY</code>, <code>Game.MEDIUM</code>,
     *        or <code>Game.HARD</code>.
     * @return a <code>JScrollPane</code> containing a <code>JTable</code> of scores
     */
    private JScrollPane createScoreContainer(int difficulty) {

        // get the table of scores
        JTable t = createScoreTable(difficulty);

        // create a scroll pane from the table
        JScrollPane scroll = new JScrollPane(t);
        t.setFillsViewportHeight(true);

        return scroll;
    }

    /**
     * Initialize the <code>HighscoreDialog</code>'s UI.
     * @param gameMode the game mode to first display
     */
    private void initialize(int gameMode) {

        // create a new tabbed pane
        JTabbedPane tabPane = new JTabbedPane();

        // easy panel with scores
        JPanel easy = new JPanel(new BorderLayout());
        easy.add(createScoreContainer(Game.EASY), BorderLayout.CENTER);

        // medium panel with scores
        JPanel med = new JPanel(new BorderLayout());
        med.add(createScoreContainer(Game.MEDIUM), BorderLayout.CENTER);

        // hard panel with scores
        JPanel hard = new JPanel(new BorderLayout());
        hard.add(createScoreContainer(Game.HARD), BorderLayout.CENTER);

        // create tabs
        tabPane.addTab("Easy", easy);
        tabPane.addTab("Medium", med);
        tabPane.addTab("Hard", hard);

        // set selected tab to current game mode
        if (gameMode == Game.EASY)
            tabPane.setSelectedIndex(0);
        else if (gameMode == Game.MEDIUM)
            tabPane.setSelectedIndex(1);
        else if (gameMode == Game.HARD)
            tabPane.setSelectedIndex(2);

        // add the tab pane
        add(tabPane);

        // fit dialog to components size
        pack();

        // set up window
        setTitle("Highscores");
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setVisible(true);
    }
}
