import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Game extends JPanel {

    public static final int BAFFLE_PRIZE = 7;
    public static final int FIRE_PENALTY = -1;
    public static final int WRONG_GUESS = -3;

    public static final int EASY = 1;
    public static final int EASY_BAFFLES = 10;
    public static final int MEDIUM = 2;
    public static final int MEDIUM_BAFFLES = 15;
    public static final int HARD = 3;
    public static final int HARD_BAFFLES = 20;


    private int gameMode;

    private HighscoreDB scoreDB;

    private HistoryDialog history;

    private ScorePanel scorePanel;

    /**
     * Constructor with the panel's preferred dimensions.
     * @param width The preferred width for the panel.
     * @param height The preferred height for the panel.
     */
    public Game(int width, int height) {

        // set the preferred size of the panel
        setPreferredSize(new Dimension(width, height));

        // create a new border layout
        setLayout(new BorderLayout());

        // create menu bar
        createMenubar();

        // connect to the highscores database
        scoreDB = new HighscoreDB();

        // initialize the interface
        initialize();

        // create a timer to repaint every 20ms
        Timer drawTimer = new Timer(20, new DrawTimerListener());
        drawTimer.start();
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /**
     * Accessor for the Game's ScorePanel.
     * @return the Game's ScorePanel
     */
    public ScorePanel getScorePanel() { return scorePanel; }


    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    /**
     * Shows the history dialog.
     */
    public void showHistory() {
        history.setVisible(true);
    }

    /**
     * Method executed when the game is won.
     */
    public void win() {

        // show the high scores
        doHighScores();

        /* The rest of this method creates a new dialog telling the user that they won. It gives the user
         * the option to quit or play again.
         */
        final JDialog dialog = new JDialog();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel text = new JPanel();
        text.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel message = new JLabel("Congratulations! You won!");

        text.add(Box.createRigidArea(new Dimension(100, 0)));
        text.add(message);
        text.add(Box.createRigidArea(new Dimension(100, 0)));

        JPanel buttons = new JPanel();
        buttons.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton play = new JButton("Play Again");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                destroy();
                initialize();
            }
        });

        buttons.add(quit);
        buttons.add(Box.createRigidArea(new Dimension(5, 0)));
        buttons.add(play);
        buttons.add(Box.createRigidArea(new Dimension(15, 0)));

        panel.add(text);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(buttons);

        dialog.add(panel);
        dialog.pack();

        dialog.setModal(true);
        dialog.setTitle("You Won!");
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);
    }

    /**
     * Logs a move to the Game's HistoryDialog.
     * @param move the move to log
     */
    public void logMove(String move) {
        history.logMove(move);
    }

    public JMenuBar createMenubar() {

        // create new menu bar
        JMenuBar menu = new JMenuBar();

        // make the file menu
        JMenu file = new JMenu("File");
        // set its shortcut to 'F'
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
                initialize();
            }
        });

        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        file.add(newGame);
        file.add(exit);

        menu.add(file);

        return menu;
    }

    /**
     * Creates a dialog prompting the user to choose a difficulty level.
     * @return the difficulty level. Must be one of <code>Game.EASY</code>, <code>Game.MEDIUM</code>,
     *         or <code>Game.HARD</code>.
     */
    private int getDifficulty() {

        // create new panel
        JPanel panel = new JPanel();

        // create radio buttons for different difficulties
        JRadioButton easy = new JRadioButton("Easy", false);
        JRadioButton med = new JRadioButton("Medium");
        JRadioButton hard = new JRadioButton("Hard");

        // add the radio buttons to a group and set the default option to medium
        ButtonGroup radios = new ButtonGroup();
        radios.add(easy);
        radios.add(med);
        radios.add(hard);
        med.setSelected(true);

        // add the buttons to the panel
        panel.add(easy);
        panel.add(med);
        panel.add(hard);

        // get the results from a JOptionPane with the custom panel
        int res = JOptionPane.showConfirmDialog(this, panel, "Difficulty", JOptionPane.DEFAULT_OPTION);
        if (res == JOptionPane.OK_OPTION) {

            if (easy.isSelected())
                return EASY;
            else if (med.isSelected())
                return MEDIUM;

            return HARD;
        } else if (res == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        // if we somehow get to this point, default to medium difficulty
        return Game.MEDIUM;
    }

    /**
     * Initializes the Game.
     */
    private void initialize() {

        // get the difficulty
        gameMode = getDifficulty();

        int numBaffles;

        // determine how many baffles to create based on the game difficulty
        switch (gameMode) {
            case EASY:
                numBaffles = EASY_BAFFLES;
                break;

            case HARD:
                numBaffles = HARD_BAFFLES;
                break;

            default:
                numBaffles = MEDIUM_BAFFLES;
        }

        // create a new 10x10 baffle grid with the previously specified number of baffles
        add(new BaffleGrid(10, 10, numBaffles), BorderLayout.CENTER);

        // create and add a new ScorePanel
        scorePanel = new ScorePanel(numBaffles);
        add(scorePanel, BorderLayout.NORTH);

        // make a new HistoryDialog
        history = new HistoryDialog(null);

        // revalidate so that all components are correctly rendered
        revalidate();
    }

    /**
     * Method used to clean up a game after winning and before restarting.
     */
    private void destroy() {

        // remove all components
        removeAll();

        // destroy the score panel
        scorePanel = null;

        // destroy the history
        history.dispose();
        history = null;

        // make sure none of the removed components are rendered
        revalidate();
    }

    /**
     * Handles displaying of the high scores.
     */
    private void doHighScores() {

        // get the user's name
        String name = JOptionPane.showInputDialog(this, "Enter your name", "Enter Name", JOptionPane.QUESTION_MESSAGE);

        // add the score to the score database and display the scores
        if (name != null) {
            scoreDB.addScore(name, scorePanel.getScore(), gameMode);
            new HighscoreDialog(null, gameMode);
        }
    }

    /**************************** Helper Classes ****************************/

    /* Helper class for the Game that listens for timer events and repaints the game */
    private class DrawTimerListener implements ActionListener {

        /**
         * Method called when an ActionEvent is received from the timer.
         * @param e the ActionEvent received
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
