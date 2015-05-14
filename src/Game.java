import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        setLayout(new BorderLayout());

        int difficulty = getDifficulty();

        int numBaffles;

        switch (difficulty) {
            case EASY:
                numBaffles = EASY_BAFFLES;
                break;

            case HARD:
                numBaffles = HARD_BAFFLES;
                break;

            default:
                numBaffles = MEDIUM_BAFFLES;
        }

        add(new BaffleGrid(10, 10, numBaffles), BorderLayout.CENTER);

        scorePanel = new ScorePanel(numBaffles);
        add(scorePanel, BorderLayout.NORTH);

        history = new HistoryDialog(null);

        Timer drawTimer = new Timer(20, new DrawTimerListener());
        drawTimer.start();
    }

    /************************** Overridden Methods **************************/

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public ScorePanel getScorePanel() { return scorePanel; }

    public void showHistory() {
        if (!history.isVisible())
            history.setVisible(true);
    }

    public void win() {

        JOptionPane.showMessageDialog(this, "Congratulations! You won!", "Winner", JOptionPane.PLAIN_MESSAGE);
    }

    public void logMove(String move) {
        history.logMove(move);
    }

    private int getDifficulty() {

        JPanel panel = new JPanel();

        JRadioButton easy = new JRadioButton("Easy", false);
        JRadioButton med = new JRadioButton("Medium");
        JRadioButton hard = new JRadioButton("Hard");

        ButtonGroup radios = new ButtonGroup();
        radios.add(easy);
        radios.add(med);
        radios.add(hard);
        med.setSelected(true);

        panel.add(easy);
        panel.add(med);
        panel.add(hard);

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

        return 0;
    }

    /**************************** Helper Classes ****************************/

    private class DrawTimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
