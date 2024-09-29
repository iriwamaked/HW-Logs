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
    <meta charset="utf-8"/>
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
                <div id="auth-buttons" class= "ms-auto me-3">
                    <button id="sign-in-button" type="button" class="btn btn-primary"
                            data-bs-toggle="modal" data-bs-target="#loginModal">Sign In</button>
                    <button id="sign-out-button" type="button"
                            class="btn btn-danger" style="display:none;">Out</button>
                </div>
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

<!-- Модальное окно -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">Authentication</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="loginForm" action="<%=contextPath%>/userauthentification">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <div id="success-message1" style="color: green; display: none;"></div>
                    <div id="error-message1" style="color: red; display: none;"></div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="<%=contextPath%>/js/site.js"></script>
</body>
</html>
