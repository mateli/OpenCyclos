<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:choose>
	<c:when test="${ad['transient']}">
		<c:set var="titleKey" value="ad.title.insert"/>
		<c:set var="helpPage" value="advertisements#ad_insert"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="ad.title.modify"/>
		<c:set var="helpPage" value="advertisements#ad_modify"/>
	</c:otherwise>
</c:choose>

<cyclos:script src="/pages/productsServices/editAd.js" />

<ssl:form method="post" action="${formAction}" enctype="multipart/form-data">
<html:hidden property="id" />
<html:hidden property="memberId" />
<html:hidden property="ad(id)" />
<html:hidden property="ad(owner)" />
<html:hidden property="ad(html)" />

<c:set var="imageRowSpan" value="${6 + fn:length(customFields)}" />
<c:if test="${enablePermanent}">
	<c:set var="imageRowSpan" value="${imageRowSpan + 1}" />
</c:if>
<c:if test="${enableExternalPublication}">
	<c:set var="imageRowSpan" value="${imageRowSpan + 1}" />
</c:if>

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
                    <td class="label"><bean:message key="ad.tradeType"/></td>
                    <td>
                   		<c:forEach var="tradeType" items="${tradeTypes}">
                    		<label>
                    			<html:radio property="ad(tradeType)" value="${tradeType}" styleClass="radio" disabled="true"/>
                    			<bean:message key="ad.tradeType.${tradeType}"/>
                    		</label>
                    		&nbsp;
                   		</c:forEach>
                    </td>
                    <td valign="top" align="right" rowspan="${imageRowSpan}">
						<cyclos:images images="${images}" editable="true" style="float:right;" />
					</td>
                </tr>
                <tr>
                    <td class="label" width="20%"><bean:message key="ad.title"/></td>
                    <td>
						<html:text property="ad(title)" maxlength="70" size="61" styleClass="full InputBoxDisabled" readonly="true"/>
                    </td>
				</tr>
                <tr>
                    <td class="label"><bean:message key="ad.category"/></td>
                    <td>
    	                <html:select value="${ad.category.id}" property="ad(category)" styleClass="InputBoxDisabled" disabled="true">
    	                	<html:option value=""><bean:message key="ad.category.choose" /></html:option>
    	                	<c:set var="lastRoot" value="${null}" />
    	                	<c:forEach items="${categories}" var="category">
    	                		<c:set var="root" value="${category.rootCategory}" />
    	                		<c:if test="${root != lastRoot && category.level > 1}">
		    	                	<c:set var="openGroup" value="${true}" />
    	                			<optgroup label="${root.name}">
    	                		</c:if>
    	                		<c:set var="prop" value="${openGroup ? 'fullNameButRoot' : 'fullName'}" />
   	    		           		<html:option value="${category.id}">${category[prop]}</html:option>
   	    		           		<c:set var="lastRoot" value="${root}" />
   	    		           	</c:forEach>
   	    		           	<c:if test="${openGroup}">
   	    		           		</optgroup>
   	    		           	</c:if>
                   		</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="ad.price"/></td>
                    <td>
						<html:text size="12" property="ad(price)" styleClass="small InputBoxDisabled float" readonly="true"/>
						<c:choose>
							<c:when test="${not empty singleCurrency}">
								<html:hidden property="ad(currency)" value="${singleCurrency.id}" />
								${singleCurrency.symbol}
							</c:when>
							<c:otherwise>
								<html:select property="ad(currency)" disabled="true" styleClass="InputBoxDisabled">
		            				<c:forEach var="currency" items="${currencies}">
		            					<html:option value="${currency.id}">${currency.symbol}</html:option>
		            				</c:forEach>
		            			</html:select>
							</c:otherwise>
						</c:choose>
                    </td>
				</tr>
				<c:if test="${enablePermanent}">
					<tr>
	                    <td class="label"><bean:message key='ad.permanent'/></td>
						<td><html:checkbox property="ad(permanent)" styleClass="checkbox" disabled="true" value="true" styleId="notExpirableCheck"/></td>
	                </tr>
	            </c:if>
                <tr>
                    <td class="label"><bean:message key="ad.publicationPeriod.begin"/></td>
                    <td>
						<html:text property="ad(publicationPeriod).begin" styleClass="small InputBoxDisabled date" styleId="publicationDate" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="ad.publicationPeriod.end"/></td>
                    <td>
						<html:text size="12" property="ad(publicationPeriod).end" styleClass="small InputBoxDisabled date" styleId="expiryDate" readonly="true"/>
                    </td>
                </tr>
                <c:if test="${enableExternalPublication}">
	                <tr>
	                    <td class="label"><bean:message key='ad.externalPublication'/></td>
						<td><html:checkbox property="ad(externalPublication)" styleClass="checkbox" disabled="true" value="true"/></td>
	                </tr>
	            </c:if>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		   				<td colspan="2">
		   					<cyclos:customField field="${field}" value="${value}" editable="true" valueName="ad(customValues).value" fieldName="ad(customValues).field" enabled="false"/>
		   				</td>
					</tr>
			    </c:forEach>
                <tr>
                    <td class="label" valign="top"><bean:message key="ad.description"/></td>
                    <td colspan="2">
                    	<c:choose><c:when test="${cyclos:name(descriptionFormat) == 'PLAIN'}">
                    		<html:textarea property="ad(description)" styleId="descriptionText" rows="9" styleClass="full InputBoxDisabled" readonly="true" />
                    	</c:when><c:otherwise>
	                    	<cyclos:richTextArea name="ad(description)" styleId="descriptionText" value="${ad.description}" disabled="true" />
                    	</c:otherwise></c:choose> 
                    </td>
                </tr>
                <c:if test="${editable}">
			        <script language="JavaScript">
			        	var maxImages = ${maxImages};
			        </script>
					<tr style="display:none" id="trMaxPictures">
						<td class="label"><bean:message key="ad.addPicture"/></td>
						<td colspan="2">						
							<div class="fieldDecoration">
								<bean:message key="ad.maxPicturesMessage"/>
							</div>
						</td>
					</tr>
					<tr style="display:none" id="trPictureCheck">
						<td class="label"><bean:message key="ad.addPicture"/></td>
						<td colspan="2">						
							<input type="checkbox" class="checkbox" disabled="disabled" id="addPictureCheck">
						</td>
					</tr>
			        <tr class="trPicture" style="display:none">
						<td class="label"><bean:message key="image.file"/></td>
						<td colspan="2">						
							<html:file property="picture" styleId="pictureFile" styleClass="InputBoxDisabled upload"/>
						</td>
					</tr>
			        <tr class="trPicture" style="display:none">
						<td class="label"><bean:message key="image.caption"/></td>
						<td colspan="2">						
							<html:text property="pictureCaption" styleId="captionText" readonly="true" styleClass="full InputBoxDisabled"/>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="3">
							<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
							&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="true" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
            </table>
		</td>            
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<c:if test="${param.fromQuickAccess || !param.fromMenu}">
			<td align="left">
				<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
			</td>
		</c:if>
		<c:if test="${!empty ad.id && !maxAds}">
			<td align="right">
				<span class="label"><bean:message key="ad.new"/></span>
				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
			</td>
		</c:if>
	</tr>
</table>
</ssl:form>