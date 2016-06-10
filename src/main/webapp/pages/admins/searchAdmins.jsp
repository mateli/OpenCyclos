<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/admins/searchAdmins.js" />

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="admin.title.search"/></td>
        <cyclos:help page="user_management#search_admin"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
          		<tr>
            		<td class="label" width="25%"><bean:message key="element.search.keywords"/></td>
            		<td colspan="2"><html:text property="query(keywords)" styleClass="large InputBoxEnabled" maxlength="30" size="49"/></td>
          		</tr>
         		<tr>
           			<td class="label"><bean:message key="admin.group"/></td>
           			<td colspan="2"><cyclos:multiDropDown name="query(groups)" emptyLabelKey="member.search.allGroups">
           					<c:forEach var="group" items="${groups}">
           						<cyclos:option value="${group.id}" text="${group.name}" />
           					</c:forEach>
             			</cyclos:multiDropDown></td>
         		</tr>
          		<tr>
          			<c:set var="colSpan" value="${empty possibleNewGroups ? 3 : 1}" />
         			<c:if test="${not empty possibleNewGroups}">
						<td class="label"><bean:message key="admin.action.create"/></td>
						<td>
							<select id="newAdminGroup">
								<option value=""><bean:message key="admin.action.create.selectGroup" /></option>
								<c:forEach var="group" items="${possibleNewGroups}">
									<option value="${group.id}">${group.name}</option>
								</c:forEach>
							</select>
						</td>
					</c:if>
					<td align="right" colspan="${colSpan}">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
          		</tr>
        	</table>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${queryExecuted}">
	<c:choose><c:when test="${empty elements}">
		<div class="footerNote" helpPage="user_management#search_admin_result"><bean:message key="admin.search.noResults"/></div>
	</c:when><c:otherwise>

		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <cyclos:help page="user_management#search_admin_result"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td class="tdHeaderContents"><bean:message key="admin.username"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="admin.name"/></td>
		                </tr>
						<c:forEach var="admin" items="${elements}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			                    <td align="left" width="30%"><cyclos:profile elementId="${admin.id}" pattern="username"/></td>
			                    <td align="left" width="70%"><cyclos:profile elementId="${admin.id}" pattern="name"/></td>
			                </tr>
			            </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><cyclos:pagination items="${elements}"/></td>
			</tr>
		</table>		
	</c:otherwise></c:choose>
</c:if>
