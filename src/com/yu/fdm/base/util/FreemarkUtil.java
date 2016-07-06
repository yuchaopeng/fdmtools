package com.yu.fdm.base.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

import com.yu.fdm.tools.rcp.Activator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkUtil {
	private static Configuration config;
	static{
		config = new Configuration();
		String path = "";
		try {
			path = FileLocator.toFileURL(Platform.getBundle(Activator.PLUGIN_ID).getResource("")).getPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			config.setDirectoryForTemplateLoading(new File(path+"ftl")); 
			config.setObjectWrapper(new DefaultObjectWrapper());
			config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static Template getTemplate(String templateName) throws IOException{
		return config.getTemplate(templateName);
	}
	
	public static String processResult(String templateName,Object dataModel) throws TemplateException, IOException{
		Template template = getTemplate(templateName);
		return processResult(template, dataModel);
	}
	
	public static String processResult(Template template,Object dataModel) throws TemplateException, IOException{
		Writer write = new StringWriter();
		template.process(dataModel, write);
		return write.toString();
	}
}
