package com.aip.servlet;

import com.aip.dao.VoteDAO;
import com.aip.model.ResultRow;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/** JSON endpoint polled by results.jsp every 3 seconds for live counts. */
@WebServlet("/results-data")
public class ResultsDataServlet extends HttpServlet {
    private final VoteDAO dao = new VoteDAO();

    private static String esc(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ResultRow> rows = dao.results();
        long total = rows.stream().mapToLong(ResultRow::getVotes).sum();
        StringBuilder sb = new StringBuilder("{\"total\":").append(total).append(",\"candidates\":[");
        for (int i = 0; i < rows.size(); i++) {
            ResultRow r = rows.get(i);
            if (i > 0) sb.append(',');
            sb.append("{\"name\":\"").append(esc(r.getName())).append("\",\"party\":\"").append(esc(r.getParty()))
              .append("\",\"votes\":").append(r.getVotes()).append('}');
        }
        sb.append("]}");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setHeader("Cache-Control", "no-store");
        resp.getWriter().write(sb.toString());
    }
}
