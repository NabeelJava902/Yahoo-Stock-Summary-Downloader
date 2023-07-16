package test;

import main.yahoo_downloader_engine.Uploader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Test class for the Uploader class.
 *
 * @author Nabeel Arif
 */
public class UploaderTest {

    /**
     * Test for fetchFile() method
     */
    @Test
    public void fetchFileTest(){
        try {
            Uploader.fetchFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test for upload() method
     */
    @Test
    public void uploadTest(){
        String ticker = "AAPL";
        HashMap<String, String> data = new HashMap<>();
        data.put("Volume", "10");
        data.put("Avg. Volume", "9");

        Uploader.upload(ticker, data, 0);
    }
}
