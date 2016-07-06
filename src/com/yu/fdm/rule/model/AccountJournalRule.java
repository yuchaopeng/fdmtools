package com.yu.fdm.rule.model;

import java.io.Serializable;

public class AccountJournalRule extends CompareDataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int rowNum;

	private String variantId;
	
	private String systemId;
	
	private String accountJournalRuleId;
	
	private String accountingType;
	
	private String accountingTypeName;
	
	/**
	 * 是否资产类
	 */
	private String isAssets;
	
	private String accountingService;
	
	private String accountingServiceName;
	
	private String seqId;
	
	private String currencyUomId;
	
	private String assetsAccountName;
	
	private String assetsAccount;
	
	private String debtAccountName;
	
	private String debtAccount;
	
	private String oppositeAccountName;
	
	private String oppositeAccount;

	private String fromDate;
	
	private String remark;
	
	public AccountJournalRule() {
		super();
	}

	public AccountJournalRule(String variantId, String accountingType,
			String accountingService, String seqId, String currencyUomId,
			String assetsAccount, String debtAccount, String oppositeAccount) {
		super();
		this.variantId = variantId;
		this.accountingType = accountingType;
		this.accountingService = accountingService;
		this.seqId = seqId;
		this.currencyUomId = currencyUomId;
		this.assetsAccount = assetsAccount;
		this.debtAccount = debtAccount;
		this.oppositeAccount = oppositeAccount;
		
	}
	
	public AccountJournalRule(int rowNum,String variantId,String systemId, String accountingType,
			String accountingTypeName, String isAssets,
			String accountingService, String accountingServiceName,
			String seqId, String currencyUomId, String assetsAccountName,
			String assetsAccount, String debtAccountName, String debtAccount,
			String oppositeAccountName, String oppositeAccount,String remark) {
		super();
		this.rowNum = rowNum;
		this.variantId = variantId;
		this.systemId = systemId;
		this.accountingType = accountingType;
		this.accountingTypeName = accountingTypeName;
		this.isAssets = isAssets;
		this.accountingService = accountingService;
		this.accountingServiceName = accountingServiceName;
		this.seqId = seqId;
		this.currencyUomId = currencyUomId;
		this.assetsAccountName = assetsAccountName;
		this.assetsAccount = assetsAccount;
		this.debtAccountName = debtAccountName;
		this.debtAccount = debtAccount;
		this.oppositeAccountName = oppositeAccountName;
		this.oppositeAccount = oppositeAccount;
		this.remark = remark;
	}
	
	
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(String variantId) {
		this.variantId = variantId;
	}

	public String getAccountJournalRuleId() {
		return accountJournalRuleId;
	}

	public void setAccountJournalRuleId(String accountJournalRuleId) {
		this.accountJournalRuleId = accountJournalRuleId;
	}

	public String getAccountingType() {
		return accountingType;
	}

	public void setAccountingType(String accountingType) {
		this.accountingType = accountingType;
	}

	public String getAccountingService() {
		return accountingService;
	}

	public void setAccountingService(String accountingService) {
		this.accountingService = accountingService;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getCurrencyUomId() {
		return currencyUomId;
	}

	public void setCurrencyUomId(String currencyUomId) {
		this.currencyUomId = currencyUomId;
	}

	public String getAssetsAccount() {
		return assetsAccount;
	}

	public void setAssetsAccount(String assetsAccount) {
		this.assetsAccount = assetsAccount;
	}

	public String getDebtAccount() {
		return debtAccount;
	}

	public void setDebtAccount(String debtAccount) {
		this.debtAccount = debtAccount;
	}

	public String getOppositeAccount() {
		return oppositeAccount;
	}

	public void setOppositeAccount(String oppositeAccount) {
		this.oppositeAccount = oppositeAccount;
	}

	public String getAccountingTypeName() {
		return accountingTypeName;
	}

	public void setAccountingTypeName(String accountingTypeName) {
		this.accountingTypeName = accountingTypeName;
	}

	public String getIsAssets() {
		return isAssets;
	}

	public void setIsAssets(String isAssets) {
		this.isAssets = isAssets;
	}

	public String getAccountingServiceName() {
		return accountingServiceName;
	}

	public void setAccountingServiceName(String accountingServiceName) {
		this.accountingServiceName = accountingServiceName;
	}

	public String getAssetsAccountName() {
		return assetsAccountName;
	}

	public void setAssetsAccountName(String assetsAccountName) {
		this.assetsAccountName = assetsAccountName;
	}

	public String getDebtAccountName() {
		return debtAccountName;
	}

	public void setDebtAccountName(String debtAccountName) {
		this.debtAccountName = debtAccountName;
	}

	public String getOppositeAccountName() {
		return oppositeAccountName;
	}

	public void setOppositeAccountName(String oppositeAccountName) {
		this.oppositeAccountName = oppositeAccountName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
}
