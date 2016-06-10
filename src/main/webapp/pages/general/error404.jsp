<%
request.setAttribute("errorKey", "errors.pageNotFound");
request.getRequestDispatcher("/pages/general/error.jsp").include(request, response);
%>