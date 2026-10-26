package com.aip.servlet;

import com.aip.dao.VoteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/** Ballot page: GET shows candidates (or "already voted"), POST casts the vote. */
@WebServlet("/vote")
public class VoteServlet extends HttpServlet {
    private final VoteDAO dao = new VoteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = (String) req.getSession().getAttribute("user");
        req.setAttribute("voted", dao.hasVoted(user));
        req.setAttribute("candidates", dao.candidates());
        req.getRequestDispatcher("/WEB-INF/views/vote.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = (String) req.getSession().getAttribute("user");
        boolean ok = false;
        try {
            ok = dao.castVote(user, Long.parseLong(req.getParameter("candidateId")));
        } catch (NumberFormatException ignored) { }
        if (ok) resp.sendRedirect(req.getContextPath() + "/results?voted=1");
        else {
            req.setAttribute("error", "Vote not recorded. You may have already voted, or you didn't pick a candidate.");
            doGet(req, resp);
        }
    }
}
