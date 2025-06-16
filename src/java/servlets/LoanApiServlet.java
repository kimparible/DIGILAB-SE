package servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Book;
import service.BookService;
import service.LoanService;

@WebServlet("/api/loan/*")
public class LoanApiServlet extends HttpServlet {
    private LoanService loanService;
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        loanService = new LoanService();
        bookService = new BookService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if ("/borrow".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                int memberId = Integer.parseInt(request.getParameter("memberId"));

                if (!validateLoan(bookId, memberId)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"status\":\"error\",\"message\":\"Buku tidak tersedia atau sudah dipinjam.\"}");
                    return;
                }

                loanService.borrowBook(bookId, memberId);
                response.getWriter().write("{\"status\":\"success\",\"message\":\"Buku berhasil dipinjam.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Endpoint tidak ditemukan\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Terjadi kesalahan saat memproses.\"}");
        }
    }

    private boolean validateLoan(int bookId, int memberId) throws Exception {
        Book book = bookService.getBookById(bookId);
        if (book == null || book.getQuantity() <= 0) return false;
        return !loanService.hasActiveLoan(memberId, bookId);
    }
}
