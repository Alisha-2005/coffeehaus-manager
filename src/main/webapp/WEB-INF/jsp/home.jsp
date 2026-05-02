<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CoffeeHaus Manager — Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <!-- Navigation -->
    <nav class="navbar">
        <div class="brand">Coffee<span>Haus</span></div>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/roasters">Roasters</a></li>
            <li><a href="${pageContext.request.contextPath}/blends">Blends</a></li>
            <li><a href="${pageContext.request.contextPath}/blends/joined">Joined View</a></li>
        </ul>
    </nav>

    <div class="container">
        <h1 class="page-title">&#9749; Welcome to CoffeeHaus Manager</h1>
        <p style="margin-bottom: 1.5rem; color: #6B5445;">
            A centralized dashboard for managing specialty coffee roasters and their unique blends.
            Use the cards below to navigate.
        </p>

        <div class="card-grid">
            <div class="card">
                <h3>&#127793; Roasters</h3>
                <p>View, add, and update coffee roasting companies from around the globe.</p>
                <a href="${pageContext.request.contextPath}/roasters" class="card-link">Manage Roasters</a>
            </div>

            <div class="card">
                <h3>&#9749; Coffee Blends</h3>
                <p>Browse the catalog of artisan blends, create new ones, or tweak existing entries.</p>
                <a href="${pageContext.request.contextPath}/blends" class="card-link">Manage Blends</a>
            </div>

            <div class="card">
                <h3>&#128279; Joined View</h3>
                <p>See blends and their roasters side-by-side using a custom inner-join query.</p>
                <a href="${pageContext.request.contextPath}/blends/joined" class="card-link">View Joined Data</a>
            </div>
        </div>
    </div>

    <div class="footer">
        CoffeeHaus Manager &copy; 2025 — Built with Spring Boot &amp; JSP
    </div>

</body>
</html>
