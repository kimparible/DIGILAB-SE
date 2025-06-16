<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loans History - Digital Library</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f3f4f6;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 85%;
            margin: 40px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #007bff;
            margin-bottom: 30px;
            font-size: 24px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e3e3e3;
        }

        th {
            background-color: #007bff;
            color: white;
            text-transform: uppercase;
            font-size: 14px;
        }

        td {
            font-size: 14px;
        }

        tr:hover {
            background-color: #f1f5f9;
            transition: background-color 0.3s;
        }

        .btn {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .btn-back {
            background-color: #6c757d;
        }

        .btn-back:hover {
            background-color: #5a6268;
        }

        .btn i {
            margin-right: 5px;
        }

        .status-badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
            text-align: center;
            display: inline-block;
        }

        .status-badge.borrowed {
            background-color: #f39c12;
            color: white;
        }

        .status-badge.returned {
            background-color: #28a745;
            color: white;
        }

        @media (max-width: 768px) {
            .container {
                width: 95%;
            }

            table {
                font-size: 12px;
            }

            th, td {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Loans History</h2>
        <table>
            <thead>
                <tr>
                    <th>Loan ID</th>
                    <th>Book ID</th>
                    <th>Member ID</th>
                    <th>Loan Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${loans}" var="loan">
                    <tr>
                        <td>${loan.id}</td>
                        <td>${loan.bookId}</td>
                        <td>${loan.memberId}</td>
                        <td>${loan.loanDate}</td>
                        <td>${loan.dueDate}</td>
                        <td>
                            <c:out value="${loan.returnDate != null ? loan.returnDate : 'Not Returned'}"/>
                        </td>
                        <td>
                            <span class="status-badge ${loan.status == 'Borrowed' ? 'borrowed' : 'returned'}">
                                ${loan.status}
                            </span>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/views/librarian_dashboard.jsp" class="btn btn-back">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>
</body>
</html>