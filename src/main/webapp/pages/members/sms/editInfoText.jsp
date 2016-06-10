<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/sms/editInfoText.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="infoText(id)" value="${currentInfoText.id}" />

<c:choose>
	<c:when test="${empty currentInfoText}">
		<c:set var="titleKey" value="infoText.title.new"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="infoText.title.edit"/>		
	</c:otherwise>
</c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="content_management#infotexts_edit"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
          			<tr>
            			<td class="label"><bean:message key="infotext.aliases"/></td>
            			<td colspan="2">
	            			<html:text styleId="aliases" property="infoText(aliases)" value="${currentInfoText.aliasesString}" styleClass="full InputBoxDisabled" readonly="true"/>
            			</td>
          			</tr>
          			<tr>
            			<td class="label"><bean:message key="infotext.subject"/></td>
            			<td colspan="2">
            				<html:textarea styleId="subject" property="infoText(subject)" rows="6" value="${currentInfoText.subject}"  readonly="true" styleClass="full InputBoxDisabled"/>
            			</td>
          			</tr>
          			<tr>
            			<td class="label"><bean:message key="infotext.body"/></td>
            			<td colspan="2">
            				<html:textarea styleId="body" property="infoText(body)" rows="6" value="${currentInfoText.body}" styleClass="full InputBoxDisabled" readonly="true"/>
            			</td>
          			</tr>
          			<tr>
            			<td class="label">
            				<bean:message key="infoText.validity"/>
							<bean:message key="global.range.from"/>
            			</td>
            			<td colspan="2">
            				<input type="text" id="validityBegin" name="infoText(validity).begin" class="InputBoxDisabled date small" value="<cyclos:format  rawDate="${currentInfoText.validity.begin}"/>" readonly="readonly"/>
            				&nbsp;
            				<span class="inlineLabel"><bean:message key="global.range.to"/></span>
            				<input id="validityEnd" type="text" name="infoText(validity).end" class="InputBoxDisabled date small" value="<cyclos:format rawDate="${currentInfoText.validity.end}"/>" readonly="readonly"/>
            			</td>
           			</tr>
          			<tr>
          				<td class="label"><bean:message key="infoText.enabled"/></td>
          				<td width="20px" colspan="2"><input name="infoText(enabled)" type="checkbox" ${currentInfoText.enabled ? 'checked="checked"' : ''} value="true" disabled="disabled" ></td>
          			</tr>
          			<tr>
            			<c:if test="${hasManagePermissions}">
	            			<td colspan="3" align="right">
	            				<c:if test="${currentInfoText != null}">
	            					<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button"/>
	            				</c:if>
	            				<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>"/>
	            			</td>
            			</c:if>
          			</tr>
        	</table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input type="button" id="backButton" value="<bean:message key="global.back"/>" class="button"/>
		</td>
	</tr>
</table>

<c:if test="${currentInfoText == null}">
<script language="javascript">
	enableFormFields.apply(document.forms[0], []);
</script>
</c:if>
</ssl:form>