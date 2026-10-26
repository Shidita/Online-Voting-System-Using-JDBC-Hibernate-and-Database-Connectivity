package com.aip.dao;

import com.aip.util.DBUtil;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

/** JDBC data access for users: registration and login with PBKDF2 hashed passwords and roles. */
public class UserDAO {

    private static String hash(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        byte[] h = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(h);
    }

    /** @return true if created, false if username already exists */
    public boolean register(String username, String password, String role) throws Exception {
        try (Connection c = DBUtil.getConnection()) {
            try (PreparedStatement check = c.prepareStatement("SELECT 1 FROM users WHERE username = ?")) {
                check.setString(1, username);
                if (check.executeQuery().next()) return false;
            }
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO users (username, salt, password_hash, role) VALUES (?, ?, ?, ?)")) {
                ps.setString(1, username);
                ps.setString(2, Base64.getEncoder().encodeToString(salt));
                ps.setString(3, hash(password, salt));
                ps.setString(4, role);
                ps.executeUpdate();
            }
            return true;
        }
    }

    /** @return the user's role (VOTER / ADMIN) on success, or null if credentials are wrong */
    public String login(String username, String password) throws Exception {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT salt, password_hash, role FROM users WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            byte[] salt = Base64.getDecoder().decode(rs.getString("salt"));
            return hash(password, salt).equals(rs.getString("password_hash")) ? rs.getString("role") : null;
        }
    }
}
