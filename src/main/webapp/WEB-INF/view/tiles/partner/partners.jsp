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
                    <a href="/partners/${partner.id}">
                        ${partner.partnerName}
                    </a>
                </li>
                </c:forEach>
            </ul>
        </div>
        <a href="/partners/create"><button class="btn btn-primary navbar-btn navbar-right add-button"><span class="glyphicon glyphicon-plus"></span> Add New Partner</button></a>
    </nav>

    <c:forEach var="partner" items="${partners}">
    <div class="panel panel-default">
        <div class="panel-heading">
            <a href="/partners/${partner.id}">
                <h3 class="panel-title pull-left">
                    ${partner.partnerName}
                </h3>
            </a>
            <span class="pull-right">
                <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                <a class="action-link trash-action" onclick="trash()"><span class="glyphicon glyphicon-trash"></span></a>
            </span>
            <div class="clearfix"></div>
        </div>
        <div class="panel-body">
            <h5>Projects Assigned <span class="small"><a class="pull-right" href="/partners/assign=${partner.id}"><span class="glyphicon glyphicon-plus"></span> add project</a></span></h5>
            <div class="well well-sm">
                <c:if test="${empty partner.associatedProjects}"><h3>yo yo dog, please assign a project to
                    dis
                    mad ill partnuhhhh</h3></c:if>
                <c:forEach var="associatedProject" items="${partner.associatedProjects}">
                <button type="button" class="btn btn-default btn well-button" data-toggle="tooltip" data-placement="bottom" title="click to unassign">
                    ${associatedProject.projectName}<span class="glyphicon glyphicon-remove"></span>
                </button>
                </c:forEach>
                <%--TODO pagination for project bubbles here--%>
                <%--<a class="well-button" href="partners/assign=${partner.id}" data-toggle="tooltip" data-placement="top" title="click to view all projects">and 3 others...</a>--%>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Title</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                    <c:if test="${empty partner.associatedContacts}">
                        <tr><td>You need to assign some contacts to this partner you thilly gooth</td></tr>
                    </c:if>
                    <c:forEach var="contact" items="${partner.associatedContacts}">
                        <tr>
                            <td>${contact.contactFirstName}</td>
                            <td>${contact.contactLastName}</td>
                            <td>${contact.contactTitle}</td>
                            <td>${contact.contactEmail}</td>
                            <td>${contact.contactPhone}</td>
                            <td width="55px" style="padding-right: 0;">
                                <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                                <a class="action-link trash-action" onclick="trash()"><span class="glyphicon glyphicon-trash"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </c:forEach>
</div>