<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"  "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:noCache/>
<html:html xhtml="true">
<head>
    <title>${localSettings.applicationName}</title>
    <cyclos:customizedFilePath type="style" name="mobile.css" var="styleUrl" groupFilterId="${styleGroupFilter.id}" groupId="${styleGroup.id}" />
    <link rel="stylesheet" href="<c:url value="${styleUrl}"/>">
</head>
<body>
<tiles:get name="body"/>
</body>
</html:html>