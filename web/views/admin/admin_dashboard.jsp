<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Digital Library System</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
        <style>
            body {
                background-color: #f8f9fa;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }
            .navbar-brand i {
                margin-right: 8px;
            }
            .header {
                width: 100%;
                padding: 10px 0;
                background-color: #e9ecef;
                color: #212529;
                border-top: 1px solid #ddd;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .navbar-brand {
                font-size: 1.5rem;
                font-weight: bold;
            }
            .nav-link {
                font-size: 1rem;
                color: #212529; /* Darker text color */
            }
            .nav-link:hover {
                color: #000; /* Slightly darker on hover */
            }
            .main-content {
                flex: 1;
                padding: 20px;
                margin-top: 20px;
            }
            .container {
                background-color: #ffffff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            h2 {
                color: #343a40;
                margin-bottom: 20px;
                text-align: center;
            }
            .card {
                border: none;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                margin-bottom: 30px; /* Added margin bottom */
            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            }
            .card-body {
                padding: 30px;
            }
            .card-title {
                font-size: 1.5rem;
                font-weight: bold;
                margin-bottom: 20px;
                color: #343a40;
            }
            .btn-primary {
                padding: 10px 20px;
                font-size: 1rem;
                border-radius: 5px;
                transition: all 0.3s ease;
            }
            .btn-primary:hover {
                transform: translateY(-2px);
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            }
            .footer {
                background-color: #e9ecef; /* Lighter background color */
                color: #212529; /* Darker text color */
                padding: 10px 0;
                text-align: center;
                margin-top: auto;
                border-top: 1px solid #ddd;
                width: 100%;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .footer-text {
                font-size: 0.8rem;
            }
            .welcome-text {
                margin-bottom: 30px;
            }
        </style>
    </head>
    <body>
        <header class="header">
            <div class="container-fluid">
                <nav class="navbar navbar-expand-lg navbar-light">
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="navbar-brand text-decoration-none">
                        <i class="fas fa-book-open"></i>
                        Digital Library System
                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">
                                    <i class="fas fa-users me-1"></i>
                                    Manage Users
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/views/logout.jsp">
                                    <i class="fas fa-sign-out-alt me-1"></i>
                                    Logout
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </header>

        <div class="main-content">
            <div class="container">
                <h2 class="welcome-text">Welcome, ${adminName}!</h2>
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><i class="fas fa-users me-2"></i>Manage Users</h5>
                        <p>Manage user accounts, for deleting users account.</p>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary">
                            Go to User Management
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <footer class="footer">
            <div class="container-fluid">
                <p class="footer-text">&copy; 2024 Digital Library System. All Rights Reserved.</p>
            </div>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>