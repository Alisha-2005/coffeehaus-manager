<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Roasters — CoffeeHaus</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

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
        <div class="toolbar">
            <h1 class="page-title">&#127793; Roasters Directory</h1>
            <a href="${pageContext.request.contextPath}/roasters/add" class="btn btn-primary">+ New Roaster</a>
        </div>

        <!-- Flash messages -->
        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">${successMsg}</div>
        </c:if>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">${errorMsg}</div>
        </c:if>

        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Company Name</th>
                    <th>Email</th>
                    <th>Country</th>
                    <th>Roast Style</th>
                    <th>Founded</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="roaster" items="${roasterList}">
                    <tr>
                        <td>${roaster.roasterId}</td>
                        <td>${roaster.companyName}</td>
                        <td>${roaster.contactEmail}</td>
                        <td>${roaster.originCountry}</td>
                        <td>${roaster.roastStyle}</td>
                        <td>${roaster.foundedYear}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/roasters/edit/${roaster.roasterId}"
                               class="action-link">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty roasterList}">
                    <tr><td colspan="7" style="text-align:center; padding:1.5rem; color:#9B8578;">No roasters found.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <div class="footer">CoffeeHaus Manager &copy; 2025</div>

</body>
</html>
