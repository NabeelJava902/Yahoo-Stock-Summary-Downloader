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
    private enum State {SAW_COPY, SAW_NOTHING}

    /**
     * State variable to keep track of which event happened last.
     */
    private State currentState = State.SAW_NOTHING;

    /**
     * Text areas.
     */
    private final JTextArea pasteField, targDirDisplay;

    /**
     * Buttons.
     */
    private final JButton bCopy;

    /**
     * Useful constants.
     */
    private static final int TEXT_AREA_HEIGHT = 20, TEXT_AREA_WIDTH = 20;

    public WindowView1(){
        super("Yahoo Stock");

        // Set up the GUI widgets --------------------------

        this.pasteField = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.targDirDisplay = new JTextArea("", TEXT_AREA_HEIGHT/10, TEXT_AREA_WIDTH);
        this.bCopy = new JButton("Copy");
        //--------------------------------------------------

        this.pasteField.setEditable(true);
        this.pasteField.setLineWrap(true);
        this.pasteField.setWrapStyleWord(true);
        pasteField.getDocument().addDocumentListener(new MyDocListener());
        this.targDirDisplay.setEditable(true);
        this.targDirDisplay.setLineWrap(true);
        this.targDirDisplay.setWrapStyleWord(true);

        // Initially, the copy button should not be enabled because the paste field will be empty
        this.bCopy.setEnabled(false);

        /**
         * Create scroll panes for the paste field in case the list is long enough
         * where it needs scrolling
         */
        JScrollPane pasteFieldScroll = new JScrollPane(this.pasteField);

        // Create a button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(bCopy);

        // Create a text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        textPanel.add(pasteFieldScroll);
        textPanel.add(targDirDisplay);

        // Organize main main.window
        this.setLayout(new FlowLayout());

        // Add scroll panes and button panel to main main.window
        this.add(textPanel);
        this.add(buttonPanel);

        // Set up the observers ------------------------------

        /**
         * Register this class as the observer for all GUI events
         */
        this.bCopy.addActionListener(this);
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
        this.targDirDisplay.setText(s);
    }

    @Override
    public void updateCopyAllowed(boolean allowed) {
        this.bCopy.setEnabled(allowed);
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