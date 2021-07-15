package com.apiautomation.utils.apiutils;


import com.apiautomation.utils.JSONUtility;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.util.LinkedHashMap;

public class APIValidator {

	private final static Logger logger = LoggerFactory.getLogger(APIValidator.class);

	private APIResponse response;

	public APIValidator(APIResponse response) {
		this.response = response;
	}

	public void validateResponseCode(String expectedResponseCode, SoftAssert softAssert) {
		Integer actualResponseCode = response.getResponseCode();
		logger.debug("actualResponseCode:-> "+ actualResponseCode);
		softAssert.assertEquals(actualResponseCode,Integer.valueOf(expectedResponseCode),
				"Expected Response Code: " + expectedResponseCode + " and Actual Response Code: " + actualResponseCode);
	}

	public void validateAuthor(String expectedAuthor, SoftAssert softAssert) {
		String responseBody = response.getResponseBody();
		logger.debug("responseBody:-> "+ responseBody);


		JSONArray authorsArray = (JSONArray) JSONUtility.parseJson(responseBody,"$.results[*].authorSlug");

		for (int i = 0; i <authorsArray.size() ; i++) {
			softAssert.assertEquals(authorsArray.get(i),expectedAuthor,
					"Expected Author:-> "+ expectedAuthor+" Actual Author:-> "+ authorsArray.get(i));

		}
	}


	public void validatePageNumber(String page, SoftAssert softAssert) {
		String responseBody = response.getResponseBody();
		logger.debug("responseBody:-> "+ responseBody);

			Integer pageNumber = (Integer) JSONUtility.parseJson(responseBody,"$.page");
			softAssert.assertEquals(String.valueOf(pageNumber),page,
					"Expected page number:-> "+ page+" Actual page number:-> "+ pageNumber);

	}


	public void validateTags(String expectedTags, SoftAssert softAssert) {
		String responseBody = response.getResponseBody();
		logger.debug("responseBody:-> "+ responseBody);

			String[] expectedTagArray = expectedTags.split("\\||,");
			JSONArray	resultArray = (JSONArray)JSONUtility.parseJson(responseBody,"$.results");

			if(expectedTags.contains(",")){
				for (int i = 0; i < resultArray.size() ; i++) {
					String actualTags = ((LinkedHashMap) resultArray.get(i)).get("tags").toString();
					for (int j = 0; j <expectedTagArray.length ; j++) {
						 if(!actualTags.contains(expectedTagArray[j])){
						 	softAssert.assertTrue(false,
									resultArray.get(i).toString()+ "doesn't contains tag "+ expectedTagArray[j]);
						 }
					}
				}

			}
			else if (expectedTags.contains("|")){
				boolean result = false;
				for (int i = 0; i < resultArray.size() ; i++) {
					String actualTags = ((LinkedHashMap) resultArray.get(i)).get("tags").toString();
					for (int j = 0; j <expectedTagArray.length ; j++) {
						if(actualTags.contains(expectedTagArray[j])){
							result = true;
							break;
						}
					}
					softAssert.assertTrue(result,
							resultArray.get(i).toString()+ "doesn't contains any of tag "+ expectedTags);
				}

			}else {
				for (int i = 0; i < resultArray.size() ; i++) {
					String actualTags = ((LinkedHashMap) resultArray.get(i)).get("tags").toString();
					softAssert.assertTrue(actualTags.contains(expectedTagArray[0]),
							resultArray.get(i).toString()+ "doesn't contains any of tag "+ expectedTags);
				}
			}


	}


	public APIResponse getResponse() {
		return response;
	}

}