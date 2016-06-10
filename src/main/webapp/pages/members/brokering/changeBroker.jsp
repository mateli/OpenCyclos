<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/brokering/changeBroker.js" />

<ssl:form action="${formAction}" method="post">
<html:hidden styleId="memberId" property="memberId"/>
<input type="hidden" id="memberGroup" name="memberGroup" value="${member.group.id}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="changeBroker.title" arg0="${member.name}"/></td>
		<cyclos:help page="brokering#change_broker"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<c:choose><c:when test="${empty member.broker}">
				<c:set var="currentBrokerName"><bean:message key="changeBroker.noBroker"/></c:set>
				<c:set var="currentBrokerUsername" value="${currentBrokerName}"/>
			</c:when><c:otherwise>
				<c:set var="currentBrokerName" value="${member.broker.name}"/>
				<c:set var="currentBrokerUsername" value="${member.broker.username}"/>
			</c:otherwise></c:choose>

			<fieldset>
				<legend><bean:message key="changeBroker.current"/></legend>
				<table class="defaultTable">
					<tr>
						<td width="24%" class="label"><bean:message key="member.username"/></td>
						<td><input class="large InputBoxDisabled" readonly value="${currentBrokerUsername}"></td>
					</tr>
					<tr>
						<td class="label"><bean:message key="member.name"/></td>
						<td><input class="large InputBoxDisabled" readonly value="${currentBrokerName}"></td>
					</tr>
				</table>
			</fieldset>
			<fieldset>
				<legend><bean:message key="changeBroker.new"/></legend>
				<table class="defaultTable">
					<tr>
						<td width="24%" class="label"><bean:message key="member.username"/></td>
						<td>
							<html:hidden property="newBrokerId" styleId="newBrokerId"/>
							<input id="brokerUsername" class="large" size="20">
							<div id="brokersByUsername" class="autoComplete"></div>
						</td>
					</tr>
					<tr>
						<td class="label"><bean:message key="member.name"/></td>
						<td>
							<input id="brokerName" class="large" size="40">
							<div id="brokersByName" class="autoComplete"></div>
						</td>
					</tr>
				</table>
			</fieldset>
			<table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key="changeBroker.suspendCommission"/></td>
					<td><html:checkbox property="suspendCommission" value="true"/></td>						
				</tr>
			   	<tr>
					<td class="label" valign="top"><bean:message key="remark.comments"/></td>
				   	<td><html:textarea styleId="comments" styleClass="full" rows="5" property="comments"/></td>
			   	</tr>
			   	<tr>
				   	<td colspan="2" align="right"><input type="submit" value="<bean:message key="global.submit"/>" class="button"></td>
			   	</tr>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left"><input type="button" id="backButton" memberId="${member.id}" id="backButton" class="button" value="<bean:message key="global.back"/>"></td>
   	</tr>
</table>
</ssl:form>


<c:if test="${not empty history}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable" style="height: 19px"><bean:message key="changeBroker.title.history" arg0="${member.name}"/></td>
			<td class="tdHelpIcon">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableLists">
				<c:forEach var="remark" items="${history}" varStatus="loop">
					<c:if test="${loop.count > 1}">
						<hr/>
					</c:if>
		            <table style="width:100%" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td class="tdHeaderContents" width="25%"><bean:message key="remark.date"/></td>
		                    <td><cyclos:format dateTime="${remark.date}"/></td>
		                </tr>
		                <tr>
							<td class="tdHeaderContents" width="25%" align="center"><bean:message key="changeBroker.old"/></td>
							<td>${remark.oldBroker.name}</td>
						</tr>
		                <tr>
							<td class="tdHeaderContents" width="25%" align="center"><bean:message key="changeBroker.new"/></td>
							<td>${remark.newBroker.name}</td>
						</tr>
		                <tr>
							<td class="tdHeaderContents" width="25%" align="center"><bean:message key="remark.writer"/></td>
							<td>${remark.writer.name}</td>
						</tr>
		                <tr>
							<td class="tdHeaderContents" width="25%" align="center"><bean:message key="changeBroker.suspendCommission"/></td>
							<td><bean:message key="global.${remark.suspendCommission ? 'yes' : 'no'}"/></td>
						</tr>
		                <tr>
							<td class="tdHeaderContents" width="25%" valign="top" align="center"><bean:message key="remark.comments"/></td>
							<td><cyclos:escapeHTML>${remark.comments}</cyclos:escapeHTML></td>
						</tr>
		            </table>
	            </c:forEach>
			</td>
		</tr>
	</table>
</c:if>