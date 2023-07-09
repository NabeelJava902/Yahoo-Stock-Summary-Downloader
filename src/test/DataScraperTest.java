package test;

import main.yahoo_downloader_engine.DataScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Test class for the DataScraper class.
 *
 * @author Nabeel Arif
 */
public class DataScraperTest {

    /**
     * Routine test for scrape() method.
     */
    @Test
    public void scrapeRoutineTest(){
        HashMap<String, String> data = new HashMap<>();
        DataScraper.scrape("AAPL", data);
        for(Map.Entry<String, String> set : data.entrySet()){
            System.out.println("The Key: " + set.getKey() + " The Value: " + set.getValue());
        }
    }

    /**
     * Routine test for the getDataPoint() method
     */
    @Test
    public void getDataPointRoutineTest(){
        String ticker = "AAPL";
        String url = "https://finance.yahoo.com/quote/" + ticker + "?p=" + ticker;
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/39.0.2171.95 Safari/537.36";

        try {
            Document document = Jsoup.connect(url).userAgent(userAgent).get();

            // Scrape specific information from the page
            Elements trs = document.getElementsByTag("tr");
            Element e = DataScraper.getDataPoint(trs, "Volume");

            String expected = "Volume 44,665,545";
            assertEquals(expected, e.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
