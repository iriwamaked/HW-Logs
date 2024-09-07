package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import itstep.learning.services.hash.HashService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class HomeServlet extends HttpServlet{
    private final HashService digestHashService;  //Message Digest
    private final HashService signatureHashService; //Secure Hash algorithm
    private final String viewsFolder;
    private final String resourcesFolder;

    @Inject
    public HomeServlet(
            @Named("digest") HashService digestHashService,
            @Named("signature") HashService signatureHashService,
            @Named("viewsFolder")String viewsFolder,
            @Named("resourcesFolder")String resourcesFolder) {
        this.digestHashService = digestHashService;
        this.signatureHashService=signatureHashService;
        this.viewsFolder=viewsFolder;
        this.resourcesFolder=resourcesFolder;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse  resp) throws ServletException, IOException {
        //ViewData["fromServlet"]="HomeServlet"
        req.setAttribute("fromServlet", digestHashService.digest("123") + " " +
                signatureHashService.digest("123") + " "  +
                viewsFolder + " " + resourcesFolder

        );
        req.setAttribute("pageBody", "index.jsp");

        //аналогія return View("index.jsp")
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req,resp);
    }

}

/*
* Сервлети - спеціалізовані класи для мережних задач.
* У веб-проектах грають роль контроллерів (АРІ - контролерів)
*
* */