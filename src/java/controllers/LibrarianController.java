package controllers;

import dao.BookDAO;
import dao.LoanDAO;
import service.LoanService;
import models.Loan;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Book;

@WebServlet("/librarian/*")
public class LibrarianController extends HttpServlet {
    private BookDAO bookDAO;
    private LoanService loanService;
    
    @Override
    public void init() {
        bookDAO = new BookDAO();
        loanService = new LoanService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/dashboard";
        }
        
        try {
            switch (action) {
                case "/dashboard":
                    showDashboard(request, response);
                    break;
                case "/books":
                    listBooks(request, response);
                    break;
                case "/loans":
                    listLoans(request, response);
                    break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/librarian_dashboard.jsp");
        dispatcher.forward(request, response);
    }
    
    private void listBooks(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/librarian/books.jsp");
        dispatcher.forward(request, response);
    }
    
    private void listLoans(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        List<Loan> loans = loanService.getAllLoans();
        request.setAttribute("loans", loans);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/list_loan_librarian.jsp");
        dispatcher.forward(request, response);
    }
    
    
}