/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/22/14
 * RoloDuck
 */

$(document).ready(function(){

    var saveButton = $('#saveProjectChanges');
    var deletedProjects = [];

    saveButton.hide();

    $('.delete-project').click(function() {
        var id = this.getAttribute('id');
        var tag = $('#projectrow-' + id);
        tag.hide(100);
        saveButton.show(100);
        deletedProjects.push(id);
    });

    saveButton.click(function() {
        var deletedProjString = "";
        while(deletedProjects.length > 0) {
            deletedProjString += deletedProjects.pop();
            if(deletedProjects.length != 0) {
                deletedProjString += ",";
            }
        }
        var data = {deleted : deletedProjString};
        ajaxCall('/deleteProjects', data);
    });
});

function trash(objectType, id) {
    if (objectType == 'partner') {
        data = {deleted : id};
        var url = '/partners/remove';
        var element = $('#partnerpanel' + id);
    }
    if (objectType == 'contact') {
        var data = {contactId : id};
        var url = '/deleteContact';
        var element = $('#contactRow' + id);
    }
    if (objectType == 'project') {
        var data = {deleted : id};
        var url = '/removeAssociation';
        var element = $('#projectRow' + id);
    }
    // TODO make the element only hide if the delete is successful
    ajaxCall(url, data);
    element.hide(200);
}

/**
 * An Ajax post call used to alter html elements on the page to a username fetched from the server
 * @param url The url the request is routed to
 * @param dataToServer Contains any information you want to send to the server side
 */
function ajaxCall(url, dataToServer) {
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: dataToServer,
        success: function(dataToServer){
            if(dataToServer.isValid) {
                // Data is processed in controller, do nothing
            } else {
                alert('There was a problem updating the data.');
            }
        }
    });
}