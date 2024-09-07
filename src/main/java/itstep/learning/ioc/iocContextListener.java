package itstep.learning.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class iocContextListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new WebModule(),
                new ServiceModule()
        );
    }
}
/*
* Веб-проект предусматривает циркулирование событий жизненного цикла
* и подписку на них.
*
*
* */
