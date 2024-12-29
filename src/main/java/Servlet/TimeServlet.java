package Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        // Налаштування Thymeleaf Template Resolver
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(this.getServletContext());
        templateResolver.setPrefix("/WEB-INF/Thymeleaf/"); // Шлях до шаблонів
        templateResolver.setSuffix(".html"); // Суфікс файлів шаблонів
        templateResolver.setTemplateMode(TemplateMode.HTML); // Режим роботи з HTML
        templateResolver.setCharacterEncoding("UTF-8"); // Кодування
        templateResolver.setCacheable(false); // Вимкнення кешування для розробки

        // Ініціалізація TemplateEngine
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8"); // Встановлення типу контенту

        // Отримання часової зони з параметра або cookie
        String timezone = req.getParameter("timezone");
        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
            if (timezone == null || timezone.isEmpty()) {
                timezone = "UTC"; // Значення за замовчуванням
            }
        }

        try {
            // Встановлення часової зони
            ZoneId zoneId = ZoneId.of(timezone);
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);

            // Форматування часу з автоматичним визначенням часової зони
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String formattedTime = zonedDateTime.format(formatter);

            // Створення cookie для останньої часової зони
            Cookie timezoneCookie = new Cookie("lastTimezone", timezone);
            timezoneCookie.setMaxAge(86400); // Термін життя 24 години
            resp.addCookie(timezoneCookie);

            // Підготовка Thymeleaf контексту
            WebContext context = new WebContext(req, resp, getServletContext());
            context.setVariable("currentTime", formattedTime);

            // Відображення сторінки за допомогою Thymeleaf
            templateEngine.process("time", context, resp.getWriter());

        } catch (Exception e) {
            // Обробка некоректних часових зон
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid timezone: " + timezone);
        }
    }
}
