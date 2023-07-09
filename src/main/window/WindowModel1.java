package main.window;

public class WindowModel1 implements WindowModel{

    /**
     * Model variables.
     */
    private String tickerSymbols, targDir;

    public static final String INITIAL_STRING1 = "Enter your list of ticker symbols";
    public static final String INITIAL_STRING2 = "Enter your target directory";

    /**
     * No argument constructor.
     */
    public WindowModel1() {
        this.tickerSymbols = INITIAL_STRING1;
        this.targDir = INITIAL_STRING2;
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
