package com.epam.xmlapp.presentation.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.MappingDispatchAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.presentation.facade.ProductFacade;
import com.epam.xmlapp.presentation.form.ProductForm;
import com.epam.xmlapp.presentation.form.TransformationForm;
import com.epam.xmlapp.util.GlobalConstants;
import com.epam.xmlapp.xsl.XSLConstants;

public class ProductAction extends MappingDispatchAction
{	
	private static final Logger log = LoggerFactory.getLogger(ProductAction.class);

	private static final String MAIN_PAGE = "mainpage";
	private static final String PRODUCT_LIST_PAGE = "productList";
	private static final String PARSER_PAGE = "parser";
	private static final String TRANSFORM_PAGE = "transform";
	private static final String PREVIOUS_PAGE = "previousPage";
	private static final String ERROR_PAGE = "error";
	private static final String TRANSFORM_VIEW = "transformview";

	private ProductFacade productFacade;

	public ProductAction()
	{
		log.info("com.epam.xmlapp.presentation.action.ProductAction has been created");
	}

	public void setProductFacade(ProductFacade productFacade)
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public void setProductFacade(ProductFacade productFacade): productFacade={}, ProductAction={}", productFacade, this);
		this.productFacade = productFacade;
	}

	public ActionForward productsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward productsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/productsList");	
		String target = ERROR_PAGE;
		ProductForm productForm = (ProductForm) actionForm;
		if (null != productForm)
		{
			if (null != productFacade)
			{
				List<Category> categories = productFacade.parse();
				log.debug("Date got from productFacade.parse() in action=/productsList: categories={}", categories);
				productForm.setCategoryList(categories);
				target = PRODUCT_LIST_PAGE;
			}
			else
			{
				log.warn("Error in springframework. Bean productFacade was not linked to com.epam.xmlapp.presentation.action.ProductAction: productFacade={}", productFacade);
				productForm.setCategoryList(new ArrayList<Category>());
			}
		}
		else
		{
			log.warn("productForm in /productsList is null.");
		}
		log.info("/productsList action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /productsList action: target={}", target);
		return actionMapping.findForward(target);
	}

	public ActionForward parser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward parser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/parser");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, PARSER_PAGE);
		ProductForm productForm = (ProductForm) actionForm;
		if (null != productForm)
		{
			target = PARSER_PAGE;
		}
		else
		{
			log.warn("productForm in /parser is null.");
		}
		log.info("/parser action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /parser action: target={}", target);
		return new ActionRedirect(actionMapping.findForward(target));
	}

	public ActionForward changeParser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward changeParser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/changeParser");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, MAIN_PAGE);
		ProductForm productForm = (ProductForm) actionForm;
		if (null != productForm)
		{
			target = PARSER_PAGE;
			String parserName = productForm.getParserName();
			log.debug("Args in /changeParser action: parserName={}", parserName);
			if (parserName != null) 
			{
				productFacade.setParser(parserName);
				if (null != productFacade.getParser())
				{
					target = MAIN_PAGE;
				}
			}		
			else
			{
				log.debug("parserName in /changeParser is null");
			}
		}
		else
		{
			log.warn("productForm in /changeParser is null.");
		}
		log.info("/changeParser action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /changeParser action: target={}", target);
		return new ActionRedirect(actionMapping.findForward(target));
	}

	public ActionForward transform(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward transform(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/transform");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, TRANSFORM_PAGE);
		TransformationForm transformForm = (TransformationForm) actionForm;
		if (null != transformForm)
		{		
			transformForm.setTransformResult(productFacade.transformTemplate(XSLConstants.CATEGORY_TEMPLATE, null));
			target = TRANSFORM_PAGE;			
		}
		else
		{
			log.warn("transformForm in /transform is null.");
		}
		log.info("/transform action finished.");
		log.debug("actionMapping.findForward(target) in /transform action: target={}", target);
		return actionMapping.findForward(target);
	}

	public ActionForward transformCategory(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward transformCategory(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/transformcategory");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, TRANSFORM_PAGE);
		TransformationForm transformForm = (TransformationForm) actionForm;
		if (null != transformForm)
		{		
			Map<String, String> params = new HashMap<String, String>();
			log.debug("action=/transformCategory params: categoryName={}", transformForm.getCategoryName());
			params.put(GlobalConstants.CATEGORY_NAME_PARAM_TRANSFORM.getContent(), transformForm.getCategoryName());
			transformForm.setTransformResult(productFacade.transformTemplate(XSLConstants.SUBCATEGORY_TEMPLATE, params));
			target = TRANSFORM_PAGE;			
		}
		else
		{
			log.warn("transformForm in /transformcategory is null.");
		}
		log.info("/transformcategory action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /transformcategory action: target={}", target);
		return new ActionRedirect(actionMapping.findForward(target));
	}

	public ActionForward transformSubcategory(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward transformSubcategory(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/transformsubcategory");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, TRANSFORM_PAGE);
		TransformationForm transformForm = (TransformationForm) actionForm;
		if (null != transformForm)
		{		
			Map<String, String> params = new HashMap<String, String>();
			log.debug("action=/transformsubcategory params: categoryName={}, subcategoryName={}", transformForm.getCategoryName(), transformForm.getSubcategoryName());
			params.put(GlobalConstants.CATEGORY_NAME_PARAM_TRANSFORM.getContent(), transformForm.getCategoryName());
			params.put(GlobalConstants.SUBCATEGORYNAME_PARAM_TRANSFORM.getContent(), transformForm.getSubcategoryName());
			transformForm.setTransformResult(productFacade.transformTemplate(XSLConstants.PRODUCT_TEMPLATE, params));
			target = TRANSFORM_PAGE;			
		}
		else
		{
			log.warn("transformForm in /transformsubcategory is null.");
		}
		log.info("/transformsubcategory action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /transformsubcategory action: target={}", target);
		return new ActionRedirect(actionMapping.findForward(target));
	}	

	public ActionForward transformAddPage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward transformAddProduct(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/transformaddproduct");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, TRANSFORM_PAGE);
		TransformationForm transformForm = (TransformationForm) actionForm;
		if (null != transformForm)
		{		
			Map<String, String> params = new HashMap<String, String>();
			log.debug("action=/transformaddproduct params: categoryName={}, subcategoryName={}", transformForm.getCategoryName(), transformForm.getSubcategoryName());
			params.put(GlobalConstants.CATEGORY_NAME_PARAM_TRANSFORM.getContent(), transformForm.getCategoryName());
			params.put(GlobalConstants.SUBCATEGORYNAME_PARAM_TRANSFORM.getContent(), transformForm.getSubcategoryName());
			transformForm.setTransformResult(productFacade.transformTemplate(XSLConstants.ADD_PRODUCT_TEMPLATE, params));
			target = TRANSFORM_PAGE;			
		}
		else
		{
			log.warn("transformForm in /transformaddproduct is null.");
		}
		log.info("/transformaddproduct action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /transformaddproduct action: target={}", target);
		return new ActionRedirect(actionMapping.findForward(target));
	}

	public ActionForward transformAddProduct(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward transformAddProduct(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response): actionForm={}", actionForm);
		log.info("ProductActionServlet: action=/transformaddproduct");		
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, TRANSFORM_PAGE);
		TransformationForm transformForm = (TransformationForm) actionForm;
		ActionRedirect actionRedirect = null;
		if (null != transformForm)
		{		
			Map<String, String> params = new HashMap<String, String>();
			log.debug("action=/transformaddproduct params: categoryName={}, subcategoryName={}, productName={}, provider={}, model={}, color={}, date={}, price={}, notInStock={}", new Object[]{ transformForm.getCategoryName(), transformForm.getSubcategoryName(), transformForm.getProductName(), transformForm.getProvider(), transformForm.getModel(), transformForm.getColor(), transformForm.getDate(), transformForm.getPrice(), transformForm.getNotInStock()});
			params.put(GlobalConstants.CATEGORY_NAME_PARAM_TRANSFORM.getContent(), transformForm.getCategoryName());
			params.put(GlobalConstants.SUBCATEGORYNAME_PARAM_TRANSFORM.getContent(), transformForm.getSubcategoryName());
			params.put(GlobalConstants.PRODUCTNAME_PARAM_TRANSFORM.getContent(), transformForm.getProductName());
			params.put(GlobalConstants.PROVIDER_PARAM_TRANSFORM.getContent(), transformForm.getProvider());
			params.put(GlobalConstants.MODEL_PARAM_TRANSFORM.getContent(), transformForm.getModel());
			params.put(GlobalConstants.COLOR_PARAM_TRANSFORM.getContent(), transformForm.getColor());
			params.put(GlobalConstants.DATE_PARAM_TRANSFORM.getContent(), transformForm.getDate());
			params.put(GlobalConstants.PRICE_PARAM_TRANSFORM.getContent(), transformForm.getPrice());
			params.put(GlobalConstants.NOTINSTOCK_PARAM_TRANSFORM.getContent(), transformForm.getNotInStock());
			transformForm.setTransformResult(productFacade.addNewProduct(params));
			if (params.get(GlobalConstants.ADD_RESULT.getContent()) != null)
			{
				target = TRANSFORM_VIEW; 
				actionRedirect = new ActionRedirect(actionMapping.findForward(target));
				actionRedirect.addParameter("categoryName", transformForm.getCategoryName());
				actionRedirect.addParameter("subcategoryName", transformForm.getSubcategoryName());
			}
			else
			{
				target = TRANSFORM_PAGE;
			}
		}
		else
		{
			log.warn("transformForm in /transformaddproduct is null.");
		}
		log.info("/transform action finished.");
		log.debug("ActionRedirect(actionMapping.findForward(target)) in /transformaddproduct action: target={}", target);
		if (actionRedirect == null)
		{
			actionRedirect = new ActionRedirect(actionMapping.findForward(target));
		}
		return actionRedirect;
	}

	public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("ccom.epam.xmlapp.presentation.action.ProductAction public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)");
		log.info("ProductActionServlet: action=/cancel");
		String target = (String) request.getSession().getAttribute(PREVIOUS_PAGE);
		log.debug("actionMapping.findForward(target) in /back action: target={}", target);
		return actionMapping.findForward(target);
	}

	public ActionForward error(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.debug("com.epam.xmlapp.presentation.action.ProductAction public ActionForward error(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)");
		log.info("ProductActionServlet: action=/error");
		String target = ERROR_PAGE;
		log.debug("actionMapping.findForward(ERROR_PAGE) in /error action: target={}", ERROR_PAGE);
		return actionMapping.findForward(target);
	}
}
