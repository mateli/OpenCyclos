<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/customization/translationMessages/searchTranslationMessages.js" />
<script>
	var removeOneConfirmation = "<cyclos:escapeJS><bean:message key="translationMessage.removeOne.confirm"/></cyclos:escapeJS>";
	var removeSelectedConfirmation = "<cyclos:escapeJS><bean:message key="translationMessage.removeSelected.confirm"/></cyclos:escapeJS>";
	var noneSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
</script>

<ssl:form styleId="searchForm" action="${formAction}" method="post">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="translationMessage.title.search"/></td>
        <cyclos:help page="translation#application"/>
    </tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key="translationMessage.key"/></td>
					<td colspan="2"><html:text property="query(key)" maxlength="50" styleClass="large"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="translationMessage.message"/></td>
					<td colspan="2"><html:text property="query(value)" maxlength="50" styleClass="large"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="translationMessage.search.showOnlyEmpty"/></td>
					<td colspan="2"><html:checkbox property="query(showOnlyEmpty)" value="true" styleClass="checkbox"/></td>
				</tr>
          		<tr>
            		<td class="label"><bean:message key="translationMessage.action.new" /></td>
            		<c:if test="${cyclos:granted(AdminSystemPermission.TRANSLATION_MANAGE)}">
            			<td><input type="button" class="button" id="newButton" value="<bean:message key="global.submit" />"></td>
            		</c:if>
            		<td align="right"><input type="submit" class="button" value="<bean:message key="global.search"/>"></td>
          		</tr>
			</table>
		</td>
	</tr>
</table>
</ssl:form>

<c:if test="${queryExecuted}">
<ssl:form styleId="removeForm" method="post" action="/admin/removeTranslationMessages">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
        <cyclos:help page="translation#application_results"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents" width="20px">&nbsp;</td>
                	<td class="tdHeaderContents" width="40%"><bean:message key="translationMessage.key"/></td>
                	<td class="tdHeaderContents"><bean:message key="translationMessage.message"/></td>
                    <td class="tdHeaderContents" width="5%">&nbsp;</td>
                </tr>
                <c:forEach var="message" items="${messages}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td align="center"><input type="checkbox" class="checkbox" name="messageIds" value="${message.id}"></td>
	                	<td><cyclos:escapeHTML>${message.key}</cyclos:escapeHTML></td>
	                	<td><cyclos:escapeHTML>${message.value}</cyclos:escapeHTML></td>
	                    <td align="center" nowrap="nowrap">
	                    	<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.TRANSLATION_MANAGE)}">
		                    	<img class="edit messageDetails" src="<c:url value="/pages/images/edit.gif"/>" messageId="${message.id}" border="0">
		                    	<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" messageId="${message.id}" border="0">
	                    	</c:when><c:otherwise>
		                    	<img class="view messageDetails" src="<c:url value="/pages/images/view.gif"/>" messageId="${message.id}" border="0">
	                    	</c:otherwise></c:choose>
	                    </td>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<c:if test="${cyclos:granted(AdminSystemPermission.TRANSLATION_MANAGE)}">
			<td>
				<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
				<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">&nbsp;
				<input type="submit" class="button" value="<bean:message key="global.removeSelected"/>">
			</td>
		</c:if>

		<td align="right">
			<cyclos:pagination items="${messages}"/>
		</td>
	</tr>
</table>
</ssl:form>
</c:if>
