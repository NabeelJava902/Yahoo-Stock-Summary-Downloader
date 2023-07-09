package test;

import main.yahoo_downloader_engine.DataPoint;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class for the DataPoint class
 *
 * @author Nabeel Arif
 */
public class DataPointTest {

    /**
     * Routine test for assignVars method
     */
    @Test
    public void assignVarsRoutineTest(){
        String test = "Hello 10";
        DataPoint dp = new DataPoint(test);

        String expectedName = "Hello";
        long expectedStat = 10;

        assertEquals(expectedName, dp.getName());
        assertEquals(expectedStat, dp.getStatistic());
    }
}
