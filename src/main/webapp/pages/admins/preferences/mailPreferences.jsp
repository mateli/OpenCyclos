<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/admins/mailPreferences/mailPreferences.js" />

<ssl:form method="post" action="${formAction}">

	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="mailPreferences.title"/></td>
	        <cyclos:help page="preferences#email_notifications"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="right" class="tdContentTableForms">
	            <table class="defaultTable">
	            	<c:if test="${not empty memberGroups}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.newMembers"/></td>
		            		<td>
		            			<cyclos:multiDropDown name="adminNotificationPreference(newMembers)" minWidth="340">
		            				<c:forEach var="group" items="${memberGroups}">
		            					<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(selectedNewMembers, group)}"/>
		            				</c:forEach>
		            			</cyclos:multiDropDown>
		            		</td>
	          			</tr>
	          		</c:if>
	          		
	            	<c:if test="${not empty transferTypes}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.payments"/></td>
		            		<td>
		            			<cyclos:multiDropDown name="adminNotificationPreference(transferTypes)" minWidth="340">
		            				<c:forEach var="transferType" items="${transferTypes}">
		            					<cyclos:option value="${transferType.id}" text="${transferType.name}" selected="${cyclos:contains(selectedTransferTypes, transferType)}"/>
		            				</c:forEach>
		            			</cyclos:multiDropDown>
		            		</td>
	          			</tr>
	          		</c:if>

	            	<c:if test="${not empty newPendingPayments}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.newPendingPayments"/></td>
		            		<td>
		            			<cyclos:multiDropDown name="adminNotificationPreference(newPendingPayments)" minWidth="340">
		            				<c:forEach var="transferType" items="${newPendingPayments}">
		            					<cyclos:option value="${transferType.id}" text="${transferType.name}" selected="${cyclos:contains(selectedNewPendingPayments, transferType)}"/>
		            				</c:forEach>
		            			</cyclos:multiDropDown>
		            		</td>
	          			</tr>
	          		</c:if>
	          		
	          		<c:if test="${not empty guaranteeTypes}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.guarantees"/></td>
		            		<td>
		            			<cyclos:multiDropDown name="adminNotificationPreference(guaranteeTypes)" minWidth="340">
		            				<c:forEach var="guaranteeType" items="${guaranteeTypes}">
		            					<cyclos:option value="${guaranteeType.id}" text="${guaranteeType.name}" selected="${cyclos:contains(selectedGuaranteeTypes, guaranteeType)}"/>
		            				</c:forEach>
		            			</cyclos:multiDropDown>
		            		</td>
	          			</tr>
	          		</c:if>
	          		
	            	<c:if test="${not empty messageCategories}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.messages"/></td>
		            		<td>
		            			<cyclos:multiDropDown name="adminNotificationPreference(messageCategories)" minWidth="340">
		            				<c:forEach var="category" items="${messageCategories}">
		            					<cyclos:option value="${category.id}" selected="${cyclos:contains(selectedMessageCategories, category)}" text="${category.name}"/>
		            				</c:forEach>
		            			</cyclos:multiDropDown>
		            		</td>
		          		</tr>
		          	</c:if>

	            	<c:if test="${cyclos:granted(AdminSystemPermission.ALERTS_VIEW_SYSTEM_ALERTS)}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.systemAlert"/></td>
		            		<td>
		            			<cyclos:multiDropDown name="adminNotificationPreference(systemAlerts)" minWidth="340">
		            				<c:forEach var="systemAlert" items="${systemAlerts}">
		            					<c:set var="systemAlertLabel"><bean:message key="alert.system.${systemAlert}"/></c:set>
		            					<cyclos:option value="${systemAlert}" text="${systemAlertLabel}" selected="${cyclos:contains(selectedSystemAlerts, systemAlert)}"/>
		            				</c:forEach>
		            			</cyclos:multiDropDown>
		            		</td>
		          		</tr>
		          	</c:if>

	            	<c:if test="${cyclos:granted(AdminSystemPermission.ALERTS_VIEW_MEMBER_ALERTS)}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.memberAlerts"/></td>
		            		<td>
		            			<cyclos:multiDropDown name="adminNotificationPreference(memberAlerts)" minWidth="340">
		            				<c:forEach var="memberAlert" items="${memberAlerts}">
		            					<c:set var="memberAlertLabel"><bean:message key="alert.member.${memberAlert}"/></c:set>
		            					<cyclos:option value="${memberAlert}" text="${memberAlertLabel}" selected="${cyclos:contains(selectedMemberAlerts, memberAlert)}"/>
		            				</c:forEach>
		            			</cyclos:multiDropDown>
		            		</td>
		          		</tr>
					</c:if>
					
					<c:if test="${cyclos:granted(AdminSystemPermission.ERROR_LOG_VIEW)}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.applicationErrors"/></td>
		            		<td style="text-align: left">
								<html:checkbox property="adminNotificationPreference(applicationErrors)" value="true" styleClass="checkbox InputBoxDisabled"/>
		            		</td>
		          		</tr>
		          	</c:if>

					<c:if test="${cyclos:granted(AdminMemberPermission.INVOICES_VIEW)}">
		          		<tr>
		            		<td class="label" width="25%"><bean:message key="mailPreferences.systemInvoices"/></td>
		            		<td style="text-align: left">
								<html:checkbox property="adminNotificationPreference(systemInvoices)" value="true" styleClass="checkbox InputBoxDisabled"/>
		            		</td>
		          		</tr>
		          	</c:if>
		          	
   					
					<tr>
						<td align="right" colspan="2">
							<input class="button" type="submit" id="saveButton" value="<bean:message key='global.submit'/>">
						</td>
					</tr>
	          		
	        	</table>
	        </td>
	    </tr>

	</table>

</ssl:form>
	
