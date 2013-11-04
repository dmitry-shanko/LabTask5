package com.epam.xmlapp.presentation.form;

import org.apache.struts.action.ActionForm;

public class TransformationForm extends ActionForm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7781810759568087366L;
	private String transformResult;
	private String categoryName;
	private String subcategoryName;
	private String productName;
	private String provider;
	private String model;
	private String color;
	private String date;
	private String price;
	private String notInStock;
	
	public TransformationForm()
	{
		
	}
	/**
	 * @return the transformResult
	 */
	public String getTransformResult() {
		return transformResult;
	}
	/**
	 * @param transformResult the transformResult to set
	 */
	public void setTransformResult(String transformResult) {
		this.transformResult = transformResult;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the subcategoryName
	 */
	public String getSubcategoryName() {
		return subcategoryName;
	}
	/**
	 * @param subcategoryName the subcategoryName to set
	 */
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the notInStock
	 */
	public String getNotInStock() {
		return notInStock;
	}
	/**
	 * @param notInStock the notInStock to set
	 */
	public void setNotInStock(String notInStock) {
		this.notInStock = notInStock;
	}
}
