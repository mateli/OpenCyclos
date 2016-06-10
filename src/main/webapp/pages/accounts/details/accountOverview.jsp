<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/details/accountOverview.js" />
<script>
	var memberId = "${member.id}";
</script>

<c:choose>
	<c:when test="${isAdmin}">
		<c:choose><c:when test="${empty member}">
			<c:set var="titleKey" value="accountOverview.title.system" />
		</c:when><c:otherwise>
			<c:set var="titleKey" value="accountOverview.title.of" />
		</c:otherwise></c:choose>
	</c:when>
	<c:when test="${byBroker}">
		<c:set var="titleKey" value="accountOverview.title.of" />
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="accountOverview.title.my" />
	</c:otherwise>
</c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${member.name}"/></td>
        <cyclos:help page="payments#account_overview"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents"><bean:message key='account.type'/></th>
                    <c:if test="${not empty member}">
                    	<th class="tdHeaderContents" width="15%"><bean:message key='account.status'/></th>
                    </c:if>
                    <th class="tdHeaderContents" width="25%"><bean:message key='account.balance'/></th>
                    <th class="tdHeaderContents" width="5%" align="center">&nbsp;</th>
                </tr>                

                <c:forEach var="entry" items="${overview}">
                	<c:set var="account" value="${entry.key}"/>
                	<c:set var="balance" value="${entry.value}"/>
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left">${account.type.name}</td>
	                    <c:if test="${not empty member}">
    	                	<td align="center"><bean:message key='account.status.${account.status}'/></td>
        	            </c:if>
						<c:choose><c:when test="${balance < 0}">
							<c:set var="balanceColor" value="red"/>
						</c:when><c:otherwise>
							<c:set var="balanceColor" value="blue"/>
						</c:otherwise></c:choose>
	                    <td align="right">
                    		<font color="${balanceColor}"><cyclos:format number="${balance}" unitsPattern="${account.type.currency.pattern}" /></font>
	                    </td>
	                    <td align="center">
	                    	&nbsp;<img class="view" typeId="${account.type.id}" src="<c:url value="/pages/images/view.gif" />" border="0">&nbsp;
	                    </td>
					</tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<c:if test="${!myAccounts || param.fromQuickAccess}">
	<table class="defaultTableContentHidden"><tr><td>
	<input class="button" type="button" id="backButton" value="<bean:message key='global.back'/>">
	</td></tr></table>
</c:if>