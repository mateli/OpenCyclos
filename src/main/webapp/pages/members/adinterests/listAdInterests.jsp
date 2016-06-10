<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/adinterests/listAdInterests.js" />

<script>
	var removeOneConfirmation = "<cyclos:escapeJS><bean:message key="adInterest.removeOne.confirm"/></cyclos:escapeJS>";
	var removeSelectedConfirmation = "<cyclos:escapeJS><bean:message key="adInterest.removeSelected.confirm"/></cyclos:escapeJS>";
	var noneSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="/member/removeAdInterests">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="adInterest.title.list"/></td>
        <cyclos:help page="ads_interest#list_ad_interests"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<th class="tdHeaderContents" width="5%">&nbsp;</th>
                    <th class="tdHeaderContents"><bean:message key="adInterest.title"/></th>
                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
                </tr>
				<c:forEach var="adInterest" items="${adInterests}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td align="center"><input type="checkbox" class="checkbox" name="adInterestsIds" value="${adInterest.id}"></td>
	                    <td align="left">${adInterest.title}</td>
	                    <td align="center">
	                    	<img class="edit" adInterestId="${adInterest.id}" src="<c:url value="/pages/images/edit.gif"/>" />
							<img class="remove" adInterestId="${adInterest.id}" src="<c:url value="/pages/images/delete.gif"/>" />
						</td>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
			<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
		</td>
		<td align="right">
			<input type="submit" class="button" value="<bean:message key="global.removeSelected"/>">
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="right" colspan="2">
			<span class="label"><bean:message key="adInterest.new"/></span>
			<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
		</td>
	</tr> 
</table>
</ssl:form>