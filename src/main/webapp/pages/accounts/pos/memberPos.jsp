<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/pos/memberPos.js" />
<script>
	var memberId = ${memberId};
</script>

<ssl:form method="post" action="${formAction}">

<c:choose>
	<c:when test="${empty memberPos}">
		<table cellspacing="0" cellpadding="0" class="defaultTableContent">
			<tr>
				<td class="tdHeaderTable">Results</td>
				<td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
			    	<cyclos:help page="access_devices#member_pos" td="false" />
		        </td>
			</tr>
			<tr>
				<td align="center" class="innerBorder tdContentTableForms" colspan="2"><br/><bean:message key="memberPos.title.noPos"/><br/></td>
			</tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		    	<td class="tdHeaderTable"><bean:message key="memberPos.title"/></td>
		    	<td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
			    	<cyclos:help page="access_devices#member_pos" td="false" />
		        </td>	        
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable">
		                <tr>
		                	<th class="tdHeaderContents" width="35%"><bean:message key='pos.posId'/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key="memberPos.name"/></th>
		                    <th class="tdHeaderContents" width="20%"><bean:message key="memberPos.status"/></th>
		                    <th class="tdHeaderContents" width="25%"><bean:message key='memberPos.date'/></th>
		                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
		                </tr>
		                <c:forEach var="mp" items="${memberPos}">
		                	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                		<td align="left" ><cyclos:truncate value="${mp.pos.posId}"/></td>
			                    <td align="left" ><cyclos:truncate value="${mp.posName}"/></td>
			                    <td align="left" ><bean:message key="pos.status.${mp.status}"/></td>
			                    <td align="center" ><cyclos:format dateTime="${mp.date}"/></td>
			                    <td align="center"><img posMainId="${mp.pos.id}" src="<c:url value="/pages/images/view.gif"/>" class="details view"/></td>                   
		                	</tr>
		                </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
				<td align="right"><cyclos:pagination items="${memberPos}"/></td>
			</tr>
		</table>		
	</c:otherwise>	
</c:choose>
</ssl:form>

<c:if test="${canAssign}">
	<ssl:form method="post" action="${actionPrefix}/assignPos">
	<html:hidden property="memberId" value="${memberId}" />
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right" colspan="4">
				<span class="label"><bean:message key="pos.new"/></span>
				<html:text property="posId" />
				<input type="submit" class="button" value="<bean:message key="global.submit"/>">
			</td>
		<tr>			
	</table>
	</ssl:form>
</c:if>