<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
    <div class="form-container">
        <core:if test="${not empty error}">
            <div class="errorblock">
                Your login attempt was not successful, try again.<br /> Caused :
                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </core:if>
        <h3>Please log in to continue</h3>
        <form name='f' action="<c:url value='j_spring_security_check' />"
              method='POST'>
            <div class="form-group" >
                <label class="sr-only">Email address</label>
                <input type='email' name='j_username' class="form-control" id="useremail" placeholder="Email">
            </div>
            <div class="form-group">
                <label class="sr-only">Password</label>
                <input type='password' name='j_password' class="form-control" id="userpassword" placeholder="Password"/>
            </div>
            <div class="checkbox checkbox-vertical">
                <label>
                    <input type="checkbox" name="_spring_security_remember_me"><span style="color:#000;"> Remember me</span>
                </label>
            </div>
            <button type="submit" class="btn btn-primary btn-lg signin-button" name="submit">Sign in</button> <span
                class="spacer">or <span class="spacer">
            <a href="/signup">signup for an account</a></span></span>.
        </form>
    </div>
</div>