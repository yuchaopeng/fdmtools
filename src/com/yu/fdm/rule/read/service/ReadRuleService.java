package com.yu.fdm.rule.read.service;

import java.io.File;
import java.util.List;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.rule.model.AccountJournalRule;

public interface ReadRuleService {
	
	/**
	 * 读取规则，有可能是word、xml、txt等
	 * @return
	 */
	List<AccountJournalRule> read(File file) throws MyException ; 
}
