<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/external/overviewExternalAccounts.js" />
<script>
	var importTooltip = "<cyclos:escapeJS><bean:message key="externalAccount.tooltip.import"/></cyclos:escapeJS>";
</script>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key='externalAccount.title.overview'/></td>
        <cyclos:help page="bookkeeping#external_accounts_overview"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents"><bean:message key='externalAccount.name'/></th>
                    <th class="tdHeaderContents" width="30%" nowrap="nowrap"><bean:message key='account.balance'/></th>
                    <th class="tdHeaderContents" width="8%">&nbsp;</th>
                </tr>                

				<c:forEach var="externalAccountDetailsVO" items="${externalAccounts}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left">${externalAccountDetailsVO.name}</td>
						<c:choose><c:when test="${externalAccountDetailsVO.balance < 0}">
							<c:set var="balanceColor" value="red"/>
						</c:when><c:otherwise>
							<c:set var="balanceColor" value="blue"/>
						</c:otherwise></c:choose>
	                    <td align="right">
                    		<font color="${balanceColor}"><cyclos:format number="${externalAccountDetailsVO.balance}"/></font>
	                    </td>
	                    <td align="center" nowrap="nowrap">
                    		<img externalAccountId="${externalAccountDetailsVO.id}" src="<c:url value="/pages/images/import.gif" />" class="import" />
							<img externalAccountId="${externalAccountDetailsVO.id}" src="<c:url value="/pages/images/view.gif" />" class="view" />
						</td>						
					</tr>
				</c:forEach>                
            </table>
        </td>
    </tr>
</table>
