package com.aip.servlet;

import com.aip.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO users = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") == null ? "" : req.getParameter("username").trim();
        String password = req.getParameter("password") == null ? "" : req.getParameter("password");
        String error = null;
        if (!username.matches("[A-Za-z0-9_]{3,20}")) error = "Username must be 3-20 letters, numbers or _";
        else if (password.length() < 6) error = "Password must be at least 6 characters";
        else {
            try {
                // Public registration always creates a VOTER; admins cannot be self-created.
                if (!users.register(username, password, "VOTER")) error = "Username already taken";
            } catch (Exception e) {
                error = "Server error: " + e.getMessage();
            }
        }
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login?registered=1");
        }
    }
}
