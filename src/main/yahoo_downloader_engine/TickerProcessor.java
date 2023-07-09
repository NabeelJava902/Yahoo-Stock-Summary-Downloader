package main.yahoo_downloader_engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.yahoo_downloader_engine.Utils.processString;

/**
 * Ticker Processor Class
 *
 * @author Nabeel Arif
 *
 * This class takes in ticker data and runs various functions to retrieve and upload relevant data to a spreadsheet.
 */

public class TickerProcessor {

    /**
     * Variables for the list of the stock ticker symbols and the target directory to upload data to
     */
    private final String tickerSymbols;
    private String targDir;

    /**
     * Constructor
     */
    public TickerProcessor(String tickerSymbols, String targDir){
        this.tickerSymbols = tickerSymbols;
        this.targDir = targDir;
    }

    /**
     * This method reads the ticker symbols and uploads data onto a spreadsheet after scraping the data off of the
     * yahoo website. run() processes the tickers that were inputted and retrieves a hashmap containing data on the current ticker.
     * After retrieving the data hashmap, the contents are then stored onto a spreadsheet.
     */
    public void run(){
        ArrayList<String> tickerList = new ArrayList<>();
        processString(this.tickerSymbols, tickerList, new StringBuilder());
        // Now tickerList contains all the individual ticker symbols which can now be iterated through

        HashMap<String, String> data = new HashMap<>();

        for(String ticker : tickerList){
            DataScraper.scrape(ticker, data);

            // TODO upload data

            data = new HashMap<>();
        }
    }
}