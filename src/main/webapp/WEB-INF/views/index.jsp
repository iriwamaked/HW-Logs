
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath= request.getContextPath(); // ~
    String fromServlet = (String) request.getAttribute("fromServlet");
%>

    <!--HTML comment-->
    <h1>JSP</h1>

    <a href="<%= contextPath %>/hello.jsp">Hello world</a>

    <p>Java Server Pages - технологія створення веб-застосунків на Java</p>
    <p>fromServlet=<%=fromServlet%></p>
  <%
      //<Блок коду за синтаксисом Java
      int x = 10;
      double y = x / .01;
      int[] arr = {1,2,3,4,5,6,7};
  %>
    //Вывод
    <p>x = <%=x%></p>
  <p>y = <%=y%></p>
  <% for (int a:arr){%>
    <span>element = <%=a%></span> &emsp;
<%}%>

