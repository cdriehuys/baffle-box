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

    public HighscoreDialog(Frame parent) {
        super(parent);

        db = new HighscoreDB();

        initialize();
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    private JTable createScoreTable(int difficulty) {
        Vector<Vector<Object>> scores = db.getScores(difficulty);

        for (Vector<Object> row : scores) {
            for (Object o : row)
                System.out.print(o + " ");
            System.out.println();
        }
        JTable t = new JTable(scores, colNames);
        System.out.format("Rows: %d, Columns: %d%n", t.getRowCount(), t.getColumnCount());

        return t;
    }

    private JScrollPane createScoreContainer(int difficulty) {

        JTable t = createScoreTable(difficulty);

        JScrollPane scroll = new JScrollPane(t);
        t.setFillsViewportHeight(true);

        return scroll;
    }

    private void initialize() {

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
        tabPane.setSelectedIndex(1);

        add(tabPane);

        setSize(600, 600);

        setTitle("Highscores");
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
