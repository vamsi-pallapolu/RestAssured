package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {

	TestBase testbase;
	String url;
	String apiUrl;
	String uri;
	RestClient client;
	CloseableHttpResponse httpRes;

	@BeforeMethod
	public void setUp() {
		testbase = new TestBase();
		url = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		uri = url + apiUrl;

	}

	@Test(priority=1)
	public void getTestWithoutHeaders() throws ClientProtocolException, IOException {
		client = new RestClient();
		httpRes = client.get(uri);
		// a. Status Code
		int statuscode = httpRes.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// b. All headers
		HashMap<String, String> hmap = new HashMap<>();
		Header[] headers = httpRes.getAllHeaders();
		for (Header header : headers) {
			hmap.put(header.getName(), header.getValue());
			System.out.println(header.getName() + ":" + header.getValue());
		}
		System.out.println(hmap);
				
		// c. JSON String
		HttpEntity entity = httpRes.getEntity();
		String response = EntityUtils.toString(entity, "UTF-8"); // Converts Http Json Response into a String
		JSONObject responsejson = new JSONObject(response);
		System.out.println("RESPONSE JSON:" + responsejson);
		
		//Single value assertion
		String per_page_value=TestUtil.getValueByJPath(responsejson, "/per_page");
		System.out.println("per_page_value:"+per_page_value);
		Assert.assertEquals(Integer.parseInt(per_page_value), 6);
		
		//Get the value from JSON Array
		String id=TestUtil.getValueByJPath(responsejson, "/data[0]/id");
		System.out.println("id:"+id);
		Assert.assertEquals("1", id);
		String email=TestUtil.getValueByJPath(responsejson, "/data[0]/email");
		System.out.println("email:"+email);
		Assert.assertEquals("george.bluth@reqres.in", email);
		String first_name=TestUtil.getValueByJPath(responsejson, "/data[0]/first_name");
		System.out.println("first_name:"+first_name);
		Assert.assertEquals("George", first_name);;
		String last_name=TestUtil.getValueByJPath(responsejson, "/data[0]/last_name");
		System.out.println("last_name:"+last_name);
		Assert.assertEquals("Bluth", last_name);
		
	}
	
	@Test(priority=2)
	public void getTestWithHeaders() throws ClientProtocolException, IOException {
		client = new RestClient();
		
		HashMap<String, String> hmap=new HashMap<>();
		hmap.put("Content-Type", "application/json");
		httpRes = client.get(uri, hmap);
		
		// a. Status Code
		int statuscode = httpRes.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// b. All headers
		HashMap<String, String> map = new HashMap<>();
		Header[] headers = httpRes.getAllHeaders();
		for (Header header : headers) {
			map.put(header.getName(), header.getValue());
			System.out.println(header.getName() + ":" + header.getValue());
		}
		System.out.println(map);
				
		// c. JSON String
		HttpEntity entity = httpRes.getEntity();
		String response = EntityUtils.toString(entity, "UTF-8"); // Converts Http Json Response into a String
		JSONObject responsejson = new JSONObject(response);
		System.out.println("RESPONSE JSON:" + responsejson);
		
		//Single value assertion
		String per_page_value=TestUtil.getValueByJPath(responsejson, "/per_page");
		System.out.println("per_page_value:"+per_page_value);
		Assert.assertEquals(Integer.parseInt(per_page_value), 6);
		
		//Get the value from JSON Array
		String id=TestUtil.getValueByJPath(responsejson, "/data[0]/id");
		System.out.println("id:"+id);
		Assert.assertEquals("1", id);
		String email=TestUtil.getValueByJPath(responsejson, "/data[0]/email");
		System.out.println("email:"+email);
		Assert.assertEquals("george.bluth@reqres.in", email);
		String first_name=TestUtil.getValueByJPath(responsejson, "/data[0]/first_name");
		System.out.println("first_name:"+first_name);
		Assert.assertEquals("George", first_name);;
		String last_name=TestUtil.getValueByJPath(responsejson, "/data[0]/last_name");
		System.out.println("last_name:"+last_name);
		Assert.assertEquals("Bluth", last_name);
		

		
	}

}
