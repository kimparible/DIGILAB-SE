package servlets;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }
    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/plain");
    response.getWriter().write("Register endpoint ready to receive POST requests.");
}


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        PrintWriter out = response.getWriter();

        if (name == null || name.isEmpty() ||
            email == null || email.isEmpty() ||
            password == null || password.isEmpty() ||
            role == null || role.isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\":\"error\",\"message\":\"Please fill in all fields.\"}");
            return;
        }

        try {
            userService.registerUser(name, email, password, role);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"status\":\"success\",\"message\":\"Account created successfully!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
