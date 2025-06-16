package controllers;

import dao.BookDAO;
import models.Book;
import service.LoanService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/catalog")
public class CatalogController extends HttpServlet {

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

        String action = request.getParameter("action");
        if (action == null) action = "list";

        System.out.println("Action: " + action);

        try {
            switch (action) {
                case "list":
                    listBooks(request, response);
                    break;
                case "search":
                    searchBooks(request, response);
                    break;
                case "borrow":
                    borrowBook(request, response);
                    break;
                default:
                    listBooks(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error handling catalog action: " + action, e);
        }
    }

    // 📚 Tampilkan semua buku
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/catalog.jsp");
        dispatcher.forward(request, response);
    }

    // 🔍 Fitur pencarian buku
    private void searchBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Book> books = bookDAO.searchBooks(keyword);
        request.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/catalog.jsp");
        dispatcher.forward(request, response);
    }

    // 📖 Fitur peminjaman buku
    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer memberId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (memberId == null) {
            request.setAttribute("message", "Silakan login terlebih dahulu untuk meminjam buku.");
            listBooks(request, response);
            return;
        }

        try {
            int bookId = Integer.parseInt(request.getParameter("id"));
            Book book = bookDAO.getBookById(bookId);

            if (book.isAvailable()) {
                loanService.borrowBook(bookId, memberId);
                request.setAttribute("message", "Buku berhasil dipinjam.");
            } else {
                request.setAttribute("message", "Buku tidak tersedia.");
            }

        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
            request.setAttribute("message", "Terjadi kesalahan saat memproses peminjaman.");
        }

        listBooks(request, response); // refresh daftar buku
    }
}
