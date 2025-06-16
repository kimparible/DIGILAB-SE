<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member Dashboard</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .container {
            margin-top: 50px;
        }

        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 30px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 12px rgba(0, 0, 0, 0.15);
        }

        .card-title {
            color: #2c3e50;
            font-weight: 600;
            margin-bottom: 20px;
        }

        .table {
            margin-bottom: 30px;
        }

        .notification-container {
            max-width: 400px;
            margin: 20px auto;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .notification-header {
            padding: 10px;
            background-color: #f5f5f5;
            border-bottom: 1px solid #ddd;
            border-radius: 4px 4px 0 0;
        }

        .notification-header h4 {
            margin: 0;
            font-size: 18px;
            color: #333;
        }

        .notification-list {
            max-height: 300px;
            overflow-y: auto;
            list-style: none;
            padding: 0;
        }

        .notification-item {
            padding: 10px;
            border-bottom: 1px solid #eee;
            cursor: pointer;
            transition: background-color 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .notification-item.unread {
            background-color: #f0f7ff;
            font-weight: bold;
        }

        .notification-item:hover {
            background-color: #f5f5f5;
        }

        .notification-message {
            flex-grow: 1;
            margin-right: 10px;
        }

        .notification-time {
            font-size: 0.8em;
            color: #666;
            white-space: nowrap;
        }

        .btn {
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .btn:hover {
            transform: translateY(-2px);
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover{
          background-color: #0056b3;
            border-color: #0056b3;
        }

        .btn-info {
            background-color: #17a2b8;
            border-color: #17a2b8;
        }

        .btn-info:hover{
           background-color: #117a8b;
            border-color: #117a8b;
        }

        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }

        .btn-secondary:hover{
            background-color: #545b62;
            border-color: #4e555b;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-body">
                <h1 class="card-title">Welcome, ${sessionScope.userName}!</h1>

                <div class="notification-container">
                    <c:if test="${not empty notifications}">
                        <div class="notification-header">
                            <h4><i class="fas fa-bell"></i> Notifications</h4>
                        </div>
                        <div class="notification-list">
                            <c:forEach items="${notifications}" var="notification">
                                <div class="notification-item ${notification.read ? 'read' : 'unread'}"
                                     data-notification-id="${notification.notificationId}">
                                    <div class="notification-message">${notification.message}</div>
                                    <div class="notification-time">
                                        <fmt:formatDate value="${notification.createdAt}"
                                                        pattern="dd-MM-yyyy HH:mm"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/catalog" class="btn btn-primary"><i class="fas fa-book"></i> View Catalog</a>
                    <a href="${pageContext.request.contextPath}/loan/list" class="btn btn-info"><i class="fas fa-list"></i> View All My Loans</a>
                    <a href="${pageContext.request.contextPath}/views/logout" class="btn btn-secondary"><i class="fas fa-sign-out-alt"></i> Logout</a>
                </div>
            </div>
        </div>
    </div>
    <script>
        document.querySelectorAll('.notification-item').forEach(item => {
            item.addEventListener('click', function () {
                const notificationId = this.dataset.notificationId;

                fetch('member?action=markNotificationRead', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `notificationId=${notificationId}&action=markNotificationRead`
                })
                    .then(response => {
                        if (response.ok) {
                            this.classList.remove('unread');
                            this.classList.add('read');
                        }
                    })
                    .catch(error => console.error('Error:', error));
            });
        });
    </script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>