import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel {

    private int score, remainingBaffles;

    private JLabel scoreLabel, bafflesLeftLabel;


    public ScorePanel(int numBaffles) {

        score = 0;
        remainingBaffles = numBaffles;

        scoreLabel = new JLabel("Score: " + score);
        bafflesLeftLabel = new JLabel("Remaining Baffles: " + remainingBaffles);

        JButton historyBtn = new JButton("History");
        historyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getGame().showHistory();
            }
        });

        add(scoreLabel);
        add(bafflesLeftLabel);
        add(historyBtn);
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    public Game getGame() { return (Game)getParent(); }

    public int getScore() { return score; }

    /****************************** Mutators ********************************/

    public void foundBaffle() {
        remainingBaffles--;
        score += Game.BAFFLE_PRIZE;
        bafflesLeftLabel.setText("Remaining Baffles: " + remainingBaffles);
        if (remainingBaffles == 0)
            getGame().win();
    }

    public void addScore(int amount) {
        score += amount;
        scoreLabel.setText("Score: " + score);
    }

    /**************************** Other Methods *****************************/
}
