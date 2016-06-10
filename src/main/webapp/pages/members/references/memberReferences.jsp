<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/references/memberReferences.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key="reference.removeConfirmation"/></cyclos:escapeJS>";
	var isGeneral = ${isGeneral};
</script>
<ssl:form method="post" action="${formAction}">
<html:hidden property="memberId"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="reference.title.summary.${isGeneral ? 'references' : 'transactionFeedbacks'}.of" arg0="${member.name}"/></td>
        <cyclos:help page="${isGeneral ? 'references#references_summary' : 'transaction_feedback#feedbacks_summary'}"/>
    </tr>
    <tr>
        <td colspan="2" class="tdContentTableLists" align="center">
        	<div>&nbsp;</div>
        	<fieldset style="width:90%">
        		<div align="center">
					<c:forEach var="direction" items="${directions}" varStatus="loop">
						<label>
							<html:radio style="vertical-align:middle" styleClass="radio update" property="direction" value="${direction}" />
							<span class="label" style="vertical-align:middle"><bean:message key="reference.direction.${isGeneral ? 'general' : 'transactionFeedback'}.${direction}"/></span>
						</label>
						<c:if test="${not loop.last}">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
					</c:forEach>
	        	</div>
	        </fieldset>
        	<div>&nbsp;</div>
            <table class="defaultTable">
                <tr>
                    <td class="tdHeaderContents" width="40%"><bean:message key="reference.level"/></td>
					<td class="tdHeaderContents" width="30%"><bean:message key="reference.summary.allTime"/></td>
                    <td class="tdHeaderContents" width="30%"><bean:message key="reference.summary.last30days"/></td>
                </tr>
                <c:forEach var="level" items="${levels}">
                	<c:set var="allTime" value="${summaryAllTime[level]}" />
                	<c:set var="last30Days" value="${summaryLast30Days[level]}" />
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="center"><bean:message key="reference.level.${level}"/></td>
	                    <td align="center">${allTime}</td>
	                    <td align="center">${last30Days}</td>
	                </tr>
                </c:forEach>
                <tr>
                	<td class="tdHeaderContents" style="align:right"><bean:message key="reference.summary.total" /></td>
                	<td align="center" class="tdHeaderContents">${totalAllTime}</td>
                	<td align="center" class="tdHeaderContents">${total30Days}</td>
                </tr>
                <tr>
                	<td class="tdHeaderContents" style="align:right"><bean:message key="reference.summary.score" /></td>
                	<td align="center" class="tdHeaderContents">${scoreAllTime}</td>
                	<td align="center" class="tdHeaderContents">${score30Days}</td>
                </tr>
                <tr>
                	<td class="tdHeaderContents" style="align:right"><bean:message key="reference.summary.positivePercentage" /></td>
                	<td align="center" class="tdHeaderContents">${percentAllTime}%</td>
                	<td align="center" class="tdHeaderContents">${percent30Days}%</td>
                </tr>
            </table>        	
		</td>
	</tr>
</table>

<c:set var="showBack" value="${loggedUser.element != member}"/>
<c:if test="${showBack || canSetReference || not isGeneral}">
	
	<table class="defaultTableContentHidden">
		<tr>
			<c:if test="${showBack}">
				<td>
					<input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
				</td>
			</c:if>
			<td align="right">
				<c:if test="${canSetReference}">
					&nbsp;&nbsp;&nbsp;
					<input type="button" class="button" id="setReferenceButton" value="<bean:message key='reference.action.set'/>">
				</c:if>
				<c:if test="${not isGeneral && myReferences}">
					&nbsp;&nbsp;&nbsp;
					<input type="button" class="button" id="paymentsAwaitingFeedbackButton" value="<bean:message key='reference.action.paymentsAwaitingFeedback'/>">
				</c:if>
			</td>
		</tr>
	</table>
</c:if>



<%-- Show a list of references the member has received --%>
<c:if test="${not empty references}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="reference.title.${isGeneral ? 'references' : 'transactionFeedbacks'}.of" arg0="${member.name}"/></td>
	        <cyclos:help page="${isGeneral ? 'references#references' : 'transaction_feedback#feedbacks'}_${isGiven ? 'given_to' : 'received_from'}_member"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	        	<jsp:include page="/pages/members/references/${isGeneral ? 'generalReferencesList' : 'transactionFeedbacksList'}.jsp" />
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right"><cyclos:pagination items="${references}"/></td>
		</tr>
	</table>
</c:if>
</ssl:form>
