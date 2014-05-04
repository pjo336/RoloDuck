<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">

<c:choose>
<c:when test="${empty contact}">
    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li><a href="/contacts">Contacts</a></li>
        <li class="active">Create</li>
    </ol>

            <h1>Add a Contact</h1>
            <div style="width:700px">
                <form:form action="/contacts/create" modelAttribute="converter" method="POST" role="form">
                    <div class="form-group" >
                        <label for="contactFirstName">First Name</label>
                        <input type="text" class="form-control" name="contactFirstName" id="contactFirstName"
                               placeholder="Contact First Name">
                    </div>
                    <div class="form-group" >
                        <label for="contactLastName">Last Name</label>
                        <input type="text" class="form-control" name="contactLastName" id="contactLastName"
                               placeholder="Contact Last Name">
                    </div>
                    <div class="form-group" id="partnerSelect">
                        <label for="partnerId">Select a Partner</label><br />
                        <select class="selectpicker" name="partnerId" id="partnerId">
                            <c:forEach var="partner" items="${partners}">
                                <option value="${partner.id}"
                                <c:if test="${partner.id == partnerSelected}">
                                    selected="selected"
                                </c:if>
                                >
                                    ${partner.partnerName}
                                </option>
                            </c:forEach>
                        </select><br />
                        or <a id="partnerFormOpenLink">add a new partner</a>
                    </div>
                    <div id="partnerForm" style="display: hidden; padding: 5px 10px; border-radius: 4px; background: #f8f8f8; border: 1px solid #CCCCCC; margin-bottom: 10px;">
                        <a id="partnerFormCloseLink" style="float:right; position: relative; top: 10px; right: 5px;"><span class="glyphicon glyphicon-remove" style="font-size: 12px;"></span> Cancel</a>
                        <h4>Create a new Partner</h4>
                        <div class="form-group" >
                            <label for="partnerName">Partner Name</label>
                            <input type="text" class="form-control" name="partnerName" id="partnername"
                                   placeholder="Partner Name">
                        </div>
                        <div class="form-group">
                            <label for="partnerDescription">Partner Description (optional)</label>
                            <textarea class="form-control" name="partnerDescription" id="partnerDescription"
                                      placeholder="Add a description to explain more about the partner" rows="3"></textarea>
                        </div>
                    </div>

                    <div class="form-group" >
                        <label for="contactTitle">Job Title</label>
                        <input type="text" class="form-control" name="contactTitle" id="contactTitle"
                               placeholder="Contact Title">
                        <span class="help-block">i.e. Account Manager, Director of Sales, etc.</span>
                    </div>
                    <div class="form-group" >
                        <label for="contactEmail">Email</label>
                        <input type="text" class="form-control" name="contactEmail" id="contactEmail"
                               placeholder="Contact Email">
                    </div>
                    <div class="form-group" >
                        <label for="contactPhone">Phone Number</label>
                        <input type="text" class="form-control" name="contactPhone" id="contactPhone"
                               placeholder="Contact Phone Number">
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
</c:when>
<c:otherwise>
            <ol class="breadcrumb">
                <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
                <li><a href="/contacts">Contacts</a></li>
                <li class="active">Edit</li>
            </ol>
            <h1>Edit Contact</h1>
            <div style="width:700px">
                <form:form action="/contacts/edit" modelAttribute="converter" method="POST" role="form">
                    <input type="hidden" name="contactId" value="${contact.id}">

                    <div class="form-group" >
                        <label for="contactFirstName">First Name</label>
                        <input type="text" class="form-control" name="contactFirstName"
                               value="${contact.contactFirstName}">
                    </div>
                    <div class="form-group" >
                        <label for="contactLastName">Last Name</label>
                        <input type="text" class="form-control" name="contactLastName"
                               value="${contact.contactLastName}">
                    </div>
                    <div class="form-group" id="partnerSelect">
                        <label for="partnerId">Select a Partner</label><br />
                        <select class="selectpicker" name="partnerId">
                            <c:forEach var="partner" items="${partners}">
                                <option value="${partner.id}"
                                        <c:if test="${partner.id == contact.partnerId}">
                                            selected="selected"
                                        </c:if>
                                        >
                                        ${partner.partnerName}
                                </option>
                            </c:forEach>
                        </select><br />
                        or <a id="partnerFormOpenLink">add a new partner</a>
                    </div>
                    <div id="partnerForm" style="display: hidden; padding: 5px 10px; border-radius: 4px; background: #f8f8f8; border: 1px solid #CCCCCC; margin-bottom: 10px;">
                        <a id="partnerFormCloseLink" style="float:right; position: relative; top: 10px; right: 5px;"><span class="glyphicon glyphicon-remove" style="font-size: 12px;"></span> Cancel</a>
                        <h4>Create a new Partner</h4>
                        <div class="form-group" >
                            <label for="partnerName">Partner Name</label>
                            <input type="text" class="form-control" name="partnerName"
                                   placeholder="Partner Name">
                        </div>
                        <div class="form-group">
                            <label for="partnerDescription">Partner Description (optional)</label>
                            <textarea class="form-control" name="partnerDescription"
                                      placeholder="Add a description to explain more about the partner" rows="3"></textarea>
                        </div>
                    </div>

                    <div class="form-group" >
                        <label for="contactTitle">Job Title</label>
                        <input type="text" class="form-control" name="contactTitle"
                               value="${contact.contactTitle}">
                        <span class="help-block">i.e. Account Manager, Director of Sales, etc.</span>
                    </div>
                    <div class="form-group" >
                        <label for="contactEmail">Email</label>
                        <input type="text" class="form-control" name="contactEmail"
                               value="${contact.contactEmail}">
                    </div>
                    <div class="form-group" >
                        <label for="contactPhone">Phone Number</label>
                        <input type="text" class="form-control" name="contactPhone"
                               value="${contact.contactPhone}">
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
        </c:otherwise>
    </c:choose>
</div>