import java.sql.*;

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

    public static void main(String[] args) {

        HighscoreDB db = new HighscoreDB();
        /*
        db.addScore("Chathan", 33, 2);
        db.addScore("Dad", 22, 2);
        db.addScore("Noah", 25, 2);
        */

        ResultSet rs = db.getScores(1);

        if (rs != null) {

            System.out.println("Scores for difficulty 1:");

            try {
                while (rs.next())
                    System.out.format("\t%s: %d%n", rs.getString("name"), rs.getInt("score"));

            } catch (SQLException e) {

                System.out.println("Error reading results.");
            }
        }
    }

    public boolean addScore(String name, int score, int difficulty) {

        try {

            PreparedStatement prep = conn.prepareStatement(String.format("INSERT INTO %s VALUES (?, ?, ?);", SCORE_TABLE));

            prep.setString(1, name);
            prep.setInt(2, score);
            prep.setInt(3, difficulty);
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public ResultSet getScores(int difficulty) {

        try {

            Statement stat = conn.createStatement();
            return stat.executeQuery(String.format("SELECT * FROM %s WHERE difficulty = %d ORDER BY score DESC",
                    SCORE_TABLE, difficulty));

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
