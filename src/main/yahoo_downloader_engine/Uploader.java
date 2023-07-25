package main.yahoo_downloader_engine;

import main.window.WindowView1;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;

import static main.yahoo_downloader_engine.Utils.containsSheet;
import static main.yahoo_downloader_engine.Utils.parseDouble;

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
     * ALL POSSIBLE DATA POINTS (and example values):
     * Previous Close 191.81
     * Open 189.84
     * 52 Week Range 124.17 - 194.48
     * Volume 44,665,545
     * Avg. Volume 56,646,141
     * Market Cap 2.999T
     * Beta (5Y Monthly) 1.29
     * PE Ratio (TTM) 31.94
     * EPS (TTM) 5.97
     * Earnings Date Aug 03, 2023
     * Forward Dividend & Yield 0.96 (0.51%)
     * Ex-Dividend Date May 12, 2023
     * 1y Target Est 188.47
     */
    public static final String[] DATA_HEADERS = {"Previous Close", "Open", "52 Week Range", "Volume", "Avg. Volume",
                                                "Market Cap", "Beta (5Y Monthly)", "PE Ratio (TTM)", "EPS (TTM)",
                                                "Earnings Date", "Forward Dividend & Yield", "Ex-Dividend Date",
                                                "1y Target Est"};
    private static final String[] TARGET_DATA = {DATA_HEADERS[0], DATA_HEADERS[1], DATA_HEADERS[3], DATA_HEADERS[4],
                                                DATA_HEADERS[5], DATA_HEADERS[6], DATA_HEADERS[7], DATA_HEADERS[8],
                                                DATA_HEADERS[9], DATA_HEADERS[10], DATA_HEADERS[11], DATA_HEADERS[12],};

    // "WR" stands for week range. Since the week range data point has a different format, a
    // boolean variable is needed to create the correct spreadsheet format.
    private static final boolean WR = true;

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
        XSSFWorkbook workbook = fetchWorkbook(tickerCount);

        XSSFSheet sheet = (containsSheet(workbook, header)) ? workbook.getSheet(header):workbook.createSheet(header);
        formatSheet(sheet);

        int columnCount = 0;

        Row row = sheet.createRow(++tickerCount);
        Cell cell = row.createCell(columnCount++);
        cell.setCellValue(ticker);

        if(WR){
            String[] value = data.get("52 Week Range").split("-");
            String low = value[0];
            String high = value[1];
            cell = row.createCell(columnCount++);
            cell.setCellValue(parseDouble(low));
            cell = row.createCell(columnCount++);
            cell.setCellValue(parseDouble(high));
        }

        for(String s : TARGET_DATA){
            cell = row.createCell(columnCount++);
            try {
                cell.setCellValue(parseDouble(data.get(s)));
            }catch (NumberFormatException e){
                cell.setCellValue(data.get(s));
            }
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

        if(WR){
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue("52WR Low");
            cell = row.createCell(columnCount++);
            cell.setCellValue("52WR High");
        }

        for(String s : TARGET_DATA){
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
     * @param tickerCount
     *              This variable is used to know whether the sheet being edited is a new one or not.
     * @return XSSFWorkbook
     *              The retrieved XSSFWorkbook from the existing file or a newly created XSSFWorkbook if the file is not found.
     * @ensures The returned XSSFWorkbook is either an existing workbook from the specified file or a new workbook.
     */
    public static XSSFWorkbook fetchWorkbook(final int tickerCount){
        XSSFWorkbook workbook;
        final String directory = WindowView1.targDirDisplay.getText();
        if(directory.isBlank() || directory.isEmpty()){
            fileName = "summary_data.xlsx";
        }else{
            fileName = WindowView1.targDirDisplay.getText() + "/summary_data.xlsx";
        }
        try {
            /*
            * this if statement checks makes sure that the sheet being worked on is new if the current ticker
            * is the first one from the list.
            */
            if(tickerCount == 0){
                throw new IOException("Forced Exception");
            }
            FileInputStream fis = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(fis);
            fis.close();
        } catch (IOException e) {
            workbook = new XSSFWorkbook();
        }
        return workbook;
    }
}