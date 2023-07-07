package test;

import main.yahoo_downloader_engine.TickerProcessor;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

/**
 * Test class for the TickerProcessor class.
 *
 * @author Nabeel Arif
 */
public class TickerProcessorTest {

    /**
     * Routine test for processString() method.
     */
    @Test
    public void processStringRoutineTest(){
        String stringToProcess = "Hello World";

        StringBuilder sb = new StringBuilder();
        ArrayList<String> test = new ArrayList<>();
        TickerProcessor.processString(stringToProcess, test, sb);

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Hello");
        expected.add("World");

        assertEquals(expected, test);
    }

    /**
     * Boundary test for processString() method.
     */
    @Test
    public void processStringBoundaryTest(){
        String stringToProcess = "   ";

        ArrayList<String> test = new ArrayList<>();
        TickerProcessor.processString(stringToProcess, test, new StringBuilder());

        ArrayList<String> expected = new ArrayList<>();

        assertEquals(expected, test);
    }
}
