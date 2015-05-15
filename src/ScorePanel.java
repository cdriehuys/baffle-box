import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel {

    private int score, remainingBaffles;

    private JLabel scoreLabel, bafflesLeftLabel;

    /**
     * Create a new score panel.
     * @param numBaffles the initial value for baffles remaining.
     */
    public ScorePanel(int numBaffles) {

        // initialize score and remaining baffles
        score = 0;
        remainingBaffles = numBaffles;

        // make labels for the score and baffles remaining.
        scoreLabel = new JLabel("Score: " + score);
        bafflesLeftLabel = new JLabel("Remaining Baffles: " + remainingBaffles);

        // history button to show log of past moves
        JButton historyBtn = new JButton("History");
        historyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGame().showHistory();
            }
        });

        // add elements to panel
        add(scoreLabel);
        add(bafflesLeftLabel);
        add(historyBtn);
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /**
     * Get the parent <code>Game</code> object.
     * @return the parent <code>Game</code> object
     */
    public Game getGame() { return (Game)getParent(); }

    /**
     * Get the current score.
     * @return the current score
     */
    public int getScore() { return score; }

    /****************************** Mutators ********************************/

    /**
     * Called when a baffle is found. Adds to score and subtracts from remaining baffles. Also
     * determines if the game has been won.
     */
    public void foundBaffle() {
        remainingBaffles--;
        score += Game.BAFFLE_PRIZE;
        bafflesLeftLabel.setText("Remaining Baffles: " + remainingBaffles);
        if (remainingBaffles == 0)
            getGame().win();
    }

    /**
     * Add to the score.
     * @param amount the amount to add to the score
     */
    public void addScore(int amount) {
        score += amount;
        scoreLabel.setText("Score: " + score);
    }

    /**************************** Other Methods *****************************/
}
