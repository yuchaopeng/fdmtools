package com.yu.fdm.rule.service;

import java.sql.SQLException;
import java.util.List;

import com.yu.fdm.rule.condition.QueryCondition;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.model.GlAccount;
import com.yu.fdm.rule.model.PaymentMethod;
import com.yu.fdm.rule.model.Product;
import com.yu.fdm.rule.model.ProductCategory;
import com.yu.fdm.rule.model.ProductCategoryMember;

public interface RuleCompareDBService {
	/**
	 * 获取数据库中已存在的核算规则
	 * @return
	 */
	List<AccountJournalRule> getRuleFromDB() throws SQLException;
	
	List<PaymentMethod> getPaymentMethodFromDB(QueryCondition condition) throws SQLException;
	
	List<GlAccount> getGlAccountFromDB(QueryCondition condition) throws SQLException;
	
	List<Product> getProductFromDB(QueryCondition condition) throws SQLException;
	
	List<ProductCategory> getProductCategoryFromDB(QueryCondition condition) throws SQLException;
	
	List<ProductCategoryMember> getProductCategoryMemberFromDB(QueryCondition condition) throws SQLException;
}
