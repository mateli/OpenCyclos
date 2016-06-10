<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/channels/listChannels.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="channel.removeConfirmation"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="channel.title.list"/></td>
        <cyclos:help page="settings#channels"/>
    </tr>
	<tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents"><bean:message key='channel.displayName'/></th>
                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
                </tr>
				<c:forEach items="${channels}" var="channel">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left">${channel.displayName}</td>
	                    <td align="center" nowrap="nowrap">
	                    	<c:choose><c:when test="${canManage}">
	            				<img src="<c:url value="/pages/images/edit.gif"/>" class="edit channelDetails" channelId="${channel.id}"/>
	            				<c:if test="${!cyclos:contains(builtin, channel)}">
	                    			<img src="<c:url value="/pages/images/delete.gif"/>" class="remove" channelId="${channel.id}"/>
	                    		</c:if>
	            			</c:when><c:otherwise>
								<img src="<c:url value="/pages/images/view.gif"/>" class="view channelDetails" channelId="${channel.id}"/>
        	    			</c:otherwise></c:choose>
	                    </td>
	                 </tr>
				</c:forEach>
			</table>
		</td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<c:if test="${canManage}">
		<tr>
			<td align="right">
	        	<span class="label">
	        		<bean:message key="channel.action.new" />
	        	</span>
	        	<input type="button" class="button" value="<bean:message key="global.submit" />" id="newButton">
			</td>
		</tr>
	</c:if>
</table>
