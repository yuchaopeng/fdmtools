package com.yu.fdm.transinfo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Version {
	public static String getVersion(){
		InputStream is = Version.class.getClassLoader().getResourceAsStream("version.txt");
		StringBuffer buffer=new StringBuffer();  
	    String line="";  
	    try {
	    	BufferedReader bf=new BufferedReader(new InputStreamReader(is,"UTF-8"));
			while((line=bf.readLine())!=null){  
			    buffer.append(line);  
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
	    return buffer.toString();  
	}
}
