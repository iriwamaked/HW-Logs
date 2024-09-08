package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import itstep.learning.services.generator.StringGeneratorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Singleton
public class GeneratorServlet extends HttpServlet {
    public final StringGeneratorService fileNameGenService;
    public final StringGeneratorService otpGenService;
    public final StringGeneratorService saltGenService;
    public final StringGeneratorService passGenService;

    @Inject
    public GeneratorServlet(
            @Named("fileName") StringGeneratorService fileNameGenService,
            @Named("otp") StringGeneratorService otpGenService,
            @Named("salt") StringGeneratorService saltGenService,
            @Named("password") StringGeneratorService passGenService
    )
    {
        this.fileNameGenService = fileNameGenService;
        this.otpGenService = otpGenService;
        this.saltGenService=saltGenService;
        this.passGenService = passGenService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ViewData["fromServlet"]="HomeServlet"
        req.setAttribute("fileName", fileNameGenService.generate(10));
        req.setAttribute("otp", otpGenService.generate(10));
        req.setAttribute("salt", saltGenService.generate(12));
        req.setAttribute("pass", passGenService.generate(10));
        req.setAttribute("pageBody", "generator.jsp");
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req,resp);
    }
}
