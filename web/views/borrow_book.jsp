<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Borrow Book - Digital Library</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            padding-top: 20px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: white;
            padding: 20px;
            border-radius: 8px;
        }

        h2 {
            color: #333;
            margin-bottom: 30px;
            text-align: center;
        }

        .form-group label {
            font-weight: bold;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            padding: 10px 20px;
            font-size: 16px;
            transition: background-color 0.3s ease;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }

        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
            padding: 10px 20px;
            font-size: 16px;
            transition: background-color 0.3s ease;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .btn-secondary:hover {
            background-color: #545b62;
            border-color: #4e555b;
        }

        .card {
            margin-bottom: 30px;
            border: none;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #ddd;
            padding: 15px;
            border-radius: 8px 8px 0 0;
        }

        .card-title {
            margin: 0;
            font-size: 1.25rem;
            color: #333;
        }

        .card-body {
            padding: 15px;
        }

        .card-body p {
            margin-bottom: 5px;
        }

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 5px;
        }

        .alert-danger {
            color: #721c24;
            background-color: #f8d7da;
            border-color: #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="mb-4"><i class="fas fa-book-reader"></i> Borrow Book</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <c:if test="${not empty book}">
            <div class="card mb-4">
                <div class="card-header">
                    <h3 class="card-title"><i class="fas fa-book"></i> Book Details</h3>
                </div>
                <div class="card-body">
                    <p><strong>Title:</strong> ${book.title}</p>
                    <p><strong>Author:</strong> ${book.author}</p>
                    <p><strong>ISBN:</strong> ${book.isbn}</p>
                    <p><strong>Category:</strong> ${book.category}</p>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/loan/borrow" method="post">
                <input type="hidden" name="bookId" value="${book.id}">
                <input type="hidden" name="memberId" value="${sessionScope.userId}">

                <div class="form-group">
                    <label><i class="fas fa-user"></i> Member:</label>
                    <p class="form-control-static">${sessionScope.userName}</p>
                </div>

                <button type="submit" class="btn btn-primary"><i class="fas fa-check-circle"></i> Confirm Borrow</button>
            </form>
        </c:if>

        <a href="${pageContext.request.contextPath}/catalog" class="btn btn-secondary mt-3"><i class="fas fa-arrow-left"></i> Back to Catalog</a>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>