package com.yu.fdm.rule.model;

import java.io.Serializable;

public class GlAccount extends CompareDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String glAccountId;
	private String glAccountTypeId;
	private String accountCode;
	private String accountName;
	private String fromDate;
	public GlAccount(String glAccountId, String glAccountTypeId,
			String accountCode, String accountName) {
		super();
		this.glAccountId = glAccountId;
		this.glAccountTypeId = glAccountTypeId;
		this.accountCode = accountCode;
		this.accountName = accountName;
	}
	public String getGlAccountId() {
		return glAccountId;
	}
	public void setGlAccountId(String glAccountId) {
		this.glAccountId = glAccountId;
	}
	public String getGlAccountTypeId() {
		return glAccountTypeId;
	}
	public void setGlAccountTypeId(String glAccountTypeId) {
		this.glAccountTypeId = glAccountTypeId;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	
}
