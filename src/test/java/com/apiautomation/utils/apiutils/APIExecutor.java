package com.apiautomation.utils.apiutils;


import com.apiautomation.environmentconfiguration.EnvVariablesConfig;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

public class APIExecutor {

	private final static Logger logger = LoggerFactory.getLogger(APIExecutor.class);

	private HttpClient client;
	String scheme = EnvVariablesConfig.getEnvironmentProperty("api_scheme");
	String hostUrl = EnvVariablesConfig.getEnvironmentProperty("api_host");

	public APIExecutor() {
			client = HttpClientBuilder.create().build();
	}



	public APIValidator get(String scheme, String hostUrl, String path, Map<String, String> headers,
							Map<String, String> requestParams) {

		URIBuilder builder = new URIBuilder();
		for(Map.Entry<String,String> entry : requestParams.entrySet())
			builder.setParameter(entry.getKey(), entry.getValue());

		builder.setScheme(scheme).setHost(hostUrl).setPath(path);

		URI uri = null;
		try {
			uri = builder.build();
			logger.info("API URI: "+uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet getRequest = new HttpGet(uri);

		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				getRequest.addHeader(key, headers.get(key));
			}
		}

		return hitGetAPI(getRequest);
	}



	public APIValidator get(String path, Map<String, String> headers,
							Map<String, String> requestParams) {

		URIBuilder builder = new URIBuilder();
		for(Map.Entry<String,String> entry : requestParams.entrySet())
			builder.setParameter(entry.getKey(), entry.getValue());

		builder.setScheme(scheme).setHost(hostUrl).setPath(path);

		URI uri = null;
		try {
			uri = builder.build();
			logger.info("API URI: "+uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet getRequest = new HttpGet(uri);

		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				getRequest.addHeader(key, headers.get(key));
			}
		}

		return hitGetAPI(getRequest);
	}




	public APIValidator get(String hostUrl, String path, Map<String, String> headers) {
		HttpGet getRequest = new HttpGet(hostUrl + path);

		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				getRequest.addHeader(key, headers.get(key));
			}
		}

		return hitGetAPI(getRequest);
	}





	private APIValidator hitGetAPI(HttpGet getRequest) {
		APIResponse responseObj = new APIResponse();
		try {

			HttpResponse httpResponse = client.execute(getRequest);
			responseObj.setResponseBody(EntityUtils.toString(httpResponse.getEntity()));
			responseObj.setResponseCode(httpResponse.getStatusLine().getStatusCode());
			responseObj.setResponseMessage(httpResponse.getStatusLine().getReasonPhrase());

			Header[] allHeaders = httpResponse.getAllHeaders();
			for (Header header : allHeaders) {
				responseObj.setHeader(header.getName(), header.getValue());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		return new APIValidator(responseObj);
	}



}
