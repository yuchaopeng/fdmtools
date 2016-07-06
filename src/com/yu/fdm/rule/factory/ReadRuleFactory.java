package com.yu.fdm.rule.factory;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.base.util.CommonUtil;
import com.yu.fdm.base.util.PropertiesUtil;
import com.yu.fdm.rule.read.service.ReadRuleService;

public class ReadRuleFactory {
	public static ReadRuleService getInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException, MyException{
		String readRuleType = PropertiesUtil.get("read.rule.file.type");
		if(CommonUtil.isEmpty(readRuleType)){
			throw new MyException("read.rule.file.type 为空");
		}
		ReadRuleService readRuleService = (ReadRuleService) Class.forName(readRuleType).newInstance();
		return readRuleService;
	}
}
