package test;

import main.yahoo_downloader_engine.DataScraper;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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
}
