package itstep.learning.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Singleton
public class CORSFilter implements Filter {
    FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp=(HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest)  request;
        // Разрешение запроса с любого домена
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // Разрешенные методы запроса
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        // Разрешенные заголовки
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // Поддержка сессий
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        //обрабатываем предварительный (preflight) запрос, который отправляется методом OPTIONS
        //такой запрос отправляется, когда клиент (браузер) хочет проверить, разрешены ли такие запросы
        //Если это OPTIONS запрос, сервер просто отвечает кодом 200, не выполняя никакую логику.
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

//        System.out.println("Request Method: " + req.getMethod());
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}

