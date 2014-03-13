<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
    <ol class="breadcrumb">
        <span> <strong>${companyName}</strong> <span class="spacer40"></span></span>
        <li><a href="/projects">Projects</a></li>
        <li class="active">Create</li>
    </ol>

    <form id="projectForm" modelAttribute="project" action="create" method="post" class="form" role="form">
        <div class="form-group" >
            <label for="projectName">Project Name</label>
            <input type="text" class="form-control" name="projectName" id="projectName" placeholder="Project Name">
            <span class="help-block">Choose a descriptive name for the project.</span>
        </div>
        <div class="form-group">
            <label for="projectDescription">Project Description (optional)</label>
            <textarea class="form-control" name="projectDescription" id="projectDescription" placeholder="Add a description to explain more about the project" rows="3"></textarea>
        </div>
        <!-- only enabled after the user enters a Project Name -->
        <button type="submit" class="btn btn-primary btn-lg signin-button">Submit</button>
    </form>
</div>