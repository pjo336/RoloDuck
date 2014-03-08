<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-static-top navbar-inverse" role="navigation">
    <div class="navbar-inner">
        <div class="container">
            <div class="navbar-header navbar-left">
                <a class="navbar-brand" alt="Home" href="/"></a>
            </div>
            <c:choose>
                <c:when test="${user != null}">
                <ul class="nav navbar-nav navbar-right">
                    <li
                    <c:if test="${page.equals('projects')}">
                        class="active"
                    </c:if>>
                        <a href="/projects">Projects</a>
                        </li>
                    <li
                    <c:if test="${page.equals('partners')}">
                        class="active"
                    </c:if>>
                        <a href="/partners">Partners</a>
                        </li>
                    <li
                    <c:if test="${page.equals('contacts')}">
                        class="active"
                    </c:if>>
                    <a href="/partners">Contacts</a>
                        </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Account <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Profile</a></li>
                            <li><a href="#">Settings</a></li>
                            <li class="divider"></li>
                            <li><a href="/logout">Log out</a></li>
                        </ul>
                    </li>
                </ul>
                </c:when>
                <c:otherwise>
                <form class="form-inline navbar-right navbar-form" action="/login" method="POST" role="form">
                    <div class="form-group" >
                        <label class="sr-only" for="exampleInputEmail2">Email address</label>
                        <input type="email" class="form-control" name="useremail" id="useremail" placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="exampleInputPassword2">Password</label>
                        <input type="password" class="form-control" name="userpassword" id="userpassword" placeholder="Password">
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"><span style="color:#fff;"> Remember me</span>
                        </label>
                    </div>
                    <button type="submit" class="btn btn-primary signin-button">Sign in</button>
                </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
