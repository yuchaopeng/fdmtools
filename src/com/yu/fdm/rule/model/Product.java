package com.yu.fdm.rule.model;

import java.io.Serializable;

public class Product extends CompareDataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String productId;
	private String productTypeId;
	private String description;
	private String productName;
	private String fromDate;
	
	public Product(String productId, String productTypeId, String productName,
			String description) {
		super();
		this.productId = productId;
		this.productTypeId = productTypeId;
		this.productName = productName;
		this.description = description;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
