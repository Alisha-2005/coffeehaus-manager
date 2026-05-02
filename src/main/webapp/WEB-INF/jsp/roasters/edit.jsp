<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Roaster — CoffeeHaus</title>
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
        <h1 class="page-title">Edit Roaster</h1>

        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">${errorMsg}</div>
        </c:if>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/roasters/update/${roaster.roasterId}" method="post">

                <div class="form-group">
                    <label for="companyName">Company Name</label>
                    <input type="text" id="companyName" name="companyName"
                           value="${roaster.companyName}" required />
                </div>

                <div class="form-group">
                    <label for="contactEmail">Contact Email</label>
                    <input type="email" id="contactEmail" name="contactEmail"
                           value="${roaster.contactEmail}" required />
                </div>

                <div class="form-group">
                    <label for="originCountry">Origin Country</label>
                    <input type="text" id="originCountry" name="originCountry"
                           value="${roaster.originCountry}" />
                </div>

                <div class="form-group">
                    <label for="roastStyle">Roast Style</label>
                    <select id="roastStyle" name="roastStyle">
                        <option value="Light"     ${roaster.roastStyle == 'Light'     ? 'selected' : ''}>Light</option>
                        <option value="Medium"    ${roaster.roastStyle == 'Medium'    ? 'selected' : ''}>Medium</option>
                        <option value="Dark"      ${roaster.roastStyle == 'Dark'      ? 'selected' : ''}>Dark</option>
                        <option value="Specialty" ${roaster.roastStyle == 'Specialty' ? 'selected' : ''}>Specialty</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="foundedYear">Founded Year</label>
                    <input type="number" id="foundedYear" name="foundedYear"
                           value="${roaster.foundedYear}" min="1800" max="2026" />
                </div>

                <button type="submit" class="btn btn-primary">Update Roaster</button>
                <a href="${pageContext.request.contextPath}/roasters" class="btn btn-secondary" style="margin-left:0.5rem;">Cancel</a>
            </form>
        </div>
    </div>

    <div class="footer">CoffeeHaus Manager &copy; 2025</div>

</body>
</html>
