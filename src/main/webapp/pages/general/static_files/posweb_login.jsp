<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<body>

<div class="poswebBanner" id="poswebBanner">
<cyclos:customImage type="system" name="systemLogo.gif" style="float:left;margin-left:47px;margin-top: 34px;" />
<div class="poswebBannerText"><bean:message key="posweb.bannerText" /></div>
</div>

<table class="poswebRoot" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div align="center">
				<table class="defaultTableContent poswebLoginTable" cellspacing="0" cellpadding="0">
				    <tr>
				        <td class="tdHeaderTable">
				        	<bean:message key="posweb.title.login"/>
				        </td>
				        <td class="tdHelpIcon">&nbsp;</td>
				    </tr>
				    <tr>
				        <td colspan="2" align="left" class="tdContentTableForms">
				        	
				        	<jsp:include flush="true" page="/pages/posweb/includes/loginForm.jsp" />
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>

</body>