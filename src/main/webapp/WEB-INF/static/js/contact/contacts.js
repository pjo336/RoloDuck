$(document).ready(function(){
    $('.selectpicker').selectpicker();
});

function star() {
    alert("star action");
}

function edit() {
    alert("edit action");
}

function trash(id) {
    var data = {contactId : id};
    ajaxCall("/deleteContact", data);
    location.reload();

}

/**
 * An Ajax post call used to alter html elements on the page to a username fetched from the server
 * @param url The url the request is routed to
 * @param dataToServer Contains any information you want to send to the server side
 */
function ajaxCall(url, dataToServer, callback) {
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: dataToServer,
        success: function(dataToServer){
            if(dataToServer.isValid) {
                // Data is processed in controller, do nothing
                return true;
            } else {
                alert('There was a problem updating the data.');
                return false;
            }
        }
    });
}