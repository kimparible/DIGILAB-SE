package servlets;

import dao.UserDAO;
import models.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();


    
    private boolean isAdminUser(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    // Check session first
    if (session != null) {
        String userRole = (String) session.getAttribute("userRole");
        if ("admin".equalsIgnoreCase(userRole)) return true;
    }

    // If no valid session, check custom headers (from Flutter)
    String roleHeader = request.getHeader("X-User-Role");
    if ("admin".equalsIgnoreCase(roleHeader)) {
        return true;
    }

    return false;
}



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdminUser(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getPathInfo();
        if (action == null || action.isEmpty()) {
            action = "/dashboard";
        }

        switch (action) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            case "/users":
                showUsers(request, response);
                break;
            case "/users/delete":
                deleteUser(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        request.setAttribute("adminName", session.getAttribute("userName"));
        request.getRequestDispatcher("/views/admin/admin_dashboard.jsp").forward(request, response);
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        List<User> users = userDAO.getAllUsers();

        String format = request.getParameter("format");
        if ("json".equalsIgnoreCase(format)) {
            // Kirim response JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Convert List<User> ke JSON (gunakan library Gson)
            com.google.gson.Gson gson = new com.google.gson.Gson();
            String json = gson.toJson(users);

            response.getWriter().write(json);
            return;
        }

        // Jika bukan JSON, tampilkan HTML (JSP)
        request.setAttribute("users", users);
        String errorMessage = request.getParameter("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
            request.setAttribute("errorMessage", errorMessage);
        }
        request.getRequestDispatcher("/views/admin/users.jsp").forward(request, response);

    } catch (RuntimeException e) {
        e.printStackTrace();
        if ("json".equalsIgnoreCase(request.getParameter("format"))) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Gagal mengambil data pengguna\"}");
        } else {
            request.setAttribute("errorMessage", "Error fetching users: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}


    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
    String contextPath = request.getContextPath();
    String redirectBase = contextPath + "/admin/users";
    String format = request.getParameter("format");

    try {
        int userId = Integer.parseInt(request.getParameter("id"));

        // Cek jika user yang ingin dihapus adalah member
        User user = userDAO.getUserById(userId);
        if (user != null && !"member".equalsIgnoreCase(user.getRole())) {
            if ("json".equalsIgnoreCase(format)) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Hanya member yang bisa dihapus\"}");
            } else {
                String errorMessage = URLEncoder.encode("Hanya member yang bisa dihapus", "UTF-8");
                response.sendRedirect(redirectBase + "?errorMessage=" + errorMessage);
            }
            return;
        }

        boolean deleted = userDAO.deleteUser(userId);

        if ("json".equalsIgnoreCase(format)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (deleted) {
                response.getWriter().write("{\"status\":\"success\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Gagal menghapus user\"}");
            }
        } else {
            response.sendRedirect(redirectBase);
        }

    } catch (NumberFormatException e) {
        if ("json".equalsIgnoreCase(format)) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"ID tidak valid\"}");
        } else {
            String errorMessage = URLEncoder.encode("ID pengguna tidak valid", "UTF-8");
            response.sendRedirect(redirectBase + "?errorMessage=" + errorMessage);
        }
    } catch (Exception e) {
        e.printStackTrace();
        if ("json".equalsIgnoreCase(format)) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Terjadi kesalahan\"}");
        } else {
            String errorMessage = URLEncoder.encode("Terjadi kesalahan saat menghapus pengguna", "UTF-8");
            response.sendRedirect(redirectBase + "?errorMessage=" + errorMessage);
        }
    }
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
