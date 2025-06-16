package dao;

import db.JDBC;
import models.Loan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public void addLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO loans (book_id, member_id, loan_date, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loan.getBookId());
            stmt.setInt(2, loan.getMemberId());
            stmt.setDate(3, Date.valueOf(loan.getLoanDate()));
            stmt.setDate(4, Date.valueOf(loan.getDueDate()));
            stmt.setString(5, loan.getStatus());
            stmt.executeUpdate();
        }
    }

    public void updateLoan(Loan loan) throws SQLException {
        String sql = "UPDATE loans SET status = ?, return_date = ? WHERE id = ?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loan.getStatus());
            stmt.setDate(2, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);
            stmt.setInt(3, loan.getId());
            stmt.executeUpdate();
        }
    }

    public Loan getLoanById(int loanId) throws SQLException {
        String sql = "SELECT * FROM loans WHERE id = ?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loanId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createLoanFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Loan> getAllLoans() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                loans.add(createLoanFromResultSet(rs));
            }
        }
        return loans;
    }

    private Loan createLoanFromResultSet(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setId(rs.getInt("id"));
        loan.setBookId(rs.getInt("book_id"));
        loan.setMemberId(rs.getInt("member_id"));
        loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
        loan.setDueDate(rs.getDate("due_date").toLocalDate());
        if (rs.getDate("return_date") != null) {
            loan.setReturnDate(rs.getDate("return_date").toLocalDate());
        }
        loan.setStatus(rs.getString("status"));
        return loan;
    }

    // Tambahkan method ini
    public boolean hasActiveLoan(int userId, int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM loans WHERE member_id = ? AND book_id = ? AND status = 'Borrowed'";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false; // Default to false if no active loan is found
    }

    public boolean createLoanTransaction(Loan loan, int bookId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtInsertLoan = null;
        PreparedStatement stmtUpdateBook = null;

        try {
            conn = JDBC.getConnection();
            conn.setAutoCommit(false); // Matikan auto-commit

            // Query untuk insert data peminjaman
            String sqlInsertLoan = "INSERT INTO loans (book_id, member_id, loan_date, due_date, status) VALUES (?, ?, ?, ?, ?)";
            stmtInsertLoan = conn.prepareStatement(sqlInsertLoan);
            stmtInsertLoan.setInt(1, loan.getBookId());
            stmtInsertLoan.setInt(2, loan.getMemberId());
            stmtInsertLoan.setDate(3, Date.valueOf(loan.getLoanDate()));
            stmtInsertLoan.setDate(4, Date.valueOf(loan.getDueDate()));
            stmtInsertLoan.setString(5, loan.getStatus());
            stmtInsertLoan.executeUpdate();

            // Query untuk update data buku
            String sqlUpdateBook = "UPDATE books SET quantity = quantity - 1, status = 'Borrowed' WHERE id = ? AND quantity > 0";
            stmtUpdateBook = conn.prepareStatement(sqlUpdateBook);
            stmtUpdateBook.setInt(1, bookId);
            int affectedRows = stmtUpdateBook.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Book not available for borrowing or stock is empty.");
            }

            conn.commit(); // Commit transaksi
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback(); // Rollback jika terjadi error
                } catch (SQLException excep) {
                    throw new RuntimeException(excep);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (stmtInsertLoan != null) {
                stmtInsertLoan.close();
            }
            if (stmtUpdateBook != null) {
                stmtUpdateBook.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true); // Kembalikan auto-commit ke true
                conn.close();
            }
        }
    }

    public List<Loan> getLoansByMemberId(Integer memberId) throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE member_id = ?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    loans.add(createLoanFromResultSet(rs));
                }
            }
        }
        return loans;
    }

    public void deleteLoansByBookId(int bookId, Connection conn) throws SQLException {
        String sql = "DELETE FROM loans WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Handle exception, log, or rethrow
            throw e; // Rethrow the exception to be handled by the calling method
        }
    }
    public int countActiveLoansForBook(int bookId) throws SQLException {
    String sql = "SELECT COUNT(*) FROM loans WHERE book_id = ? AND return_date IS NULL";
    try (Connection conn = JDBC.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, bookId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }
    return 0;
}

}
