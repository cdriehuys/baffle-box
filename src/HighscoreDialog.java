import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class HighscoreDialog extends JDialog {

    private static final Vector<String> colNames;


    private HighscoreDB db;

    static {

        colNames = new Vector<String>();
        colNames.add("Name");
        colNames.add("Score");
    }

    public HighscoreDialog(Frame parent, int currentGameMode) {
        super(parent);

        db = new HighscoreDB();

        initialize(currentGameMode);
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    private JTable createScoreTable(int difficulty) {

        JTable t = new JTable(db.getScores(difficulty), colNames);
        t.setEnabled(false);

        return t;
    }

    private JScrollPane createScoreContainer(int difficulty) {

        JTable t = createScoreTable(difficulty);

        JScrollPane scroll = new JScrollPane(t);
        t.setFillsViewportHeight(true);

        return scroll;
    }

    private void initialize(int gameMode) {

        JTabbedPane tabPane = new JTabbedPane();

        JPanel easy = new JPanel(new BorderLayout());
        easy.add(createScoreContainer(Game.EASY), BorderLayout.CENTER);

        JPanel med = new JPanel(new BorderLayout());
        med.add(createScoreContainer(Game.MEDIUM), BorderLayout.CENTER);

        JPanel hard = new JPanel(new BorderLayout());
        hard.add(createScoreContainer(Game.HARD), BorderLayout.CENTER);

        tabPane.addTab("Easy", easy);
        tabPane.addTab("Medium", med);
        tabPane.addTab("Hard", hard);

        if (gameMode == Game.EASY)
            tabPane.setSelectedIndex(0);
        else if (gameMode == Game.MEDIUM)
            tabPane.setSelectedIndex(1);
        else if (gameMode == Game.HARD)
            tabPane.setSelectedIndex(2);

        add(tabPane);

        pack();

        setTitle("Highscores");
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setVisible(true);
    }
}
