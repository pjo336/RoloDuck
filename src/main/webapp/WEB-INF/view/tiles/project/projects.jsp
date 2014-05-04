<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li class="active">Projects</li>
    </ol>

    <h1 style="margin-bottom:20px"> All Projects </h1>

    <nav class="navbar navbar-default">
        <span class="navbar-jump">Jump to:</span>
        <div class="btn-group jump-dropdown">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                Select Projects
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="project" items="${projects}">
                <li>
                    <a href="/projects/${project.id}">
                        ${project.projectName}
                    </a>
                </li>
                </c:forEach>
            </ul>
        </div>
        <a href="/projects/create"><button class="btn btn-primary navbar-btn navbar-right add-button"><span class="glyphicon glyphicon-plus"></span> Add New Project</button></a>
    </nav>

    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Project Name</th>
            <th>Partners</th>
            <th>Contacts</th>
            <th>Created <b class="caret"></b></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="project" items="${projects}">
        <tr id="projectrow-${project.id}">
            <td>
                <a href="/projects/${project.id}">
                    ${project.projectName}
                </a>
            </td>
            <td><a href="#">4 Partners</a></td>
            <td><a href="#">10 Contacts</a></td>
            <td>1 day ago</td>
            <td width="55px" style="padding-right: 0;">
                <a class="action-link edit-action" href="/projects/edit=${project.id}"><span class="glyphicon glyphicon-pencil"></span></a>
                <a class="action-link trash-action" onclick="trash()"><span class="glyphicon glyphicon-trash"></span></a>
            </td>
        </tr>
        </c:forEach>
    </table>
    <%--<div>--%>
        <%--<ul class="pagination pull-right">--%>
            <%--<li class="disabled"><a href="#">&laquo;</a></li>--%>
            <%--<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>--%>
            <%--<li><a href="#">2 <span class="sr-only">page 2</span></a></li>--%>
            <%--<li><a href="#">3 <span class="sr-only">page 3</span></a></li>--%>
            <%--<li><a href="#">4 <span class="sr-only">page 4</span></a></li>--%>
            <%--<li><a href="#">&raquo;<span class="sr-only">next page</span></a></li>--%>
        <%--</ul>--%>
        <%--<div style="clear:both;"></div>--%>
    <%--</div>--%>
</div>