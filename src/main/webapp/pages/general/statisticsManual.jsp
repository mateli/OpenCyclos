<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="manual.title.stats"/></td>
        <td class="tdHelpIcon" height="16">&nbsp;</td>
    </tr>
    <tr> 
        <td class="tdContentTable" colspan="2">
			<table class="defaultTableContentHidden">
				<tr>
        			<td align="left">
        				<div align="justify">
							<cyclos:includeCustomizedFile type="static" name="statistics_manual.jsp" />
						</div>
					</td>
				</tr>
			</table>
        </td>
   </tr>
</table>