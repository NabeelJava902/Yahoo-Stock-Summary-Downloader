package main.window;

/**
 * Model class
 *
 * @author Nabeel Arif
 */
public class WindowModel1 implements WindowModel{

    /**
     * Model variables.
     */
    private String tickerSymbols, targDir;

    public static final String INITIAL_STRING1 = "Enter your list of ticker symbols";

    /**
     * No argument constructor.
     */
    public WindowModel1() {
        this.tickerSymbols = INITIAL_STRING1;
    }

    @Override
    public String tickerSymbols() {
        return this.tickerSymbols;
    }

    @Override
    public String targDir() {
        return this.targDir;
    }

    @Override
    public void setTickerSymbols(String tickerSymbols){
        this.tickerSymbols = tickerSymbols;
    }

    @Override
    public void setTargDir(String targDir){
        this.targDir = targDir;
    }
}
