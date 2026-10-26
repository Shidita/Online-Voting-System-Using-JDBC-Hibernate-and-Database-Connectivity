package com.aip.servlet;

import com.aip.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO users = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") == null ? "" : req.getParameter("username").trim();
        String password = req.getParameter("password") == null ? "" : req.getParameter("password");
        try {
            String role = users.login(username, password);
            if (role != null) {
                req.changeSessionId(); // prevent session fixation
                HttpSession session = req.getSession(true);
                session.setAttribute("user", username);
                session.setAttribute("role", role);
                session.setMaxInactiveInterval(30 * 60);
                resp.sendRedirect(req.getContextPath() + ("ADMIN".equals(role) ? "/admin" : "/vote"));
                return;
            }
            req.setAttribute("error", "Invalid username or password");
        } catch (Exception e) {
            req.setAttribute("error", "Server error: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
