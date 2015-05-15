import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class HighscoreDB {

    private static final String SCORE_TABLE = "scores";


    private Connection conn;


    /** Default constructor **/
    public HighscoreDB() {

        // connect to the database
        createConnection();
        // initialize the score table
        initializeScoreTable();
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    /**
     * Add a score to the database.
     * @param name the person who got the score
     * @param score the score value
     * @param difficulty the game difficulty level. Must be one of <code>Game.EASY</code>, <code>Game.MEDIUM</code>,
     *        or <code>Game.HARD</code>.
     * @return <code>true</code> if the score was successfully added, <code>false</code> otherwise.
     */
    public boolean addScore(String name, int score, int difficulty) {

        try {

            // prepare an sql statement to insert the score
            PreparedStatement prep = conn.prepareStatement(String.format("INSERT INTO %s VALUES (?, ?, ?);", SCORE_TABLE));

            // bind the correct values
            prep.setString(1, name);
            prep.setInt(2, score);
            prep.setInt(3, difficulty);
            prep.addBatch();

            // execute the statement
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            return true;

        } catch (SQLException e) {

            // failed to add score
            return false;
        }
    }

    /**
     * Gets a list of scores obtained on the specified difficulty.
     * @param difficulty the game difficulty level. Must be one of <code>Game.EASY</code>, <code>Game.MEDIUM</code>,
     *        or <code>Game.HARD</code>.
     * @return a <code>Vector</code> of <code>Vector</code>s of <code>Object</code>s
     */
    public Vector<Vector<Object>> getScores(int difficulty) {

        try {

            // create and execute a statement to find a list of scores matching the given difficulty, sorted in
            // descending order by score.
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(String.format("SELECT * FROM %s WHERE difficulty = %d ORDER BY score DESC",
                    SCORE_TABLE, difficulty));

            // create the vector to store the scores
            Vector<Vector<Object>> scores = new Vector<Vector<Object>>();

            // loop through all the results and add them to the scores vector
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("name"));
                row.add(rs.getInt("score"));
                scores.add(row);
            }

            // return the vector
            return scores;

        } catch (SQLException e) {

            // error getting the scores, return null
            return null;
        }
    }

    /**
     * Create a connection to the scores db.
     */
    private void createConnection() {

        try {

            // attempt to load the sqlite manager class
            Class.forName("org.sqlite.JDBC");
            // create the connection
            conn = DriverManager.getConnection("jdbc:sqlite:scores.db");

        } catch (ClassNotFoundException e) {

            // couldn't find the driver for sqlite
            JOptionPane.showMessageDialog(null, "Failed to find sqlite driver. Cannot display highscores", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {

            // couldn't connect to database
            JOptionPane.showMessageDialog(null, "Failed to connect to score database. Cannot display highscores", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Create the scores table if it doesn't exist.
     */
    private void initializeScoreTable() {

        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS " + SCORE_TABLE + " (name TEXT, score INTEGER, difficulty INTEGER);");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Could not create score table.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
