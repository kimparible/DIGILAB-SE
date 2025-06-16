package service;

import dao.LoanDAO;
import models.Book;
import models.Loan;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoanService {
    private final LoanDAO loanDAO;
    private final BookService bookService;

    public LoanService() {
        this.loanDAO = new LoanDAO();
        this.bookService = new BookService();
    }

    public void borrowBook(int bookId, int memberId) throws SQLException {
        Book book = bookService.getBookById(bookId);

        // Hitung jumlah peminjaman aktif
        int activeLoans = loanDAO.countActiveLoansForBook(bookId);

        // Cek apakah stok mencukupi
        if (activeLoans >= book.getQuantity()) {
            throw new RuntimeException("Stok habis. Semua eksemplar buku sedang dipinjam.");
        }

        // Buat entri peminjaman
        Loan loan = new Loan();
        loan.setBookId(bookId);
        loan.setMemberId(memberId);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setStatus("Borrowed");

        // Simpan ke database
        loanDAO.addLoan(loan);
    }

    public void returnBook(int loanId) throws SQLException {
        Loan loan = loanDAO.getLoanById(loanId);
        if (loan == null) {
            throw new RuntimeException("Loan not found.");
        }

        if ("Returned".equalsIgnoreCase(loan.getStatus())) {
            throw new RuntimeException("Loan has already been returned.");
        }

        // Tandai sebagai dikembalikan
        loan.setStatus("Returned");
        loan.setReturnDate(LocalDate.now());
        loanDAO.updateLoan(loan);
    }

    public List<Loan> getAllLoans() throws SQLException {
        return loanDAO.getAllLoans();
    }

    public Loan getLoanById(int loanId) throws SQLException {
        return loanDAO.getLoanById(loanId);
    }

    public boolean hasActiveLoan(int userId, int bookId) throws SQLException {
        return loanDAO.hasActiveLoan(userId, bookId);
    }

    public List<Loan> getLoansByMemberId(Integer memberId) throws SQLException {
        return loanDAO.getLoansByMemberId(memberId);
    }

    // ✅ Method tambahan untuk mendukung logika stok
    public int countActiveLoansForBook(int bookId) throws SQLException {
        return loanDAO.countActiveLoansForBook(bookId);
    }
}
