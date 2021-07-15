package com.apiautomation.testcases;

import com.apiautomation.utils.XLSUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SearchQuoteDataProvider {

    static XLSUtils reader;
    static String filePath =".\\src\\test\\resources\\TestData";
    static String fileName = "SearchQuotes.xlsx";

   static  {
       try {
           reader = new XLSUtils(filePath, fileName);
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

    @DataProvider(name = "SearchQuotes" )
    public static Object[][] searchQuotesDataProvider() throws IOException {
        String  sheetName = "GetQuotes";
        int rowcount = reader.getRowCount(sheetName);
        Object result[][] = new Object[rowcount - 1][3];

        for (int i = 2; i <=rowcount ; i++) {
            Map<Object, Object> valueMap = new HashMap<>();
            valueMap.put("TestCase_type",reader.getCellData(sheetName,"TestCase_type",i));
            valueMap.put("Tags",reader.getCellData(sheetName,"Tags",i));
            valueMap.put("Author",reader.getCellData(sheetName,"Author",i));
            valueMap.put("Page",reader.getCellData(sheetName,"Page",i));
            valueMap.put("ExpectedStatusCode",reader.getCellData(sheetName,"ExpectedStatusCode",i));


            result[i - 2][0] = i;
            result[i - 2][1] = valueMap.get("TestCase_type");
            result[i - 2][2] = valueMap;
        }
        return result;
    }

}
