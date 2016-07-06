package com.yu.fdm.rule.factory;

import com.yu.fdm.rule.service.RuleCompareDBService;
import com.yu.fdm.rule.service.impl.RuleCompareDBServiceImpl;

public class RuleCompareDBFactory {
	private static RuleCompareDBService service ;
	
	public static synchronized RuleCompareDBService getInstance(){
		if(service == null){
			service = new RuleCompareDBServiceImpl();
		}
		return service;
	}
}
