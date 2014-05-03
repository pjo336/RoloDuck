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
        <div id="partnerpanel${partner.id}" class="partner-container">
            <div class="partner-header">

                <h3 class="partner-name">
                    <a href="/partners/${partner.id}">${partner.partnerName}</a>
                    <span class=" pull-right small action-links">
                        <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                        <a class="action-link trash-action"
                           onclick="trash('partner', ${partner.id})"><span class="glyphicon glyphicon-trash"></span></a>
                    </span>
                </h3>
            </div>
            <div class="partner-body">
                <h5>Projects Assigned <span class="small"><a class="pull-right" href="/partners/assign=${partner.id}"><span class="glyphicon glyphicon-plus"></span> add project</a></span></h5>
                <div class="well well-sm">
                    <c:if test="${empty partner.associatedProjects}">
                        <span class="noProjects">No projects currently assigned</span>
                    </c:if>
                    <c:forEach var="associatedProject" items="${partner.associatedProjects}">
                        <button type="button" id="projectBubble${associatedProject.id}${partner.id}"
                                class="btn btn-default btn well-button" data-toggle="tooltip" data-placement="bottom"
                                title="click to unassign"
                                onclick="removeAssociation(${associatedProject.id}, ${partner.id})">
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
                        <tr><td colspan="6" class="noProjects">No contacts currently assigned</td></tr>
                    </c:if>
                    <c:forEach var="contact" items="${partner.associatedContacts}">
                        <tr id="contactRow${contact.id}">
                            <td>${contact.contactFirstName}</td>
                            <td>${contact.contactLastName}</td>
                            <td>${contact.contactTitle}</td>
                            <td>${contact.contactEmail}</td>
                            <td>${contact.contactPhone}</td>
                            <td width="55px" style="padding-right: 0;">
                                <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                                <a class="action-link trash-action" onclick="trash('contact', ${contact.id})"><span class="glyphicon glyphicon-trash"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <span class="small"><a href="/contacts/create/partner=${partner.id}" class="pull-right"><span class="glyphicon glyphicon-plus"></span> add a contact</a></span>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </c:forEach>
</div>