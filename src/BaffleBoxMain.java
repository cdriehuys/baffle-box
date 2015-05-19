import javax.swing.*;

public class BaffleBoxMain extends JFrame {

    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 480;


    /** Default constructor **/
    public BaffleBoxMain() {

        // create a new game with the specified dimensions
        Game game = new Game(GAME_WIDTH, GAME_HEIGHT);

        // add the menu bar
        setJMenuBar(game.createMenubar());

        // add the game to the frame
        add(game);

        // pack the frame so that the contents fit
        pack();

        // setup the frame
        setTitle("Baffle Box");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Main method
     * @param args command line args
     */
    public static void main(String[] args) {

        // create new window in a thread-safe manner
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BaffleBoxMain();
            }
        });
    }
}
