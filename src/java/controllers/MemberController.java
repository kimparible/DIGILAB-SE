package controllers;

import dao.BookDAO;
import service.BookService;
import service.LoanService;
import models.Book;
import models.Loan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
    private BookDAO bookDAO;
    private LoanService loanService;
    private BookService bookService;
    

    @Override
    public void init() {
        bookDAO = new BookDAO();
        loanService = new LoanService();
        bookService = new BookService();
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("userId") == null || 
            !"member".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            action = "/dashboard";
        }

        switch (action) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null || 
            !"member".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if ("markNotificationRead".equals(action)) {
            try {
                int notificationId = Integer.parseInt(request.getParameter("notificationId"));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Log untuk debugging
        System.out.println("MemberController.showDashboard(): Session - " + session);
        if (session != null) {
            System.out.println("MemberController.showDashboard(): User ID - " + 
                             session.getAttribute("userId"));
            System.out.println("MemberController.showDashboard(): User Role - " + 
                             session.getAttribute("userRole"));
            System.out.println("MemberController.showDashboard(): User Name - " + 
                             session.getAttribute("userName"));
        }

        Integer memberId = (Integer) session.getAttribute("userId");
        try {
            // Ambil data peminjaman
            List<Loan> loans = loanService.getLoansByMemberId(memberId);
            
            // Periksa apakah list loans kosong dan ambil data buku hanya jika kosong
            if (loans.isEmpty()) {
                List<Book> books = bookService.getAllBooks();
                request.setAttribute("books", books);
            }
            request.setAttribute("loans", loans);

            // Path JSP relatif terhadap webapp root
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member_dashboard.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving member data", e);
        }
    }
}