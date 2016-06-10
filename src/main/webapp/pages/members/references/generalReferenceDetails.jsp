<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/references/generalReferenceDetails.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key="reference.removeConfirmation"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="memberId"/>
<html:hidden property="transferId"/>
<html:hidden property="reference(id)"/>
<html:hidden property="reference(from)"/>
<html:hidden property="reference(to)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr> 
        <td class="tdHeaderTable"><bean:message key="reference.title.${reference.persistent ? 'details' : 'new'}.general" /></td>
		<cyclos:help page="references#reference_details"/>
    </tr>
	<tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${reference.persistent}">
	            	<tr>
	            		<td width="25%" class="headerLabel"><bean:message key="reference.date"/></td>
	            		<td class="headerField"><cyclos:format dateTime="${reference.date}" /></td>
	            	</tr>
	            	<tr>
	            		<td class="headerLabel"><bean:message key="reference.from"/></td>
	            		<td class="headerField">${reference.from.name}</td>
	            	</tr>
	            </c:if>
            	<tr>
            		<td width="25%" class="headerLabel"><bean:message key="reference.to"/></td>
            		<td class="headerField">${reference.to.name}</td>
            	</tr>
                <tr>
                    <td class="headerLabel" width="25%"><bean:message key="reference.level"/></td>
                    <td class="headerField">
                    	<c:choose><c:when test="${editable}">
	                    	<html:select styleId="levelSelect" property="reference(level)">
	                    		<html:option value=""><bean:message key="reference.level.select"/></html:option>
	                    		<c:forEach var="level" items="${levels}">
	                    			<html:option value="${level}"><bean:message key="reference.level.${level}"/></html:option>
	                    		</c:forEach>
	                        </html:select>
	                    </c:when><c:otherwise>
	                    	<bean:message key="reference.level.${reference.level}"/>
	                    </c:otherwise></c:choose>
					</td>
                </tr>
                <tr>
                    <td valign="top" class="headerLabel"><bean:message key="reference.comments"/></td>
                    <td class="headerField">
                    	<c:choose><c:when test="${editable}">
	                    	<html:textarea styleId="comments" property="reference(comments)" rows="6" styleClass="full" />
	                    </c:when><c:otherwise>
	                    	<cyclos:escapeHTML>${reference.comments}</cyclos:escapeHTML>
	                    </c:otherwise></c:choose>
                    </td>
                </tr>
				<c:if test="${editable}">
					<tr>
						<td colspan="2" align="right">
							<c:if test="${reference.persistent}">
								<input type="button" id="removeButton" value="<bean:message key="reference.action.remove" />" class="button" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:if>
							<input type="submit" id="saveButton" class="button" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
            </table>
        </td>            
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>