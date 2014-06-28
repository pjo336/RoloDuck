$(document).ready(function() {

    /*
    Handles the input validation message for partner name
     */
    // Get each input
    var elements = document.getElementsByTagName("INPUT");

    $('.well-button').tooltip();

    $('.selectpicker').selectpicker();

    for (var i = 0; i < elements.length; i++) {
        // Only occurs on "required" inputs
        elements[i].oninvalid = function(e) {
            if (!e.target.validity.valid) {
                switch(e.target.id){
                    // Set the message for Partner Name validation
                    case 'partnerName' :
                        e.target.setCustomValidity("You must enter a Partner Name");
                        break;
                    // Can implement more here
                    default : e.target.setCustomValidity("This field cannot be blank");
                        break;
                }
            }
        };
    }

    $("#projectForm").hide();


    var selectPicker = $(".selectpicker");
    $("#projectFormOpenLink").click(function(){
        selectPicker.prop('disabled', true);
        selectPicker.selectpicker('refresh');
        $("#projectSelect").hide(200);
        $("#projectForm").show(200);
    });

    $("#projectFormCloseLink").click(function(){
        $("#projectSelect").show(200);
        selectPicker.prop('disabled', false);
        selectPicker.selectpicker('refresh');
        $("#projectForm").hide(200);
    });
});


function star() {
    alert("star action");
}

function edit() {
    alert("edit action");
}

function trash(objectType, id) {
    var data = null;
    var url = '';
    var element = '';
    if (objectType == 'partner') {
        data = {deleted : id};
        url = '/partners/remove';
        element = $('#partnerpanel' + id);
    }
    if (objectType == 'contact') {
        data = {contactId : id};
        url = '/deleteContact';
        element = $('#contactRow' + id);
    }
    // TODO make the element only hide if the delete is successful
    ajaxCall(url, data);
    element.hide(200);
}

function removeAssociation(projectId, partnerId) {
    var data = {projectId : projectId, partnerId: partnerId};
    var url = '/partners/unassign';
    var element = $('#projectBubble' + projectId + partnerId);
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
        success: function(){
            if(dataToServer.isValid) {
                console.log('isValid');
            } else {
                console.log('reloading');
                window.location.reload();
            }
        }
    });
}