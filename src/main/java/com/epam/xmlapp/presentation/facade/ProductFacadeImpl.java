package com.epam.xmlapp.presentation.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.parser.XMLParser;
import com.epam.xmlapp.xsl.XSLTransformer;

@Service(value = "productFacade")
public class ProductFacadeImpl implements ProductFacade
{
	private static final Logger log = LoggerFactory.getLogger(ProductFacadeImpl.class);

	private XMLParser parser;
	@Autowired
	private XMLParser saxXmlParser;
	@Autowired
	private XMLParser domXmlParser;
	@Autowired
	private XMLParser staxXmlParser;
	@Autowired
	private XSLTransformer xslTransformer;

	private ProductFacadeImpl()
	{
		log.debug("{} has been created", getClass());
	}


	/**
	 * @return the parser
	 */
	@Override
	public XMLParser getParser() {
		return parser;
	}
	/**
	 * @param parser the parser to set
	 */
	@Override
	public void setParser(XMLParser parser) 
	{
		this.parser = parser;
	}

	@Override
	public void setParser(String parserName) 
	{
		log.debug("Args in com.epam.xmlapp.presentation.facade.ProductFacadeImpl.setParser(String parserName): parserName={}", parserName);
		if (null != parserName)
		{
			switch (parserName.trim().toLowerCase())
			{
			case "sax":
				this.parser = saxXmlParser;
				break;
			case "dom":
				this.parser = domXmlParser;
				break;
			case "stax":
				this.parser = staxXmlParser;
				break;
			default:
				log.debug("Unknown parserName in setParser(String parserName)");
				break;
			}
		}
		log.debug("Now parser in com.epam.xmlapp.presentation.facade.ProductFacadeImpl is: parser={}", parser);
	}

	@Override
	public List<Category> parse()
	{
		log.info("Attempt to parse document using parser={}", parser);
		if (null != parser)
		{
			List<Category> categories = parser.parse();
			log.debug("Parsing finished (com.epam.xmlapp.presentation.facade.ProductFacadeImpl.parse(): parser={}, categories={}", parser, categories);
			return categories;
		}
		else
		{
			return new ArrayList<Category>();
		}		
	}

	@Override
	public String transformTemplate(int template, Map<String, String> params)
	{
		try
		{
			log.debug("Params in public String transformTemplate(int template, Map<String, String> params): template={}, params={}", template, params);
			return xslTransformer.transform(template, params);
		}
		catch (TransformerException e)
		{
			log.error("Can't transform.", e);
		}
		return null;
	}


	@Override
	public String addNewProduct(Map<String, String> params)
	{
		try
		{
			log.debug("Params in public String addNewProduct(Map<String, String> params): params={}", params);
			if (null != params)
			{
				return xslTransformer.transformAndWrite(params);
			}
			else
			{
				return xslTransformer.transform(1, null);
			}
		}
		catch (TransformerException e)
		{
			log.error("Can't save such data.", e);
		}
		return null;
	}
}
