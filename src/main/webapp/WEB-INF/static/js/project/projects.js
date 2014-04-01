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
        ajaxCall('/deleteProjects', data, null);
    });
});

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