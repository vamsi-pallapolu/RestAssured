	package com.qa.restclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {
	
	//1. GET Method without headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		//Opens the connection
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		CloseableHttpResponse httpResponse=httpClient.execute(httpGet);
		return httpResponse;
		
	}
	
	//2. GET Method with headers
	public CloseableHttpResponse get(String url, HashMap<String, String> hmap) throws ClientProtocolException, IOException {
		//Opens the connection
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		for(Map.Entry<String, String> entry:hmap.entrySet()) {
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse httpResponse=httpClient.execute(httpGet);
		return httpResponse;
	}
	
	//3. POST Method
	public CloseableHttpResponse post(String url, String entity, HashMap<String, String> hmap) throws 
													ClientProtocolException, IOException {
		CloseableHttpClient httpclient=HttpClients.createDefault();
		
		//add payload
		HttpPost post=new HttpPost(url);
		post.setEntity(new StringEntity(entity));
		
		//adding header
		for(Map.Entry<String, String> entry:hmap.entrySet())
			post.addHeader(entry.getKey(), entry.getValue());
		
		CloseableHttpResponse httpResponse=httpclient.execute(post);
		return httpResponse;
	}
}
