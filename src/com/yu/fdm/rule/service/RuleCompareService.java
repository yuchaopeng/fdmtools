package com.yu.fdm.rule.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.model.GlAccount;
import com.yu.fdm.rule.model.PaymentMethod;
import com.yu.fdm.rule.model.Product;
import com.yu.fdm.rule.model.ProductCategory;
import com.yu.fdm.rule.model.ProductCategoryMember;

public interface RuleCompareService {
	
	/**
	 * 根据选择对应路径里的“过账引擎核算规则”，跟数据库里已有的规则进行比对，返回数据库中不存在的数据
	 * @param rules
	 * @param file
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public List<AccountJournalRule> compareRule(File file) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MyException,SQLException;
	
	/**
	 * 根据规则校验的结果
	 * 获取该结果下，支付方式编码不存在的记录总和
	 * @param rules
	 * @return
	 */
	List<PaymentMethod> getNewPaymentMethod(List<AccountJournalRule> rules) throws SQLException;
	
	/**
	 * 根据规则校验结果
	 * 获取该结果下，科目编号不存在的记录总和
	 * @param rules
	 * @return
	 */
	List<GlAccount> getNewGlAccount(List<AccountJournalRule> rules) throws SQLException;
	
	List<Product> getNewProduct(List<AccountJournalRule> rules) throws SQLException;
	
	List<ProductCategory> getNewProductCategory(List<AccountJournalRule> rules) throws SQLException;
	
	List<ProductCategoryMember> getNewProductCategoryMember(List<AccountJournalRule> rules) throws SQLException;
	
	String getResultTestSql(List<AccountJournalRule> rules);
}
