package main.yahoo_downloader_engine;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Uploader {

    private static final String[] DESIRED_DATA = {"52 Week Range", "Volume", "Avg. Volume", "Market Cap",
            "Forward Dividend & Yield"};

    public static void upload(String ticker, HashMap<String, String> data, int tickerCount){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Stock Summary");
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

    public static void formatSheet(XSSFSheet sheet){
        final int firstRow = 0;
        int columnCount = 1;
        Row row = sheet.createRow(firstRow);
        for(String s : DESIRED_DATA){
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(s);
        }
    }

    public static FileOutputStream fetchFile() throws IOException {
        final String fileName = "summary_data.xlsx";
        File file = new File(fileName);
        return new FileOutputStream(file);
    }
}