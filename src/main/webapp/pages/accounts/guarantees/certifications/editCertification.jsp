<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:choose>
	<c:when test="${isInsert}">
		<c:set var="titleKey" value="certification.title.new"/>
	</c:when>
	<c:when test="${not isEditable}">
		<c:set var="titleKey" value="certification.title.view"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="certification.title.modify"/>
	</c:otherwise>
</c:choose>

<c:set var="helpPage" value="guarantees#edit_certification"/>
<c:set var="requiredClassName" value="${isEditable ? 'required' : ''}"/>

<script language="javascript">
	var buyerGroupIds = ${buyerGroupIds};
	var removeConfirmation = "<cyclos:escapeJS><bean:message key='certification.removeConfirmation'/></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/accounts/guarantees/certifications/editCertification.js" />

<ssl:form method="post" action="${formAction}">
	<html:hidden property="certificationId"/>
	<html:hidden property="certification(id)"/>

	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
			<cyclos:help page="${helpPage}" />
		</tr>
		<tr>
	        <td colspan="2" align="left" class="tdContentTableForms">	        
	        	<cyclos:layout columns="4">
					<c:choose>
	           			<c:when test="${not isEditable}">	        
							<cyclos:cell className="label"><bean:message key='certification.status'/></cyclos:cell>		        	
				        	<cyclos:cell>
		           				<bean:define id="certificationStatus"><bean:message key="certification.status.${certification.status}"/></bean:define>
		           				<input value="${certificationStatus}" class="full InputBoxDisabled"/>
							</cyclos:cell>
						</c:when>
						<c:otherwise>
							<cyclos:cell>&nbsp;</cyclos:cell>
							<cyclos:cell>&nbsp;</cyclos:cell>
						</c:otherwise>
					</c:choose>
					<cyclos:cell>&nbsp;</cyclos:cell>
					<cyclos:cell>&nbsp;</cyclos:cell>
	        	
					<cyclos:cell className="label"><bean:message key='certification.guaranteeType'/></cyclos:cell>
					<cyclos:cell colspan="3">	
						<c:choose>
							<c:when test="${isEditable}">	
							<div style="white-space: nowrap">		
								<html:select property="certification(guaranteeType)" disabled="true" styleClass="full InputBoxDisabled ${requiredClassName}">
		   	        				<c:forEach var="guaranteeType" items="${guaranteeTypes}">
		       	    					<html:option value="${guaranteeType.id}">${guaranteeType.name}</html:option>
		           					</c:forEach>
		           				</html:select>
							</div>		           				
		           			</c:when>
		           			<c:otherwise>
		           				<input value="${certification.guaranteeType.name}" class="full InputBoxDisabled"/>
		           			</c:otherwise>
	           			</c:choose>
		        	</cyclos:cell>

					<c:if test="${not isBuyer}">
				     	<cyclos:cell className="label"><bean:message key="certification.buyerUsername"/></cyclos:cell>
						<cyclos:cell>
							<html:hidden styleId="buyerId" property="certification(buyer)"/>
							<div style="white-space: nowrap"><input id="buyerUsername" class="full InputBoxDisabled ${requiredClassName}" value="${certification.buyer.username}" readonly></div>
							<div id="buyersByUsername" class="autoComplete"></div>						
						</cyclos:cell>
						<cyclos:cell className="label"><bean:message key="certification.buyerName"/></cyclos:cell>
						<cyclos:cell>
							<div style="white-space: nowrap"><input id="buyerName" class="full InputBoxDisabled ${requiredClassName}" value="<cyclos:escapeHTML value="${certification.buyer.name}"/>" readonly></div>
							<div id="buyersByName" class="autoComplete"></div>
						</cyclos:cell>
					</c:if>
					
			     	<c:if test="${not isIssuer}">
				     	<cyclos:cell className="label"><bean:message key="certification.issuerUsername"/></cyclos:cell>
						<cyclos:cell>
							<input id="issuerUsername" class="full InputBoxDisabled" value="${certification.issuer.username}" readonly>
						</cyclos:cell>
						<cyclos:cell className="label"><bean:message key="certification.issuerName"/></cyclos:cell>
						<cyclos:cell>
							<input id="issuerName" class="full InputBoxDisabled" value="<cyclos:escapeHTML value="${certification.issuer.name}"/>" readonly>
						</cyclos:cell>
					</c:if>

					<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='certification.validity'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><div style="white-space: nowrap"><html:text styleId="beginDate" styleClass="date full InputBoxDisabled ${requiredClassName}" readonly="true" property="certification(validity).begin"/></div></cyclos:cell>
					<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><div style="white-space: nowrap"><html:text styleClass="date full InputBoxDisabled ${requiredClassName}" readonly="true" property="certification(validity).end"/></div></cyclos:cell>

					<c:if test="${false and not isBuyer}">
						<cyclos:cell className="label"><bean:message key='certification.qualification'/></cyclos:cell>
						<cyclos:cell>
							<c:choose>
								<c:when test="${isEditable}">					        						
									<div style="white-space: nowrap">
										<html:select property="certification(qualification)" styleClass="full InputBoxDisabled ${requiredClassName}" disabled="true">
											<c:forEach var="qualif" items="${qualifications}">
												<html:option value="${qualif}"><bean:message key="certification.qualification.${qualif}"/></html:option>
											</c:forEach>
										</html:select>
									</div>
			           			</c:when>
			           			<c:otherwise>
			           				<bean:define id="certificationQualification"><bean:message key="certification.qualification.${certification.qualification}"/></bean:define>
			           				<input value="${certificationQualification}" class="full InputBoxDisabled"/>
			           			</c:otherwise>	
			           		</c:choose>													
						</cyclos:cell>
					</c:if>
		        	<cyclos:cell className="label"><bean:message key="certification.amount"/></cyclos:cell>
		        	<cyclos:cell>
		        		<c:choose>
			        		<c:when test="${isEditable}">
				        		<div style="white-space: nowrap"><html:text styleId="amount" property="certification(amount)" readonly="true" styleClass="float full InputBoxDisabled ${requiredClassName}"/></div>
				        	</c:when>
				        	<c:otherwise>
				        		<bean:define id="certificationAmount"><cyclos:format number="${certification.amount}" unitsPattern="${certification.guaranteeType.currency.pattern}"/></bean:define>
								<input value="${certificationAmount}" class="float full InputBoxDisabled"/>  
				        	</c:otherwise>
			        	</c:choose>
	        		</cyclos:cell>
		        	<c:if test="${not isEditable}">
		        		<cyclos:cell className="label"><bean:message key="certification.usedAmount"/></cyclos:cell>
			        	<cyclos:cell>
			        		<cyclos:format number="${usedAmount}" unitsPattern="${certification.guaranteeType.currency.pattern}"/>
		        		</cyclos:cell>
	        		</c:if>
	        		
	        		<cyclos:cell colspan="4">&nbsp;</cyclos:cell>
		        	
	            	<cyclos:cell align="right" colspan="4">
						<c:if test="${canLock}">
							<input type="button" class="button" id="lockButton" certificationId="${certification.id}" value="<bean:message key="certification.lock"/>"/>
						</c:if>
						<c:if test="${canUnlock}">
							<input type="button" class="button" id="unlockButton" certificationId="${certification.id}" value="<bean:message key="certification.unlock"/>"/>
						</c:if>								
						<c:if test="${canCancel}">
							<input type="button" class="button" id="cancelButton" certificationId="${certification.id}" value="<bean:message key="certification.cancel"/>"/>
						</c:if>
						<c:if test="${canDelete}">
							<input type="button" class="button" id="deleteButton" certificationId="${certification.id}" value="<bean:message key="certification.delete"/>"/>
						</c:if>
			        	<c:if test="${isEditable}">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</c:if>
					</cyclos:cell>
	        	</cyclos:layout>
	        </td>
		</tr>
	</table>
</ssl:form>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>

<c:if test="${showGuarantees and not empty guarantees}"> 
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<td class="tdHeaderTable"><bean:message key="certification.guarantees"/></td>
		<cyclos:help page="guarantees#certification_guarantees" />
		<tr>
		    <td colspan="4" align="left" class="tdContentTableForms">
	    	  	<table class="defaultTable">
					<tr>
						<td class="tdHeaderContents"><bean:message key="guarantee.status"/></td>
						<td class="tdHeaderContents"><bean:message key="guarantee.amount"/></td>
						<td class="tdHeaderContents"><bean:message key="guarantee.creditFee"/></td>
						<td class="tdHeaderContents"><bean:message key="guarantee.issueFee"/></td>
						<td class="tdHeaderContents">&nbsp;</td>
					</tr>
					<c:forEach var="guarantee" items="${guarantees}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td><bean:message key="guarantee.status.${guarantee.status}"/></td>
							<td align="right"><cyclos:format number="${guarantee.amount}" unitsPattern="${guarantee.guaranteeType.currency.pattern}" /></td>
							<td align="right">								
				            	<bean:define id="creditFeePattern" value="${guarantee.creditFeeSpec.type == fixedFeeType ? guarantee.guaranteeType.currency.pattern : null}"/>
								<cyclos:format number="${guarantee.creditFeeSpec.fee}" unitsPattern="${creditFeePattern}" />
								<c:if test="${guarantee.creditFeeSpec.type != fixedFeeType}">
									<bean:message key="guaranteeType.feeType.${guarantee.creditFeeSpec.type}"/>
								</c:if>													
							</td>	
							<td align="right">								
				            	<bean:define id="issueFeePattern" value="${guarantee.issueFeeSpec.type == fixedFeeType ? guarantee.guaranteeType.currency.pattern : null}"/>
								<cyclos:format number="${guarantee.issueFeeSpec.fee}" unitsPattern="${issueFeePattern}" />
								<c:if test="${guarantee.issueFeeSpec.type != fixedFeeType}">
									<bean:message key="guaranteeType.feeType.${guarantee.issueFeeSpec.type}"/>
								</c:if>								
							</td>
		                    <td align="center"><img class="guaranteeDetails" src="<c:url value="/pages/images/view.gif" />" guaranteeId="${guarantee.id}" />	</td>
					</tr>						
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right"><cyclos:pagination items="${guarantees}" onClickHandler="refreshGuarantees" /></td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty certification.logs}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key='certification.logs'/></td>
	        <td class="tdHelpIcon" align="right" width="15%" valign="middle">
	       		<cyclos:help page="guarantees#certification_logs" td="false"/>
	        </td>
	    </tr>
		<tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	    	  	<table class="defaultTable">
					<tr>
						<th class="tdHeaderContents"><bean:message key="certificationLog.status"/></th>
						<th class="tdHeaderContents"><bean:message key="certificationLog.by"/></th>
						<th class="tdHeaderContents"><bean:message key="certificationLog.date"/></th>
					</tr>
					<c:forEach var="log" items="${certification.logs}" varStatus="idx">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="left"><cyclos:escapeHTML><bean:message key="certification.status.${log.status}"/></cyclos:escapeHTML></td>						
							<c:choose>	            				
		            			<c:when test="${logsBy[idx.index].byType == 'System'}">
		            				<td align="left" ><cyclos:escapeHTML value="${localSettings.applicationUsername}"/></td>
		            			</c:when>
		            			<c:when test="${logsBy[idx.index].byType == 'SystemTask'}">
		            				<td align="left" ><bean:message key="global.system"/></td>
		            			</c:when>
		            			<c:otherwise>
		            				<td align="left" title="<cyclos:escapeHTML value="${logsBy[idx.index].by.name}"/>">
		            				<cyclos:profile elementId="${logsBy[idx.index].by.id}"/></td>
		            			</c:otherwise>		            			
		            		</c:choose>		    
							<td align="center"><cyclos:format dateTime="${log.date}"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</c:if>
