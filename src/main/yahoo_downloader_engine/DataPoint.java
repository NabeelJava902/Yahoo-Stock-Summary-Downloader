package main.yahoo_downloader_engine;

/**
 * Data Point Class
 *
 * @author Nabeel Arif
 * The DataPoint class represents a data point that encapsulates a statistic value and its corresponding name.
 * The class provides methods to retrieve the statistic value and name.
 */
public class DataPoint {

    private long statistic;
    private String name;

    /**
     * Initializes a DataPoint object with a string containing the name and statistic value separated by a space.
     * The assignVars method is called internally to assign the values.
     * @param string
     *          the string to be processed
     */
    public DataPoint(String string) {
        assignVars(string);
    }

    /**
     * Returns the statistic value of the data point.
     * @return statistic
     */
    public long getStatistic() {
        return statistic;
    }

    /**
     * Returns the name of the data point.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Parses the input string to extract the name and statistic value.
     * The string is split by a space character, and the first element is assigned as the name,
     * while the second element is parsed and assigned as the statistic value.
     * @param string
     *          the string to be parsed
     * @updates this.name & this.statistic
     * @requires string = [a string starting with a word, following a space, following a number]
     */
    private void assignVars(String string){
        String[] words = string.split(" ");
        this.name = words[0];
        this.statistic = Long.parseLong(words[1]);
    }
}
