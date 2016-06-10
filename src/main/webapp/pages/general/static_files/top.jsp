<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<div class="topBanner" id="topBanner" style="display:none">
	<div class="topBannerText"><bean:message key="top.message" /></div>
</div>
<script>
    if (self === top) {
        $('topBanner').show();
    }        
</script>