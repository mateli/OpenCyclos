<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/productsServices/viewAd.js" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="ad.title.view"/>
        </td>
        <cyclos:help page="advertisements#ads_of_member_description"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td width="25%" class="headerLabel"><bean:message key="ad.tradeType"/></td>
                    <td class="headerField"><bean:message key="ad.tradeType.${ad.tradeType}"/></td>
                    <td valign="top" align="right" rowspan="6">
						<cyclos:images images="${images}"/>
					</td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="ad.title"/></td>
                    <td class="headerField">${ad.title}</td>
				</tr>
                <tr>
                    <td class="headerLabel"><bean:message key="ad.price"/></td>
                    <td class="headerField">
                    	<c:set var="unspecified"><bean:message key="ad.price.unspecified" /></c:set>
                    	<cyclos:format number="${ad.price}" unitsPattern="${ad.currency.pattern}" default="${unspecified}" />
                    </td>
				</tr>
                <tr>
                    <td class="headerLabel"><bean:message key="ad.category"/></td>
                    <td class="headerField">${ad.category.fullName}</td>
                </tr>
              	<c:choose><c:when test="${ad.permanent}">
	                <tr>
	                    <td class="headerLabel"><bean:message key="ad.publicationPeriod.begin"/></td>
	                    <td class="headerField"><cyclos:format date="${ad.creationDate}" /></td>
	                </tr>
               	</c:when><c:otherwise>
	                <tr>
	                    <td class="headerLabel"><bean:message key="ad.publicationPeriod"/></td>
	                    <td class="headerField">
		               		<cyclos:format rawDate="${ad.publicationPeriod.begin}" /> - <cyclos:format rawDate="${ad.publicationPeriod.end}" />
	                    </td>
	                </tr>
               	</c:otherwise></c:choose>
                <tr>
                    <td class="headerLabel"><bean:message key="ad.owner"/></td>
                    <td class="headerField">
                    	<cyclos:profile elementId="${ad.owner.id}"/>
                    </td>
				</tr>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			                <td class="headerLabel">${field.name}</td>
			   				<td class="headerField" colspan="2">
			   					<cyclos:customField field="${field}" value="${value}" textOnly="true"/>
			   				</td>
						</tr>
					</c:if>
			    </c:forEach>
                <tr>
                    <td valign="top" class="headerLabel"><bean:message key="ad.description"/></td>
                    <td class="headerField" colspan="2">
                    	<c:choose><c:when test="${ad.html}">
                    		${ad.description}
                    	</c:when><c:otherwise>
	                    	<cyclos:escapeHTML>${ad.description}</cyclos:escapeHTML>
                    	</c:otherwise></c:choose>
                    </td>
                </tr>
            </table>
		</td>            
    </tr>
</table>

<table class="defaultTableContentHidden"><tr><td>
<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
</td></tr></table>