<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">

    email: ${user.email}
    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li class="active">Contacts</li>
    </ol>

    <h1 style="margin-bottom:20px"> All Contacts </h1>

    <nav class="navbar navbar-default">
        <span class="navbar-jump">Jump to:</span>
        <div class="btn-group jump-dropdown">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                Select Contact
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="contact" items="${contacts}">
                <li>
                    <a href="/contact/" onclick="location.href=this.href + ${contact.id}; return false;">
                        ${contact.contactFirstName} ${contact.contactLastName}
                    </a>
                </li>
                </c:forEach>
            </ul>
        </div>
        <a href="/contacts/create"><button class="btn btn-primary navbar-btn navbar-right add-button">+ Add New Contact</button></a>
    </nav>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Company</th>
            <th>Role</th>
            <th>Title</th>
            <th>Email</th>
            <th>Phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="contact" items="${contacts}">
        <tr>
            <td>${contact.contactFirstName}</td>
            <td>${contact.contactLastName}</td>
            <td>${contact.partnerId}</td>
            <td>ROLE?</td>
            <td>${contact.contactTitle}</td>
            <td>${contact.contactEmail}</td>
            <td>${contact.contactPhone}</td>
        </tr>
        </c:forEach>
    </table>

    <div>
        <ul class="pagination pull-right">
            <li class="disabled"><a href="#">&laquo;</a></li>
            <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
            <li><a href="#">2 <span class="sr-only">page 2</span></a></li>
            <li><a href="#">3 <span class="sr-only">page 3</span></a></li>
            <li><a href="#">4 <span class="sr-only">page 4</span></a></li>
            <li><a href="#">&raquo;<span class="sr-only">next page</span></a></li>
        </ul>
        <div style="clear:both;"></div>
    </div>
</div>