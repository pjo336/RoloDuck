<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">

    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li class="active">Partners</li>
    </ol>

    <h1 style="margin-bottom:20px"> All Partners </h1>

    <nav class="navbar navbar-default">
        <span class="navbar-jump">Jump to:</span>
        <div class="btn-group jump-dropdown">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                Select Partner
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="partner" items="${partners}">
                <li>
                    <a href="/partner/" onclick="location.href=this.href+ ${partner.id}; return false;">
                        ${partner.partnerName}
                    </a>
                </li>
                </c:forEach>
            </ul>
        </div>
        <a href="/partners/create"><button class="btn btn-primary navbar-btn navbar-right add-button">+ Add New Partner</button></a>
    </nav>

    <c:forEach var="partner" items="${partners}">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                <a href="/project/partner/" onclick="location.href=this.href+ ${partner.id}; return false;">
                    ${partner.partnerName}</a>
            </h3>
        </div>
        <div class="panel-body">
        </div>
    </div>
    </c:forEach>
</div>