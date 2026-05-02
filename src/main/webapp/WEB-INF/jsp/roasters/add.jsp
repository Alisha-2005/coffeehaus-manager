<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Roaster — CoffeeHaus</title>
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
        <h1 class="page-title">Add a New Roaster</h1>

        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">${errorMsg}</div>
        </c:if>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/roasters/save" method="post">

                <div class="form-group">
                    <label for="companyName">Company Name</label>
                    <input type="text" id="companyName" name="companyName"
                           placeholder="e.g. Alpine Roasters" required />
                </div>

                <div class="form-group">
                    <label for="contactEmail">Contact Email</label>
                    <input type="email" id="contactEmail" name="contactEmail"
                           placeholder="e.g. hello@alpine.com" required />
                </div>

                <div class="form-group">
                    <label for="originCountry">Origin Country</label>
                    <input type="text" id="originCountry" name="originCountry"
                           placeholder="e.g. Switzerland" />
                </div>

                <div class="form-group">
                    <label for="roastStyle">Roast Style</label>
                    <select id="roastStyle" name="roastStyle">
                        <option value="Light">Light</option>
                        <option value="Medium" selected>Medium</option>
                        <option value="Dark">Dark</option>
                        <option value="Specialty">Specialty</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="foundedYear">Founded Year</label>
                    <input type="number" id="foundedYear" name="foundedYear"
                           placeholder="e.g. 2005" min="1800" max="2026" />
                </div>

                <button type="submit" class="btn btn-primary">Save Roaster</button>
                <a href="${pageContext.request.contextPath}/roasters" class="btn btn-secondary" style="margin-left:0.5rem;">Cancel</a>
            </form>
        </div>
    </div>

    <div class="footer">CoffeeHaus Manager &copy; 2025</div>

</body>
</html>
