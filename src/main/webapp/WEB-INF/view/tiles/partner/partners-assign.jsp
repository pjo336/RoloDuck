<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">

    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li><a href="/partners">Partners</a></li>
        <li><a href="/partners/${partner.id}">${partner.partnerName}</a></li>
        <li class="active">Assign</li>
    </ol>
    <h1>Assign to Project</h1>
    <div style="width:700px">
        <form:form action="/partners/assign=${partner.id}" modelAttribute="project" method="POST" role="form">
            <div class="form-group" id="projectSelect">
                <label for="projectId">Project</label><br />
                <select class="selectpicker" name="id" id="projectId" width="400px">
                    <c:forEach var="project" items="${projects}">
                        <option value="${project.id}">
                                ${project.projectName}
                        </option>
                    </c:forEach>
                </select><br />
                <a id="projectFormOpenLink">Add a new project</a>
            </div>
            <div id="projectForm" style="display: hidden; padding: 5px 10px; border-radius: 4px; background: #f8f8f8; border: 1px solid #CCCCCC; margin-bottom: 10px;">
                <a id="projectFormCloseLink" style="float:right; position: relative; top: 10px; right: 5px;"><span class="glyphicon glyphicon-remove" style="font-size: 12px;"></span> Cancel</a>
                <h4>Create a new Project</h4>
                <div class="form-group" >
                    <label for="projectName">Project Name</label>
                    <input type="text" class="form-control" name="projectName" id="projectName"
                           placeholder="Project Name">
                </div>
                <div class="form-group">
                    <label for="projectDescription">Project Description (optional)</label>
                    <textarea class="form-control" name="projectDescription" id="projectDescription"
                              placeholder="Add a description to explain more about the project" rows="3"></textarea>
                </div>
            </div>
            <!-- only enabled after the user enters a Partner Name -->
            <button type="submit" class="btn btn-primary btn-lg signin-button">Submit</button>
        </form:form>
    </div>
</div>