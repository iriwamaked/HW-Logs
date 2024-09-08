<%
    String fileName = (String) request.getAttribute("fileName");
    String otp = (String) request.getAttribute("otp");
    String salt = (String) request.getAttribute("salt");
    String pass = (String) request.getAttribute("pass");
%>
<h1>String Generator + Inject</h1>
<p>fileName = <%= fileName %></p>
<p>otp = <%= otp %></p>
<p>salt = <%= salt %></p>
<p>pass = <%= pass %></p>