package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.dal.UserDao;
import itstep.learning.data.dto.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Singleton
public class UserListServlet extends HttpServlet {
    private final UserDao userDao;

    @Inject
    public UserListServlet(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.getAllUsers();
        req.setAttribute("pageBody", "userList.jsp");
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/views/_layout.jsp");
        dispatcher.forward(req, resp);
    }
}
