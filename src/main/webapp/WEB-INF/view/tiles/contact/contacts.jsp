<div class="container">
    {% include 'common/flash_messages.html' %}

    <ol class="breadcrumb">
        <span> <strong>{{ user.company.companyName }}</strong> <span class="spacer40"></span></span>
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
                {% for each_contact in contacts %}
                <li>
                    <a href="/project/" onclick="location.href=this.href+ '{{each_contact._id}}';return false;">
                        {{each_contact.contact_firstName}}
                    </a>
                </li>
                {% endfor %}
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
        {% for each_contact in contacts %}
        <tr>
            <td>{{each_contact.contact_firstName}}</td>
            <td>{{each_contact.contact_lastName}}</td>
            <td>{WIP}</td>
            <td>{{each_contact.contact_role}}</td>
            <td>{{each_contact.contact_title}}</td>
            <td>{{each_contact.contact_email}}</td>
            <td>{{each_contact.contact_phone}}</td>
        </tr>
        {% endfor %}
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