package itstep.learning.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysql.cj.jdbc.Driver;
import itstep.learning.data.dal.LogsDao;
import itstep.learning.data.dto.LogsEntity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
@Singleton
public class LogFilter implements Filter {
    FilterConfig filterConfig;
    private final LogsDao logDao;
    @Inject
    public LogFilter(LogsDao logDao) {
        this.logDao = logDao;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        System.out.println("LOGFilter  works");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURL().toString();
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println("Processing request URL: " + url + " at " + dateTime);

        LogsEntity logsEntity=new LogsEntity(url,dateTime);
        logDao.logRequest(logsEntity);
//        try {
//            DriverManager.registerDriver(new Driver());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try (Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3308/JAVA_SPU_221_LOC?useUnicode=true&characterEncoding=UTF8",
//                "user_221", "pass_221")) {
//
//            String createTableQuery = "CREATE TABLE IF NOT EXISTS logi (" +
//                    "id INT AUTO_INCREMENT PRIMARY KEY," +
//                    "url VARCHAR(255)," +
//                    "date_time TIMESTAMP" +
//                    ");";
//
//            try (PreparedStatement createTableStmt = connection.prepareStatement(createTableQuery)) {
//                createTableStmt.executeUpdate();
//            }
//
//            String insertSql = "INSERT INTO logi (url, date_time) VALUES (?, ?)";
//
//            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
//                insertStmt.setString(1, url);
//                insertStmt.setTimestamp(2, Timestamp.valueOf(dateTime));
//                insertStmt.executeUpdate();
//            }
//            System.out.println("Logged request URL: " + url);
//        } catch (SQLException e) {
//            throw new ServletException("Database access error", e);
//        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

}