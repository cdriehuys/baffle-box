import javax.swing.*;

public class ScorePanel extends JPanel {

    private int score, remainingBaffles;

    private JLabel scoreLabel, bafflesLeftLabel;


    public ScorePanel(int numBaffles) {

        score = 0;
        remainingBaffles = numBaffles;

        scoreLabel = new JLabel("Score: " + score);
        bafflesLeftLabel = new JLabel("Remaining Baffles: " + remainingBaffles);

        add(scoreLabel);
        add(bafflesLeftLabel);
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    public void foundBaffle() {
        remainingBaffles--;
        bafflesLeftLabel.setText("Remaining Baffles: " + remainingBaffles);
    }

    public void addScore(int amount) {
        score += amount;
        scoreLabel.setText("Score: " + score);
    }

    /**************************** Other Methods *****************************/
}
