package com.epam.xmlapp.util;

public enum GlobalConstants 
{
	ENCODING_PARAM("encoding"),
	CATEGORY_NAME_PARAM_TRANSFORM("categoryName"),
	SUBCATEGORYNAME_PARAM_TRANSFORM("subcategoryName"),
	PRODUCTNAME_PARAM_TRANSFORM("productName"),
	PROVIDER_PARAM_TRANSFORM("provider"),
	MODEL_PARAM_TRANSFORM("model"),
	COLOR_PARAM_TRANSFORM("color"),
	DATE_PARAM_TRANSFORM("date"),
	PRICE_PARAM_TRANSFORM("price"),
	NOTINSTOCK_PARAM_TRANSFORM("notInStock"),
	ADD_RESULT("result"),
	;

	private String content;
	private GlobalConstants (String content) 
	{
		this.content = content;
	}

	public String getContent()
	{
		return content;
	}
}
