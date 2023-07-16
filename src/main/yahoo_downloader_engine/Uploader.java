package main.yahoo_downloader_engine;

import main.window.WindowView1;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;

import static main.yahoo_downloader_engine.Utils.containsSheet;

/**
 * Uploader class
 * @author  Nabeel Arif
 * <p>
 * The Uploader class manages storing ticker data on an Excel sheet. It offers methods for uploading data,
 * formatting the sheet, fetching the file output stream, and retrieving the workbook. It simplifies the
 * process of storing ticker data by handling key operations.
 */

public class Uploader {

    /**
     * A string array containing all desired data points, changing the values in this
     * array will change what is written to the Excel sheet
     */
    private static final String[] DESIRED_DATA = {"52 Week Range", "Volume", "Avg. Volume", "Market Cap",
            "Forward Dividend & Yield"};

    // The file path for the Excel sheet
    private static String fileName;
    // The name of the Excel sheet
    private static final String header = "Stock Summary";

    /**
     * Uploads the data corresponding to a ticker symbol into an Excel file.
     *
     * @param ticker
     *          The current ticker symbol being processed.
     * @param data
     *          The data corresponding to the current ticker.
     * @param tickerCount
     *          The row number where the current ticker should be written to.
     * @requires ticker != null && data != null && tickerCount >= 0
     * @ensures The data is properly uploaded into an Excel file with the specified ticker and tickerCount.
     */
    public static void upload(String ticker, HashMap<String, String> data, int tickerCount){

        XSSFWorkbook workbook = fetchWorkbook();

        XSSFSheet sheet = (containsSheet(workbook, header)) ? workbook.getSheet(header):workbook.createSheet(header);
        formatSheet(sheet);

        int columnCount = 0;

        Row row = sheet.createRow(++tickerCount);
        Cell cell = row.createCell(columnCount++);
        cell.setCellValue(ticker);

        for(String s : DESIRED_DATA){
            cell = row.createCell(columnCount++);
            cell.setCellValue(data.get(s));
        }

        try {
            FileOutputStream os = fetchFile();
            workbook.write(os);
            os.close();
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Formats the Excel sheet by printing out various headers, making it ready to receive data.
     *
     * @param sheet
     *          The Excel sheet to be formatted.
     * @requires sheet != null
     * @ensures The sheet is properly formatted with the desired headers.
     */
    public static void formatSheet(XSSFSheet sheet){
        final int firstRow = 0;
        int columnCount = 1;
        Row row = sheet.createRow(firstRow);
        for(String s : DESIRED_DATA){
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(s);
        }
    }

    /**
     * Retrieves the output stream for the target file based on the user-selected directory.
     * The method constructs the file path by appending "/summary_data.xlsx" to the value of
     * the targDirDisplay field in the WindowView1 class.
     *
     * @return The FileOutputStream for the target file.
     * @throws IOException If an I/O error occurs while retrieving the file.
     * @requires this.fileName != null.
     * @ensures The returned FileOutputStream is ready to write data to the target file.
     */
    public static FileOutputStream fetchFile() throws IOException {
        File file = new File(fileName);
        return new FileOutputStream(file);
    }

    /**
     * Retrieves an existing XSSFWorkbook from the specified file or creates a new one if the file is not found.
     * The file path is obtained from the directory specified in the targDirDisplay field of WindowView1.
     *
     * @return XSSFWorkbook
     *              The retrieved XSSFWorkbook from the existing file or a newly created XSSFWorkbook if the file is not found.
     * @ensures The returned XSSFWorkbook is either an existing workbook from the specified file or a new workbook.
     */
    public static XSSFWorkbook fetchWorkbook(){
        XSSFWorkbook workbook;
        final String directory = WindowView1.targDirDisplay.getText();
        if(directory.isBlank() || directory.isEmpty()){
            fileName = "summary_data.xlsx";
        }else{
            fileName = WindowView1.targDirDisplay.getText() + "/summary_data.xlsx";
        }
        try {
            FileInputStream fis = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            workbook = new XSSFWorkbook();
        }
        return workbook;
    }
}