<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Librarian Dashboard - Library System</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .navbar {
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .container {
            margin-top: 30px;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #343a40;
            margin-bottom: 30px;
            text-align: center;
        }
        .btn-primary, .btn-danger {
            padding: 12px 25px;
            font-size: 1.1rem;
            border-radius: 5px;
            transition: all 0.3s ease;
        }
        .btn-primary:hover, .btn-danger:hover {
            transform: translateY(-2px);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }
        .card {
            border: none;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }
        .card-body {
            text-align: center;
        }
        .card-icon {
            font-size: 3rem;
            color: #007bff;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/librarian">
            <i class="fas fa-book-open mr-2"></i>Library System
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link btn btn-danger" href="<%=request.getContextPath()%>/views/logout.jsp">
                        <i class="fas fa-sign-out-alt mr-1"></i>Logout
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h1>Welcome, ${userName}!</h1>

        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <i class="fas fa-book card-icon"></i>
                        <h5 class="card-title">Manage Books</h5>
                        <p class="card-text">Add, edit, and delete books in the library catalog.</p>
                        <a href="<%=request.getContextPath()%>/librarian/books?action=list" class="btn btn-primary">Manage Books</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <i class="fas fa-handshake card-icon"></i>
                        <h5 class="card-title">Manage Loans</h5>
                        <p class="card-text">View loans history.</p>
                        <a href="<%=request.getContextPath()%>/librarian/loans?action=list" class="btn btn-primary">Loans History</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>