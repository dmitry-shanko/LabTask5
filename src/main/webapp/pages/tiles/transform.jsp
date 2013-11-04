<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="contentNewsTitle"><html:link action="/products">
		<bean:message key="label.products.products" />
	</html:link></span>
&gt;&gt;
<bean:message key="label.transform.transform" />
<br>
<br>
<logic:empty name="transformForm" property="transformResult">
	<bean:message key="label.transform.noresult" />
</logic:empty>
<logic:notEmpty name="transformForm" property="transformResult">
<%-- <c:out value="${transformForm.transformResult }"/> --%>
	<bean:write name="transformForm" property="transformResult" filter="false"/>
</logic:notEmpty>
<br>