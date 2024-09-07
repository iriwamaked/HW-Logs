package itstep.learning.servlets;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class LogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> logs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/JAVA_SPU_221_LOC?useUnicode=true&characterEncoding=UTF8",
                "user_221", "pass_221")) {

            String selectSql = "SELECT url, date_time FROM logi ORDER BY date_time DESC";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql);
                 ResultSet rs = selectStmt.executeQuery()) {

                while (rs.next()) {
                    String url = rs.getString("url");
                    Timestamp timestamp = rs.getTimestamp("date_time");
                    logs.add(url + " at " + timestamp);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
        req.setAttribute("pageBody", "logs.jsp");
        req.setAttribute("logs", logs);
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req, resp);
    }
}