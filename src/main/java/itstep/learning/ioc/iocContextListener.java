package itstep.learning.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContextEvent;

public class iocContextListener extends GuiceServletContextListener {
    private DbModule dbModule;

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new WebModule(),
                new ServiceModule(),
                dbModule= new DbModule()
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(dbModule!=null){
            dbModule.close();
            System.out.println("Connection is closed");
        }
        super.contextDestroyed(servletContextEvent);
    }
}
/*
* Веб-проект предусматривает циркулирование событий жизненного цикла
* и подписку на них.
*
*
* */
