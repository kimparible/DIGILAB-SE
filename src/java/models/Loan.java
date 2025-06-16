package models;

import java.time.LocalDate;

public class Loan {
    private int id;
    private int bookId;
    private int memberId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // Tambahkan properti ini
    private String status; // Tambahkan properti ini

    // Constructors
    public Loan() {
    }

    public Loan(int id, int bookId, int memberId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBook() {
       return bookId;
    }

    public int getUserId() {
     return memberId; 
    }

    
}