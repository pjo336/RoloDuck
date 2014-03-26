<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
    {% include 'common/flash_messages.html' %}
    <ol class="breadcrumb">
        <span> <strong>{{ user.company.companyName }}</strong> <span class="spacer40"></span></span>
        <li><a href="/contacts">Contacts</a></li>
        <li class="active">Create</li>
    </ol>
    <h1>Add a Contact</h1>
    <div style="width:700px">
        <form:form action="/contacts/create" modelAttribute="contact" method="POST" role="form">
            <div class="form-group" >
                <label for="contactfirstname">First Name</label>
                <input type="text" class="form-control" name="contactFirstName" id="contactFirstName"
                       placeholder="Contact First Name">
            </div>
            <div class="form-group" >
                <label for="contactlastname">Last Name</label>
                <input type="text" class="form-control" name="contactLastName" id="contactLastName"
                       placeholder="Contact Last Name">
            </div>
            <%--<div class="form-group">--%>
                <%--<label for="contactcompany">Company</label><br />--%>
                <%--<select class="selectpicker" name="contactcompany" id="contactcompany">--%>
                    <%--<option value="1">Option 1</option>--%>
                    <%--<option value="2">Option 1</option>--%>
                    <%--<option value="3">Option 1</option>--%>
                <%--</select>--%>
            <%--</div>--%>

            <%--<div class="form-group" >--%>
                <%--<label for="contactrole">Role</label>--%>
                <%--<input type="text" class="form-control" name="contactrole" id="contactrole" placeholder="Contact Role">--%>
                <%--<span class="help-block">Job type - i.e. Sales, Operations, etc.</span>--%>
            <%--</div>--%>

            <div class="form-group" >
                <label for="contacttitle">Title</label>
                <input type="text" class="form-control" name="contactTitle" id="contactTitle"
                       placeholder="Contact Title">
                <span class="help-block">Job title - i.e. Account Manager, Director of Sales, etc.</span>
            </div>
            <div class="form-group" >
                <label for="contactemail">Email</label>
                <input type="text" class="form-control" name="contactEmail" id="contactEmail"
                       placeholder="Contact Email">
            </div>

            <%--<label for="contactphone">Phone</label><br />--%>
            <%--<div class="input-group">--%>
                <%--<div class="input-group-btn">--%>
                    <%--<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" id="contactphone[0].dropdown">--%>
          <%--<span style="padding:0px 5px 0 0;">--%>
            <%--<img src="/static/img/phone_icon.png" style="position:relative; top:-1px;"/>--%>
          <%--</span> Land Line <span class="caret"></span>--%>
                    <%--</button>--%>
                    <%--<ul class="dropdown-menu">--%>
                        <%--<li><a onclick="change('landline', 0)">--%>
            <%--<span style="padding:0px 5px 0 0;">--%>
              <%--<img src="/static/img/phone_icon.png" style="position:relative; top:-1px;" />--%>
            <%--</span> Land line</a>--%>
                        <%--</li>--%>
                        <%--<li><a onclick="change('cell', 0)">--%>
            <%--<span style="padding:0 9px 0 3px;">--%>
              <%--<img src="/static/img/cell_icon.png" style="position:relative; top:-1px;" />--%>
            <%--</span> Cell Phone</a></li>--%>
                        <%--<li><a onclick="change('fax', 0)">--%>
            <%--<span style="padding:0 5px 0 0;">--%>
              <%--<img src="/static/img/fax_icon.png" style="position:relative; top:-1px;" />--%>
            <%--</span> Fax</a></li>--%>
                        <%--<li class="divider"></li>--%>
                        <%--<li><a onclick="change('other', 0)">Other</a></li>--%>
                    <%--</ul>--%>
                <%--</div><!-- /btn-group -->--%>
                <%--<input type="text" class="form-control" name="contactphone[0].number" id="contactphone[0].number" placeholder="(XXX) XXX-XXX xXXX">--%>
            <%--</div><!-- /input-group -->--%>
            <%--<input type="text" class="form-control" name="contactphone[0].type" id="contactphone[0].type" placeholder="landline">--%>
            <%--<div id="extra"></div>--%>
            <%--<a onclick="addnumber()" class="pull-right" style="margin-top:5px; cursor:pointer;">+ Add another number</a>--%>
            <br /><br />
            <!-- only enabled after the user enters a Contact Name -->
            <div class="form-group" >
                <button type="submit" class="btn btn-primary btn-lg signin-button">Submit</button>
            </div>

        </form:form>
    </div>
</div>