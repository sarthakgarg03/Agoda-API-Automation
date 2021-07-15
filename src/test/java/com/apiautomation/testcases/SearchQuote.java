package com.apiautomation.testcases;


import com.apiautomation.api.GetQuote;
import com.apiautomation.utils.JSONUtility;
import com.apiautomation.utils.apiutils.APIValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class SearchQuote {
    private final static Logger logger = LoggerFactory.getLogger(SearchQuote.class);
    GetQuote getQuote = new GetQuote();
    APIValidator validator;

    /**
     *
     * @param rowNum :- define the row number in the test data sheet
     * @param tc_type :- define the test case type
     * @param valuemap :- hashmap contains all data of excel data file
     */
    @Test(dataProvider = "searchQuotesDataProvider",dataProviderClass = SearchQuoteDataProvider.class)
    public void TC(int rowNum , String tc_type , Map<String,String> valuemap){

        SoftAssert softAssert = new SoftAssert();

        String tags = valuemap.get("Tags");
        String author = valuemap.get("Author");
        String page = valuemap.get("Page");
        String expectedStatusCode = valuemap.get("ExpectedStatusCode");

        //hit the get Quote API
        validator  = getQuote.getQuotes(tags,author,page);

        validator.validateResponseCode(expectedStatusCode,softAssert);  //validate response code

        if(validator.getResponse().getResponseCode()==200){
            //total count :- no of results of the page
            int totalCount = (int) JSONUtility.parseJson(validator.getResponse().getResponseBody(),"$.count");
            if(totalCount>0){
            //validate Author
            if(!author.equals("") && author!=null) validator.validateAuthor(author,softAssert);
            //validate Page Number
            if(!page.equals("") && page!=null) validator.validatePageNumber(page,softAssert);
            //validate Tags
            if(!tags.equals("") && tags!=null) validator.validateTags(tags,softAssert);

            }else{
                //print is result count is zero
                logger.info("No Data found for params " + valuemap.toString());
            }
        }





        
        softAssert.assertAll();
    }
}
