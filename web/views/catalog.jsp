<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Catalog - Digital Library</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
        }

        .container {
            width: 90%;
            margin: 20px auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: white;
            padding: 20px;
            border-radius: 8px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .btn i {
            margin-right: 8px;
        }

        .btn-primary {
            background-color: #007bff;
            color: white;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #545b62;
        }

        .btn-success {
            background-color: #28a745;
            color: white;
        }
        .btn-success:hover{
             background-color: #1e7e34;
        }

        .actions-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f8f9fa;
            font-weight: 600;
        }

        .action-buttons {
            display: flex;
            gap: 8px;
        }

        .search-form {
            display: flex;
            gap: 5px;
        }

        .search-box {
            padding: 10px;
            width: 250px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            font-size: 16px;
        }

        /* Responsive Table */
        @media (max-width: 768px) {
            .table thead {
                display: none;
            }

            .table, .table tbody, .table tr, .table td {
                display: block;
                width: 100%;
            }

            .table tr {
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 8px;
            }

            .table td {
                text-align: right;
                padding-left: 50%;
                position: relative;
                border-bottom: none;
            }

            .table td::before {
                content: attr(data-label);
                position: absolute;
                left: 0;
                width: 50%;
                padding-left: 15px;
                font-weight: 600;
                text-align: left;
            }

            .action-buttons {
                justify-content: flex-end;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="actions-container">
                <a href="${pageContext.request.contextPath}/views/member_dashboard.jsp" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
                <h2>Catalog</h2>
            </div>
            <form action="${pageContext.request.contextPath}/catalog" method="get" class="search-form">
                <input type="text" class="search-box" placeholder="Search books..." name="search" value="${param.search}" id="searchInput">
                <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
            </form>
        </div>

        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Category</th>
                    <th>ISBN</th>
                    <th>Publication Year</th>
                    <th>Quantity</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty books}">
                        <tr>
                            <td colspan="8" data-label="No Data">No books found.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${books}" var="book">
                            <tr>
                                <td data-label="Title">${book.title}</td>
                                <td data-label="Author">${book.author}</td>
                                <td data-label="Category">${book.category}</td>
                                <td data-label="ISBN">${book.isbn}</td>
                                <td data-label="Publication Year">${book.publicationYear}</td>
                                <td data-label="Quantity">${book.quantity}</td>
                                <td data-label="Description">${book.description}</td>
                                <td data-label="Actions" class="action-buttons">
                                    <c:if test="${book.available}">
                                        <a href="${pageContext.request.contextPath}/loan/borrow?id=${book.id}" class="btn btn-success">
                                            <i class="fas fa-hand-paper"></i> Pinjam
                                        </a>
                                    </c:if>
                                    <c:if test="${!book.available}">
                                        <button class="btn btn-secondary" disabled>
                                            <i class="fas fa-hand-paper"></i> Pinjam
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#searchInput').on('keyup', function() {
                let value = $(this).val().toLowerCase();
                $('tbody tr').filter(function() {
                  // toggle the row based on whether it contains the search term
                  $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });
        });
    </script>
</body>
</html>