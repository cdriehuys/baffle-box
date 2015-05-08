import javax.swing.*;

public class BaffleBoxMain extends JFrame {

    public BaffleBoxMain() {

        setTitle("Baffle Box");
        setSize(640, 480);
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
