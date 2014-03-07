<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>RoloDuck</title>
</head>
<body>
<c:url var="userRegistration" value="signup" />
<form id="registerForm" modelAttribute="user" method="post" class="form" role="form"
      action="${userRegistration}">
    <div class="row">
        <div class="col-xs-6 col-md-6">
            <input class="form-control" name="name" placeholder="Name" type="text" path="name"
                   required autofocus />
        </div>
    </div>
    <input class="form-control" name="email" placeholder="Your Email" type="email" path="email"/>
    <input class="form-control" name="password" placeholder="New Password" type="password" path="password"/>
    <br />
    <br />
    <button class="btn btn-lg btn-primary btn-block" type="submit">
        Sign up</button>
</form>
</body>
</html>
