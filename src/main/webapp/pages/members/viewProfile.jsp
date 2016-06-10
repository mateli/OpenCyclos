<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/viewProfile.js" />

<c:choose>
	<c:when test="${byBroker}">
		<c:set var="titleKey" value="profile.member.title.of"/>
	</c:when>
	<c:when test="${isAdmin}">
		<c:set var="titleKey" value="profile.member.title.of"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="profile.member.title.of"/>
	</c:otherwise>
</c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${member.name}"/></td>
        <cyclos:help page="profiles#member_profile"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable" cellpadding="0" cellspacing="0">
		        <c:set var="customFieldCount" value="0"/>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="value"><cyclos:customField field="${entry.field}" value="${entry.value}" textOnly="true" /></c:set>
			        <c:if test="${not entry.value.hidden and not empty value}">
			            <c:set var="customFieldCount" value="${customFieldCount + 1}"/>
					</c:if>
			    </c:forEach>
				<c:choose><c:when test="${!isAdmin && groupFilters != null}">
					<c:set var="rowCount" value="${4 + customFieldCount}"/>
					<tr>
						<td width="25%" class="headerLabel"><bean:message key="member.groupFilter"/></td>
						<td width="55%" class="headerField">${groupFilters}</td>
						<td width="20%" rowspan="${rowCount}" valign="top" align="right">
							<cyclos:images images="${images}" editable="${editable}" />
						</td>
					</tr>
					<tr>
						<td class="headerLabel"><bean:message key="member.username"/></td>
						<td class="headerField">${member.username}</td>
					</tr>
				</c:when><c:otherwise>
					<c:set var="rowCount" value="${3 + (isAdmin ? 2 : 0) + customFieldCount}"/>
					<tr>
						<td width="25%" class="headerLabel"><bean:message key="member.username"/></td>
						<td width="55%" class="headerField">${member.username}</td>
						<td width="20%" rowspan="${rowCount}" valign="top" align="right">
							<cyclos:images images="${images}" editable="${editable}" />
						</td>
					</tr>
				</c:otherwise></c:choose>
				<tr>
					<td class="headerLabel"><bean:message key="member.name"/></td>
					<td class="headerField">${member.name}</td>
				</tr>
				<c:if test="${isAdmin}">
					<tr>
						<td width="25%" class="headerLabel"><bean:message key="member.group"/></td>
						<td class="headerField">${member.group.name}</td>
			        </tr>
			        <tr>
						<td class="headerLabel"><bean:message key="member.lastLogin"/></td>
						<td class="headerField">
			    	        <c:set var="memberLoggedOn" value="${false}"/>
							<c:choose>
								<c:when test="${isLoggedIn}">
				    	       		<c:set var="lastLogin"><bean:message key="profile.userOnline"/></c:set>
				    	       		<c:set var="memberLoggedOn" value="${true}"/>
						       	</c:when>
						       	<c:when test="${empty member.user.lastLogin}">
				    	       		<c:set var="lastLogin"><bean:message key="profile.neverLoggedOn"/></c:set>
						       	</c:when>
						       	<c:otherwise>
				    	       		<c:set var="lastLogin"><cyclos:format dateTime="${member.user.lastLogin}"/></c:set>
					        	</c:otherwise>
					        </c:choose>
							${lastLogin}
						</td>			
					</tr>
				</c:if>
				<c:if test="${not empty member.email and not member.hideEmail}">
					<tr>
						<td class="headerLabel"><bean:message key="member.email"/></td>
						<td class="headerField"><a class="default" href="mailto:${member.email}">${member.email}</a></td>
					</tr>
				</c:if>
				
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value"><cyclos:customField field="${field}" value="${entry.value}" textOnly="true" /></c:set>
			        <c:set var="hidden" value="${entry.value.hidden}"/>
			        <c:if test="${not hidden and not empty value}">
			            <tr>
			                <td valign="top" class="headerLabel">${field.name}</td>
			   				<td width="15%" class="headerField">${value}</td>
						</tr>
					</c:if>
			    </c:forEach>
			</table>
    	</td>
    </tr>
</table>
<c:if test="${!myProfile}">
	<c:choose>
	    <c:when test="${isAdmin}">
	        <jsp:include page="/pages/members/includes/profileOfMemberByAdmin.jsp"/>
	    </c:when>
	    <c:when test="${byBroker}">
			
	        <jsp:include page="/pages/members/includes/profileOfMemberByBroker.jsp"/>
	    </c:when>
	    <c:when test="${isMember && profileOfOtherMember && !member.group.removed}">
			
	        <jsp:include page="/pages/members/includes/profileOfMemberByMember.jsp"/>
	    </c:when>
	    <c:when test="${isOperator && loggedUser.operator.member != member}">
			
	        <jsp:include page="/pages/members/includes/profileOfMemberByOperator.jsp"/>
	    </c:when>
	</c:choose>
	
	<table class="defaultTableContentHidden">
		<tr>
			<td>
				<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
			</td>
		</tr>
	</table>
</c:if>