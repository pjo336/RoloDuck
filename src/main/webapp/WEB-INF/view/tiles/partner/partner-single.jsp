<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">

    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li><a href="/partners">Partners</a></li>
        <li class="active">${partner.partnerName}</li>
    </ol>

    <h1 style="margin-bottom:20px"> ${partner.partnerName} </h1>

    <div class="single-partner_Projects">
        <h4>Projects assigned</h4>
        <div class="well well-sm">
            <c:if test="${empty partner.associatedProjects}">
                <span class="noProjects">No projects currently assigned</span>
            </c:if>
            <c:forEach var="associatedProject" items="${partner.associatedProjects}">
                <button type="button" class="btn btn-default btn well-button" data-toggle="tooltip" data-placement="bottom" title="click to unassign">
                        ${associatedProject.projectName}<span class="glyphicon glyphicon-remove"></span>
                </button>
            </c:forEach>
        </div>
        <a href="/partners/assign=${partner.id}" class="btn btn-primary add-button pull-right">Add to Project</button></a>
        <div style="clear:both"></div>
    </div>

    <div class="single-partner_Contacts">
        <h4>Contacts</h4>
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
        <span class="small"><a href="#" class="pull-right"><span class="glyphicon glyphicon-plus"></span> add a contact</a></span>
    </div>
</div>