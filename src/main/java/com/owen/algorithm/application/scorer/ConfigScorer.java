package com.owen.algorithm.application.scorer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class ConfigScorer extends Scorer{

	//protected HashMap<String,Object> config;
	protected Properties config;
	protected HashMap<String,Object> factors;
	public ConfigScorer(){
		
	}
	
	public void initConfig(String filename){
		InputStream inputStream=getClass().getClassLoader().getResourceAsStream(filename);
		config=new Properties();
		try {
			config.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getConfig(String name){
		return config.getProperty(name);
	}
	
}
