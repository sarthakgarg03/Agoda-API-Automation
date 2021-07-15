package com.apiautomation.utils.apiutils;



import java.util.HashMap;

public class ApiHeaders {

	public static HashMap<String, String> getContentTypeAsJsonOnlyHeader() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		return headers;
	}

	public static HashMap<String, String> getDefaultHeaders() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		return headers;
	}


	public static HashMap<String, String> addHeader(HashMap<String, String> existingHeaders, String header, String value) {
		existingHeaders.put(header, value);
		return existingHeaders;
	}

	public static HashMap<String, String> getDefaultLegacyHeaders() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Content-Type", "application/json;charset=UTF-8");

		return headers;
	}

}