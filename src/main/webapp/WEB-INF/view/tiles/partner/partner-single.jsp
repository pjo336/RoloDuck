<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">

    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li><a href="/partners">Partners</a></li>
        <li class="active">${partner.partnerName}</li>
    </ol>

    <h1 style="margin-bottom:20px"> ${partner.partnerName} </h1>

    <h3>Projects assigned</h3>
    <a href="/partners/assign"><button class="btn btn-primary navbar-btn navbar-right add-button">Add to Project</button></a>
    <%--<nav class="navbar navbar-default">--%>
        <%--<span class="navbar-jump">Jump to:</span>--%>
        <%--<div class="btn-group jump-dropdown">--%>
            <%--<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
                <%--Select Partner--%>
                <%--<span class="caret"></span>--%>
            <%--</button>--%>
            <%--<ul class="dropdown-menu">--%>
                <%--<c:forEach var="partner" items="${partners}">--%>
                    <%--<li>--%>
                        <%--<a href="/partner/${partner.id}">--%>
                                <%--${partner.partnerName}--%>
                        <%--</a>--%>
                    <%--</li>--%>
                <%--</c:forEach>--%>
            <%--</ul>--%>
        <%--</div>--%>
        <%--<a href="/partners/create"><button class="btn btn-primary navbar-btn navbar-right add-button">+ Add New Partner</button></a>--%>
    <%--</nav>--%>

    <c:forEach var="partner" items="${partners}">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <a href="/partner/${partner.id}">
                            ${partner.partnerName}</a>
                </h3>
            </div>
            <div class="panel-body">
            </div>
        </div>
    </c:forEach>
</div>