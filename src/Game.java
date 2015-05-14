import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JPanel {

    public static final int BAFFLE_PRIZE = 7;
    public static final int FIRE_PENALTY = -1;
    public static final int WRONG_GUESS = -3;

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

        int numBaffles = getNumBaffles();

        add(new BaffleGrid(10, 10, numBaffles), BorderLayout.CENTER);

        scorePanel = new ScorePanel(numBaffles);
        add(scorePanel, BorderLayout.NORTH);

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

    private int getNumBaffles() {

        int num;

        try {
            num = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of baffles to generate.",
                    "How Many Baffles?", JOptionPane.QUESTION_MESSAGE));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return getNumBaffles();
        }

        return num;
    }

    /**************************** Helper Classes ****************************/

    private class DrawTimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
