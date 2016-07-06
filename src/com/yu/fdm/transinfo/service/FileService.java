package com.yu.fdm.transinfo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.transinfo.model.SearchModel;

public class FileService {
	public static StringBuffer searchFile(File dir,SearchModel model) throws MyException{
		File[] fileList = dir.listFiles();
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferReader = null;
		FileWriter writer = null;
		StringBuffer result = new StringBuffer();
		try {
			for(File file : fileList){
				inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8"); 
				bufferReader = new BufferedReader(inputStreamReader);
				String line = null;
				while((line = bufferReader.readLine()) != null){
					if(model.getGlobalSeqNo() != null && !"".equals(model.getGlobalSeqNo().toString().trim())){
						if(line.indexOf("|"+model.getGlobalSeqNo()+"|") == -1){
							continue;
						}
					}
					System.out.println(line);
					result.append(line+"\n");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MyException(e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null) writer.close();
				if(bufferReader != null) bufferReader.close();
				if(inputStreamReader != null) inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new MyException(e);
			}
		}
		return result;
	}
}
