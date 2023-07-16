package main.window;

import main.yahoo_downloader_engine.TickerProcessor;

import javax.swing.*;
import java.io.File;

/**
 * Controller class
 *
 * @author Nabeel Arif
 */
public class WindowController1 implements WindowController{

    /**
     * Model object.
     */
    private final WindowModel model;

    /**
     * View object
     */
    private final WindowView view;

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *      the model
     * @param view
     *      the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToModel(WindowModel model,
                                          WindowView view){
        view.updatePasteFieldDisplay(model.tickerSymbols());
        view.updateTargDirDisplay(model.targDir());
        updateAllowedButtons(view, model.tickerSymbols());
    }

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *      the model
     * @param view
     *      the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateModelToView(WindowModel model,
                                          WindowView view){
        model.setTickerSymbols(view.getPasteFieldText());
        model.setTargDir(view.getTargDirDisplayText());
        updateAllowedButtons(view, model.tickerSymbols());
    }

    /**
     * Constructor.
     *
     * @param model
     *          model to connect to
     * @param view
     *          view to connect to
     */
    public WindowController1(WindowModel model, WindowView view) {
        this.model = model;
        this.view = view;
        updateViewToModel(model, view);
    }

    @Override
    public void processCopyEvent() {
        // Process the list of ticker symbols
        updateModelToView(model, view);
        long startTime = System.nanoTime();

        TickerProcessor tp = new TickerProcessor(this.model.tickerSymbols());
        tp.run();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        double seconds = (double) duration / 1_000_000_000.0;

        System.out.println("All symbols fetched... " + seconds + " seconds");
    }

    @Override
    public void processFileChooseEvent() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(null);
        File f = fileChooser.getSelectedFile();
        String file = f.getAbsolutePath();
        this.view.updateTargDirDisplay(file);
        updateModelToView(this.model, this.view);
    }

    /**
     * Disables or enables buttons based on what is inputted by the user
     *
     * @param view
     *          the view object tied to the main.window
     * @param tickerSymbols
     *          the list of ticker symbols inputted by user
     * @updates this.view
     * @ensures copy button is enabled if there is at least one
     *          ticker symbol inputted
     */
    public static void updateAllowedButtons(WindowView view,
                                            String tickerSymbols){
        view.updateCopyAllowed(!tickerSymbols.isBlank());
    }
}
