package com.yu.fdm.rule.model;

import java.io.Serializable;

public class PaymentMethod extends CompareDataModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String paymentMethodId;
	private String paymentMethodTypeId;
	private String glAccountId;
	private String description;
	private String fromDate;
	public PaymentMethod(String paymentMethodId, String paymentMethodTypeId,
			String glAccountId, String description) {
		super();
		this.paymentMethodId = paymentMethodId;
		this.paymentMethodTypeId = paymentMethodTypeId;
		this.glAccountId = glAccountId;
		this.description = description;
	}
	public String getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public String getPaymentMethodTypeId() {
		return paymentMethodTypeId;
	}
	public void setPaymentMethodTypeId(String paymentMethodTypeId) {
		this.paymentMethodTypeId = paymentMethodTypeId;
	}
	public String getGlAccountId() {
		return glAccountId;
	}
	public void setGlAccountId(String glAccountId) {
		this.glAccountId = glAccountId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
}
