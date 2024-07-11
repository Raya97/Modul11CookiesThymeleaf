#EN
##Thymeleaf Time Zone Servlet Project
verview
This Java servlet project leverages Thymeleaf for rendering HTML and managing time zones dynamically. It is designed to demonstrate handling HTTP requests, managing cookies, and dynamically generating responses based on user input or stored preferences. The primary functionality revolves around displaying the current time in a specified or remembered time zone.

##Features
Dynamic Time Zone Conversion: Converts and displays the current time according to the user-specified or previously saved time zone.
Cookie Management: Remembers the last selected time zone through cookies, enhancing user convenience by preserving settings across sessions.
Thymeleaf Integration: Utilizes Thymeleaf as a Java XML/XHTML/HTML5 template engine to render server-side generated HTML.
Custom Time Formatting: Displays time in a customized format, specifically showing the timezone offset like "UTC+2".

##How It Works
Time Zone Retrieval: The servlet checks for a 'timezone' parameter in the HTTP request. If not found, it looks for this parameter in cookies.
Time Calculation: If a time zone is specified or retrieved from cookies, the servlet calculates the current time in that time zone. If none is available, it defaults to "UTC+02:00".
Response Rendering: Uses Thymeleaf templates to generate the HTML response, embedding the calculated time.
Cookie Handling: If a new time zone is specified, the servlet updates the 'lastTimezone' cookie with this new value.

#UA
##Проект Thymeleaf Time Zone
Огляд
Цей проект - Java сервлет, який використовує Thymeleaf для рендерингу HTML і управління часовими поясами. Він призначений для демонстрації роботи з HTTP-запитами, управління cookies та динамічної генерації відповідей на основі вхідних даних користувача або збережених налаштувань. Основна функціональність полягає у відображенні поточного часу у вказаному або запам'ятованому часовому поясі.

##Особливості
Динамічне перетворення часових поясів: Конвертація та відображення поточного часу згідно з користувацьким або раніше збереженим часовим поясом.
Управління Cookies: Запам'ятовування останнього вибраного часового поясу через cookies, що покращує зручність користувача, зберігаючи налаштування між сесіями.
Інтеграція Thymeleaf: Використання Thymeleaf як двигуна шаблонів Java XML/XHTML/HTML5 для рендерингу HTML на стороні сервера.
Кастомізація форматування часу: Відображення часу у кастомізованому форматі, зокрема показ зміщення часового поясу як "UTC+2".

##Як це працює
Отримання часового поясу: Сервлет перевіряє наявність параметра 'timezone' у HTTP-запиті. Якщо його немає, він шукає цей параметр у cookies.
Обчислення часу: Якщо часовий пояс вказаний або отриманий з cookies, сервлет обчислює поточний час у цьому часовому поясі. Якщо часовий пояс недоступний, він використовується за замовчуванням "UTC+02:00".
Генерація відповіді: Використання шаблонів Thymeleaf для генерації HTML відповіді.
Управління cookies: Якщо новий часовий пояс вказано, сервлет оновлює cookie 'lastTimezone' з цим новим значенням.
