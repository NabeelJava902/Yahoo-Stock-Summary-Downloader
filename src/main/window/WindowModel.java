package main.window;

/**
 * Model interface
 * <p>
 * The Window Model consists of the collection of ticker symbols and the target
 * directory.
 *
 * @author Nabeel Arif
 */
public interface WindowModel {

    /**
     * Reports the inputted ticker symbols.
     *
     * @return this.tickerSymbols
     * @ensures tickerSymbols = this.tickerSymbols
     */
    String tickerSymbols();

    /**
     * Reports inputted target directory for data dump
     *
     * @return this.targDir
     * @ensures targDir = this.targDir
     */
    String targDir();

    /**
     * Setter method for the ticker symbols variable
     * @param tickerSymbols
     *          variable to be copied
     */
    public void setTickerSymbols(String tickerSymbols);

    /**
     * Setter method for the target directory variable
     * @param targDir
     *          variable to be copied
     */
    public void setTargDir(String targDir);
}
