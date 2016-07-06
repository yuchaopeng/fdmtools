package com.yu.fdm.rule.facade;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.rule.factory.RuleCompareFactory;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.model.GlAccount;
import com.yu.fdm.rule.model.PaymentMethod;
import com.yu.fdm.rule.model.Product;
import com.yu.fdm.rule.model.ProductCategory;
import com.yu.fdm.rule.model.ProductCategoryMember;
import com.yu.fdm.rule.result.CompareResult;
import com.yu.fdm.rule.result.impl.SqlCompareResultImpl;
import com.yu.fdm.rule.result.impl.XmlCompareResultImpl;
import com.yu.fdm.rule.service.RuleCompareService;

import freemarker.template.TemplateException;

public class RuleCompareFacade {
	
	public static String buildCompareResult(List<AccountJournalRule> rulesCompareResult) throws TemplateException, IOException, MyException, SQLException{
		RuleCompareService service = RuleCompareFactory.getInstance();
		CompareResult resultXml = new XmlCompareResultImpl();
		CompareResult resultSql = new SqlCompareResultImpl();
		
		List<PaymentMethod> methods = service.getNewPaymentMethod(rulesCompareResult);
		List<GlAccount> glAccounts = service.getNewGlAccount(rulesCompareResult);
		List<Product> products = service.getNewProduct(rulesCompareResult);
		List<ProductCategory> productCategorys = service.getNewProductCategory(rulesCompareResult);
		List<ProductCategoryMember> productCategoryMembers = service.getNewProductCategoryMember(rulesCompareResult);
		
		StringBuffer result = new StringBuffer();
		result.append("新增的种子数据xml：\r\n");
		result.append(resultXml.getResult(rulesCompareResult));
		result.append(resultXml.getResult(methods));
		result.append(resultXml.getResult(glAccounts));
		result.append(resultXml.getResult(products));
		result.append(resultXml.getResult(productCategorys));
		result.append(resultXml.getResult(productCategoryMembers));
		result.append("\r\n");
		result.append("新增的种子数据sql：\r\n");
		result.append(resultSql.getResult(rulesCompareResult));
		result.append(resultSql.getResult(methods));
		result.append(resultSql.getResult(glAccounts));
		result.append(resultSql.getResult(products));
		result.append(resultSql.getResult(productCategorys));
		result.append(resultSql.getResult(productCategoryMembers));
		result.append("\r\n");
		result.append("测试结果的sql：\r\n");
		result.append(service.getResultTestSql(rulesCompareResult));
		return result.toString();
	}
	
	public static List<AccountJournalRule> compareRule(File newRulefile) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MyException, SQLException{
		RuleCompareService service = RuleCompareFactory.getInstance();
		List<AccountJournalRule> rulesCompareResult = service.compareRule(newRulefile);
		return rulesCompareResult;
	}
}
