package test;

import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static main.yahoo_downloader_engine.Utils.processString;

/**
 * Test class for Utils class
 */
public class UtilsTest {

    /**
     * Routine test for processString() method.
     */
    @Test
    public void processStringRoutineTest(){
        String stringToProcess = "Hello World";

        StringBuilder sb = new StringBuilder();
        ArrayList<String> test = new ArrayList<>();
        processString(stringToProcess, test, sb);

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
        processString(stringToProcess, test, new StringBuilder());

        ArrayList<String> expected = new ArrayList<>();

        assertEquals(expected, test);
    }
}
