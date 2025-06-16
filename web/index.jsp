<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Digital Library</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background: white;
            color: #ffffff;
            font-family: 'Arial', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card {
            border: none;
            border-radius: 10px;
            background-color: #ffffff;
            color: #333;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            overflow: hidden;
        }
        .card-header {
            background: white;
            color: #fff;
            padding: 1.5rem;
        }
        .card-title {
            color: black;
            font-size: 1.75rem;
            font-weight: bold;
        }
        .btn {
            border-radius: 25px;
            font-size: 1rem;
            font-weight: bold;
            padding: 0.75rem 1.5rem;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        .btn-primary {
            background-color: #6a11cb;
            border: none;
        }
        .btn-primary:hover {
            background-color: #2575fc;
            color: #ffffff;
        }
        .btn-secondary {
            background-color: #f8f9fa;
            color: #333;
            border: none;
        }
        .btn-secondary:hover {
            background-color: #ddd;
            color: #333;
        }
        .icon {
            font-size: 2rem;
            margin-bottom: 1rem;
            color: #6a11cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header text-center">
                        <i class="fas fa-book icon"></i>
                        <h1 class="card-title">Selamat datang di Digital Library</h1>
                    </div>
                    <div class="card-body text-center">
                        <p class="mb-4">Akses koleksi buku digital terbaik kami di ujung jari Anda.</p>
                        <a href="views/login.jsp" class="btn btn-primary mr-3">Login</a>
                        <a href="views/register.jsp" class="btn btn-secondary">Register</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
