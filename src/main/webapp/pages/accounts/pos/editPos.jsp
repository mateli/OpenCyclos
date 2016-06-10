<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/pos/editPos.js" />
<script>
var confirmBlockPos = "<cyclos:escapeJS><bean:message key="pos.blockPos.confirmation"/></cyclos:escapeJS>"
var confirmUnblockPos = "<cyclos:escapeJS><bean:message key="pos.unblockPos.confirmation"/></cyclos:escapeJS>"
var confirmUnblockPin = "<cyclos:escapeJS><bean:message key="pos.unblockPin.confirmation"/></cyclos:escapeJS>"
var confirmAssignPos = "<cyclos:escapeJS><bean:message key="pos.assignPos.confirmation"/></cyclos:escapeJS>"
var confirmChangeParameters = "<cyclos:escapeJS><bean:message key="pos.changeParameters.confirmation"/></cyclos:escapeJS>"
var confirmChangePin = "<cyclos:escapeJS><bean:message key="memberPos.changePin.confirmation"/></cyclos:escapeJS>"
var confirmDiscardPos = "<cyclos:escapeJS><bean:message key="pos.discardPos.confirmation"/></cyclos:escapeJS>"
var confirmUnassignPos = "<cyclos:escapeJS><bean:message key="pos.unassignPos.confirmation"/></cyclos:escapeJS>"
var pinNotEqual = "<cyclos:escapeJS><bean:message key="memberPos.changePin.notEqual"/></cyclos:escapeJS>"
var pinIsEmpty = "<cyclos:escapeJS><bean:message key="memberPos.changePin.isEmpty"/></cyclos:escapeJS>"
var invalidMember = "<cyclos:escapeJS><bean:message key="memberPos.assign.invalidMember"/></cyclos:escapeJS>"
var hasMemberPos = "${hasMemberPos}"
var memberId = ${memberId}
var isRegularUser = ${isRegularUser}
var actionAssign = "<cyclos:escapeJS><bean:message key="pos.actions.assign"/></cyclos:escapeJS>"


</script>
<ssl:form method="post" action="${formAction}">

<html:hidden property="id"/>
<html:hidden property="pos(id)"/>
<html:hidden property="pos(status)"/>
<html:hidden property="pos(memberPos).status"/>
<html:hidden property="pos(memberPos).id"/>
<html:hidden property="operation"/>
<html:hidden property="pin"/>
<html:hidden property="assignTo"/>

	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="${isInsert ? 'pos.title.insert' : 'pos.title.modify'}"/></td>
	        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
	        	<cyclos:help page="access_devices#edit_pos" td="false" />
	        </td>		        
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	        	<cyclos:layout className="defaultTable" columns="4">
		        	<tr>
						<td colspan="4">
							<fieldset>
								<legend><bean:message key="pos.title"/></legend>
								<cyclos:layout className="defaultTable" columns="4">
  										<tr>
					            		<td class="label" width="25%"><bean:message key="pos.posId"/></td>
					            		<td><html:text property="pos(posId)" styleId="posIdentifier" maxlength="64" readonly="true" styleClass="large InputBoxDisabled"/></td>
					            	</tr>
					            	<c:if test="${!hasMemberPos}">
						            	<tr>
						            		<td class="label"><bean:message key="pos.status"/></td>
						            		<td><input value="<bean:message key='pos.status.${pos.status}'/>" id="posStatus" readonly="true" class="large InputBoxDisabled"></td>
						            	</tr>
					            	</c:if>
					            	<tr>
					            		<td class="label"><bean:message key="pos.description"/></td>
					            		<td><html:text property="pos(description)" styleId="description" maxlength="20" readonly="true" styleClass="large InputBoxDisabled"/></td>
					            	</tr>
					            	
					            	
					            	<c:if test="${hasMemberPos}">
					            	
					            		<tr>
						            		<td class="label" valign="top"><bean:message key="member.username"/></td>
						            		<td><input type="text" value="${memberLogin}" id="memberLogin" readonly="readonly" class="large InputBoxDisabled"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label" valign="top"><bean:message key="member.name"/></td>
						            		<td><input type="text" value="${userName}" id="userName" readonly="readonly" class="large InputBoxDisabled"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label" valign="top"><bean:message key="memberPos.name"/></td>
						            		<td><html:text property="pos(memberPos).posName" readonly="true" styleClass="large InputBoxDisabled"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label" valign="top"><bean:message key="memberPos.date"/></td>
						            		<td><html:text property="pos(memberPos).date" styleId="memberPosDate" readonly="true" styleClass="small date InputBoxDisabled"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label"><bean:message key="memberPos.status"/></td>
						            		<td><input value="<bean:message key='memberPos.status.${pos.memberPos.status}'/>" id="memberPosStatus" readonly="true" class="large InputBoxDisabled"></td>
						            	</tr>
						            	<c:if test="${canChangeParameters}">
							            	<tr>
							            		<td class="label" valign="top"><bean:message key="memberPos.allowMakePayment"/></td>
							            		<td><html:checkbox property="pos(memberPos).allowMakePayment" disabled="true" styleClass="checkbox" value="true"/></td>
							            	</tr>
							            	<tr>
							            		<td class="label"><bean:message key="memberPos.resultPageSize" /></td>
						            			<td><html:text property="pos(memberPos).resultPageSize" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/></td>
						            		</tr>
						            		<tr>
							            		<td class="label"><bean:message key="memberPos.numberOfCopies" /></td>
						            			<td><html:text property="pos(memberPos).numberOfCopies" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/></td>
						            		</tr>
						            		<tr>
							            		<td class="label"><bean:message key="memberPos.maxSchedulingPayments" /></td>
						            			<td><html:text property="pos(memberPos).maxSchedulingPayments" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/></td>
						            		</tr>            		
						            	</c:if>
					            	</c:if>
					            	<c:if test="${!isDiscarded}">            	            
						            	<c:if test="${editable}">
											<tr>
												<td colspan="3" align="right">
													
													<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">&nbsp;
													<c:choose>
														<c:when test="${isInsert}">
															<input type="button" id="newPosButton" class="button" value="<bean:message key="global.submit"/>">
														</c:when>
														<c:otherwise>
															<input type="submit" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>">
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:if>
									</c:if>
								</cyclos:layout>
							</fieldset>
						</td>
					</tr>
					<c:if test="${canAssign || canBlock || canChangePin || canDiscard || canUnassign || canUnblock || canUnblockPin}">
				            <tr>
								<td colspan="4">
									<fieldset>
										<legend><bean:message key="pos.actions"/></legend>
										<cyclos:layout className="defaultTable" columns="4">
									        <c:if test="${canChangePin}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="pos.actions.changePin"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="changePin" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>
									        <c:if test="${canUnblockPin}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="pos.actions.unblockPin"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="unblockPin" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>									        					        
											<c:if test="${canAssign}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="pos.actions.assign"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="assign" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>							                    			                    
									        </c:if>
									        <c:if test="${canDiscard}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="pos.actions.discard"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="discard" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>
									        <c:if test="${canUnassign}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="pos.action.unassign"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="unassign" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>
									        <c:if test="${canBlock}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="pos.actions.block"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="block" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>							                    			                    
									        </c:if>
									        <c:if test="${canUnblock}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="pos.actions.unblock"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="unblock" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>									        					        
									   	</cyclos:layout>
									</fieldset>
								</td>
							</tr>
						</c:if>
						<tr>
							<td colspan="4">
								<fieldset id="tableDiv" style="display:none">
									<legend id="legendId"></legend>
									<table class="defaultTable">										
						        		<tr id="pinDiv1" style="display:none">
								        	<td class="label" ><bean:message key="memberPos.changePin.newPin1"/></td>
									        <td>
								            	<input type="password" id="_pin1" class="medium number" style="width: 150px;" >
								            </td>
								        </tr>
								        <tr  style=""><td></td></tr>
								        <tr id="pinDiv2" style="display:none">
								            <td class="label" ><bean:message key="memberPos.changePin.newPin2"/></td>
			                     			<td>
			                     				<input type="password" id="_pin2" class="medium number" style="width: 150px;" >
			                     			</td>
			                     		</tr>
			                     		<tr id="assignDiv" style="display:none">
								        	<cyclos:cell className="label"><bean:message key="member.username"/></cyclos:cell>
											<cyclos:cell>
												<html:hidden styleId="memberId" property="pos(memberPos.member)"/>
												<input id="memberUsername" class="full" value="${memberPos.member.username}">
												<div id="membersByUsername" class="autoComplete"></div>
											</cyclos:cell>
										
											<cyclos:cell className="label"><bean:message key="member.memberName"/></cyclos:cell>
											<cyclos:cell>
												<input id="memberName" class="full" value="${memberPos.member.name}">
												<div id="membersByName" class="autoComplete"></div>
											</cyclos:cell>
								        </tr>						       
			                     	    <tr id="submitDiv" style="display:none">
							            	<td colspan="4" align="right">
												<input type="button" id="submitButton" class="button" value="<bean:message key="global.submit"/>">
											</td>
									    </tr>									    
								    </table>
						    	</fieldset>
							</td>
						</tr>								
	
				</cyclos:layout>
       	 	</td>
    	</tr>
	</table>
</ssl:form>
<c:if test="${isAdmin && not empty pos.posLog}">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<td class="tdHeaderTable"><bean:message key="posLog.title"/></td>
		<cyclos:help page="access_devices#pos_logs" />
		<tr>
		    <td colspan="6" align="left" class="tdContentTableForms">
	    	  	<table class="defaultTable">
					<tr>
						<td class="tdHeaderContents" width="20%"><bean:message key="posLog.date"/></td>
						<th class="tdHeaderContents" width="20%"><bean:message key="posLog.by"/></th>
						<td class="tdHeaderContents" width="20%"><bean:message key="posLog.status"/></td>
						<td class="tdHeaderContents" width="20%"><bean:message key="posLog.assignTo"/></td>
					</tr>
					<c:forEach var="log" items="${pos.posLog}" varStatus="idx">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="center"><cyclos:format date="${log.date}"/></td>
							<td align="center"><cyclos:profile elementId="${log.by.id}"/></td>
							<c:choose>
								<c:when test="${log.memberPosStatus.value != null}">
									<td align="center"><bean:message key="pos.status.${log.memberPosStatus}"/></td>
								</c:when>
								<c:otherwise>
									<td align="center"><bean:message key="pos.status.${log.posStatus}"/></td>
								</c:otherwise>
							</c:choose>							
							<td align="center"><cyclos:profile elementId="${log.assignedTo.id}"/></td>
						</tr>						
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</c:if>

<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">