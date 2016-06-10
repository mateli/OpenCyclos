<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/alerts/searchAlerts.js" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="alert.title.search"/></td>
        <cyclos:help page="alerts_logs#alerts_history"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
           	<ssl:form method="post" action="admin/searchAlerts">
            <table class="defaultTable">
          		<tr>
            		<td class="label" width="20%"><bean:message key="alert.type"/></td>
            		<td colspan="3">
            			<html:select styleId="typeSelect" property="query(type)">
            				<c:forEach var="type" items="${types}">
	                			<html:option value="${type}"><bean:message key="alert.type.${type}"/></html:option>
                			</c:forEach>
              			</html:select>
              		</td>
          		</tr>
          		<tr>
            		<td class="label" width="20%"><bean:message key="alert.search.date.begin"/></td>
            		<td nowrap="nowrap"><html:text property="query(period).begin" styleClass="date small" maxlength="10"/></td>
            		<td class="label" width="20%"><bean:message key="alert.search.date.end"/></td>
            		<td nowrap="nowrap"><html:text property="query(period).end" styleClass="date small" maxlength="10"/></td>
          		</tr>
				<tr id="trMember" style="display:none">
					<td class="label"><bean:message key="member.username"/></td>
					<td>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" style="width:100%" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
					<td class="label"><bean:message key="member.memberName"/></td>
					<td>
						<input id="memberName" style="width:100%" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
          		<tr>
            		<td align="right" colspan="4">
						
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
          		</tr>
        	</table>        	
       		</ssl:form>
        </td>
    </tr>
</table>

<c:if test="${not empty alerts}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <cyclos:help page="alerts_logs#alerts_history_result"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents"><bean:message key="alert.title"/></th>
	                    <c:if test="${isMember}">
		                    <th class="tdHeaderContents"><bean:message key="member.member"/></th>
	                    </c:if>
					    <th class="tdHeaderContents" width="25%"><bean:message key="alert.date"/></th>
	                </tr>
					<c:forEach var="alert" items="${alerts}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left"><bean:message key="${alert.key}" arg0="${alert.arg0}" arg1="${alert.arg1}" arg2="${alert.arg2}" arg3="${alert.arg3}" arg4="${alert.arg4}"/></td>  
		                    <c:if test="${isMember}">
		                    	<td align="left"><cyclos:profile elementId="${alert.member.id}"/></td>
		                    </c:if>
		                    <td align="center" nowrap><cyclos:format dateTime="${alert.date}"/></td>
		                </tr>
	                </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden">
		<tr>
	        <td align="right"><cyclos:pagination items="${alerts}"/></td>
		</tr>
	</table>
</c:if>