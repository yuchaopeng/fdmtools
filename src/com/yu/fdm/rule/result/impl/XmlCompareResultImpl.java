package com.yu.fdm.rule.result.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.base.util.FreemarkUtil;
import com.yu.fdm.rule.result.CompareResult;

import freemarker.template.TemplateException;

public class XmlCompareResultImpl implements CompareResult{
	
	@Override
	public String getResult(Object datas) throws TemplateException, IOException, MyException {
		if(datas instanceof List){
			List list = (List) datas;
			String ftlFileName = "";
			if(list.size() > 0){
				Object obj = list.get(0);
				String className = obj.getClass().getSimpleName();
				ftlFileName = "xml/"+className+"_xml.ftl";
				Map<String, Object> dataModel = new HashMap<String, Object>();  
				dataModel.put("datas", datas);
				return FreemarkUtil.processResult(ftlFileName, dataModel);
			}else{
				return "";
			}
		}else{
			throw new MyException("构建结果时，传入的datas不为List类型");
		}
	}
}
