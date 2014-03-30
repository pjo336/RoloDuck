<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">

    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li><a href="/partners">Partners</a></li>
        <li class="active">Create</li>
    </ol>
    <h1>Add a Partner</h1>
    <div style="width:700px">
        <form:form action="/partners/create" modelAttribute="partner" method="POST" role="form">
            <div class="form-group" >
                <label for="partnername">Partner Name</label>
                <input type="text" class="form-control" name="partnerName" id="partnerName"
                       placeholder="Partner Name" required>
                <span class="help-block">Choose a descriptive name for the partner.</span>
            </div>
            <div class="form-group">
                <label for="partnerdescription">Partner Description (optional)</label>
                <textarea class="form-control" name="partnerDescription" id="partnerDescription"
                          placeholder="Add a description to explain more about the partner" rows="3"></textarea>
            </div>
            <!-- only enabled after the user enters a Partner Name -->
            <button type="submit" class="btn btn-primary btn-lg signin-button">Submit</button>
        </form:form>
    </div>
</div>