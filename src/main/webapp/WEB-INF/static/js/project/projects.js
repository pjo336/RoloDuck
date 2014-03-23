/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/22/14
 * RoloDuck
 */

$(document).ready(function(){

    $('.delete-project').click(function(){
        var id = this.getAttribute('id');
        var tag = $('#projectrow-' + id);
        tag.hide();
    })
});

/**
 * An Ajax post call used to alter html elements on the page to a username fetched from the server
 * @param url The url the request is routed to
 * @param dataToServer Contains any information you want to send to the server side
 * @param dataToChange The tag you want to change the html of to a username
 */
function ajaxCall(url, dataToServer, dataToChange) {
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: dataToServer,
        success: function(dataToServer){
            if(dataToServer.isValid) {
                dataToChange.html(dataToServer.username);
            } else {
                alert('There was a problem updating the data.');
            }
        }
    });
}