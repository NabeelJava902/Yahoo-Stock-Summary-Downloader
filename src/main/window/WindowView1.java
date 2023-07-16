package main.window;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import static main.window.WindowModel1.INITIAL_STRING1;

/**
 * View class.
 *
 * @author Nabeel Arif
 */
public final class WindowView1 extends JFrame implements WindowView {

    /**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private WindowController controller;

    /**
     *
     * State of user interaction: last event "seen".
     */
    private enum State {SAW_COPY, SAW_FILE, SAW_NOTHING}

    /**
     * State variable to keep track of which event happened last.
     */
    private State currentState = State.SAW_NOTHING;

    /**
     * Text areas.
     */
    private final JTextArea pasteField;
    public static JTextArea targDirDisplay;

    /**
     * Buttons.
     */
    private final JButton bCopy;
    private final JButton bFile;

    /**
     * Useful constants.
     */
    private static final int TEXT_AREA_HEIGHT = 20, TEXT_AREA_WIDTH = 20;

    public WindowView1(){
        super("Yahoo Stock");

        // Set up the GUI widgets --------------------------

        this.pasteField = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        targDirDisplay = new JTextArea("", TEXT_AREA_HEIGHT/10, TEXT_AREA_WIDTH);
        this.bCopy = new JButton("Download");
        this.bFile = new JButton("File");
        //--------------------------------------------------

        this.pasteField.setEditable(true);
        this.pasteField.setLineWrap(true);
        this.pasteField.setWrapStyleWord(true);
        pasteField.getDocument().addDocumentListener(new MyDocListener());
        targDirDisplay.setEditable(false);
        targDirDisplay.setLineWrap(true);
        targDirDisplay.setWrapStyleWord(true);

        // Initially, the copy button should not be enabled because the paste field will be empty
        this.bCopy.setEnabled(false);

        JScrollPane pasteFieldScroll = new JScrollPane(this.pasteField);

        // Create a button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(bCopy);

        // Create a text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        textPanel.add(pasteFieldScroll);

        //Create directory choosing bar
        JPanel dirPanel = new JPanel();
        dirPanel.setLayout(new FlowLayout());
        dirPanel.add(targDirDisplay);
        dirPanel.add(bFile);
        textPanel.add(dirPanel);

        // Organize main main.window
        this.setLayout(new FlowLayout());

        // Add scroll panes and button panel to main main.window
        this.add(textPanel);
        this.add(buttonPanel);

        // Set up the observers ------------------------------

        this.bCopy.addActionListener(this);
        this.bFile.addActionListener(this);

        //----------------------------------------------------

        // Set up the main application main.window
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    @Override
    public void registerObserver(WindowController controller) {
        this.controller = controller;
    }

    @Override
    public void updatePasteFieldDisplay(String s) {
        this.pasteField.setText(s);
    }

    @Override
    public void updateTargDirDisplay(String s) {
        targDirDisplay.setText(s);
    }

    @Override
    public void updateCopyAllowed(boolean allowed) {
        this.bCopy.setEnabled(allowed);
    }

    @Override
    public String getPasteFieldText() {
        return this.pasteField.getText();
    }

    @Override
    public String getTargDirDisplayText() {
        return targDirDisplay.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */
        Object source = e.getSource();
        if(source == this.bCopy){
            this.controller.processCopyEvent();
            this.currentState = State.SAW_COPY;
        }else if(source == this.bFile){
            this.controller.processFileChooseEvent();
            this.currentState = State.SAW_FILE;
        }
    }

    class MyDocListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateCopyAllowed(!pasteField.getText().isEmpty()
                                && !pasteField.getText().equals(INITIAL_STRING1));
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateCopyAllowed(!pasteField.getText().isEmpty()
                    && !pasteField.getText().equals(INITIAL_STRING1));
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateCopyAllowed(!pasteField.getText().isEmpty()
                    && !pasteField.getText().equals(INITIAL_STRING1));
        }
    }
}