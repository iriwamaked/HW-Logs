<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<?> logs = (List<?>) request.getAttribute("logs");
%>

<h1>Log Entries</h1>
<ul class="list-group">
    <%
        for (Object log: logs){%>
            <li class="list-group-item"><%=log.toString()%></li>
       <% }%>

</ul>