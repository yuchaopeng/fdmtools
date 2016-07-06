package com.yu.fdm.base.test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.base.util.CommonUtil;
import com.yu.fdm.base.util.FreemarkUtil;
import com.yu.fdm.rule.facade.RuleCompareFacade;
import com.yu.fdm.rule.model.AccountJournalRule;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class YuTest {
	@Test
	public void main() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		try {
			RuleCompareFacade.compareRule(null);
		} catch (MyException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void freemarker() throws IOException, TemplateException{
		Template template = FreemarkUtil.getTemplate("xml/AccountJounralRule_xml.ftl");
		Map<String, Object> data = new HashMap<String, Object>();  
		List<AccountJournalRule> list = new ArrayList<AccountJournalRule>();
		data.put("accountJournalRules", list);
		Writer write = new StringWriter();
		template.process(data, write);
		System.out.println(write.toString());
	}
	
	//@Test
	public void getNewRuleId(){
		String rule = CommonUtil.getNewRuleId("04500017");
		System.out.println(rule);
	}
}