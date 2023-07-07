package main.yahoo_downloader_engine;

import java.util.HashMap;

/**
 * Data Retriever Class
 *
 * @author Nabeel Arif
 *
 * This class takes in a single ticker and retrieves the desired data on it using yahoo's api
 */

public class DataRetriever {
    /**
     * Takes in a single ticker string and returns a hashmap containing the desired data for that stock ticker
     *
     * @param ticker
     *          the given stock ticker symbol
     * @return data
     * @ensures data = [dataset of all desired data for the specific stock]
     */
    public HashMap<String, String> retrieve(String ticker){
        // Hashmap object initialized to store the incoming data from yahoo
        HashMap<String, String> data = new HashMap<>();



        return data;
    }
}
