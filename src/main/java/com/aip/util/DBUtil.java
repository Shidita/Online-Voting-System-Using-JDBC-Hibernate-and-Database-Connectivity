package com.aip.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Plain JDBC connection helper (used for the USERS table).
 * The H2 database file is stored in your user home folder (~/votingdb), so it always has write permission.
 */
public class DBUtil {
    public static final String URL = "jdbc:h2:file:~/votingdb";
    public static final String USER = "sa";
    public static final String PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            try (Connection c = getConnection(); Statement s = c.createStatement()) {
                s.execute("CREATE TABLE IF NOT EXISTS users ("
                        + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                        + "username VARCHAR(30) UNIQUE NOT NULL,"
                        + "salt VARCHAR(64) NOT NULL,"
                        + "password_hash VARCHAR(128) NOT NULL,"
                        + "role VARCHAR(10) DEFAULT 'VOTER' NOT NULL)");
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /** Called when the app stops so the database file is released (needed for redeploy). */
    public static void shutdown() {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            s.execute("SHUTDOWN");
        } catch (Exception ignored) { }
    }
}
