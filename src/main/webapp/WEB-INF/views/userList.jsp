<%@ page import="java.util.List" %>
<%@ page import="itstep.learning.data.dto.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String contextPath= request.getContextPath();
    List<?> users = (List<?>) request.getAttribute("users");
%>
<h1>Users List</h1>
<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Object obj : users) {
            User user = (User) obj; // Приводим к типу User
    %>
    <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td><button id = "updateUserBtn" type="button" class="btn btn-primary"
                    data-bs-toggle="modal" data-bs-target="#updateModal"
                    data-id="<%= user.getId() %>"
                    data-name="<%= user.getName() %>"
                    data-email="<%= user.getEmail() %>"
                    data-password="<%= user.getPasswordHash() %>">Update</button></td>
        <td><button type="button" class="btn btn-danger" data-id="<%= user.getId()%>">Delete</button></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<!-- Модальное окно для редактирования пользователя -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalLabel">Edit User</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="updateUser" action="<%=contextPath%>/user">
                    <input type="hidden" name="userId" id="userId">
                    <div class="form-group">
                        <label for="userName">Name</label>
                        <input type="text" class="form-control" id="userName" name="userName" required>
                    </div>
                    <div class="form-group">
                        <label for="userEmail">Email</label>
                        <input type="email" class="form-control" id="userEmail" name="userEmail" required>
                    </div>
                    <div class="form-group">
                        <label for="userEmail">Password</label>
                        <input type="password" class="form-control" id="userPassword" name="userEmail" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </form>
            </div>
        </div>
    </div>
</div>
