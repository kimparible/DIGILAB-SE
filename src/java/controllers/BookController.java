package controllers;

import dao.BookDAO;
import models.Book;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/librarian/books/*")
public class BookController extends HttpServlet {

    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) action = "/list";

        try {
            switch (action) {
                case "/list":
                    listBooks(request, response);
                    break;
                case "/add":
                    showAddForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteBook(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new ServletException("Error in BookController (GET): " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) action = "/list";

        try {
            switch (action) {
                case "/add":
                    addBook(request, response);
                    break;
                case "/edit":
                    updateBook(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new ServletException("Error in BookController (POST): " + ex.getMessage(), ex);
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookDAO.getAllBooks();
        List<String> categories = bookDAO.getAllCategories();
        request.setAttribute("books", books);
        request.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/manage_books.jsp");
        dispatcher.forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categories", bookDAO.getAllCategories());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/book_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        try {
            if (!isValidId(idParam)) {
                throw new IllegalArgumentException("ID buku tidak valid");
            }

            int id = Integer.parseInt(idParam);
            Book existingBook = bookDAO.getBookById(id);

            request.setAttribute("book", existingBook);
            request.setAttribute("categories", bookDAO.getAllCategories());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/book_form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Book newBook = extractBookFromRequest(request);
            bookDAO.addBook(newBook);
            response.sendRedirect(request.getContextPath() + "/librarian/books/list");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Format angka salah pada form.");
            showAddForm(request, response);
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (!isValidId(idParam)) {
            request.setAttribute("errorMessage", "ID buku tidak valid.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Book updatedBook = extractBookFromRequest(request);
            updatedBook.setId(id);
            bookDAO.updateBook(updatedBook);
            response.sendRedirect(request.getContextPath() + "/librarian/books/list");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Format angka tidak valid.");
            showEditForm(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Gagal mengupdate data buku.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        try {
            if (!isValidId(idParam)) {
                throw new NumberFormatException("ID buku tidak valid.");
            }

            int id = Integer.parseInt(idParam);
            bookDAO.deleteBook(id);
            response.sendRedirect(request.getContextPath() + "/librarian/books/list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID buku harus berupa angka.");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Gagal menghapus buku: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private Book extractBookFromRequest(HttpServletRequest request) {
        Book book = new Book();
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setIsbn(request.getParameter("isbn"));
        book.setCategory(request.getParameter("category"));
        book.setCoverImage(request.getParameter("coverImage"));
        book.setDescription(request.getParameter("description"));

        try {
            String quantityParam = request.getParameter("quantity");
            book.setQuantity(quantityParam != null && !quantityParam.trim().isEmpty()
                    ? Integer.parseInt(quantityParam)
                    : 0);

            String yearParam = request.getParameter("publicationYear");
            book.setPublicationYear(yearParam != null && !yearParam.trim().isEmpty()
                    ? Integer.parseInt(yearParam)
                    : 0);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Format angka salah pada jumlah atau tahun publikasi.");
        }

        return book;
    }

    private boolean isValidId(String idParam) {
        if (idParam == null || idParam.trim().isEmpty()) return false;
        try {
            return Integer.parseInt(idParam) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
