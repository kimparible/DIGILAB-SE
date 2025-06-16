<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Digital Library</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
        }

        .container {
            max-width: 400px;
            margin: auto;
        }

        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
        }

        .card-title {
            color: #2c3e50;
            font-weight: 600;
            margin-bottom: 30px;
            text-align: center;
        }

        .form-control {
            border-radius: 5px;
            padding: 12px;
            margin-bottom: 15px;
            border: 1px solid #ced4da;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .form-control:focus {
            border-color: #80bdff;
            outline: 0;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
        }

        .btn-primary {
            padding: 12px;
            font-weight: 500;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        .alert {
            margin-bottom: 20px;
            border-radius: 5px;
            padding: 15px;
        }

        .alert-danger {
            background-color: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
        }

        .close {
            font-size: 1.5rem;
            font-weight: 700;
            line-height: 1;
            color: #000;
            opacity: .5;
            padding: 0;
            background-color: transparent;
            border: 0;
            -webkit-appearance: none;
        }

        .close:hover, .close:focus {
          color: #000;
          text-decoration: none;
          opacity: .75;
        }

        .register-link {
            margin-top: 15px;
            text-align: center;
        }

        .register-link a {
            color: #007bff;
            text-decoration: none;
        }

        .register-link a:hover {
            text-decoration: underline;
        }

        .input-group-text {
            background-color: transparent;
            border: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-body">
                <h2 class="card-title"><i class="fas fa-book-open"></i> Digital Library</h2>

                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${successMessage}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${errorMessage}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <c:if test="${not empty loginError}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${loginError}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                            </div>
                            <input type="email"
                                   class="form-control"
                                   name="email"
                                   id="email"
                                   placeholder="Email"
                                   required
                                   autocomplete="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-lock"></i></span>
                            </div>
                            <input type="password"
                                   class="form-control"
                                   name="password"
                                   id="password"
                                   placeholder="Password"
                                   required
                                   autocomplete="current-password">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block" id="loginButton">Login</button>
                </form>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        // Add a console log for debugging
        console.log("login.jsp loaded");
    </script>
</body>
</html>