package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExcelUtils {
    private static final String filePath = "src/test/resources/TestData.xlsx";

    /**
     * Returns true if the given TestCaseID in sheetName has RunMode = Y.
     * Safe: returns false if row/sheet/columns not found.
     */
    public static boolean isRunnable(String testCaseID, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return false;

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return false;

            int testCaseCol = -1;
            int runModeCol = -1;

            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                String h = getCellValueAsString(headerRow.getCell(c));
                if ("TestCaseID".equalsIgnoreCase(h)) testCaseCol = c;
                if ("RunMode".equalsIgnoreCase(h)) runModeCol = c;
            }
            if (testCaseCol == -1 || runModeCol == -1) return false;

            DataFormatter formatter = new DataFormatter();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                String tc = formatter.formatCellValue(row.getCell(testCaseCol)).trim();
                if (testCaseID.equalsIgnoreCase(tc)) {
                    String runMode = formatter.formatCellValue(row.getCell(runModeCol)).trim();
                    return "Y".equalsIgnoreCase(runMode);
                }
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns a map for the row which matches TestCaseID in given sheet.
     * Keys are header names; values are the cell values as String.
     * Returns null if not found.
     */
    public static Map<String, String> getTestCaseById(String sheetName, String testCaseId) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return null;

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return null;

            List<String> headers = new ArrayList<>();
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                headers.add(getCellValueAsString(headerRow.getCell(c)).trim());
            }

            DataFormatter formatter = new DataFormatter();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                String tc = formatter.formatCellValue(row.getCell(0)).trim(); // assume first column could be TestCaseID, but we will search
                // search by header name in row
                Map<String, String> map = new HashMap<>();
                boolean match = false;
                for (int c = 0; c < headers.size(); c++) {
                    String header = headers.get(c);
                    String val = formatter.formatCellValue(row.getCell(c));
                    map.put(header, val);
                    if ("TestCaseID".equalsIgnoreCase(header) && testCaseId.equalsIgnoreCase(val)) {
                        match = true;
                    }
                }
                if (match) return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all rows (every row as Map<header,value>) from sheet.
     */
    public static List<Map<String, String>> getAllRows(String sheetName) {
        List<Map<String, String>> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return list;

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return list;

            List<String> headers = new ArrayList<>();
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                headers.add(getCellValueAsString(headerRow.getCell(c)).trim());
            }

            DataFormatter formatter = new DataFormatter();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Map<String, String> map = new HashMap<>();
                for (int c = 0; c < headers.size(); c++) {
                    String val = formatter.formatCellValue(row.getCell(c));
                    map.put(headers.get(c), val);
                }
                list.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns only rows where RunMode = Y (as list of maps).
     */
    public static List<Map<String, String>> getAllRowsWithRunModeY(String sheetName) {
        List<Map<String, String>> all = getAllRows(sheetName);
        List<Map<String, String>> filtered = new ArrayList<>();
        for (Map<String, String> row : all) {
            if ("Y".equalsIgnoreCase(row.getOrDefault("RunMode", ""))) {
                filtered.add(row);
            }
        }
        return filtered;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        DataFormatter df = new DataFormatter();
        return df.formatCellValue(cell);
    }
}
