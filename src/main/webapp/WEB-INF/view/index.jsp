<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>RoloDuck</title>
    <link rel="shortcut icon" href="/static/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/static/images/favicon.ico" type="image/x-icon">
</head>
<body>
<h1>Welcome to RoloDuck</h1>
<core:if test="${user != null}">
    <h1>${user}</h1>
</core:if>

<br />
<a href="j_spring_security_logout">Logout</a> <br />
</body>
</html>
