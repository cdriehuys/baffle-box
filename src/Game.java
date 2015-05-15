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

        initialize();

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

    private void initialize() {

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

        revalidate();
    }

    private void destroy() {

        removeAll();

        scorePanel = null;

        history.dispose();
        history = null;

        revalidate();
    }

    /**************************** Helper Classes ****************************/

    private class DrawTimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
