package com.aip.servlet;

import com.aip.dao.VoteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/results")
public class ResultsServlet extends HttpServlet {
    private final VoteDAO dao = new VoteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("results", dao.results()); // first paint; JS then refreshes via /results-data
        req.getRequestDispatcher("/WEB-INF/views/results.jsp").forward(req, resp);
    }
}
