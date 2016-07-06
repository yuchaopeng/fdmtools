package com.yu.fdm.rule.model;

import java.io.Serializable;

public class ProductCategory extends CompareDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String productCategoryId;
	private String productCategoryTypeId;
	private String categoryName;
	private String fromDate;
	
	public ProductCategory(String productCategoryId,
			String productCategoryTypeId, String categoryName) {
		super();
		this.productCategoryId = productCategoryId;
		this.productCategoryTypeId = productCategoryTypeId;
		this.categoryName = categoryName;
	}
	public String getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getProductCategoryTypeId() {
		return productCategoryTypeId;
	}
	public void setProductCategoryTypeId(String productCategoryTypeId) {
		this.productCategoryTypeId = productCategoryTypeId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
}
