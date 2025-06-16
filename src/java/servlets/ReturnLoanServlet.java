package servlets;

import service.LoanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/loan/return")
public class ReturnLoanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            int loanId = Integer.parseInt(request.getParameter("loan_id"));

            LoanService loanService = new LoanService();
            loanService.returnBook(loanId); // Tidak butuh returnDate dari request

            response.getWriter().write("{\"success\":true}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false, \"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
