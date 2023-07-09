package main.yahoo_downloader_engine;

import java.util.ArrayList;

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
    private String tickerSymbols;
    private String targDir;

    /**
     * Constructor
     */
    public TickerProcessor(String tickerSymbols, String targDir){
        this.tickerSymbols = tickerSymbols;
        this.targDir = targDir;
    }

    /**
     * This method passes information to a data retriever object and then uploads the data to a spreadsheet
     * using an uploader object.
     */
    public void run(){
        ArrayList<String> tickerList = new ArrayList<>();
        processString(this.tickerSymbols, tickerList, new StringBuilder());
        // Now tickerList contains all the individual ticker symbols which can now be iterated through

        for(String ticker : tickerList){

        }
    }

    /**
     * Utility method to extract each individual word from a string and store them individually into an
     * array list.
     *
     * @param s
     *          string to process
     * @param stringList
     *          pass-by-reference object to store individual strings on
     * @param currString
     *          the current string being built
     * @updates stringList
     * @ensures stringList = [collection of all words in the given string]
     */
    public static void processString(String s, ArrayList<String> stringList, StringBuilder currString){
        if(s.isEmpty()){
            if(!currString.toString().isBlank()){
                stringList.add(currString.toString().trim());
            }
        }else {
            char c = s.charAt(0);

            if (c != ' ') {
                currString.append(c);
            } else {
                if(!currString.toString().isBlank()){
                    stringList.add(currString.toString().trim());
                }
                currString = new StringBuilder();
            }

            processString(s.substring(1), stringList, currString);
        }
    }
}
