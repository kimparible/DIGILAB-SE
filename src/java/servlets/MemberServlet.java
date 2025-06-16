package servlets;

import dao.MemberDAO;
import java.io.BufferedReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/users/members/*")
public class MemberServlet extends HttpServlet {

    private MemberDAO memberDAO;

    @Override
    public void init() {
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // e.g. "/5"
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\":false,\"message\":\"ID anggota diperlukan dalam URL.\"}");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1)); // hapus '/' di depan
            boolean deleted = memberDAO.deleteMember(id);
            
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"success\":true,\"message\":\"Anggota berhasil dihapus.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"success\":false,\"message\":\"Anggota tidak ditemukan.\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\":false,\"message\":\"ID tidak valid.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\":false,\"message\":\"Terjadi kesalahan pada server.\"}");
        }

        out.flush();
    }
    @Override
protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    String pathInfo = request.getPathInfo(); // e.g. /13

    if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        out.print("{\"success\":false,\"message\":\"ID anggota diperlukan.\"}");
        return;
    }

    try {
        int userId = Integer.parseInt(pathInfo.substring(1));

        // Baca JSON body
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String body = sb.toString();
        System.out.println("[Servlet] JSON body: " + body);

        // Parse JSON pakai Gson
        com.google.gson.JsonObject json = new com.google.gson.JsonParser().parse(body).getAsJsonObject();

        String fullName = json.has("fullName") ? json.get("fullName").getAsString() : null;
        String bio = json.has("bio") ? json.get("bio").getAsString() : null;
        String description = json.has("description") ? json.get("description").getAsString() : null;
        String phoneNumber = json.has("phoneNumber") ? json.get("phoneNumber").getAsString() : null;
        String address = json.has("address") ? json.get("address").getAsString() : null;

        boolean updated = memberDAO.updateMemberProfile(
            userId, fullName, bio, description, phoneNumber, address
        );

        if (updated) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"success\":true,\"message\":\"Profil berhasil diperbarui.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"success\":false,\"message\":\"Anggota tidak ditemukan.\"}");
        }

    } catch (Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.print("{\"success\":false,\"message\":\"Terjadi kesalahan saat memperbarui.\"}");
    } finally {
        out.flush();
    }
}


}
