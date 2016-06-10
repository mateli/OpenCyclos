<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/references/transactionFeedbackDetails.js" />

<script language="Javascript">
	var canComment = ${canComment};
	var canReply = ${canReply};
	var editable = ${editable};
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="memberId"/>
<html:hidden property="reference(id)"/>
<html:hidden property="reference(from)"/>
<html:hidden property="reference(to)"/>
<html:hidden property="reference(transfer)"/>
<html:hidden property="reference(scheduledPayment)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr> 
        <td class="tdHeaderTable"><bean:message key="reference.title.${transactionFeedback.persistent ? 'details' : 'new'}.transactionFeedback" /></td>
		<cyclos:help page="transaction_feedback#transaction_feedback_details"/>
    </tr>
	<tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<c:set var="payment" value="${transactionFeedback.payment}" />
          	<fieldset>
          		<legend><bean:message key="reference.paymentDatails"/></legend>
           		<table class="defaultTable">
		        	<c:if test="${showPayment}">
		            	<tr>
		            		<td width="24%" class="headerLabel"><bean:message key="transfer.date"/></td>
		            		<td class="headerField"><cyclos:format dateTime="${payment.date}" /></td>
		            	</tr>
		            	<tr>
		            		<td class="headerLabel"><bean:message key="transfer.amount"/></td>
		            		<td class="headerField"><cyclos:format number="${payment.amount}" unitsPattern="${payment.from.type.currency.pattern}" /></td>
		            	</tr>
			        </c:if>
	            	<tr>
	            		<td class="headerLabel" width="25%"><bean:message key="transfer.from"/></td>
	            		<td class="headerField">
	            			<c:set var="fromMember" value="${payment.from.member}" />
	            			<cyclos:profile elementId="${fromMember.id}"/>
	            		</td>
	            	</tr>
	            	<tr>
	            		<td class="headerLabel"><bean:message key="transfer.to"/></td>
	            		<td class="headerField">
	            			<c:set var="toMember" value="${payment.to.member}" />
	            			<cyclos:profile elementId="${toMember.id}"/>
	            		</td>
	            	</tr>
		        	<c:if test="${showPayment}">
		            	<tr>
		            		<td class="headerLabel" valign="top"><bean:message key="transfer.description"/></td>
		            		<td class="headerField"><cyclos:escapeHTML>${payment.description}</cyclos:escapeHTML></td>
		            	</tr>
		            </c:if>
            	</table>
            </fieldset>
          	<fieldset>
          		<legend><bean:message key="reference.feedbackComments"/></legend>
	            <table class="defaultTable">
	            	<c:if test="${transactionFeedback.persistent}">
		            	<tr>
		            		<td width="25%" class="headerLabel"><bean:message key="reference.date"/></td>
		            		<td class="headerField"><cyclos:format dateTime="${transactionFeedback.date}" /></td>
		            	</tr>
		            </c:if>
	                <tr>
	                    <td class="headerLabel" width="25%"><bean:message key="reference.from"/></td>
	                    <td class="headerField">${transactionFeedback.from.name}</td>
	                </tr>
	                <tr>
	                    <td class="headerLabel"><bean:message key="reference.level"/></td>
	                    <td class="headerField">
	                    	<c:choose><c:when test="${canComment}">
		                    	<html:select styleClass="InputBoxDisabled" styleId="levelSelect" property="reference(level)">
		                    		<html:option value=""><bean:message key="reference.level.select"/></html:option>
		                    		<c:forEach var="level" items="${levels}">
		                    			<html:option value="${level}"><bean:message key="reference.level.${level}"/></html:option>
		                    		</c:forEach>
		                        </html:select>
		                    </c:when><c:otherwise>
		                    	<bean:message key="reference.level.${transactionFeedback.level}"/>
		                    </c:otherwise></c:choose>
						</td>
	                </tr>
	                <tr>
	                    <td valign="top" class="headerLabel"><bean:message key="reference.comments"/></td>
	                    <td class="headerField">
	                    	<c:choose><c:when test="${canComment}">
		                    	<html:textarea styleId="comments" property="reference(comments)" rows="6" styleClass="full InputBoxDisabled" readonly="true"/>
		                    </c:when><c:otherwise>
		                    	<cyclos:escapeHTML>${transactionFeedback.comments}</cyclos:escapeHTML>
		                    </c:otherwise></c:choose>
	                    </td>
	                </tr>
					<c:if test="${canComment && !editable}">
						<tr>
							<td colspan="2" align="right">
								<input type="button" id="commentModifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
								<input type="submit" id="commentSaveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
							</td>
						</tr>
					</c:if>
	            </table>
	        </fieldset>
	        
            <c:if test="${canReply or (not empty transactionFeedback.replyComments)}">
		        <fieldset>
	          		<legend><bean:message key="reference.feedbackReply"/></legend>
		            <table class="defaultTable">
		            	<c:if test="${not empty transactionFeedback.replyComments}">
			            	<tr>
			            		<td class="headerLabel"><bean:message key="reference.date"/></td>
			            		<td class="headerField"><cyclos:format dateTime="${transactionFeedback.replyCommentsDate}" /></td>
			            	</tr>
			            </c:if>		            	
		                <tr>
		                    <td class="headerLabel" width="25%"><bean:message key="reference.from"/></td>
		                    <td class="headerField">${transactionFeedback.to.name}</td>
		                </tr>
		                <tr>
		                    <td valign="top" class="headerLabel"><bean:message key="reference.replyComments"/></td>
		                    <td class="headerField">
		                    	<c:choose><c:when test="${canReply}">
			                    	<html:textarea styleId="replyComments" property="reference(replyComments)" rows="6" styleClass="full InputBoxDisabled" readonly="true"/>
			                    </c:when><c:otherwise>
			                    	<cyclos:escapeHTML>${transactionFeedback.replyComments}</cyclos:escapeHTML>
			                    </c:otherwise></c:choose>
		                    </td>
		                </tr>
						<c:if test="${canReply && !editable}">
							<tr>
								<td colspan="2" align="right">
									<input type="button" id="replyModifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
									<input type="submit" id="replySaveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
								</td>
							</tr>
						</c:if>
	           		</table>
	        	</fieldset>
	        </c:if>
	        
            <c:if test="${editable or (not empty transactionFeedback.adminComments)}">
		        <fieldset>
	          		<legend><bean:message key="reference.adminComments"/></legend>
		            <table class="defaultTable">
		            	<c:if test="${not empty transactionFeedback.adminComments}">
			            	<tr>
			            		<td class="headerLabel" width="25%"><bean:message key="reference.date"/></td>
			            		<td class="headerField"><cyclos:format dateTime="${transactionFeedback.adminCommentsDate}" /></td>
			            	</tr>
			            </c:if>		            	
		                <tr>
		                    <td valign="top" class="headerLabel" width="25%"><bean:message key="reference.adminComments"/></td>
		                    <td class="headerField">
		                    	<c:choose><c:when test="${editable}">
			                    	<html:textarea styleId="adminComments" property="reference(adminComments)" rows="6" styleClass="full InputBoxDisabled" readonly="true"/>
			                    </c:when><c:otherwise>
			                    	<cyclos:escapeHTML>${transactionFeedback.adminComments}</cyclos:escapeHTML>
			                    </c:otherwise></c:choose>
		                    </td>
		                </tr>
	           		</table>
	        	</fieldset>
				<c:if test="${editable}">
					<table class="defaultTable">
						<tr>
							<td colspan="2" align="right">
								<td align="right" colspan="2">
									<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
									<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
								</td>							
							</td>
						</tr>
					</table>
				</c:if>
	        </c:if>
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