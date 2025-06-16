<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Return Book - Digital Library</title>
    <style>
        /* Reset browser styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        /* Container styling */
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #f9f9f9;
        }

        /* Title styling */
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        /* Form styling */
        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #fff;
            font-size: 14px;
            color: #333;
        }

        .form-group input[readonly] {
            background-color: #e9ecef;
            cursor: not-allowed;
        }

        /* Button styling */
        .btn {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                padding: 15px;
            }

            .btn {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Return Book</h2>
        <form action="${pageContext.request.contextPath}/loan/return" method="post">
            <input type="hidden" name="loanId" value="${loan.id}" />

            <div class="form-group">
                <label>Loan ID:</label>
                <input type="text" value="${loan.id}" readonly />
            </div>
            <div class="form-group">
                <label>Book ID:</label>
                <input type="text" value="${loan.bookId}" readonly />
            </div>
            <div class="form-group">
                <label>Member ID:</label>
                <input type="text" value="${loan.memberId}" readonly />
            </div>
            <div class="form-group">
                <label>Loan Date:</label>
                <input type="text" value="${loan.loanDate}" readonly />
            </div>
            <div class="form-group">
                <label>Due Date:</label>
                <input type="text" value="${loan.dueDate}" readonly />
            </div>

            <button type="submit" class="btn">Return</button>
        </form>
    </div>
</body>
</html>
