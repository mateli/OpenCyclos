<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>

<html:html>
<jsp:include page="/pages/general/layout/head.jsp" />
<body class="helpWindowBody">
	<table>
		<tr>
			<td align="center" valign="middle">
				<table class="helpWindowTable">
   					<tr>
       					<td valign="bottom" class="helpWindowTableContent">
							<tiles:insert attribute="body" />
							
							<center>
								<input type="button" onclick="window.close();" class="button" value="<bean:message key="global.close"/>">
							</center>
				        </td>
				    </tr>
				</table>   
			</td>
		</tr>
	</table>					
</body>
<script>
init();
</script>
</html:html>