package service;

import dao.BookDAO;
import dao.LoanDAO;
import models.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDAO bookDAO;
    private final LoanDAO loanDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
        this.loanDAO = new LoanDAO();
    }

    // ✅ Ambil buku dan hitung peminjaman aktif
    public Book getBookById(int bookId) throws SQLException {
        Book book = bookDAO.getBookById(bookId);
        if (book != null) {
            int activeLoans = loanDAO.countActiveLoansForBook(bookId);
            book.setActiveLoanCount(activeLoans); // otomatis memengaruhi isAvailable()
        }
        return book;
    }

    // ✅ Kembalikan semua buku dan isi jumlah pinjaman aktif
    public List<Book> getAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            try {
                int activeLoans = loanDAO.countActiveLoansForBook(book.getId());
                book.setActiveLoanCount(activeLoans);
            } catch (SQLException e) {
                e.printStackTrace(); // Log error tapi lanjut
                book.setActiveLoanCount(0); // fallback aman
            }
        }
        return books;
    }

    // ❌ Tidak lagi update quantity dan available secara manual
    // Jika tetap dibutuhkan untuk admin edit jumlah stok, buat method baru khusus untuk itu
}
