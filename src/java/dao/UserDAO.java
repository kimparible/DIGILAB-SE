package dao;

import com.google.gson.Gson;
import db.JDBC;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void addUser(User user) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user: " + e.getMessage(), e);
        }
    }

    public User getUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during login: " + e.getMessage(), e);
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE LOWER(role) = 'member'";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all users: " + e.getMessage(), e);
        }
        return users;
    }

    public boolean deleteUser(int userId) {
        try (Connection connection = JDBC.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkUserStmt = connection.prepareStatement("SELECT role FROM users WHERE id = ?");
                 PreparedStatement checkActiveLoansStmt = connection.prepareStatement("SELECT COUNT(*) FROM loans WHERE member_id = ? AND status != 'Returned'");
                 PreparedStatement deleteLoansStmt = connection.prepareStatement("DELETE FROM loans WHERE member_id = ?");
                 PreparedStatement deleteUserStmt = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {

                checkUserStmt.setInt(1, userId);
                try (ResultSet rs = checkUserStmt.executeQuery()) {
                    if (!rs.next()) throw new RuntimeException("User tidak ditemukan");

                    String role = rs.getString("role");
                    if (!"member".equalsIgnoreCase(role)) {
                        throw new RuntimeException("Hanya member yang dapat dihapus");
                    }
                }

                checkActiveLoansStmt.setInt(1, userId);
                try (ResultSet rs = checkActiveLoansStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new RuntimeException("Member masih memiliki peminjaman aktif.");
                    }
                }

                deleteLoansStmt.setInt(1, userId);
                deleteLoansStmt.executeUpdate();

                deleteUserStmt.setInt(1, userId);
                int affected = deleteUserStmt.executeUpdate();

                connection.commit();
                return affected > 0;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user by ID: " + e.getMessage(), e);
        }
        return null;
    }

    // ✅ Perbaikan utama: Include semua kolom yang relevan ke model User
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));

        // Tambahan kolom profil:
        user.setFullName(rs.getString("full_name"));
        user.setBio(rs.getString("bio"));
        user.setDescription(rs.getString("description"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAddress(rs.getString("address"));

        return user;
    }
}
