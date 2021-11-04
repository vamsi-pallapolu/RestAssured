package com.qa.base;

import java.io.FileInputStream;
import java.util.Properties;

public class TestBase {

	public Properties prop;
	public int RESPONSE_STATUS_CODE_200=200;
	public int RESPONSE_STATUS_CODE_201=201;
	public int RESPONSE_STATUS_CODE_400=400;
	public int RESPONSE_STATUS_CODE_401=401;
	public int RESPONSE_STATUS_CODE_500=500;
	
	public TestBase() {
		try {
			prop=new Properties();
			FileInputStream ip=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\com\\qa\\config\\config.properties");
			prop.load(ip);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
