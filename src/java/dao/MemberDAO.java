package dao;

    import db.JDBC;
    import models.User;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class MemberDAO {

        public List<User> getAllMembers() {
    List<User> members = new ArrayList<>();
    String sql = "SELECT * FROM users WHERE role = 'Member'";

    try (Connection conn = JDBC.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            User member = new User();
            member.setId(rs.getInt("id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setRole(rs.getString("role"));

            // Field tambahan
            member.setFullName(rs.getString("full_name"));
            member.setBio(rs.getString("bio"));
            member.setDescription(rs.getString("description"));
            member.setPhoneNumber(rs.getString("phone_number"));
            member.setAddress(rs.getString("address"));

            members.add(member);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error fetching all members", e);
    }

    return members;
}

        public boolean deleteMember(int memberId) {
        String sql = "DELETE FROM members WHERE id = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        public User getMemberById(int memberId) throws SQLException {
    String sql = "SELECT * FROM users WHERE id = ? AND role = 'Member'";
    try (Connection conn = JDBC.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, memberId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                User member = new User();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                member.setRole(rs.getString("role"));

                // Tambahan
                member.setFullName(rs.getString("full_name"));
                member.setBio(rs.getString("bio"));
                member.setDescription(rs.getString("description"));
                member.setPhoneNumber(rs.getString("phone_number"));
                member.setAddress(rs.getString("address"));

                return member;
            }
        }
    }
    return null;
}
public boolean updateMemberProfile(int userId, String fullName, String bio, String description, String phone, String address) {
    String sql = "UPDATE users SET full_name = ?, bio = ?, description = ?, phone_number = ?, address = ? WHERE id = ?";

    try (Connection conn = JDBC.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        System.out.println("=== [DAO] MENJALANKAN UPDATE ===");
        System.out.println("userId       : " + userId);
        System.out.println("full_name    : " + (fullName != null ? fullName : "[NULL]"));
        System.out.println("bio          : " + (bio != null ? bio : "[NULL]"));
        System.out.println("description  : " + (description != null ? description : "[NULL]"));
        System.out.println("phone_number : " + (phone != null ? phone : "[NULL]"));
        System.out.println("address      : " + (address != null ? address : "[NULL]"));
        System.out.println("===============================");

        // Tangani kemungkinan null dengan aman
        if (fullName != null) {
            stmt.setString(1, fullName);
        } else {
            stmt.setNull(1, java.sql.Types.VARCHAR);
        }

        if (bio != null) {
            stmt.setString(2, bio);
        } else {
            stmt.setNull(2, java.sql.Types.VARCHAR);
        }

        if (description != null) {
            stmt.setString(3, description);
        } else {
            stmt.setNull(3, java.sql.Types.VARCHAR);
        }

        if (phone != null) {
            stmt.setString(4, phone);
        } else {
            stmt.setNull(4, java.sql.Types.VARCHAR);
        }

        if (address != null) {
            stmt.setString(5, address);
        } else {
            stmt.setNull(5, java.sql.Types.VARCHAR);
        }

        stmt.setInt(6, userId);

        int rowsAffected = stmt.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);

        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("SQL Exception: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    }