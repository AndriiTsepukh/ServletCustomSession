package com.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/evening")
public class EveningServletCustomCookie extends HttpServlet {

    Map<String, Map<String, String>> session = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        var name = request.getParameter("name");

        Map<String, String> sessionMap = getSession(request, response);

        if (name != null) sessionMap.put("name", name);
        if (name == null) name = sessionMap.get("name");
        if (name == null) name = "Buddy";

        response.setStatus(200);
        try (var writer = response.getWriter()) {
            writer.printf("Good evening, %s", name);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getSession(HttpServletRequest request, HttpServletResponse response) {
        var cookie = request.getHeader("Cookie");

        Map<String, String> cookieMap = new HashMap<>();

        if (cookie != null) {
            cookieMap = Arrays.stream(cookie.split(";"))
                    .collect(Collectors.toMap(value -> value.split("=")[0].strip(), value -> value.split("=")[1].strip()));
        }

        Map<String, String> sessionMap = new HashMap<>();
        String sessionId;

        if (cookieMap.containsKey("JSESSIONID")) {
            sessionId = cookieMap.get("JSESSIONID");
            sessionMap = session.computeIfAbsent(sessionId, k -> new HashMap<>());
        } else {
            sessionId = UUID.randomUUID().toString();
            session.put(sessionId, sessionMap);
        }

        response.setHeader("Set-Cookie","JSESSIONID=" + sessionId + ";");
        return sessionMap;
    }
}