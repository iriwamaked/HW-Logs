package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filters.*;
import itstep.learning.servlets.*;

public class WebModule extends ServletModule{
    @Override
    protected void configureServlets(){
        //регистрируем фильтры
        //filter("/*").through(CharsetFilter.class);
       // filter("/*").through(LogFilter.class);
        //особенность: фильтр отрабатывает на ресурсные запросы (/сss, /js и т.д.)
        //Решение проблемы - нужны не все, а некоторые, внутрь передаем регулярное выражение
        //то есть будет проходить все, кроме
        //filterRegex - исключаем из фильтра сss/ и js/ и img/

        filterRegex("^/(?!css/.*|js/.*|img/.*).*$").through(CharsetFilter.class);
        filter("/*").through(CORSFilter.class);
        filterRegex("^/(?!css/.*|js/.*|img/.*).*$").through(LogFilter.class);

        //and servlets
        serve("/").with(HomeServlet.class);
        serve("/db").with(DbServlet.class);
        serve("/logs").with(LogServlet.class);
        serve("/generator").with(GeneratorServlet.class);
        serve("/user").with(UserServlet.class);
        serve("/userauthentification").with(UserAuthentificationServlet.class);
        serve("/userList").with(UserListServlet.class);
    }
}

/*Модуль конфигурации веб-сущностей (сервлетов, фильтров и т.д.)
 * Он предоставляет третий вариант регистрации фильтров и сервлетов.
 * Для него необходимо добавить для всех классов фильтров и сервлетов
 * аннотацию Singleton
 * а также снимаем другие формы регистрации (аннотации или web.xml)
 *
 * */
