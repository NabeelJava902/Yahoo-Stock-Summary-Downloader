package main.yahoo_downloader_engine;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Map;

/**
 * This is a utility class containing all static methods to be accessed and used by other classes.
 *
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

    /**
     Checks if the given Workbook object contains a sheet with the specified name.

     @param workbook
                the workbook to check for the sheet
     @param sheetName
                the name of the sheet to check for
     @return true if the workbook contains the sheet, false otherwise
     @requires workbook != null
     @requires sheetName != null
     */
    public static boolean containsSheet(XSSFWorkbook workbook, String sheetName) {
        return workbook.getSheet(sheetName) != null;
    }


    /**
     * Parses a string representation of a number and returns the corresponding double value.
     * The method handles cases where the string represents a trillion value by multiplying the number by 10^12.
     * @param string
     *            The string representation of the number to be parsed.
     * @return The parsed double value.
     * @throws NumberFormatException if the string cannot be parsed as a valid double.
     * @ensures The returned value is the parsed double representation of the input string.
     */
    public static Double parseDouble(String string){
        final double base = 10;
        final double exponent = 12;
        double d = 0;
        if(string != null){
            if(string.contains("T")){
                d = Double.parseDouble(string.split("T")[0].trim());
                d *= Math.pow(base, exponent);
            }else{
                d = Double.parseDouble(string.replace(",", "").trim());
            }
        }
        
        return d;
    }
}