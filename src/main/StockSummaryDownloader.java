package main;

import main.window.*;

/**
 * A Yahoo Stock Summary Downloader application
 *
 * This is a program that takes in a list of stock ticker symbols
 * and retrieves various data points from each stock ticker symbol given.
 * The program then uploads the data onto a spreadsheet.
 *
 * @author Nabeel Arif
 */
public final class StockSummaryDownloader {

    /**
     * No argument private constructor so this utility class cannot be
     * instantiated.
     */
    private StockSummaryDownloader(){}

    /**
     * Main program that sets up main application main.window and starts user
     * interaction.
     *
     * @param args
     *            command-line arguments; not used
     */
    public static void main(String[] args) {
        /*
         * Create instances of the model, view, and controller objects;
         * controller needs to know about model and view, and view needs to know
         * about controller
         */
        WindowModel model = new WindowModel1();
        WindowView view = new WindowView1();
        WindowController controller = new WindowController1(model, view);

        view.registerObserver(controller);
    }
}