Servlet exercise example + Servlet with custom session implementation example.

URL for test:
http://localhost:8080/servlet_war/evening?name=Andrii
http://localhost:8080/servlet_war/eveningnativesession?name=Oleksii

Tomcat configuration:
 - deploy URL: http://localhost:8080/servlet_war/
 - port: 8080

Task details:
 - Implement an EveningServlet that accepts your name as a query param and responds with a greeting "Good evening, {yourName}" use "Buddy" if no name was provided
 - Make sure it works in browser
 - Add a session, and store a name, so the server remembers who you are
