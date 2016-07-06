package com.yu.fdm.rule.dao;

import java.sql.SQLException;
import java.util.List;

import com.yu.fdm.rule.condition.QueryCondition;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.model.GlAccount;
import com.yu.fdm.rule.model.PaymentMethod;
import com.yu.fdm.rule.model.Product;
import com.yu.fdm.rule.model.ProductCategory;
import com.yu.fdm.rule.model.ProductCategoryMember;

public interface RuleDao {
	
	List<AccountJournalRule> getRuleList() throws SQLException;
	
	List<PaymentMethod> getPaymentMethod(QueryCondition condition) throws SQLException;
	
	List<GlAccount> getGlAccount(QueryCondition condition) throws SQLException;
	
	List<Product> getProduct(QueryCondition condition) throws SQLException;
	
	List<ProductCategory> getProductCategory(QueryCondition condition) throws SQLException;
	
	List<ProductCategoryMember> getProductCategoryMember(QueryCondition condition) throws SQLException;
	
	String getCurrentRuleId() throws SQLException;
}
