$(document).ready(function(){
    $('.selectpicker').selectpicker();
});

function star() {
    alert("star action");
}

function edit() {
    alert("edit action");
}

function removeContact(id) {
    var data = {contactId : id};
    var element = $('#contactRow' + id);
    ajaxDeletion("/deleteContact", data, element);
}
