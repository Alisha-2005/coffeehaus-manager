<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blends &amp; Roasters (Joined) — CoffeeHaus</title>
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
        <h1 class="page-title">&#128279; Blends × Roasters — Inner Join</h1>
        <p style="margin-bottom:1rem; color:#6B5445;">
            This view uses a custom JPQL inner-join query to display each blend
            alongside the roaster that produces it.
        </p>

        <table class="data-table">
            <thead>
                <tr>
                    <th>Blend Name</th>
                    <th>SKU</th>
                    <th>Flavor</th>
                    <th>Price/Kg ($)</th>
                    <th>Bean</th>
                    <th>Roaster Company</th>
                    <th>Roaster Country</th>
                    <th>Roast Style</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cb" items="${joinedResults}">
                    <tr>
                        <td>${cb.blendName}</td>
                        <td>${cb.skuCode}</td>
                        <td>${cb.flavorProfile}</td>
                        <td>${cb.pricePerKg}</td>
                        <td>${cb.beanType}</td>
                        <td>${cb.roaster.companyName}</td>
                        <td>${cb.roaster.originCountry}</td>
                        <td>${cb.roaster.roastStyle}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty joinedResults}">
                    <tr><td colspan="8" style="text-align:center; padding:1.5rem; color:#9B8578;">No joined results found.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <div class="footer">CoffeeHaus Manager &copy; 2025</div>

</body>
</html>
