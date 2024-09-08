package itstep.learning.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import java.io.IOException;

@Singleton
public class CharsetFilter implements Filter {
    FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
       //прямой ход (от сервера к сервелету)
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        System.out.println("Filter works");

        //Передача работы следующему фильтру/сервлету
        chain.doFilter(servletRequest, servletResponse);
        //обратный ход (от сервлета к серверу)


    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}

/*
* Фильтры (сервлетные фильтры) - реализация концепции "Middleware" -
последовательного прохождения блоков программного обеспечения (объектов)
на пути обработки запроса и формирования ответа

У фильтра есть три метода жизненного цикла:
- init - создание фильтра (объекта класса);
*
* */