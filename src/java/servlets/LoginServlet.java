package servlets;

import models.User;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            User user = userService.authenticateUser(email, password);
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userRole", user.getRole());
                session.setMaxInactiveInterval(30 * 60); // 30 menit

                // Kirim respons JSON lengkap ke frontend
                response.setStatus(HttpServletResponse.SC_OK);
                String json = "{"
                        + "\"status\":\"success\","
                        + "\"id\":" + user.getId() + ","
                        + "\"name\":\"" + escapeJson(user.getName()) + "\","
                        + "\"email\":\"" + escapeJson(user.getEmail()) + "\","
                        + "\"role\":\"" + escapeJson(user.getRole()) + "\","
                        + "\"fullName\":\"" + escapeJson(user.getFullName()) + "\","
                        + "\"bio\":\"" + escapeJson(user.getBio()) + "\","
                        + "\"description\":\"" + escapeJson(user.getDescription()) + "\","
                        + "\"phoneNumber\":\"" + escapeJson(user.getPhoneNumber()) + "\","
                        + "\"address\":\"" + escapeJson(user.getAddress()) + "\""
                        + "}";

                response.getWriter().write(json);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Email atau password salah.\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private String escapeJson(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }
}
