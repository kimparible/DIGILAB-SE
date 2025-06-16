package dao;

import db.JDBC;
import models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDAO {

    private LoanDAO loanDAO = new LoanDAO(); // digunakan untuk menghitung activeLoanCount

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setQuantity(rs.getInt("quantity"));
        book.setCategory(rs.getString("category"));
        book.setPublicationYear(rs.getInt("publication_year"));
        book.setCoverImage(rs.getString("cover_image"));
        book.setDescription(rs.getString("description"));

        // Hitung jumlah pinjaman aktif untuk buku ini
        int activeLoanCount = loanDAO.countActiveLoansForBook(book.getId());
        book.setActiveLoanCount(activeLoanCount);

        return book;
    }

    private void validateBookId(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("ID buku tidak valid");
        }
    }

    public boolean isBookExist(int bookId) {
        validateBookId(bookId);
        String sql = "SELECT id FROM books WHERE id = ?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error memeriksa keberadaan buku", e);
        }
    }

    public Book getBookById(int bookId) {
        validateBookId(bookId);
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException("Buku tidak ditemukan");
                }
                return createBookFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error mengambil data buku", e);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                books.add(createBookFromResultSet(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all books", e);
        }
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ? OR LOWER(isbn) LIKE ? OR LOWER(category) LIKE ?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String search = "%" + keyword.toLowerCase() + "%";
            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, search);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(createBookFromResultSet(rs));
                }
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Error searching books", e);
        }
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, quantity, category, publication_year, cover_image, description, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getQuantity());
            stmt.setString(5, book.getCategory());
            stmt.setInt(6, book.getPublicationYear());
            stmt.setString(7, book.getCoverImage());
            stmt.setString(8, book.getDescription());
            stmt.setString(9, "available"); // default status, real-time isAvailable() is dynamic
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding book", e);
        }
    }

    public void updateBook(Book book) {
        validateBookId(book.getId());
        Connection conn = null;
        try {
            conn = JDBC.getConnection();
            conn.setAutoCommit(false);

            String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, quantity = ?, "
                    + "category = ?, publication_year = ?, cover_image = ?, description = ?, "
                    + "status = ? WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setInt(4, book.getQuantity());
                stmt.setString(5, book.getCategory());
                stmt.setInt(6, book.getPublicationYear());
                stmt.setString(7, book.getCoverImage());
                stmt.setString(8, book.getDescription());
                stmt.setString(9, "available"); // always assume available, real logic is in isAvailable()
                stmt.setInt(10, book.getId());

                if (stmt.executeUpdate() == 0) {
                    throw new RuntimeException("Buku tidak ditemukan");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Error rolling back transaction", ex);
                }
            }
            throw new RuntimeException("Error updating book", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // Log error
                }
            }
        }
    }

    public void deleteBook(int bookId) {
        validateBookId(bookId);
        Connection conn = null;
        try {
            conn = JDBC.getConnection();
            conn.setAutoCommit(false);

            LoanDAO loanDAO = new LoanDAO();
            loanDAO.deleteLoansByBookId(bookId, conn);

            String sql = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, bookId);
                if (stmt.executeUpdate() == 0) {
                    conn.rollback();
                    throw new RuntimeException("Buku tidak ditemukan");
                }
            }

            conn.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            rollbackQuietly(conn);
            throw new RuntimeException("Tidak dapat menghapus buku karena masih terdapat data peminjaman terkait.", e);
        } catch (SQLException e) {
            rollbackQuietly(conn);
            throw new RuntimeException("Error deleting book", e);
        } finally {
            closeQuietly(conn);
        }
    }

    public boolean updateBookQuantity(int bookId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        String sql = "UPDATE books SET quantity = ? WHERE id = ?";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book quantity", e);
        }
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM books";
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] categoryArray = rs.getString("category").split(",");
                for (String category : categoryArray) {
                    category = category.trim();
                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }
            Collections.sort(categories);
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching categories", e);
        }
    }

    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                // Log rollback error
            }
        }
    }

    private void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                // Log close error
            }
        }
    }
}
