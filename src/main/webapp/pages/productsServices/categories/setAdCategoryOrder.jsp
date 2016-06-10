<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/productsServices/categories/setAdCategoryOrder.js" />

<script>
	var currentCategory = "${currentCategory.id}";
</script>


<c:set var="titleKey" value="ad.category.title.order"/>

<ssl:form method="post" action="${formAction}">
<input type="hidden" value="${currentCategory.id}" name="currentCategory"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="advertisements#ad_category_order"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTable">
            <table class="defaultTable">
            	<tr>
            		<td align="center">
			        	<br>
			            <ul id="adCategories" class="draggableList" style="width:300px">
			                <c:forEach var="adCategory" items="${adCategories}">
			                	<li>
					            	<input type="hidden" name="adCategoryIds" value="${adCategory.id}"/>
					                <cyclos:escapeHTML>${adCategory.name}</cyclos:escapeHTML>
					            </li>
			                </c:forEach>
						</ul>
						<br>&nbsp;
						<bean:message key="adCategory.title.order.description"/>
						<br>&nbsp;<br>&nbsp;
						<a id="applyOrder" class="default"><bean:message  locale="applyOrder" key="category.order.alpha"/></a> 
					</td>
				</tr>
			</table>

		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left" width="20%">
		    <span class="label">&nbsp;</span>
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
		<td align="right" width="40%">
		    <span class="label"><bean:message key="order.save"/></span>
			<input type="submit" class="button" id="submitButton" value="<bean:message key="global.submit"/>">
		</td>
		
	</tr>
</table>
</ssl:form>