package servlets;

import com.google.gson.Gson;
import dao.BookDAO;
import models.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/books/*")
public class BookApiController extends HttpServlet {
    private BookDAO bookDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo(); // / or /{id}
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (pathInfo == null || pathInfo.equals("/")) {
            // GET /api/books -> semua buku
            List<Book> books = bookDAO.getAllBooks();
            String json = gson.toJson(books);
            out.print(json);
        } else {
            // GET /api/books/{id}
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Book book = bookDAO.getBookById(id);
                if (book != null) {
                    String json = gson.toJson(book);
                    out.print(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Book not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid book ID\"}");
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Book book = gson.fromJson(request.getReader(), Book.class);
        bookDAO.addBook(book);
        response.setContentType("application/json");
        response.getWriter().print("{\"message\":\"Book added successfully\"}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo(); // /{id}
        response.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Book ID is required\"}");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            Book book = gson.fromJson(request.getReader(), Book.class);
            book.setId(id);
            bookDAO.updateBook(book);
            response.getWriter().print("{\"message\":\"Book updated successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Invalid book ID\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo(); // /{id}
        response.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Book ID is required\"}");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            bookDAO.deleteBook(id);
            response.getWriter().print("{\"message\":\"Book deleted successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Invalid book ID\"}");
        }
    }
}
