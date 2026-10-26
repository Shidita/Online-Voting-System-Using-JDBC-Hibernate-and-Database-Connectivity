package com.aip.servlet;

import com.aip.dao.VoteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/** Admin panel: add / delete candidates (role check is done in AuthFilter). */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final VoteDAO dao = new VoteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("results", dao.results());
        req.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if ("delete".equals(req.getParameter("action"))) {
            try { dao.deleteCandidate(Long.parseLong(req.getParameter("id"))); } catch (NumberFormatException ignored) { }
        } else {
            String name = req.getParameter("name") == null ? "" : req.getParameter("name").trim();
            String party = req.getParameter("party") == null ? "" : req.getParameter("party").trim();
            if (name.isEmpty() || name.length() > 100 || party.length() > 100) {
                req.setAttribute("error", "Candidate name is required (max 100 characters).");
                doGet(req, resp);
                return;
            }
            dao.addCandidate(name, party);
        }
        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}
