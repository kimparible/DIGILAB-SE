<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Manage Books - Digital Library</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <style>
            .container {
                max-width: 90%;
                margin: 20px auto;
            }
            .header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }
            .action-buttons
            {
                display: flex;
                gap: 5px;
            }
            .search-box
            {
                width: 200px;
            } 
            .modal-content
            {
                margin: 5% auto;
                padding: 20px;
                width: 60%;
                max-width: 700px;
                border-radius: 5px;
                max-height: 90vh;
                overflow-y: auto;
            }
            .form-group label
            {
                font-weight: bold;
            }
            .form-group input, .form-group select, .form-group textarea {
                box-sizing: border-box;
            }
            .form-group textarea {
                height: 100px;
                resize: vertical;
            }
            .error-message {
                color: #dc3545;
                margin-top: 10px;
                padding: 10px;
                background-color: #f8d7da;
                border-radius: 4px;
                display: none;
            }
            body {
                padding-top: 20px;
            }
            .table th, .table td {
                text-align: center;
            }
            .search-add-container {
                display: flex;
                justify-content: flex-end;
                align-items: center;
                gap: 10px;
            } /* Container untuk search box dan tombol Add */
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="actions-container">
                    <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Back to Dashboard
                    </a>
                    <h2>Manage Books</h2>
                </div>
                <div class="search-add-container">
                    <input type="text" class="form-control search-box" placeholder="Search books..." id="searchInput">
                    <button class="btn btn-primary" onclick="showAddModal()">
                        <i class="fas fa-plus"></i> Add New Book
                    </button>
                </div>
            </div>

            <table class="table table-striped table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Category</th>
                        <th>ISBN</th>
                        <th>Publication Year</th>
                        <th>Status</th>
                        <th>Quantity</th>
                        <th style="width: 150px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${books}" var="book">
                        <tr data-book-id="${book.id}">
                            <td>${book.id}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.category}</td>
                            <td>${book.isbn}</td>
                            <td>${book.publicationYear}</td>
                            <td>${book.available ? 'Available' : 'Not Available'}</td>
                            <td>${book.quantity}</td>
                            <td class="action-buttons">
                                <button class="btn btn-warning btn-sm" onclick="showEditModal(${book.id})">
                                    <i class="fas fa-edit"></i> Edit
                                </button>
                                <button class="btn btn-danger btn-sm" onclick="deleteBook(${book.id})">
                                    <i class="fas fa-trash"></i> Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div id="addModal" class="modal">
            <div class="modal-content">
                <h3>Add New Book</h3>
                <div class="error-message" id="addErrorMessage"></div>
                <form id="addBookForm" action="${pageContext.request.contextPath}/librarian/books/add" method="POST">
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>
                    <div class="form-group">
                        <label for="author">Author</label>
                        <input type="text" class="form-control" id="author" name="author" required>
                    </div>
                    <div class="form-group">
                <label for="category">Category</label>
                <input type="text" class="form-control" id="category" name="category" required>
            </div>
                    <div class="form-group">
                        <label for="isbn">ISBN</label>
                        <input type="text" class="form-control" id="isbn" name="isbn" required>
                    </div>
                    <div class="form-group">
                        <label for="publicationYear">Publication Year</label>
                        <input type="number" class="form-control" id="publicationYear" name="publicationYear" min="1000" max="2024" required>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <input type="number" class="form-control" id="quantity" name="quantity" min="0" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" name="description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" id="status" name="status" required>
                            <option value="available">Available</option>
                            <option value="not available">Not Available</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                    <button type="button" class="btn btn-danger" onclick="hideModal('addModal')">Cancel</button>
                </form>
            </div>
        </div>

        <div id="editModal" class="modal">
            <div class="modal-content">
                <h3>Edit Book</h3>
                <div class="error-message" id="editErrorMessage"></div>
                <form id="editBookForm" action="${pageContext.request.contextPath}/librarian/books/edit" method="POST">
                    <div class="form-group">
                        <label for="editID">ID</label>
                        <input type="text" class="form-control" id="editId" name="id" value="">
                    </div>
                    <div class="form-group">
                        <label for="editTitle">Title</label>
                        <input type="text" class="form-control" id="editTitle" name="title" required>
                    </div>
                    <div class="form-group">
                        <label for="editAuthor">Author</label>
                        <input type="text" class="form-control" id="editAuthor" name="author" required>
                    </div>
                    <div class="form-group">
                        <label for="editCategory">Category</label>
                        <select class="form-control" id="editCategory" name="category" required>
                            <c:forEach items="${categories}" var="category">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editIsbn">ISBN</label>
                        <input type="text" class="form-control" id="editIsbn" name="isbn" required>
                    </div>
                    <div class="form-group">
                        <label for="editPublicationYear">Publication Year</label>
                        <input type="number" class="form-control" id="editPublicationYear" name="publicationYear" min="1000" max="2024" required>
                    </div>
                    <div class="form-group">
                        <label for="editQuantity">Quantity</label>
                        <input type="number" class="form-control" id="editQuantity" name="quantity" min="0" required>
                    </div>
                    <div class="form-group">
                        <label for="editDescription">Description</label>
                        <textarea class="form-control" id="editDescription" name="description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editStatus">Status</label>
                        <select class="form-control" id="editStatus" name="status" required>
                            <option value="available">Available</option>
                            <option value="not available">Not Available</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                    <button type="button" class="btn btn-danger" onclick="hideModal('editModal')">Cancel</button>
                </form>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/your-font-awesome-kit.js" crossorigin="anonymous"></script>
        <script>

                        function showAddModal() {
                            $('#addModal').modal('show');
                        }

                        function showEditModal(bookId) {
                            const row = $(`tr[data-book-id="${bookId}"]`);
                            if (row.length) {
                                const cells = row.find('td');
                                $('#editId').val(bookId);
                                $('#editTitle').val(cells.eq(1).text());
                                $('#editAuthor').val(cells.eq(2).text());
                                $('#editCategory').val(cells.eq(3).text());
                                $('#editIsbn').val(cells.eq(4).text());
                                $('#editPublicationYear').val(cells.eq(5).text());
                                $('#editStatus').val(cells.eq(6).text().toLowerCase() === 'available' ? 'available' : 'not available');
                                $('#editQuantity').val(cells.eq(7).text());
                                $('#editDescription').val(cells.eq(8).text());
                            }
                            $('#editModal').modal('show');
                        }

                        function hideModal(modalId) {
                            $(`#${modalId}`).modal('hide');
                        }

                        function deleteBook(bookId) {
                            if (confirm('Are you sure you want to delete this book?')) {
                                window.location.href = '${pageContext.request.contextPath}/librarian/books/delete?id=' + bookId;
                            }
                        }

                        $(document).ready(function () {
                            $('#addBookForm').submit(validateForm);
                            $('#editBookForm').submit(validateForm);

                            function validateForm(event) {
                                const form = $(event.target);
                                const year = parseInt(form.find('[name="publicationYear"]').val());
                                const quantity = parseInt(form.find('[name="quantity"]').val());
                                const errorDiv = form.parent().find('.error-message');

                                if (year < 1000 || year > 2024) {
                                    errorDiv.text('Please enter a valid publication year (1000-2024)');
                                    errorDiv.show();
                                    event.preventDefault();
                                    return false;
                                }

                                if (quantity < 0) {
                                    errorDiv.text('Quantity cannot be negative');
                                    errorDiv.show();
                                    event.preventDefault();
                                    return false;
                                }

                                errorDiv.hide();
                                return true;
                            }

                            $('#searchInput').on('keyup', function () {
                                const filter = this.value.toLowerCase();
                                $('tbody tr').each(function () {
                                    const text = $(this).text().toLowerCase();
                                    $(this).toggle(text.includes(filter));
                                });
                            });

                            $('.modal').click(function (event) {
                                if ($(event.target).hasClass('modal')) {
                                    $(this).modal('hide');
                                }
                            });
                        });
        </script>
    </body>
</html>