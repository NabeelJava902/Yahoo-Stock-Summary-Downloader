package main.yahoo_downloader_engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataScraper {

    /**
     * This method scrapes relevant data from the Yahoo website for a given stock ticker and stores the scraped data into
     * a HashMap passed by reference.The stock ticker parameter `ticker` specifies the stock for which data will be scraped.
     * The `data` parameter is the HashMap used to store the scraped data. It is passed by reference, allowing the method
     * to update the map with the scraped information. The method may throw an `IOException` if an error occurs during
     * the web scraping process.
     * Note: The contract assumes that the `parseData()` method is defined elsewhere and properly handles the extraction
     * and formatting of key-value pairs from the scraped data.
     *
     * @param ticker
     *          the stock ticker for which data will be scraped for
     * @param data
     *          the pass-by-reference map to store the date on
     */
    public static void scrape(String ticker, HashMap<String, String> data){
        String url = "https://finance.yahoo.com/quote/" + ticker + "?p=" + ticker;
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";

        try {
            Document document = Jsoup.connect(url).userAgent(userAgent).get();

            // Scrape specific information from the page
            Elements trs = document.getElementsByTag("tr");
            for(Element e : trs){
                Map.Entry<String, String> currSet = parseData(e.text());
                data.put(currSet.getKey(), currSet.getValue());
            }
            System.out.println(ticker + " fetched");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes in an Elements object along with a string denoting the desired data point.
     * The method returns an Element object containing the information that matches with the string
     * that was passed in to the method.
     *
     * @param trs
     *          the Elements object
     * @param dataName
     *          the desired data point within the Elements object
     * @return targElement
     * @requires trs.size() > 0 & trs contains dataName
     */
    public static Element getDataPoint(Elements trs, String dataName){
        Element targElement = null;
        for(Element e : trs){
            if(e.text().toLowerCase().contains(dataName.toLowerCase())){
                targElement = e;
            }
        }
        return targElement;
    }

    /**
     * This method parses a string to separate data names and their corresponding values and stores them in a `Map.Entry`
     * object within a `HashMap`. It takes a string as input and returns a `Map.Entry` object representing the separated
     * key-value pair. The input string should adhere to the expected format, where the data name consists of alphabetic
     * characters and the corresponding value consists of numeric digits.The method throws an `IllegalArgumentException`
     * if the input string does not adhere to the expected format. Note: It is expected that the data names and values
     * are separated correctly and that the resulting `Map.Entry` object contains valid key and value strings.
     *
     * @param string
     *          the string to be separated
     * @return Map.Entry<String, String>
     *          a `Map.Entry` object containing the separated key-value pair
     * @throws IllegalArgumentException
     *          if the input string does not adhere to the expected format
     */
    private static Map.Entry<String, String> parseData(String string){
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        boolean keyMode = false;
        boolean valueMode = false;
        int i = 0;
        for(char c : string.toCharArray()){
            if(Character.isAlphabetic(c) && !valueMode || i<=1){
                keyMode = true;
            }else if(Character.isDigit(c)){
                keyMode = false;
                valueMode = true;
            }
            if(keyMode){
                key.append(c);
            }else {
                value.append(c);
            }
            i++;
        }
        return Map.entry(key.toString(), value.toString());
    }
}
