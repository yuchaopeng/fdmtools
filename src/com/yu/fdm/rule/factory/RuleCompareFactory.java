package com.yu.fdm.rule.factory;

import com.yu.fdm.rule.service.RuleCompareService;
import com.yu.fdm.rule.service.impl.RuleCompareServiceImpl;

public class RuleCompareFactory {
	
	private static RuleCompareService service ;
	
	public static synchronized RuleCompareService getInstance(){
		if(service == null){
			service = new RuleCompareServiceImpl();
		}
		return service;
	}
}
