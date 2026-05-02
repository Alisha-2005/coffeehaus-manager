<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Blends — CoffeeHaus</title>
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
            <h1 class="page-title">&#9749; Coffee Blends Catalog</h1>
            <a href="${pageContext.request.contextPath}/blends/add" class="btn btn-primary">+ New Blend</a>
        </div>

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
                    <th>Blend Name</th>
                    <th>SKU</th>
                    <th>Flavor Profile</th>
                    <th>Price/Kg ($)</th>
                    <th>Bean Type</th>
                    <th>Roaster</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="blend" items="${blendList}">
                    <tr>
                        <td>${blend.blendId}</td>
                        <td>${blend.blendName}</td>
                        <td>${blend.skuCode}</td>
                        <td>${blend.flavorProfile}</td>
                        <td>${blend.pricePerKg}</td>
                        <td>${blend.beanType}</td>
                        <td>${blend.roaster.companyName}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/blends/edit/${blend.blendId}"
                               class="action-link">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty blendList}">
                    <tr><td colspan="8" style="text-align:center; padding:1.5rem; color:#9B8578;">No blends found.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <div class="footer">CoffeeHaus Manager &copy; 2025</div>

</body>
</html>
