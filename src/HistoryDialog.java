import javax.swing.*;
import java.awt.*;

public class HistoryDialog extends JDialog {

    private String log;
    private JTextArea history;

    HistoryDialog(Frame parent) {
        super(parent);

        log = "";

        createElements();
    }
    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    public void logMove(String move) {
        log += move + "\n";
        history.setText(log);
    }

    private void createElements() {

        history = new JTextArea(20, 30);
        history.setEditable(false);

        JScrollPane scroll = new JScrollPane(history);
        scroll.setBorder(BorderFactory.createTitledBorder("History:"));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll);

        pack();

        setTitle("History");
        setLocation(new Point(0, 0));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
