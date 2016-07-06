package com.yu.fdm.rule.model;

import java.io.Serializable;

public class ProductCategoryMember extends CompareDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String productCategoryId;
	private String productId;
	private String fromDate;
	
	public ProductCategoryMember(String productCategoryId, String productId) {
		super();
		this.productCategoryId = productCategoryId;
		this.productId = productId;
	}
	public String getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	
}
