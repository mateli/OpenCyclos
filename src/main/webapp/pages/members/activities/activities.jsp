<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/activities/activities.js" />


<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<c:choose><c:when test="${myActivities}">
	        	<bean:message key="activities.title.my"/>
	        </c:when><c:otherwise>
		        <bean:message key="activities.title.of" arg0="${member.name}"/>
	        </c:otherwise></c:choose>
        </td>
        <cyclos:help page="reports#member_activities"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<tr>
					<td width="30%" class="headerLabel"><bean:message key="activities.sinceActive"/></td>
					<td class="headerField"><cyclos:format date="${activities.sinceActive}"/></td>
	            	<c:if test="${member.group.broker}">
						<td width="30%" class="headerLabel"><bean:message key="activities.brokering.numberMembers"/></td>
						<td class="headerField"><cyclos:format number="${activities.numberBrokeredMembers}" /></td>
					</c:if>
                </tr>
               	
               	<c:if test="${activities.showReferencesInformation}">
	               	<tr>
						<td colspan="4" class="label" style="text-align:left">
							<hr>
							<bean:message key="activities.references"/>
						</td>
					</tr>            	
	               	<tr>
						<td colspan="2" class="headerLabel" width="50%" style="text-align: center"><bean:message key="activities.references.given"/></td>
						<td colspan="2" class="headerLabel" width="50%" style="text-align: center"><bean:message key="activities.references.received"/></td>
					</tr>
					<c:set var="givenReferences" value="${activities.givenReferencesByLevel}"/>
					<c:set var="receivedReferences" value="${activities.receivedReferencesByLevel}"/>
					<c:forEach var="level" items="${referenceLevels}">
		            	<tr>
							<td width="30%" class="headerLabel"><bean:message key="reference.level.${level}"/></td>
							<td width="20%" class="headerField"><cyclos:format number="${givenReferences[level]}" /></td>
							<td width="30%" class="headerLabel"><bean:message key="reference.level.${level}"/></td>
							<td width="20%" class="headerField"><cyclos:format number="${receivedReferences[level]}" /></td>
		                </tr>
					</c:forEach>
				</c:if>
               	
               	<c:if test="${activities.showAdsInformation}">
	               	<tr>
						<td colspan="4" class="label" style="text-align:left">
							<hr>
							<bean:message key="activities.ads"/>
						</td>
					</tr>            	
	            	
	            	<tr>
						<td class="headerLabel"><bean:message key="ad.status.ACTIVE"/></td>
						<td class="headerField"><cyclos:format number="${activities.adsByStatus[adStatus['ACTIVE']]}" /></td>
						<c:if test="${activities.showNonActiveAdsInformation}">
							<td class="headerLabel"><bean:message key="ad.status.EXPIRED"/></td>
							<td class="headerField"><cyclos:format number="${activities.adsByStatus[adStatus['EXPIRED']]}" /></td>
						</c:if>
	                </tr>
					<c:if test="${activities.showNonActiveAdsInformation}">
		            	<tr>
							<td class="headerLabel"><bean:message key="ad.status.PERMANENT"/></td>
							<td class="headerField"><cyclos:format number="${activities.adsByStatus[adStatus['PERMANENT']]}" /></td>
							<td class="headerLabel"><bean:message key="ad.status.SCHEDULED"/></td>
							<td class="headerField"><cyclos:format number="${activities.adsByStatus[adStatus['SCHEDULED']]}" /></td>
		                </tr>
		            </c:if>
	            </c:if>
                
				<c:if test="${activities.showInvoicesInformation}">
	               	<tr>
						<td colspan="4" class="label" style="text-align:left">
							<hr>
							<bean:message key="activities.invoices"/>
						</td>
					</tr>            	
	            	<tr>
						<td width="30%" class="headerLabel"><bean:message key="activities.invoices.outgoing.count"/></td>
						<td class="headerField"><cyclos:format number="${activities.outgoingInvoices.count}" /></td>
						<td width="30%" class="headerLabel"><bean:message key="activities.invoices.outgoing.amount"/></td>
						<td class="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${activities.outgoingInvoices.amount}"/></td>
	                </tr>
	               	<tr>
						<td width="30%" class="headerLabel"><bean:message key="activities.invoices.incoming.count"/></td>
						<td class="headerField"><cyclos:format number="${activities.incomingInvoices.count}" /></td>
						<td width="30%" class="headerLabel"><bean:message key="activities.invoices.incoming.amount"/></td>
						<td class="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${activities.incomingInvoices.amount}"/></td>
	                </tr>
	            </c:if>
			</table>
		</td>
	</tr>
</table>

<c:forEach var="entry" items="${activities.accountActivities}">
	<c:set var="accountName" value="${entry.key}"/>
	<c:set var="accountActivities" value="${entry.value}"/>
	<c:set var="accountStatus" value="${accountActivities.accountStatus}"/>
	<c:set var="unitsPattern" value="${accountStatus.account.type.currency.pattern}"/>
	<c:set var="reservedAmount" value="${cyclos:round(accountStatus.reservedAmount)}"/>
	<c:if test="${activities.showAccountInformation || accountActivities.hasRateInfo}">
		
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
			<tr>
	   	    	<td class="tdHeaderTable">${accountName}</td>
	        	<td class="tdHelpIcon" height="16">&nbsp;</td>
	    	</tr>
	    	<tr>
	        	<td align="left" class="tdContentTableForms" colspan="2">
	        		<cyclos:layout columns="4" className="defaultTable">
						<c:if test="${activities.showAccountInformation && accountActivities.showAccountInfo}">
            				<cyclos:cell width="30%" className="headerLabel"><bean:message key="account.balance"/></cyclos:cell>
            				<cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountStatus.balance}"/></cyclos:cell>
                   			<cyclos:rowBreak/>
						</c:if>

						<c:if test="${activities.showAccountInformation && accountActivities.showAccountInfo}">
	                       	<c:if test="${accountStatus.creditLimit > 0}">
	                           	<cyclos:cell width="30%" className="headerLabel"><bean:message key="account.creditLimit"/></cyclos:cell>
	                           	<cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${-accountStatus.creditLimit}"/></cyclos:cell>
	                       	</c:if>
	                       	<c:if test="${accountStatus.upperCreditLimit > 0}">
	                           	<cyclos:cell width="30%" className="headerLabel"><bean:message key="account.upperCreditLimit"/></cyclos:cell>
	                           	<cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountStatus.upperCreditLimit}"/></cyclos:cell>
	                       	</c:if>
	                       	<c:if test="${reservedAmount > 0}">
	                           	<cyclos:cell width="30%" className="headerLabel"><bean:message key="account.reservedAmount"/></cyclos:cell>
	                           	<cyclos:cell><cyclos:format unitsPattern="${unitsPattern}" number="${reservedAmount}"/></cyclos:cell>
	                       	</c:if>
	                       	<cyclos:rowBreak/>
	
		            		<cyclos:cell className="headerLabel"><bean:message key="activities.loans.count"/></cyclos:cell>
		            		<cyclos:cell className="headerField"><cyclos:format number="${accountActivities.remainingLoans.count}" /></cyclos:cell>
		            		<cyclos:cell className="headerLabel"><bean:message key="activities.loans.amount"/></cyclos:cell>
		            		<cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountActivities.remainingLoans.amount}"/></cyclos:cell>
	
							<cyclos:cell colspan="4" className="label" style="text-align:left">
	                        	<hr>
	                        	<bean:message key="activities.transactions.last30Days"/>
		                    </cyclos:cell>
		                    
		                    <cyclos:cell className="headerLabel"><bean:message key="activities.transactions.numberBuy"/></cyclos:cell>
		                    <cyclos:cell className="headerField"><cyclos:format number="${accountActivities.debitsLast30Days.count}" /></cyclos:cell>
		                    <cyclos:cell className="headerLabel"><bean:message key="activities.transactions.averageBuy"/></cyclos:cell>
	                        <cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountActivities.debitsLast30Days.average}"/></cyclos:cell>
	                        
	                        <cyclos:cell className="headerLabel"><bean:message key="activities.transactions.numberSell"/></cyclos:cell>
		                    <cyclos:cell className="headerField"><cyclos:format number="${accountActivities.creditsLast30Days.count}" /></cyclos:cell>
	                        <cyclos:cell className="headerLabel"><bean:message key="activities.transactions.averageSell"/></cyclos:cell>
	                        <cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountActivities.creditsLast30Days.average}"/></cyclos:cell>
	                        
							<cyclos:cell colspan="4" className="label" style="text-align:left">
	                         	<hr>
								<bean:message key="activities.transactions.total"/>
		                    </cyclos:cell>
		                    
		                    <cyclos:cell className="headerLabel"><bean:message key="activities.transactions.numberBuy"/></cyclos:cell>
		                    <cyclos:cell className="headerField"><cyclos:format number="${accountActivities.debitsAllTime.count}" /></cyclos:cell>
							<cyclos:cell className="headerLabel"><bean:message key="activities.transactions.averageBuy"/></cyclos:cell>
	                        <cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountActivities.debitsAllTime.average}"/></cyclos:cell>
	
		                    <cyclos:cell className="headerLabel"><bean:message key="activities.transactions.numberSell"/></cyclos:cell>
		                    <cyclos:cell className="headerField"><cyclos:format number="${accountActivities.creditsAllTime.count}" /></cyclos:cell>
	                        <cyclos:cell className="headerLabel"><bean:message key="activities.transactions.averageSell"/></cyclos:cell>
	                        <cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountActivities.creditsAllTime.average}"/></cyclos:cell>
	                        
		                    <c:if test="${member.group.broker}">
			            		<cyclos:cell colspan="4" className="label" style="text-align:left"><b><bean:message key="activities.brokering"/></b></cyclos:cell>
			            		
				                <cyclos:cell className="headerLabel"><bean:message key="activities.brokering.commission.count"/></cyclos:cell>
			                    <cyclos:cell className="headerField"><cyclos:format number="${accountActivities.brokerCommission.count}" /></cyclos:cell>
		                        <cyclos:cell className="headerLabel"><bean:message key="activities.brokering.commission.amount"/></cyclos:cell>
		                        <cyclos:cell className="headerField"><cyclos:format unitsPattern="${unitsPattern}" number="${accountActivities.brokerCommission.amount}"/></cyclos:cell>
			                </c:if>
		                </c:if>
	    			</cyclos:layout>
	    		</td>
	    	</tr>
	    </table>
	</c:if>
</c:forEach>

<c:if test="${!myActivities}">
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>