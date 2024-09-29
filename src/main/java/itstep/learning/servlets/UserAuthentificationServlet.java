package itstep.learning.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.dal.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Singleton
public class UserAuthentificationServlet extends HttpServlet {
    private final UserDao userDao;
    private final Gson gson = new Gson();
    @Inject
    public UserAuthentificationServlet(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonData = req.getReader().lines().collect(Collectors.joining());
        JsonObject body = JsonParser.parseString(jsonData).getAsJsonObject();

        String email = body.get("email").getAsString();
        String password = body.get("password").getAsString();

        boolean authenticated = userDao.authenticateUser(email, password); // Проверьте аутентификацию
        if (authenticated) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"status\":{\"isOk\": true}}");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"status\":{\"isOk\": false, \"phrase\": \"Invalid credentials\"}}");
        }
    }
}
