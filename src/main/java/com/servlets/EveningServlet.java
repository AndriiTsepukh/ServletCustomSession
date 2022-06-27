package com.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/eveningnativesession")
public class EveningServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        var name = request.getParameter("name");

        if (name != null) request.getSession().setAttribute("name", name);

        if (name == null) name = (String) request.getSession().getAttribute("name");

        if (name == null) name = "Buddy";

        response.setStatus(200);
        try (var writer = response.getWriter()) {
            writer.printf("Good evening, %s", name);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
