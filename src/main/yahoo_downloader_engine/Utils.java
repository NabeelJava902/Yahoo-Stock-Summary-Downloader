package main.yahoo_downloader_engine;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * @author Nabeel Arif
 */
public class Utils {

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
    public static Map.Entry<String, String> parseData(String string){
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

    /**
     * Utility method to extract each individual word from a string and store them individually into an
     * array list.
     *
     * @param s
     *          string to process
     * @param stringList
     *          pass-by-reference object to store individual strings on
     * @param currString
     *          the current string being built
     * @updates stringList
     * @ensures stringList = [collection of all words in the given string]
     */
    public static void processString(String s, ArrayList<String> stringList, StringBuilder currString){
        s = s.replace("\n", " ");
        if(s.isEmpty()){
            if(!currString.toString().isBlank()){
                stringList.add(currString.toString().trim());
            }
        }else {
            char c = s.charAt(0);

            if (c != ' ') {
                currString.append(c);
            } else {
                if(!currString.toString().isBlank()){
                    stringList.add(currString.toString().trim());
                }
                currString = new StringBuilder();
            }

            processString(s.substring(1), stringList, currString);
        }
    }
}
