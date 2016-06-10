<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:choose>
	<c:when test="${empty adInterest.id}">
		<c:set var="titleKey" value="adInterest.title.insert"/>
		<c:set var="helpPage" value="ads_interest#ad_interest_insert"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="adInterest.title.modify"/>
		<c:set var="helpPage" value="ads_interest#ad_interest_modify"/>
	</c:otherwise>
</c:choose>

<cyclos:script src="/pages/members/adinterests/editAdInterest.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="id" />
<html:hidden property="adInterest(id)" />
<html:hidden property="adInterest(owner)" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="${titleKey}"/>
        </td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="adInterest.title"/></td>
                    <td>
						<html:text property="adInterest(title)" maxlength="70" size="61" styleClass="full InputBoxDisabled" readonly="true"/>
                    </td>
				</tr>
			</table>
			<fieldset>
				<legend><bean:message key="adInterest.adFields"/></legend>
				<table class="defaultTable">
	                <tr>
	                    <td class="label"><bean:message key="adInterest.type"/></td>
	                    <td>
	                   		<c:forEach var="tradeType" items="${tradeTypes}">
	                    		<label>
	                    			<html:radio property="adInterest(type)" value="${tradeType}" styleClass="radio" disabled="true"/>
	                    			<bean:message key="adInterest.type.${tradeType}"/>
	                    		</label>
	                   		</c:forEach>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="adInterest.category"/></td>
	                    <td>
	    	                <html:select property="adInterest(category)" styleClass="InputBoxDisabled" disabled="true">
								<html:option value=""><bean:message key="messageCategory.all"/></html:option>
	    	                	<c:forEach var="adCategory" items="${adCategories}" >
	   	    		           		<html:option value="${adCategory.id}">${adCategory.fullName}</html:option>
	   	    		           	</c:forEach>
	                   		</html:select>
	                    </td>
	                </tr>
	                <tr>
						<td class="label"><bean:message key='member.username'/></td>
						<td>
							<html:hidden styleId="memberId" property="adInterest(member)"/>
							<input id="memberUsername" class="InputBoxDisabled large" disabled="true" value="${adInterest.member.username}">
							<div id="membersByUsername" class="autoComplete"></div>
						</td>
					</tr>
					<tr>
						<td class="label" width="25%"><bean:message key='member.name'/></td>
						<td>
							<input id="memberName" class="InputBoxDisabled large" disabled="true" value="${adInterest.member.name}">
							<div id="membersByName" class="autoComplete"></div>
						</td>
					</tr>
					<c:if test="${groupFilters != null}">
		                <tr>
		                    <td class="label"><bean:message key="adInterest.groupFilter"/></td>
		                    <td>
		    	                <html:select property="adInterest(groupFilter)" styleClass="InputBoxDisabled" disabled="true">
		    	                	<html:option value=""></html:option>
		    	                	<c:forEach var="groupFilter" items="${groupFilters}" >
		   	    		           		<html:option value="${groupFilter.id}">${groupFilter.name}</html:option>
		   	    		           	</c:forEach>
		                   		</html:select>
		                    </td>
		                </tr>
		            </c:if>
					<tr>
	   					<td class="nestedLabel">
	   						<span class="label"><bean:message key="adInterest.priceRange"/></span>
							<span class="lastLabel"><bean:message key="global.range.from"/></span>
						</td>
						<td>
							<html:text size="12" property="adInterest(initialPrice)" styleClass="small InputBoxDisabled float" readonly="true"/>
							&nbsp;
							<span class="inlineLabel"><bean:message key="global.range.to"/></span>
							<html:text size="12" property="adInterest(finalPrice)" styleClass="small InputBoxDisabled float" readonly="true"/>
							<c:choose>
							<c:when test="${not empty singleCurrency}">
								<html:hidden property="adInterest(currency)" value="${singleCurrency.id}" />
								${singleCurrency.symbol}
							</c:when>
							<c:otherwise>
								<html:select property="adInterest(currency)" disabled="true" styleClass="InputBoxDisabled">
		            				<c:forEach var="currency" items="${currencies}">
		            					<html:option value="${currency.id}">${currency.name}</html:option>
		            				</c:forEach>
		            			</html:select>
							</c:otherwise>
						</c:choose>
						</td>
			   		</tr>
	                <tr>
	                    <td class="label" valign="top"><bean:message key="adInterest.keywords"/></td>
	                    <td colspan="2">
	                    	<html:text property="adInterest(keywords)" maxlength="70" size="61" styleClass="full InputBoxDisabled" readonly="true"/>
	                    </td>
	                </tr>
	            </table>
			</fieldset>
	        <table class="defaultTable">
               	<tr>
					<td align="right" colspan="2">
						<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
						&nbsp;
						<input type="submit" id="saveButton" value="<bean:message key="global.submit"/>" class="ButtonDisabled" disabled="true">
					</td>
				</tr>
            </table>
		</td>            
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>