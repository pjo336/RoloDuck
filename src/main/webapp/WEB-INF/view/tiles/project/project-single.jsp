<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">

    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li><a href="/projects">Projects</a></li>
        <li class="active">${project.projectName}</li>
    </ol>

    <h1 style="margin-bottom:20px"> ${project.projectName} </h1>

    <nav class="navbar navbar-default">
    <span class="navbar-jump">Jump to:</span>
    <div class="btn-group jump-dropdown">
    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
    Select Partner
    <span class="caret"></span>
    </button>
    <ul class="dropdown-menu">
    <c:forEach var="partner" items="${project.partnerAssocs}">
    <li>
    <a href="/partner/${partner.id}">
    ${partner.partnerName}
    </a>
    </li>
    </c:forEach>
    </ul>
    </div>
    <a href="/partners/create"><button class="btn btn-primary navbar-btn navbar-right add-button"><span class="glyphicon glyphicon-plus"></span> Add New Partner</button></a>
    </nav>

    <div class="panel panel-default">
        <div class="panel-heading">
            <a href="/partners/${partner.id}">
                <h3 class="panel-title pull-left">
                    Fandango
                </h3>
            </a>
            <span class="pull-right">
                <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                <a class="action-link remove-action" onclick="remove()"><span class="glyphicon glyphicon-remove"></span></a>
            </span>
            <div class="clearfix"></div>
        </div>
        <div class="panel-body">
            <h5>Contacts</h5>
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
                <!-- forEach var="contact" items="partner.contacts"> -->
                <tr>
                    <td>Carlos</td>
                    <td>Aparicio</td>
                    <td>Supervisor, Digital Investment</td>
                    <td>Carlos.Aparicio@us.initiative.com</td>
                    <td>310-283-2324</td>
                    <td width="55px" style="padding-right: 0;">
                        <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                        <a class="action-link trash-action" onclick="trash()"><span class="glyphicon glyphicon-trash"></span></a>
                    </td>
                </tr>
                <tr>
                    <td>Mike</td>
                    <td>Harker</td>
                    <td>Strategist, Digital Investment</td>
                    <td>Michael.Harker@us.initiative.com</td>
                    <td>310-283-2184</td>
                    <td width="55px" style="padding-right: 0;">
                        <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                        <a class="action-link trash-action" onclick="trash()"><span class="glyphicon glyphicon-trash"></span></a>
                    </td>
                </tr>
                <tr>
                    <td>Lydne</td>
                    <td>Moffat</td>
                    <td>Ad Operations Coordinator</td>
                    <td>Lynde.Moffat@us.initiative.com</td>
                    <td>310-283-5287</td>
                    <td width="55px" style="padding-right: 0;">
                        <a class="action-link edit-action" onclick="edit()"><span class="glyphicon glyphicon-pencil"></span></a>
                        <a class="action-link trash-action" onclick="trash()"><span class="glyphicon glyphicon-trash"></span></a>
                    </td>
                </tr>
                <!-- End forEach -->
            </table>
        </div>
    </div>
</div>
