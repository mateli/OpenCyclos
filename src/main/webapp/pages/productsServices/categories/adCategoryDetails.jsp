<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/productsServices/categories/adCategoryDetails.js" />

<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="adCategory.removeConfirmation"/></cyclos:escapeJS>";
    var currentCategory = "${category.id}";
</script>

<ssl:form action="${formAction}" method="post">
<html:hidden property="category(id)"/>
<html:hidden property="category(parent)"/>
<html:hidden property="category(order)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="adCategory.title.${isInsert ? 'insert' : 'modify'}"/>
        </td>
        <cyclos:help page="advertisements#edit_category"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	
            	<c:if test="${isInsert}">
            		<tr>
						<td class="label" width="30%"><bean:message key="adCategory.parent"/></td>
						<td>
							<c:choose><c:when test="${empty category.parent}">
								<c:set var="parentName"><bean:message key="adCategory.navigator.root"/></c:set>
							</c:when><c:otherwise>
								<c:set var="parentName" value="${category.parent.fullName}" />
							</c:otherwise></c:choose>
							<input id="parentName" readonly="readonly" class="InputBoxDisabled large" value="${parentName}">
						</td>
					</tr>
            	</c:if>
				<tr>
					<td valign="top" class="label" width="30%"><bean:message key="adCategory.name"/></td>
					<td valign="top">
						<c:choose><c:when test="${category['transient']}">
							<html:textarea property="category(name)" rows="6" styleClass="large InputBoxEnabled required" />
							<br>
							<bean:message key="adCategory.insertMultiple" />
						</c:when><c:otherwise>
							<c:choose>
								<c:when test="${editable}">
									<html:text property="category(name)" maxlength="100" styleClass="large InputBoxEnabled required"/>	
								</c:when><c:otherwise>
									<html:text property="category(name)" maxlength="100" readonly="true" styleClass="large InputBoxDisabled"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise></c:choose>
					</td>
				</tr>
				<tr>
					<td class="label" width="30%"><bean:message key="adCategory.active"/></td>
					<td valign="top">
						<c:choose>
							<c:when test="${editable}">
								<html:checkbox property="category(active)" />	
							</c:when><c:otherwise>
								<html:checkbox property="category(active)" disabled="true" styleClass="InputBoxDisabled" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${editable}">
					<tr>
						<td colspan="2" align="right">
							<input type="submit" id="saveButton" class="button" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
            </table>
        </td>
    </tr>
</table>

<c:if test="${not empty category}">
	<table class="defaultTableContentHidden"><tr><td>
	<span class="inlineLabel"><bean:message key="adCategory.path"/></span>
	<c:if test="${not empty category}">
		<a class="default listAdCategories" categoryId="">
	</c:if>	
	<bean:message key="adCategory.navigator.root"/>
	<c:if test="${not empty category}">
		</a>
	</c:if>	
	<c:forEach var="categoryPath" items="${categoryPath}">
	  	&gt;
	  	<c:if test="${categoryPath.id != category.id}">
	  		<a class="default alterCurrentCategory" categoryId="${categoryPath.id}">
	  	</c:if>
	  	${categoryPath.name}
	  	<c:if test="${categoryPath.id != category.id}">	        		 
	  		</a>
	  	</c:if>
	</c:forEach>	
	</td></tr></table>
</c:if>

<c:if test="${!(isInsert || isMaxLevel)}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<input type="hidden" name="currentCategory" value="${currentCategory.id}"/>
	    <tr>
	        <td class="tdHeaderTable">
	        	<bean:message key="adCategory.title.list"/>
	        </td>
	        <cyclos:help page="advertisements#manage_categories"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <td class="tdHeaderContents"><bean:message key="adCategory.name"/></td>
	                    <td width="15%" class="tdHeaderContents"><bean:message key="category.status"/></td>
	                    <td width="15%" class="tdHeaderContents"><bean:message key="category.children"/></td>
	                    <td width="8%"  class="tdHeaderContents">&nbsp;</td>
	                </tr>
	                <c:forEach var="category" items="${categories}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left">${category.name}</td>
		                    <td align="center"><bean:message key="category.status.${category.active ? 'ACTIVE' : 'INACTIVE'}"/></td>
							<td align="center">${fn:length(category.children)}</td>
		                    <td align="right" nowrap="nowrap">
		                    	<c:choose><c:when test="${!editable}">
		                    		<img class="view" categoryId="${category.id}" src="<c:url value="/pages/images/view.gif"/>"/>
		                    	</c:when><c:otherwise>
		                    		<img class="edit" categoryId="${category.id}" src="<c:url value="/pages/images/edit.gif"/>"/>
		                    		<img class="remove" categoryId="${category.id}" src="<c:url value="/pages/images/delete.gif"/>"/>
		                    	</c:otherwise></c:choose>
		                    </td>
		                </tr>
	                </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<c:if test="${editable}">
		<table class="defaultTableContentHidden">
			<tr>
				<c:if test="${not empty categories && fn:length(categories) > 1}">
					<td align="right" width="50%">
						<span class="label"><bean:message key="adCategory.alterOrder"/></span>
						<input type="button" class="button" id="changeOrderButton" value="<bean:message key="global.submit"/>">
					</td>
				</c:if>
				<td align="right">
					<span class="label"><bean:message key="adCategory.new"/></span>
					<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>
</ssl:form>