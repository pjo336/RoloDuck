<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <c:url var="userRegistration" value="signup" />
    <form id="registerForm" modelAttribute="user" method="post" class="form" role="form"
          action="${userRegistration}">
        <div class="row">
            <div class="col-xs-6 col-md-6">
                <input class="form-control" name="name" placeholder="Name" type="text"
                       required autofocus />
            </div>
        </div>
        <input class="form-control" name="email" placeholder="Your Email" type="email"/>
        <input class="form-control" name="password" placeholder="New Password" type="password"/>
        <h3>Enter your company identifier here:</h3>
        <input class="form-control" name="companyIdentifier" placeholder="Company Identifier" type="text"/>
        <br />
        <br />
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            Sign up</button>
    </form>
</div>