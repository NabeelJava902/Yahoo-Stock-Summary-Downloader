package main.yahoo_downloader_engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static main.yahoo_downloader_engine.Utils.parseData;

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
}
