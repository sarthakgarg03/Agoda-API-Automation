package com.apiautomation.api;


import com.apiautomation.utils.apiutils.APIResponse;
import com.apiautomation.utils.apiutils.APIValidator;
import com.apiautomation.utils.apiutils.ApiHeaders;
import com.apiautomation.utils.apiutils.TestAPIBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GetQuote extends TestAPIBase {

    private final static Logger logger = LoggerFactory.getLogger(GetQuote.class);
    String apiPath = "/quotes";

    /**
     *
     * @param tags :- define tags of quotes (AND-list xx,yy and OR-list xx|yy)
     * @param author :- define the author of the quote
     * @param page  :-define the page number
     * @return APIValidator instance
     */
    public APIValidator getQuotes(String tags, String author, String page){

        Map<String, String> requestParams = new HashMap<>();

        if(tags!=null && tags!= "")  requestParams.put("tags",tags);
        if(author!=null && author!= "") requestParams.put("author",author);
        if(page!=null && page!= "") requestParams.put("page", page);

        APIResponse response = executor.get(apiPath, ApiHeaders.getDefaultHeaders(),
                requestParams).getResponse();

        logger.debug("Search API response code" + response.getResponseCode());
        logger.debug("Search API response body" + response.getResponseBody());

        return new APIValidator(response);
    }



}
