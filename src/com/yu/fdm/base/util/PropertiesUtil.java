package com.yu.fdm.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

import com.yu.fdm.tools.rcp.Activator;

public class PropertiesUtil {
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	
	private static Properties properties = new Properties();
	
	private static String propertiesPath;
	
	static {
		InputStream is = null;
		try {
			propertiesPath = FileLocator.toFileURL(Platform.getBundle(Activator.PLUGIN_ID).getResource("")).getPath()+"jdbc.properties";
			is = new FileInputStream(new File(propertiesPath));
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String get(String key){
		logger.info("获取属性值，key："+key);
		logger.info("属性文件路径："+propertiesPath);
		String value = properties.getProperty(key);
		logger.info("属性值："+value);
		return value;
	}
	
	public static void set(String key,String value){
		logger.info("修改属性值，key："+key+"，value："+value);
		properties.setProperty(key, value);
		try {
			logger.info("属性文件路径："+propertiesPath);
			FileOutputStream oFile = new FileOutputStream(propertiesPath);
			properties.store(oFile, propertiesPath);
			oFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("修改属性值失败",e);
		}
	}
}
