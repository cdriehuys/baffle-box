import javax.swing.*;
import java.awt.*;

public class HistoryDialog extends JDialog {

    private String log;
    private JTextArea history;

    /**
     * Create a new <code>HistoryDialog</code>.
     * @param parent the dialog's parent <code>Frame</code>
     */
    HistoryDialog(Frame parent) {
        super(parent);

        // create empty log
        log = "";

        // initialize UI
        createElements();
    }
    /************************** Overridden Methods **************************/

    /****************************** Accessors *******************************/

    /****************************** Mutators ********************************/

    /**************************** Other Methods *****************************/

    /**
     * Log the given move
     * @param move the move to log
     */
    public void logMove(String move) {

        // add the move to the log and update the display
        log += move + "\n";
        history.setText(log);
    }

    /**
     * Initializes the <code>HistoryDialog</code>'s UI.
     */
    private void createElements() {

        // read-only text area to display history
        history = new JTextArea(20, 30);
        history.setEditable(false);

        // make text area scrollable
        JScrollPane scroll = new JScrollPane(history);
        scroll.setBorder(BorderFactory.createTitledBorder("History:"));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // add scroll pane
        add(scroll);

        // fit window to components
        pack();

        // setup window
        setTitle("History");
        setLocation(new Point(0, 0));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
