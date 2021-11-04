package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.data.Users;
import com.qa.restclient.RestClient;

public class PostApiTest  extends TestBase{
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
	
	@Test
	public void postApiTest() throws StreamWriteException, DatabindException, IOException {
		client = new RestClient();
		
		HashMap<String, String> hmap=new HashMap<>();
		hmap.put("Content-Type", "application/json");
		
		//jackson api
		ObjectMapper mapper=new ObjectMapper();
		Users user=new Users("vamsi","engineer");
		String filepath=System.getProperty("user.dir")+"\\src\\test\\java\\com\\qa\\data\\user.json";
		System.out.println("filepath:"+filepath);
	
		//Java Object to JSON (marshaling)
		mapper.writeValue(new File(filepath), user);
		
		String jsonstring=mapper.writeValueAsString(user);
		System.out.println("jsonstring:"+jsonstring);
		
		httpRes=client.post(uri, jsonstring, hmap);
		
		//a. Status Line
		StatusLine statusLine=httpRes.getStatusLine();
		ProtocolVersion pv=statusLine.getProtocolVersion();
		int responsecode=statusLine.getStatusCode();
		String reason=statusLine.getReasonPhrase();
		System.out.println(pv);
		System.out.println(responsecode);
		Assert.assertEquals(responsecode, testbase.RESPONSE_STATUS_CODE_201);
		System.out.println(reason);
		
		//b. Headers
		Header[] headers=httpRes.getAllHeaders();
		for(Header header:headers)
			System.out.println(header.getName()+":"+header.getValue());
		
		//c payload
		HttpEntity entity=httpRes.getEntity();
		String response=EntityUtils.toString(entity,"UTF-8");
		System.out.println(response);
		JSONObject jsonobject=new JSONObject(response);
		System.out.println(jsonobject);
		
		// json to java object (unmarshaling)
		Users userobj=mapper.readValue(response, Users.class); //actual users object
		System.out.println(userobj);
		
		Assert.assertTrue(user.getName().equals(userobj.getName()));
		Assert.assertTrue(user.getJob().equals(userobj.getJob()));
		
		
	}
}
