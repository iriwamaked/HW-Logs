package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filters.*;
import itstep.learning.servlets.*;

public class WebModule extends ServletModule{
    @Override
    protected void configureServlets(){

        filter("/*").through(CharsetFilter.class);
        filter("/*").through(LogFilter.class);


        //and servlets
        serve("/").with(HomeServlet.class);
        serve("/db").with(DbServlet.class);
        serve("/logs").with(LogServlet.class);
    }
}

/*Модуль конфигурации веб-сущностей (сервлетов, фильтров и т.д.)
 * Он предоставляет третий вариант регистрации фильтров и сервлетов.
 * Для него необходимо добавить для всех классов фильтров и сервлетов
 * аннотацию Singleton
 * а также снимаем другие формы регистрации (аннотации или web.xml)
 *
 * */
