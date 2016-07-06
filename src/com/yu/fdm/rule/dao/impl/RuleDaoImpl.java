package com.yu.fdm.rule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yu.fdm.base.jdbc.JdbcUtil;
import com.yu.fdm.base.util.CommonUtil;
import com.yu.fdm.rule.condition.QueryCondition;
import com.yu.fdm.rule.dao.RuleDao;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.model.GlAccount;
import com.yu.fdm.rule.model.PaymentMethod;
import com.yu.fdm.rule.model.Product;
import com.yu.fdm.rule.model.ProductCategory;
import com.yu.fdm.rule.model.ProductCategoryMember;

public class RuleDaoImpl implements RuleDao{

	@Override
	public List<AccountJournalRule> getRuleList() throws SQLException {
		String sql = "select ACCOUNT_JOURNAL_RULE_ID,VARIANT_ID,ACCOUNTING_TYPE,ACCOUNTING_SERVICE,SEQ_ID,CURRENCY_UOM_ID,ASSETS_ACCOUNT,DEBT_ACCOUNT,OPPOSITE_ACCOUNT from ACCOUNT_JOURNAL_RULE ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AccountJournalRule> list = new ArrayList<AccountJournalRule>();
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String accountJournalRuleId = rs.getString("ACCOUNT_JOURNAL_RULE_ID");
				String variantId = rs.getString("VARIANT_ID");
				String accountingType = rs.getString("ACCOUNTING_TYPE");
				String accountingService = rs.getString("ACCOUNTING_SERVICE");
				String seqId = rs.getString("SEQ_ID");
				String currencyUomId = rs.getString("CURRENCY_UOM_ID");
				String assetsAccount = rs.getString("ASSETS_ACCOUNT");
				String debtAccount = rs.getString("DEBT_ACCOUNT");
				String oppositeAccount = rs.getString("OPPOSITE_ACCOUNT");
				
				AccountJournalRule rule = new AccountJournalRule();
				rule.setAccountJournalRuleId(accountJournalRuleId);
				rule.setVariantId(variantId);
				rule.setAccountingType(accountingType);
				rule.setAccountingService(accountingService);
				rule.setSeqId(seqId);
				rule.setCurrencyUomId(currencyUomId);
				rule.setAssetsAccount(assetsAccount);
				rule.setDebtAccount(debtAccount);
				rule.setOppositeAccount(oppositeAccount);
				
				list.add(rule);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return list;
	}
	
	public List<PaymentMethod> getPaymentMethod(QueryCondition condition) throws SQLException{
		StringBuffer sql = new StringBuffer("select PAYMENT_METHOD_ID,PAYMENT_METHOD_TYPE_ID,GL_ACCOUNT_ID,DESCRIPTION from PAYMENT_METHOD where 1=1");
		if(CommonUtil.isNotEmpty(condition.getPaymentMethodId())){
			sql.append(" and PAYMENT_METHOD_ID = '").append(condition.getPaymentMethodId()).append("'");
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PaymentMethod> methods = new ArrayList<PaymentMethod>();
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String paymentMethodId = rs.getString("PAYMENT_METHOD_ID");
				String paymentMethodTypeId = rs.getString("PAYMENT_METHOD_TYPE_ID");
				String glAccountId = rs.getString("GL_ACCOUNT_ID");
				String description = rs.getString("DESCRIPTION");
				PaymentMethod method = new PaymentMethod(paymentMethodId, paymentMethodTypeId, glAccountId, description);
				methods.add(method);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return methods;
	}
	
	public List<GlAccount> getGlAccount(QueryCondition condition) throws SQLException{
		StringBuffer sql = new StringBuffer("select GL_ACCOUNT_ID,GL_ACCOUNT_TYPE_ID,ACCOUNT_CODE,ACCOUNT_NAME from GL_ACCOUNT where 1=1");
		if(CommonUtil.isNotEmpty(condition.getGlAccountId())){
			sql.append(" and GL_ACCOUNT_ID = '").append(condition.getGlAccountId()).append("'");
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<GlAccount> glAccounts = new ArrayList<GlAccount>();
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String glAccountId = rs.getString("GL_ACCOUNT_ID");
				String glAccountTypeId = rs.getString("GL_ACCOUNT_TYPE_ID");
				String accountCode = rs.getString("ACCOUNT_CODE");
				String accountName = rs.getString("ACCOUNT_NAME");
				GlAccount glAccount = new GlAccount(glAccountId, glAccountTypeId, accountCode, accountName);
				glAccounts.add(glAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return glAccounts;
	}

	@Override
	public String getCurrentRuleId() throws SQLException {
		String sql = "select ACCOUNT_JOURNAL_RULE_ID from ACCOUNT_JOURNAL_RULE order by ACCOUNT_JOURNAL_RULE_ID desc";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String currentRuleId = "";
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				currentRuleId = rs.getString("ACCOUNT_JOURNAL_RULE_ID");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return currentRuleId;
	}

	@Override
	public List<Product> getProduct(QueryCondition condition)
			throws SQLException {
		StringBuffer sql = new StringBuffer("select PRODUCT_ID,PRODUCT_TYPE_ID,PRODUCT_NAME,DESCRIPTION from PRODUCT where 1=1");
		if(CommonUtil.isNotEmpty(condition.getProductId())){
			sql.append(" and PRODUCT_ID = '").append(condition.getProductId()).append("'");
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Product> products = new ArrayList<Product>();
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String productId = rs.getString("PRODUCT_ID");
				String productTypeId = rs.getString("PRODUCT_TYPE_ID");
				String productName = rs.getString("PRODUCT_NAME");
				String description = rs.getString("DESCRIPTION");
				Product product = new Product(productId, productTypeId, productName, description);
				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return products;
	}

	@Override
	public List<ProductCategory> getProductCategory(QueryCondition condition) throws SQLException {
		StringBuffer sql = new StringBuffer("select PRODUCT_CATEGORY_ID,PRODUCT_CATEGORY_TYPE_ID,CATEGORY_NAME from PRODUCT_CATEGORY where 1=1");
		if(CommonUtil.isNotEmpty(condition.getProductCategoryId())){
			sql.append(" and PRODUCT_CATEGORY_ID = '").append(condition.getProductCategoryId()).append("'");
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProductCategory> productCategorys = new ArrayList<ProductCategory>();
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String productCategoryId = rs.getString("PRODUCT_CATEGORY_ID");
				String productCategoryTypeId = rs.getString("PRODUCT_CATEGORY_TYPE_ID");
				String categoryName = rs.getString("CATEGORY_NAME");
				ProductCategory category = new ProductCategory(productCategoryId, productCategoryTypeId, categoryName);
				productCategorys.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return productCategorys;
	}

	@Override
	public List<ProductCategoryMember> getProductCategoryMember(QueryCondition condition) throws SQLException {
		StringBuffer sql = new StringBuffer("select PRODUCT_ID,PRODUCT_CATEGORY_ID from PRODUCT_CATEGORY_MEMBER where 1=1");
		if(CommonUtil.isNotEmpty(condition.getProductId())){
			sql.append(" and PRODUCT_ID = '").append(condition.getProductId()).append("'");
		}
		if(CommonUtil.isNotEmpty(condition.getProductCategoryId())){
			sql.append(" and PRODUCT_CATEGORY_ID = '").append(condition.getProductCategoryId()).append("'");
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProductCategoryMember> productCategoryMembers = new ArrayList<ProductCategoryMember>();
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				String productId = rs.getString("PRODUCT_ID");
				String productCategoryId = rs.getString("PRODUCT_CATEGORY_ID");
				ProductCategoryMember categoryMember = new ProductCategoryMember(productCategoryId, productId);
				productCategoryMembers.add(categoryMember);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return productCategoryMembers;
	}
}
