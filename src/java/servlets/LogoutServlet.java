package servlets; // Ganti dengan package Anda

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/views/logout") // URL yang akan digunakan untuk logout
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mengambil sesi yang ada
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Menghapus atribut sesi jika ada
            session.invalidate(); // Menghapus sesi
        }
        // Mengarahkan pengguna ke halaman logout.jsp
        response.sendRedirect("logout.jsp"); // Ganti dengan URL halaman logout Anda
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Biasanya logout dilakukan dengan GET, tetapi jika Anda ingin mendukung POST, panggil doGet
        doGet(request, response);
    }
}