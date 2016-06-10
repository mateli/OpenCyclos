<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/sms/searchInfoTexts.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="infoText.removeConfirmation"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="infoText.title.search"/></td>
        <cyclos:help page="content_management#infotext_search"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
          		<tr>
            		<td class="label" width="25%"><bean:message key="element.search.keywords"/></td>
            		<td colspan="2"><html:text property="query(keywords)" styleClass="InputBoxEnabled large"/></td>
          		</tr>
          			<tr>
            			<td class="nestedLabel">
            				<span class="label"><bean:message key="infoText.start"/></span>
            				<span class="lastLabel"><bean:message key="global.range.from"/></span>
            			</td>
            			<td colspan="2">
            				<html:text property="query(validity).begin" styleClass="InputBoxEnabled date small"/>
            				&nbsp;
            				<span class="label"><bean:message key="global.range.to"/></span>
            				<html:text property="query(validity).end" styleClass="InputBoxEnabled date small"/>
            			</td>
          			</tr>
          			<tr>
            			<td class="nestedLabel">
            				<span class="label"><bean:message key="infoText.end"/></span>
            				<span class="lastLabel"><bean:message key="global.range.from"/></span>
            			</td>
            			<td colspan="2">
            				<html:text property="query(validityEnd).begin" styleClass="InputBoxEnabled date small"/>
            				&nbsp;
            				<span class="label"><bean:message key="global.range.to"/></span>
            				<html:text property="query(validityEnd).end" styleClass="InputBoxEnabled date small"/>
            			</td>
          			</tr>
          			<tr>
            			<td colspan="1" align="left">
            				<input type="button" class="button" value="<bean:message key="infoText.new"/>" onclick="javascript: self.location='editInfoText';"/>
            			</td>
            			<td colspan="2" align="right">
            				<input type="submit" class="button" value="<bean:message key="global.search"/>"/>
            			</td>
          			</tr>
        	</table>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${queryExecuted}">
	<c:choose><c:when test="${empty infoTexts}">
		<div class="footerNote" helpPage="content_management#infotexts_result"><bean:message key="infoText.noMatch"/></div>
	</c:when><c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <cyclos:help page="content_management#infotexts_result"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td class="tdHeaderContents" width="40%"><bean:message key="infotext.aliases"/></td>
							<td class="tdHeaderContents"  width="40%" align="center"><bean:message key="infotext.subject"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="infotext.active"/></td>							
							<td class="tdHeaderContents" align="center"></td>
		                </tr>
						<c:forEach var="infoText" items="${infoTexts}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
								<td>${infoText.aliasesString}</a></td>
			                    <td>${infoText.subject}</a></td>
			                    <c:choose><c:when test="${infoText.active}">
			                    	<td align="center" ><bean:message key="global.yes"/></a></td>
			                    </c:when><c:otherwise>
			                    	<td align="center" ><bean:message key="global.no"/></a></td>
			                    </c:otherwise></c:choose>                    
			                    <td align="center" >
			                    	<c:choose>
										<c:when test="${hasManagePermissions}">
											<img class="edit details" src="<c:url value="/pages/images/edit.gif"/>" infoTextId="${infoText.id}"/>
											<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" infoTextId="${infoText.id}" />
										</c:when>
										<c:otherwise>
											<img class="view details" src="<c:url value="/pages/images/edit.gif"/>" infoTextId="${infoText.id}"/>
										</c:otherwise>
									</c:choose>
			                    </td>
			                </tr>
			            </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><cyclos:pagination items="${infoTexts}"/></td>
			</tr>
		</table>		
	</c:otherwise></c:choose>
</c:if>
