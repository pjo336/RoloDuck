<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-static-top navbar-inverse" role="navigation">
    <div class="navbar-inner">
        <div class="container">
            <div class="navbar-header navbar-left">
                <a class="navbar-brand" alt="Home" href="/"></a>
            </div>
            <sec:authorize var="loggedIn" access="isAuthenticated()" />
            <c:choose>
                <c:when test="${loggedIn}">

                    <ul id="rolo-main-nav" class="nav navbar-nav navbar-right">
                        <li style="height: 50px;">
                            <form class="navbar-search form-inline navbar-right navbar-form" action="/search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search" width="260px">
                                </div>
                            </form>
                        </li>
                        <li>
                            <a href="/projects">Projects</a>
                            </li>
                        <li>
                            <a href="/partners">Partners</a>
                            </li>
                        <li>
                        <a href="/contacts">Contacts</a>
                            </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Account <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="/profile">Profile</a></li>
                                <li><a href="/settings">Settings</a></li>
                                <li class="divider"></li>
                                <li><a href="j_spring_security_logout">Log out</a></li>
                            </ul>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                <form class="form-inline navbar-right navbar-form" action="<s:url value='j_spring_security_check' />" method="POST" role="form">
                    <div class="form-group" >
                        <label class="sr-only">Email address</label>
                        <input type="email" class="form-control" name='j_username' id="useremail" placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label class="sr-only">Password</label>
                        <input type="password" class="form-control" name='j_password' id="userpassword" placeholder="Password">
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="_spring_security_remember_me"><span style="color:#fff;"> Remember me</span>
                        </label>
                    </div>
                    <button type="submit" class="btn btn-primary signin-button">Sign in</button>
                </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
