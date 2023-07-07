package main.window;

/**
 * Controller interface
 *
 * @author Nabeel Arif
 *
 * @mathmodel <pre>
 * type WindowController is modeled by
 *  (model: WindowModel,
 *      view: WindowView)
 * </pre>
 * @initially <pre>
 *     (WindowModel model, WindowView view):
 *          ensures
 *              this.model = model and
 *              this.view = view
 * </pre>
 */
public interface WindowController {

    /**
     * Processes event to read the given ticker symbols and upload
     * relevant data to a spreadsheet file
     *
     * @updates this.model, this.view
     * @clears
     * this.model.pasteField
     * [this.view has been updated to match this.model]
     */
    void processCopyEvent();
}
