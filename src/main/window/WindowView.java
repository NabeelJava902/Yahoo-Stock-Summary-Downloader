package main.window;

import java.awt.event.ActionListener;

/**
 * View interface.
 *
 * @author Nabeel Arif
 */
public interface WindowView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     *
     * @param controller
     *          controller to register.
     */
    void registerObserver(WindowController controller);

    /**
     * Updates the paste field display based on the argument given.
     * @param s
     *          new value of paste field display text.
     */
    void updatePasteFieldDisplay(String s);

    /**
     * Updates the target directory display based on the argument given.
     * @param s
     *          the new value of the target directory display text.
     */
    void updateTargDirDisplay(String s);

    /**
     * Updates display of whether copy operation is allowed.
     *
     * @param allowed
     *            true iff copy is allowed
     */
    void updateCopyAllowed(boolean allowed);
}
