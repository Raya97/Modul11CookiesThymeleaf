package Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class TimeServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        // Setting up the template resolver for Thymeleaf
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(this.getServletContext());
        templateResolver.setPrefix("/WEB-INF/Thymeleaf/"); // Setting the path prefix for templates
        templateResolver.setSuffix(".html"); // Setting the file suffix for templates
        templateResolver.setTemplateMode(TemplateMode.HTML); // Setting the template mode to HTML
        templateResolver.setCharacterEncoding("UTF-8"); // Setting the character encoding for templates
        templateResolver.setCacheable(false); // Disabling template caching for easier development

        // Creating and configuring the TemplateEngine for Thymeleaf
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8"); // Setting the response content type and encoding

        // Retrieving the timezone from the request parameter or cookie
        String timezone = req.getParameter("timezone");
        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                if ("lastTimezone".equals(cookie.getName())) {
                    timezone = cookie.getValue();
                    break;
                }
            }
            if (timezone == null || timezone.isEmpty()) {
                timezone = "UTC+02:00"; // Default value if timezone is not specified
            }
        }

        // Setting the timezone and obtaining the current time
        ZoneId zoneId = ZoneId.of(timezone);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);

        // Manually create the timezone string for display
        String customTimeZoneString = "UTC+2";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss '" + customTimeZoneString + "'");
        String formattedTime = zonedDateTime.format(formatter);

        // Creating and setting a cookie with the last used timezone
        Cookie timezoneCookie = new Cookie("lastTimezone", timezone);
        timezoneCookie.setMaxAge(86400); // Setting the cookie lifetime to 24 hours
        resp.addCookie(timezoneCookie);

        // Preparing and sending the response using Thymeleaf
        WebContext context = new WebContext(req, resp, getServletContext());
        context.setVariable("currentTime", formattedTime);

        templateEngine.process("time", context, resp.getWriter());
    }

}
