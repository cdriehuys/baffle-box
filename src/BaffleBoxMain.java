import javax.swing.*;

public class BaffleBoxMain extends JFrame {

    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 480;

    public BaffleBoxMain() {

        Game game = new Game(GAME_WIDTH, GAME_HEIGHT);

        add(game);

        pack();

        setTitle("Baffle Box");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BaffleBoxMain();
            }
        });
    }
}
