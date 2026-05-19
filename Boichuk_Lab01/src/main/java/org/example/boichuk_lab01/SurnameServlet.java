package org.example.boichuk_lab01;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "surnameServlet", value = "/surname")
public class SurnameServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Бойчук";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
        out.close();
    }

    public void destroy() {
    }
}