import java.sql.*;
import java.util.Vector;

public class HighscoreDB {

    private static final String SCORE_TABLE = "scores";


    private Connection conn;

    public HighscoreDB() {

        createConnection();
        initializeScoreTable();
    }

    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public boolean addScore(String name, int score, int difficulty) {

        try {

            System.out.print("Adding score...");
            PreparedStatement prep = conn.prepareStatement(String.format("INSERT INTO %s VALUES (?, ?, ?);", SCORE_TABLE));

            prep.setString(1, name);
            prep.setInt(2, score);
            prep.setInt(3, difficulty);
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            System.out.println("Success");
            return true;

        } catch (SQLException e) {
            System.out.println("Failed to add score");
            return false;
        }
    }

    public Vector<Vector<Object>> getScores(int difficulty) {

        try {

            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(String.format("SELECT * FROM %s WHERE difficulty = %d ORDER BY score DESC",
                    SCORE_TABLE, difficulty));

            Vector<Vector<Object>> scores = new Vector<Vector<Object>>();

            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("name"));
                row.add(rs.getInt("score"));
                scores.add(row);
            }

            return scores;

        } catch (SQLException e) {
            return null;
        }
    }

    private void createConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:scores.db");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find 'org.sqlite.JDBC' class.");
        } catch (SQLException e) {
            System.out.println("Unable to create connection to database.");
        }
    }

    private void initializeScoreTable() {

        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS " + SCORE_TABLE + " (name TEXT, score INTEGER, difficulty INTEGER);");
        } catch (SQLException e) {
            System.out.println("Failed to create score table");
        }
    }
}
