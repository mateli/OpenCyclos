<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/external/searchTransferImports.js" />
<script>
var removeConfirmation = "<cyclos:escapeJS><bean:message key='externalTransferImport.removeConfirmation'/></cyclos:escapeJS>";
var noFileMessage = "<cyclos:escapeJS><bean:message key='externalTransferImport.noFile.message'/></cyclos:escapeJS>";
</script>

<ssl:form action="${formAction}" method="post">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="externalTransferImport.title.search" arg0="${externalAccount.name}"/></td>
        <cyclos:help page="bookkeeping#external_transfer_import_list"/>
    </tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<tr>
					<td class="label" width="20%"><bean:message key="externalTransferImport.period.begin"/></td>
					<td nowrap="nowrap"><html:text property="query(period).begin" styleClass="date small"/></td>
					<td class="label" width="20%"><bean:message key="externalTransferImport.period.end"/></td>
					<td nowrap="nowrap"><html:text property="query(period).end" styleClass="date small"/></td>
				</tr>
   				<tr>
					<td align="left" colspan="2">
						<input id="backButton" type="button" class="button" value="<bean:message key="global.back"/>">
					</td>
   					<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key="global.search"/>"></td>
   				</tr>
			</table>
		</td>
	</tr>
</table>
</ssl:form>


<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
        <cyclos:help page="bookkeeping#external_transfer_import_result"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents"><bean:message key='externalTransferImport.date'/></th>
                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
                </tr>                
				<c:forEach var="externalTransferImport" items="${externalTransferImports}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="center"><cyclos:format dateTime="${externalTransferImport.date}"/></td>
	                    <td align="center">
	                    	<img transferImportId="${externalTransferImport.id}" src="<c:url value="/pages/images/view.gif" />" class="view" />
	                    	<c:if test="${editable}">
		                    	<img transferImportId="${externalTransferImport.id}" src="<c:url value="/pages/images/delete.gif" />" class="remove" />
		                    </c:if>
						</td>
					</tr>
				</c:forEach>                
            </table>
        </td>
    </tr>
</table>
<c:if test="${not empty externalTransferImports}">
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right"><cyclos:pagination items="${externalTransferImports}"/></td>
		</tr>
	</table>
</c:if>

<c:if test="${cyclos:granted(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE_PAYMENT)}">
	<ssl:form action="${actionPrefix}/importNewTransactionFile" method="post" enctype="multipart/form-data" styleId="newImportForm">
		<html:hidden property="externalAccountId" value="${externalAccount.id}" />
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="externalTransferImport.title.import" arg0="${externalAccount.name}"/></td>
		        <cyclos:help page="bookkeeping#external_transfer_import_new"/>
		    </tr>
			<tr>
				<td colspan="2" align="left" class="tdContentTableForms">
					
					<table class="defaultTable">
						<tr>
							<td class="label" width="30%"><bean:message key="externalTransferImport.chooseFile"/></td>
							<td nowrap="nowrap"><html:file property="file"/></td>
							<td width="30%" align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
		   				</tr>
					</table>
				</td>
			</tr>
		</table>
	</ssl:form>
</c:if>
