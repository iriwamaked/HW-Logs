<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath=request.getContextPath();
    String pageBody = (String) request.getAttribute("pageBody");
    if (pageBody == null) {
        pageBody = "hello.jsp";
    }
%>
<html>
<head>
    <title>Java Web</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=contextPath%>/css/site.css"/>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="<%=contextPath%>/img/smile.jpg"  alt="Logo"
                     class="d-inline-block align-text-top"/>
                Java-Web</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="<%=contextPath%>">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=contextPath%>/db">DB</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=contextPath%>/logs">Logs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=contextPath%>/generator">Generator</a>
                    </li>
                    <%--                <li class="nav-item">--%>
                    <%--                    <a class="nav-link disabled" aria-disabled="true">Disabled</a>--%>
                    <%--                </li>--%>
                </ul>
            </div>
        </div>

    </nav>
</header>

<main class="container">
    <jsp:include page="<%=pageBody%>"/>
</main>

<div class="spacer"></div>
<footer class="d-flex justify-content-center text-center">
    <div class="px-3 border-end">
        <a class="nav-link active" aria-current="page" href="<%=contextPath%>">Home</a>
    </div>
    <div class="px-3 border-end">
        <a class="nav-link " href="<%=contextPath%>/db">DB</a>
    </div>
    <div class="px-3 border-end">
        <a class="nav-link" href="<%=contextPath%>/logs">Logs</a>
    </div>
    <div class="px-3 border-end">
    <a class="nav-link" href="<%=contextPath%>/generator">Generator</a>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="<%=contextPath%>/js/site.js"></script>
</body>
</html>
