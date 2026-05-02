<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Blend — CoffeeHaus</title>
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
        <h1 class="page-title">Edit Blend</h1>

        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">${errorMsg}</div>
        </c:if>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/blends/update/${blend.blendId}" method="post">

                <div class="form-group">
                    <label for="blendName">Blend Name</label>
                    <input type="text" id="blendName" name="blendName"
                           value="${blend.blendName}" required />
                </div>

                <div class="form-group">
                    <label for="skuCode">SKU Code</label>
                    <input type="text" id="skuCode" name="skuCode"
                           value="${blend.skuCode}" required />
                </div>

                <div class="form-group">
                    <label for="flavorProfile">Flavor Profile</label>
                    <input type="text" id="flavorProfile" name="flavorProfile"
                           value="${blend.flavorProfile}" />
                </div>

                <div class="form-group">
                    <label for="pricePerKg">Price per Kg ($)</label>
                    <input type="number" id="pricePerKg" name="pricePerKg"
                           step="0.01" min="0" value="${blend.pricePerKg}" />
                </div>

                <div class="form-group">
                    <label for="beanType">Bean Type</label>
                    <select id="beanType" name="beanType">
                        <option value="Arabica"  ${blend.beanType == 'Arabica'  ? 'selected' : ''}>Arabica</option>
                        <option value="Robusta"  ${blend.beanType == 'Robusta'  ? 'selected' : ''}>Robusta</option>
                        <option value="Liberica" ${blend.beanType == 'Liberica' ? 'selected' : ''}>Liberica</option>
                        <option value="Excelsa"  ${blend.beanType == 'Excelsa'  ? 'selected' : ''}>Excelsa</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="roasterId">Produced By (Roaster)</label>
                    <select id="roasterId" name="roasterId" required>
                        <c:forEach var="r" items="${availableRoasters}">
                            <option value="${r.roasterId}"
                                ${blend.roaster.roasterId == r.roasterId ? 'selected' : ''}>
                                ${r.companyName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Update Blend</button>
                <a href="${pageContext.request.contextPath}/blends" class="btn btn-secondary" style="margin-left:0.5rem;">Cancel</a>
            </form>
        </div>
    </div>

    <div class="footer">CoffeeHaus Manager &copy; 2025</div>

</body>
</html>
