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
public class DbServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Подключение к БД
        //JDBC (Java Database Connectivity) - аналог ADO/PDO
        //Унифицированная технология доступа к БД
        //суть которой: подключение - инструкция - результат
        Connection connection = null;
        Driver mysqlDriver = null;
        try{
            mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/JAVA_SPU_221_LOC?useUnicode=true&characterEncoding=UTF8",
                    "user_221", "pass_221");
            String sql= "SHOW DATABASES";
            Statement stmt=connection.createStatement(); //SQLCommand
            ResultSet res= stmt.executeQuery(sql);      //SqlDataReader
            List<String> databases  = new ArrayList<>();
            while (res.next()){
                databases.add(res.getString(1)); //нумерація у JDBC - з 1 (у PHP, C# - з 0)
            }
            req.setAttribute("databases", databases);
            res.close();
            stmt.close();
        }
        catch (SQLException ex){
            req.setAttribute("error", ex.getMessage());
        }


        req.setAttribute("pageBody", "db.jsp");
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req, resp);
    }
}
