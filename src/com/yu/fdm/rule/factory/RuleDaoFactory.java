package com.yu.fdm.rule.factory;

import com.yu.fdm.rule.dao.RuleDao;
import com.yu.fdm.rule.dao.impl.RuleDaoImpl;

public class RuleDaoFactory {
	
	private static RuleDao ruleDao;
	public static synchronized RuleDao getRuleDao(){
		if(ruleDao == null){
			ruleDao = new RuleDaoImpl();
		}
		return ruleDao;
	}
}
