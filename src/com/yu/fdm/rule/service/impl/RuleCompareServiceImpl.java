package com.yu.fdm.rule.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.base.util.CommonUtil;
import com.yu.fdm.base.util.DateUtil;
import com.yu.fdm.rule.condition.QueryCondition;
import com.yu.fdm.rule.dao.RuleDao;
import com.yu.fdm.rule.factory.ReadRuleFactory;
import com.yu.fdm.rule.factory.RuleCompareDBFactory;
import com.yu.fdm.rule.factory.RuleDaoFactory;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.model.GlAccount;
import com.yu.fdm.rule.model.PaymentMethod;
import com.yu.fdm.rule.model.Product;
import com.yu.fdm.rule.model.ProductCategory;
import com.yu.fdm.rule.model.ProductCategoryMember;
import com.yu.fdm.rule.read.service.ReadRuleService;
import com.yu.fdm.rule.service.RuleCompareDBService;
import com.yu.fdm.rule.service.RuleCompareService;

public class RuleCompareServiceImpl implements RuleCompareService{
	
	private RuleCompareDBService getDBService(){
		return RuleCompareDBFactory.getInstance();
	}

	public List<AccountJournalRule> compareRule(File file) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MyException, SQLException {
		//先已有的核算规则
		List<AccountJournalRule> old_ = getDBService().getRuleFromDB();
		
		//新的核算规则
		ReadRuleService readRuleService = ReadRuleFactory.getInstance();
		List<AccountJournalRule> new_ = readRuleService.read(file);
		
		//对新的核算规则进行比对
		List<AccountJournalRule> resultList = compareResult(old_, new_);
		
		//返回比对中不存在的部分
		return resultList;
	}
	
	private List<AccountJournalRule> compareResult(List<AccountJournalRule> old_,List<AccountJournalRule> new_) throws SQLException{
		Map<String,AccountJournalRule> oldMap = new HashMap<String, AccountJournalRule>();
		for(AccountJournalRule rule : old_){
			String key = CommonUtil.getCompareMapKey(rule);
			oldMap.put(key, rule);
		}
		
		List<AccountJournalRule> result = new ArrayList<AccountJournalRule>();
		String prevRuleId = "";
		for(AccountJournalRule rule : new_){
			String key = CommonUtil.getCompareMapKey(rule);
			if(oldMap.get(key) == null){
				String newRuleId = getNewRuleId(prevRuleId);
				prevRuleId = newRuleId;
				rule.setAccountJournalRuleId(newRuleId);
				rule.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(rule);
				
				//将此规则放到old里，标记这个规则已经被确定为新增的，后面出现的规则不能与此重复
				oldMap.put(key, rule);
			}
		}
		return result;
	}
	
	public String getNewRuleId(String currentRuleId) throws SQLException{
		if(currentRuleId == null || "".equals(currentRuleId)){
			RuleDao ruleDao = RuleDaoFactory.getRuleDao();
			currentRuleId = ruleDao.getCurrentRuleId();
		}
		return CommonUtil.getNewRuleId(currentRuleId);
	}
	
	public List<PaymentMethod> getNewPaymentMethod(List<AccountJournalRule> rules) throws SQLException{
		QueryCondition condition = null;
		List<PaymentMethod> result = new ArrayList<PaymentMethod>();
		for(AccountJournalRule rule : rules){
			String paymentMethodId = rule.getAccountingService();
			condition = new QueryCondition();
			condition.setPaymentMethodId(paymentMethodId);
			List<PaymentMethod> methods = getDBService().getPaymentMethodFromDB(condition);
			if(methods == null || methods.size() == 0){
				//如果已存在与结果中，不再重复添加
				if(checkExistPaymentMethod(result, paymentMethodId)){
					continue;
				}
				String description = rule.getAccountingServiceName();
				PaymentMethod method = new PaymentMethod(paymentMethodId, CommonUtil.PAYMENT_METHOD_TYPE_ID, "", description);
				method.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(method);
			}
		}
		return result;
	}
	
	private boolean checkExistPaymentMethod(List<PaymentMethod> methods,String paymentMethodId){
		for(PaymentMethod method : methods){
			if(paymentMethodId.equals(method.getPaymentMethodId())){
				return true;
			}
		}
		return false;
	}
	
	public List<GlAccount> getNewGlAccount(List<AccountJournalRule> rules) throws SQLException{
		QueryCondition condition = null;
		List<GlAccount> result = new ArrayList<GlAccount>();
		for(AccountJournalRule rule : rules){
			condition = new QueryCondition();
			
			//检查资产科目
			String assetsAccount = rule.getAssetsAccount();
			String assetsAccountName = rule.getAssetsAccountName();
			condition.setGlAccountId(assetsAccount);
			List<GlAccount> glAssetsAccounts = getDBService().getGlAccountFromDB(condition);
			if(glAssetsAccounts == null || glAssetsAccounts.size() == 0){
				//如果已存在与结果中，不再重复添加
				if(checkExistGlAccount(result, assetsAccount)){
					continue;
				}
				GlAccount account = new GlAccount(assetsAccount, CommonUtil.GL_ACCOUNT_TYPE_ID, assetsAccount, assetsAccountName);
				account.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(account);
			}
			
			//检查负债科目
			String debtAccount = rule.getDebtAccount();
			String debtAccountName = rule.getDebtAccountName();
			condition.setGlAccountId(debtAccount);
			List<GlAccount> glDebtAccounts = getDBService().getGlAccountFromDB(condition);
			if(glDebtAccounts == null || glDebtAccounts.size() == 0){
				//如果已存在与结果中，不再重复添加
				if(checkExistGlAccount(result, debtAccount)){
					continue;
				}
				GlAccount account = new GlAccount(debtAccount, CommonUtil.GL_ACCOUNT_TYPE_ID, debtAccount, debtAccountName);
				account.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(account);
			}
			
			//检查对方科目
			String oppositeAccount = rule.getOppositeAccount();
			String oppositeAccountName = rule.getOppositeAccountName();
			condition.setGlAccountId(oppositeAccount);
			List<GlAccount> glOppositeAccounts = getDBService().getGlAccountFromDB(condition);
			if(glOppositeAccounts == null || glOppositeAccounts.size() == 0){
				//如果已存在与结果中，不再重复添加
				if(checkExistGlAccount(result, rule.getOppositeAccount())){
					continue;
				}
				GlAccount account = new GlAccount(oppositeAccount, CommonUtil.GL_ACCOUNT_TYPE_ID, oppositeAccount, oppositeAccountName);
				account.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(account);
			}
		}
		return result;
	}
	
	private boolean checkExistGlAccount(List<GlAccount> result,String glAccountId){
		for(GlAccount account : result){
			if(glAccountId.equals(account.getGlAccountId())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Product> getNewProduct(List<AccountJournalRule> rules) throws SQLException{
		QueryCondition condition = null;
		List<Product> result = new ArrayList<Product>();
		for(AccountJournalRule rule : rules){
			String productId = rule.getAccountingType();
			String productName = rule.getAccountingTypeName();
			condition = new QueryCondition();
			condition.setProductId(productId);
			List<Product> products = getDBService().getProductFromDB(condition);
			if(products == null || products.size() == 0){
				//如果已存在与结果中，不再重复添加
				if(checkExistProduct(result, productId)){
					continue;
				}
				String description = "";
				if("是".equals(rule.getIsAssets())){
					description = CommonUtil.FDM_ASSETS;
				}else{
					description = CommonUtil.FDM_NOT_ASSETS;
				}
				Product product = new Product(productId, CommonUtil.PRODUCT_TYPE_ID, productName, description);
				product.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(product);
			}
		}
		return result;
	}
	
	private boolean checkExistProduct(List<Product> result,String productId){
		for(Product product : result){
			if(productId.equals(product.getProductId())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<ProductCategory> getNewProductCategory(
			List<AccountJournalRule> rules) throws SQLException {
		QueryCondition condition = null;
		List<ProductCategory> result = new ArrayList<ProductCategory>();
		for(AccountJournalRule rule : rules){
			String productCategoryId = rule.getAccountingType();
			String catetoryName = rule.getAccountingTypeName();
			condition = new QueryCondition();
			condition.setProductCategoryId(productCategoryId);
			List<ProductCategory> productCategorys = getDBService().getProductCategoryFromDB(condition);
			if(productCategorys == null || productCategorys.size() == 0){
				//如果已存在与结果中，不再重复添加
				if(checkExistProductCategory(result, productCategoryId)){
					continue;
				}
				ProductCategory category = new ProductCategory(productCategoryId, CommonUtil.PRODUCT_CATEGORY_TYPE_ID, catetoryName);
				category.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(category);
			}
		}
		return result;
	}
	
	private boolean checkExistProductCategory(List<ProductCategory> result,String productCategoryId){
		for(ProductCategory category : result){
			if(productCategoryId.equals(category.getProductCategoryId())){
				return true;
			}
		}
		return false;
	}
	

	@Override
	public List<ProductCategoryMember> getNewProductCategoryMember(
			List<AccountJournalRule> rules) throws SQLException {
		QueryCondition condition = null;
		List<ProductCategoryMember> result = new ArrayList<ProductCategoryMember>();
		for(AccountJournalRule rule : rules){
			String productCategoryId = rule.getAccountingType();
			String productId = rule.getAccountingType();
			condition = new QueryCondition();
			condition.setProductId(productId);
			condition.setProductCategoryId(productCategoryId);
			List<ProductCategoryMember> categoryMembers = getDBService().getProductCategoryMemberFromDB(condition);
			if(categoryMembers == null || categoryMembers.size() == 0){
				//如果已存在与结果中，不再重复添加
				if(checkExistProductCategoryMember(result, productCategoryId,productId)){
					continue;
				}
				ProductCategoryMember member = new ProductCategoryMember(productCategoryId, productId);
				member.setFromDate(DateUtil.getCurrentDateFullSimple());
				result.add(member);
			}
		}
		return result;
	}
	
	private boolean checkExistProductCategoryMember(List<ProductCategoryMember> result,String productCategoryId,String productId){
		for(ProductCategoryMember categoryMember : result){
			if(productId.equals(categoryMember.getProductId()) && productCategoryId.equals(categoryMember.getProductCategoryId())){
				return true;
			}
		}
		return false;
	}
	
	public String getResultTestSql(List<AccountJournalRule> rules){
		StringBuffer result = new StringBuffer();
		
		for(AccountJournalRule rule : rules){
			result.append("select * from ACCOUNT_JOURNAL_RULE WHERE ACCOUNTING_TYPE = '").append(rule.getAccountingType()).append("' AND ACCOUNTING_SERVICE = '").append(rule.getAccountingService()).append("';\r\n");
		}
		return result.toString();
	}
	
}
