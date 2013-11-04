package com.epam.xmlapp.xsl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.xmlapp.parser.DomXmlParser;
import com.epam.xmlapp.util.GlobalConstants;

public class XSLTransformer 
{
	private static final Logger log = LoggerFactory.getLogger(XSLTransformer.class);
	private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final TransformerFactory factory = TransformerFactory.newInstance();

	private ProductValidator validator;
	private DomXmlParser domXmlParser;
	private String categoryTemplate;
	private String subcategoryTemplate;
	private String productTemplate;
	private String addProductTemplate;
	private String saveProductTemplate;
	private String validationTemplate;
	private File xmlFile;

	private XSLTransformer()
	{

	}

	protected void init()
	{
		xmlFile = new File(domXmlParser.getXmlFileName());
	}
	/**
	 * @return the categoryTemplate
	 */
	public String getCategoryTemplate() {
		return categoryTemplate;
	}
	/**
	 * @param categoryTemplate the categoryTemplate to set
	 */
	public void setCategoryTemplate(String categoryTemplate) {
		this.categoryTemplate = categoryTemplate;
	}
	/**
	 * @return the subcategoryTemplate
	 */
	public String getSubcategoryTemplate() {
		return subcategoryTemplate;
	}
	/**
	 * @param subcategoryTemplate the subcategoryTemplate to set
	 */
	public void setSubcategoryTemplate(String subcategoryTemplate) {
		this.subcategoryTemplate = subcategoryTemplate;
	}
	/**
	 * @return the productTemplate
	 */
	public String getProductTemplate() {
		return productTemplate;
	}
	/**
	 * @param productTemplate the productTemplate to set
	 */
	public void setProductTemplate(String productTemplate) {
		this.productTemplate = productTemplate;
	}

	/**
	 * @return the addProductTemplate
	 */
	public String getAddProductTemplate() {
		return addProductTemplate;
	}
	/**
	 * @param addProductTemplate the addProductTemplate to set
	 */
	public void setAddProductTemplate(String addProductTemplate) {
		this.addProductTemplate = addProductTemplate;
	}
	/**
	 * @return the saveProductTemplate
	 */
	public String getSaveProductTemplate() {
		return saveProductTemplate;
	}
	/**
	 * @param saveProductTemplate the saveProductTemplate to set
	 */
	public void setSaveProductTemplate(String saveProductTemplate) {
		this.saveProductTemplate = saveProductTemplate;
	}

	/**
	 * @return the validationTemplate
	 */
	public String getValidationTemplate() {
		return validationTemplate;
	}
	/**
	 * @param validationTemplate the validationTemplate to set
	 */
	public void setValidationTemplate(String validationTemplate) {
		this.validationTemplate = validationTemplate;
	}

	public ProductValidator getValidator()
	{
		return this.validator;
	}

	public void setValidator(ProductValidator validator)
	{
		this.validator = validator;
	}

	/**
	 * @return the domXmlParser
	 */
	public DomXmlParser getDomXmlParser() {
		return domXmlParser;
	}
	/**
	 * @param domXmlParser the domXmlParser to set
	 */
	public void setDomXmlParser(DomXmlParser domXmlParser) {
		this.domXmlParser = domXmlParser;
	}

	public String transform(int template, Map<String, String> params) throws TransformerException
	{		
		String fileName = null;		
		switch (template)
		{
		case XSLConstants.CATEGORY_TEMPLATE:
			fileName = categoryTemplate;
			break;
		case XSLConstants.SUBCATEGORY_TEMPLATE:
			fileName = subcategoryTemplate;
			break;
		case XSLConstants.PRODUCT_TEMPLATE:
			fileName = productTemplate;
			break;
		case XSLConstants.ADD_PRODUCT_TEMPLATE:
			fileName = addProductTemplate;
			break;
		case XSLConstants.SAVE_PRODUCT_TEMPLATE:
			fileName = productTemplate;
			break;
		case XSLConstants.VALIDATION_TEMPLATE:
			fileName = productTemplate;
		}
		log.info("{}: template={}", getClass(), fileName);
		if (null != fileName)
		{
			lock.readLock().lock();
			try 
			{				
				StreamSource styleSource = new StreamSource(fileName);
				try
				{
					Templates templates = factory.newTemplates(styleSource);
					Transformer t = templates.newTransformer();
					if (params != null)
					{
						switch (template)
						{
						case XSLConstants.CATEGORY_TEMPLATE:
							break;
						case XSLConstants.SUBCATEGORY_TEMPLATE:
							String value = params.get(GlobalConstants.CATEGORY_NAME_PARAM_TRANSFORM.getContent());
							if (value != null && value.length() > 0)
							{
								t.setParameter(XSLConstants.NODE_CATEGORYNAME_ATTR, value);
							}
							else
							{
								return null;
							}
							break;
						case XSLConstants.SAVE_PRODUCT_TEMPLATE:
						case XSLConstants.VALIDATION_TEMPLATE:
						case XSLConstants.ADD_PRODUCT_TEMPLATE:
						case XSLConstants.PRODUCT_TEMPLATE:
							value = params.get(GlobalConstants.CATEGORY_NAME_PARAM_TRANSFORM.getContent());
							if (value != null && value.length() > 0)
							{
								t.setParameter(XSLConstants.NODE_CATEGORYNAME_ATTR, value);
							}
							else
							{
								return null;
							}
							value = params.get(GlobalConstants.SUBCATEGORYNAME_PARAM_TRANSFORM.getContent());
							if (value != null && value.length() > 0)
							{
								t.setParameter(XSLConstants.NODE_SUBCATEGORYNAME_ATTR, value);
							}
							else
							{
								return null;
							}
							break;
						}
					}
					else
					{
						if (template != XSLConstants.CATEGORY_TEMPLATE)
						{
							return null;
						}
					}
					log.debug("Attempt to transform template={}", fileName);
					return this.read(t);
				}
				catch (TransformerConfigurationException e)
				{
					log.error("Can't transform xmlFileName={} using template={}", domXmlParser.getXmlFileName(), fileName);
					log.error("Exception catched: ", e);
				}				
			} 
			finally
			{
				lock.readLock().unlock();					
			}
		}
		return null;
	}

	public String transformAndWrite(Map<String, String> params) throws TransformerException
	{
		long lastModified;
		String fileName = validationTemplate;	
		String content = null;
		log.info("{}: template={}", getClass(), fileName);
		Transformer t = null;
		if (null != fileName)
		{
			lock.readLock().lock();
			try 
			{				
				StreamSource styleSource = new StreamSource(fileName);
				lastModified = xmlFile.lastModified();
				try
				{
					Templates template = factory.newTemplates(styleSource);
					t = template.newTransformer();
					if (params != null)
					{
						t.setParameter(XSLConstants.NODE_CATEGORYNAME_ATTR, params.get(GlobalConstants.CATEGORY_NAME_PARAM_TRANSFORM.getContent()));
						t.setParameter(XSLConstants.NODE_SUBCATEGORYNAME_ATTR, params.get(GlobalConstants.SUBCATEGORYNAME_PARAM_TRANSFORM.getContent()));
						t.setParameter(XSLConstants.NODE_PRODUCTNAME_ATTR, params.get(GlobalConstants.PRODUCTNAME_PARAM_TRANSFORM.getContent()));
						t.setParameter(XSLConstants.NODE_PROVIDER_VALUE, params.get(GlobalConstants.PROVIDER_PARAM_TRANSFORM.getContent()));
						t.setParameter(XSLConstants.NODE_MODEL_VALUE, params.get(GlobalConstants.MODEL_PARAM_TRANSFORM.getContent()));
						t.setParameter(XSLConstants.NODE_COLOR_VALUE, params.get(GlobalConstants.COLOR_PARAM_TRANSFORM.getContent()));
						t.setParameter(XSLConstants.NODE_DATE_VALUE, params.get(GlobalConstants.DATE_PARAM_TRANSFORM.getContent()));	
						t.setParameter(XSLConstants.VALIDATOR, validator);
						if (params.get(GlobalConstants.NOTINSTOCK_PARAM_TRANSFORM.getContent()) != null)
						{
							t.setParameter(XSLConstants.NODE_NOTINSTOCK_VALUE, params.get(GlobalConstants.NOTINSTOCK_PARAM_TRANSFORM.getContent()));
						}
						if (params.get(GlobalConstants.PRICE_PARAM_TRANSFORM.getContent()) != null)
						{
							t.setParameter(XSLConstants.NODE_PRICE_VALUE, params.get(GlobalConstants.PRICE_PARAM_TRANSFORM.getContent()));
						}						
						log.debug("params={}", params);
						log.debug("Attempt to transform template={}", fileName);
						content = this.read(t);
					}					
				}
				catch (TransformerConfigurationException e)
				{
					log.error("Can't transform xmlFileName={} using template={}", domXmlParser.getXmlFileName(), fileName);
					log.error("Exception catched: ", e);
				}
			} 
			finally
			{
				lock.readLock().unlock();
			}
			if (null != content)
			{
				if (validator.isProductValid())
				{
					try
					{					
						lock.writeLock().lock();
						if (lastModified != xmlFile.lastModified() && t != null)
						{
							content = this.read(t);
						}
						return this.write(content, params);
					}
					catch (TransformerException e) 
					{
						log.error("Can't write xmlFile", e);
						throw e;
					}

					finally
					{
						lock.writeLock().unlock();
					}
				}
				else
				{
					log.info("Attempt to write not a valid product. Writing cancelled");
					return content;
				}
			}
		}
		return this.transform(XSLConstants.CATEGORY_TEMPLATE, null);
	}


	private String read(Transformer transformer) throws TransformerException
	{
		StreamResult sr = null;
		try
		{			
			sr = new StreamResult(new StringWriter());
			transformer.transform(new StreamSource(domXmlParser.getXmlFileName()), sr);
			return ((StringWriter)sr.getWriter()).toString();
		}
		finally
		{
			if(sr.getWriter() != null)
			{
				try 
				{
					sr.getWriter().close();
				} 
				catch (IOException e) 
				{
					log.error("Error trying to close {}", sr.getWriter());
					log.error("Exception catched: ", e);
				}
			}
		}
	}

	private String write(String content, Map<String, String> params) throws TransformerException
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(domXmlParser.getXmlFileName());
			log.debug("Attempt to write resul={}", content);
			writer.write(content);
			params.put(GlobalConstants.ADD_RESULT.getContent(), "true");
			writer.flush();
			writer.close();		
			return transform(3, params);
		} 
		catch (IOException e) 
		{
			throw new TransformerException("Can't write transformed xmlFile", e);
		}
		finally
		{
			try
			{
				if (writer != null)
				{
					writer.close();
				}
			}
			catch (IOException e)
			{
				log.error("Can't close xmlFile", e);
			}
		}
	}

}
