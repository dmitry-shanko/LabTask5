package com.epam.xmlapp.xsl;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProductValidator
{
	private static final Pattern NAME_PATTERN = Pattern.compile("([a-zA-Z0-9[ ]]+)");
	private static final Pattern PRODUCER_PATTERN = Pattern.compile("([a-zA-Z0-9[ ]]+)");
	private static final Pattern COLOR_PATTERN = Pattern.compile("([a-zA-Z0-9]+)");
	private static final Pattern MODEL_PATTERN = Pattern.compile("([a-zA-Z0-9]+)");
	private static final Pattern PRICE_PATTERN = Pattern.compile("(([0-9]+)|([0-9]+[.][0-9]+))");
	private static final Pattern DATE_PATTERN = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-]((19|20)[0-9]{2})");
	private static final String PRICE_FORMAT = "#################.00";
	private static final Logger log = LoggerFactory.getLogger(ProductValidator.class);

	private boolean productValid = false;

	private ProductValidator()
	{
		
	}
	
	public boolean isProductValid() 
	{
		log.debug("{} public boolean isProductValid(): result={}", getClass(), productValid);
		return productValid;
	}

	public void setProductValid(boolean productValid) 
	{
		this.productValid = productValid;
	}

	public boolean isCorrectModel(String model) 
	{
		Matcher m = MODEL_PATTERN.matcher(model);
		boolean result = m.matches();
		log.debug("{} public boolean isCorrectModel(String model): model={}, result={}", new Object[]{getClass(), model, result});
		return result;
	}

	public boolean isCorrectDate(String date) 
	{
		Matcher m = DATE_PATTERN.matcher(date);
		boolean result = m.matches();
		log.debug("{} public boolean isCorrectDate(String date): date={}, result={}", new Object[]{getClass(), date, result});
		return result;
	}

	public boolean isCorrectPrice(String price) 
	{
		Matcher m = PRICE_PATTERN.matcher(price);
		boolean result = m.matches();
		log.debug("{} public boolean isCorrectPrice(String price): price={}, result={}", new Object[]{getClass(), price, result});
		return result;
	}

	public boolean isCorrectName(String name) 
	{
		Matcher m = NAME_PATTERN.matcher(name);
		boolean result = m.matches();
		log.debug("{} public boolean isCorrectName(String name): name={}, result={}", new Object[]{getClass(), name, result});
		return result;
	}

	public boolean isCorrectColor(String color) 
	{
		Matcher m = COLOR_PATTERN.matcher(color);
		boolean result = m.matches();
		log.debug("{} public boolean isCorrectColor(String color): color={}, result={}", new Object[]{getClass(), color, result});
		return result;
	}

	public boolean isCorrectProvider(String privder) 
	{
		Matcher m = PRODUCER_PATTERN.matcher(privder);
		boolean result = m.matches();
		log.debug("{} public boolean isCorrectProvider(String privder): privder={}, result={}", new Object[]{getClass(), privder, result});
		return result;
	}
	
	public String formatPrice(String price)
	{
		if ((null == price) && (!this.isCorrectPrice(price)))
		{
			return "";
		}
		return this.formatDouble(Double.parseDouble(price), PRICE_FORMAT);
	}
	
	private String formatDouble(double price, String format)
	{
		 DecimalFormat myFormatter = new DecimalFormat(format);
		 String result = myFormatter.format(price);
		 log.debug("Attempt to format double price: price={}, format={}, result={}", new Object[]{price, format, result});
		 return result;
	}
}