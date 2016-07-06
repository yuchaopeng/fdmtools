package com.yu.fdm.transinfo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.transinfo.model.SearchModel;

public class TransInfoService {
	
	public static synchronized boolean merge(File selectFile,File saveFile) throws MyException{
		boolean flag = false;
		File[] fileList = selectFile.listFiles();
		FileReader fileReader = null;
		BufferedReader bufferReader = null;
		FileWriter writer = null;
		File save = new File(saveFile.getAbsoluteFile()+File.separator+"test");
		try {
			save.createNewFile();
			writer = new FileWriter(save);
			for(File file : fileList){
				fileReader = new FileReader(file);
				bufferReader = new BufferedReader(fileReader);
				String line = null;
				while((line = bufferReader.readLine()) != null){
					writer.write(line+"\n");
				}
			}
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MyException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException(e);
		} finally {
			try {
				if(writer != null) writer.close();
				if(bufferReader != null) bufferReader.close();
				if(fileReader != null) fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new MyException(e);
			}
		}
		return flag;
	}
	
	
	public static synchronized StringBuffer search(File dir042,File dir044,SearchModel model) throws MyException{
		StringBuffer file042 = FileService.searchFile(dir042, model);
		StringBuffer file044 = FileService.searchFile(dir044, model);
		StringBuffer result = file042.append(file044);
		return result;
	}
}
