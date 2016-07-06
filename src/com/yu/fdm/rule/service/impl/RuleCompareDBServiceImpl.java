package com.yu.fdm.rule.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.yu.fdm.rule.condition.QueryCondition;
import com.yu.fdm.rule.dao.RuleDao;
import com.yu.fdm.rule.factory.RuleDaoFactory;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.model.GlAccount;
import com.yu.fdm.rule.model.PaymentMethod;
import com.yu.fdm.rule.model.Product;
import com.yu.fdm.rule.model.ProductCategory;
import com.yu.fdm.rule.model.ProductCategoryMember;
import com.yu.fdm.rule.service.RuleCompareDBService;

public class RuleCompareDBServiceImpl implements RuleCompareDBService{
	
	private RuleDao getRuleDao(){
		return RuleDaoFactory.getRuleDao();
	}
	
	public List<AccountJournalRule> getRuleFromDB() throws SQLException{
		return getRuleDao().getRuleList();
	}
	
	public List<PaymentMethod> getPaymentMethodFromDB(QueryCondition condition) throws SQLException{
		return getRuleDao().getPaymentMethod(condition);
	}
	
	public List<GlAccount> getGlAccountFromDB(QueryCondition condition) throws SQLException{
		return getRuleDao().getGlAccount(condition);
	}
	
	public List<Product> getProductFromDB(QueryCondition condition) throws SQLException{
		return getRuleDao().getProduct(condition);
	}
	
	public List<ProductCategory> getProductCategoryFromDB(QueryCondition condition) throws SQLException{
		return getRuleDao().getProductCategory(condition);
	}
	
	public List<ProductCategoryMember> getProductCategoryMemberFromDB(QueryCondition condition) throws SQLException{
		return getRuleDao().getProductCategoryMember(condition);
	}
}
