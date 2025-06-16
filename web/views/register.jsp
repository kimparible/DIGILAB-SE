<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Register - Digital Library</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(to right, #6a11cb, #2575fc);
                height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .container {
                max-width: 450px;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
                padding: 30px;
            }
            .card-title {
                font-weight: 600;
                margin-bottom: 20px;
                color: #2c3e50;
                text-align: center;
            }
            .form-control {
                border: 1px solid #ced4da;
                border-radius: 10px;
                padding: 12px 15px;
                margin-bottom: 20px;
                box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.05);
            }

            .form-control:focus {
                border-color: #6a11cb;
                box-shadow: 0 0 8px rgba(106, 17, 203, 0.2);
            }

            .btn-primary {
                background: linear-gradient(to right, #6a11cb, #2575fc);
                border: none;
                padding: 12px;
                font-weight: 500;
                border-radius: 10px;
                transition: all 0.3s;
            }
            .btn-primary:hover {
                background: linear-gradient(to right, #5a0ecb, #1a5fcf);
                transform: scale(1.05);
            }
            .alert {
                margin-bottom: 20px;
                border-radius: 10px;
            }
            .login-link {
                margin-top: 15px;
                text-align: center;
            }
            .login-link a {
                color: #6a11cb;
                text-decoration: none;
                font-weight: 500;
            }
            .login-link a:hover {
                text-decoration: underline;
            }
            select.form-control {
                min-width: 100%; /* Pastikan dropdown menggunakan lebar penuh */
                background-color: #fff;
                padding: 12px 15px; /* Beri padding agar tidak terlihat sempit */
                overflow: visible; /* Pastikan dropdown tidak terpotong */
                height: auto; /* Biarkan tinggi menyesuaikan isi */
                cursor: pointer;
            }

            select.form-control:focus {
                border-color: #6a11cb;
                box-shadow: 0 0 8px rgba(106, 17, 203, 0.2);
            }

            .form-group {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Register</h2>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${errorMessage}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="form-group">
                            <input type="text" class="form-control" name="name" placeholder="Name" required>
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control" name="email" placeholder="Email" required>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="password" placeholder="Password" required>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="role">
                                <option value="Member">Member</option>
                                <option value="Librarian">Librarian</option>
                                <option value="Admin">Admin</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Register</button>
                    </form>
                    <div class="login-link">
                        Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
