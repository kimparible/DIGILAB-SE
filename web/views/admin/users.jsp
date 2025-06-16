<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <style>
        body {
            padding-top: 20px;
            background-color: #f8f9fa; /* Light background color */
        }
        .container {
            max-width: 900px; /* Increased container width */
            margin: auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Subtle shadow */
            background-color: white;
            padding: 20px;
            border-radius: 8px;
        }
        .table th, .table td {
            text-align: center;
        }
        .action-buttons form {
            display: inline-block;
            margin: 0 5px; /* Add margin between buttons */
        }
        h2 {
            color: #343a40; /* Dark text color */
            font-weight: bold;
        }
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: rgba(0, 123, 255, 0.05); /* Light highlight for odd rows */
        }
        .btn-primary, .btn-secondary, .btn-danger {
            padding: 8px 16px; /* Increase button padding */
            font-size: 1rem;
            border-radius: 5px;
            transition: all 0.3s ease; /* Smooth transition for hover effects */
        }
        .btn-primary:hover, .btn-secondary:hover, .btn-danger:hover {
            transform: translateY(-2px); /* Slightly lift the button on hover */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* Add shadow on hover */
        }
        .btn-sm {
          padding: 5px 10px;
          font-size: 0.8rem;
        }
        .alert-danger {
            padding: 15px;
            border-radius: 5px;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="mb-4 text-center">Manage Members</h2>

        <c:if test="${param.errorMessage != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${param.errorMessage}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary mb-3">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>

        <table class="table table-striped table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th style="width: 200px;">Actions</th> 
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>
                            <div class="action-buttons">
                                <form action="${pageContext.request.contextPath}/admin/users/delete" method="post"
                                    onsubmit="return confirm('Are you sure you want to delete this member?');">
                                    <input type="hidden" name="id" value="${user.id}" />
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="fas fa-trash-alt"></i> Delete
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        // Add any custom JavaScript here, if needed
    </script>
</body>
</html>