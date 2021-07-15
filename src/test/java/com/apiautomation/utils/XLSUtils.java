package com.apiautomation.utils;



import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import org.apache.poi.ss.usermodel.*;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;

import java.util.*;






public class XLSUtils {
    private final static Logger logger = LoggerFactory.getLogger(XLSUtils.class);
    public String filePath;
    public String fileName;
    private List<String> sheetNames;
    private Workbook workBook = null;
    private Sheet sheet = null;
    private Row row = null;
    private Cell cell = null;



    public XLSUtils(String filePathWithName) throws IOException {
        //Create an object of File class to open xlsx file
        File file = new File(filePathWithName);
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        //Find the file extension by splitting  file name in substring and getting only extension name
        String fileExtensionName = filePathWithName.substring(filePathWithName.indexOf("."));

        //Check condition if the file is xlsx file
        if (fileExtensionName.equals(".xlsx")) {
            workBook = new XSSFWorkbook(inputStream);
        } else if (fileExtensionName.equals(".xlsm")) {
            workBook = new XSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if (fileExtensionName.equals(".xls")) {
            workBook = new HSSFWorkbook(inputStream);
        }
        sheetNames = readAllSheetsName(workBook);

    }

    public XLSUtils(String filePath, String fileName) throws IOException {
        //logger.info("Internal Method Name is :---->{}", Thread.currentThread().getStackTrace()[1].getMethodName());
        this.filePath = filePath;
        this.fileName = fileName;

        //Create an object of File class to open xlsx file
        File file = new File(filePath + "//" + fileName);
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        //Find the file extension by splitting  file name in substring and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        //Check condition if the file is xlsx file
        if (fileExtensionName.equals(".xlsx")) {
            workBook = new XSSFWorkbook(inputStream);
        } else if (fileExtensionName.equals(".xlsm")) {
            workBook = new XSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if (fileExtensionName.equals(".xls")) {
            workBook = new HSSFWorkbook(inputStream);
        }
        sheetNames = readAllSheetsName(workBook);

    }


    // it will return all the sheetsName in WorkBook
    private List<String> readAllSheetsName(Workbook workbook) {

        List<String> sheetNames = new ArrayList<>();
        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
            sheetNames.add(workBook.getSheetName(i));
        }
        return sheetNames;
    }



    // Xls_Reader - Returns Sheet Existence
    public boolean isSheetExist(String sheetName) {
        int index = workBook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workBook.getSheetIndex(sheetName.toUpperCase());
            if (index == -1)
                return false;

            return true;
        } else
            return true;
    }

    // Xls_Reader - Returns Row Count
    public int getRowCount(String sheetName) {
        int index = workBook.getSheetIndex(sheetName);
        if (index == -1) {
            return 0;
        } else {
            sheet = workBook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            logger.debug("Row Count is {} ", number);
            return number;
        }
    }




    //Added by sarthak
    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0) {
                return "";
            }
            int index = this.workBook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1) {
                return "";
            }
            this.sheet = this.workBook.getSheetAt(index);
            this.row = this.sheet.getRow(0);
            for (int i = 0; i < this.row.getLastCellNum(); ++i) {
                if (this.row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1) {
                return "";
            }
            this.sheet = this.workBook.getSheetAt(index);
            this.row = this.sheet.getRow(rowNum - 1);
            if (this.row == null)
                return "";
            this.cell = this.row.getCell(col_Num);

            if (this.cell == null) {
                return "";
            }
            // System.out.println(this.cell.getCellType());
            if (this.cell.getCellType() == 1)
                return this.cell.getStringCellValue();
            if ((this.cell.getCellType() == 0) || (this.cell.getCellType() == 2)) {
                String cellText = String.valueOf((int)this.cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(this.cell)) {
                    double d = this.cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));

                    String.valueOf(cal.get(1)).substring(2);
                    cellText = cal.get(5) + "/" +
                            cal.get(2) + 1 + "/" +
                            cellText;
                }

                return cellText;
            }
            if (this.cell.getCellType() == 3) {
                return "";
            }
            return String.valueOf(this.cell.getBooleanCellValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "row " + rowNum + " or column " + colName + " does not exist in xls";
    }


    //Added by sarthak
    public String getCellDataByCustomcolumn(String sheetName, String colName, int rowNum, int columnHeaderIndex) {
        try {
            if (rowNum <= 0) {
                return "";
            }
            int index = this.workBook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1) {
                return "";
            }
            this.sheet = this.workBook.getSheetAt(index);
            this.row = this.sheet.getRow(columnHeaderIndex);
            for (int i = 0; i < this.row.getLastCellNum(); ++i) {
                if (this.row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1) {
                return "";
            }
            this.sheet = this.workBook.getSheetAt(index);
            this.row = this.sheet.getRow(rowNum - 1);
            if (this.row == null)
                return "";
            this.cell = this.row.getCell(col_Num);

            if (this.cell == null) {
                return "";
            }
            // System.out.println(this.cell.getCellType());
            if (this.cell.getCellType() == 1)
                return this.cell.getStringCellValue();
            if ((this.cell.getCellType() == 0) || (this.cell.getCellType() == 2)) {
                String cellText = String.valueOf(this.cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(this.cell)) {
                    double d = this.cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));

                    String.valueOf(cal.get(1)).substring(2);
                    cellText = cal.get(5) + "/" +
                            cal.get(2) + 1 + "/" +
                            cellText;
                }

                return cellText;
            }
            if (this.cell.getCellType() == 3) {
                return "";
            }
            return String.valueOf(this.cell.getBooleanCellValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "row " + rowNum + " or column " + colName + " does not exist in xls";
    }



   }