package com.aip.servlet;

import com.aip.dao.UserDAO;
import com.aip.dao.VoteDAO;
import com.aip.util.DBUtil;
import com.aip.util.HibernateUtil;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/** Runs at startup: creates the default admin account and sample candidates. Releases the DB on shutdown. */
@WebListener
public class AppInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            new UserDAO().register("admin", "admin123", "ADMIN"); // ignored if it already exists
            VoteDAO dao = new VoteDAO();
            if (dao.candidates().isEmpty()) {
                dao.addCandidate("Asha Verma", "Progress Party");
                dao.addCandidate("Rahul Mehta", "Unity Alliance");
                dao.addCandidate("Neha Kapoor", "Independent");
            }
            System.out.println("[Voting] Database ready. Admin login: admin / admin123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
        DBUtil.shutdown();
    }
}
