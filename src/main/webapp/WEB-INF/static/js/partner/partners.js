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
    $("#projectFormLink").click(function(){
        $("#projectId").val(0);
        $("#projectId").hide(200);
        $("#projectForm").show(200);
    });
});


function star() {
    alert("star action");
}

function edit() {
    alert("edit action");
}

function trash() {
    alert("trash action");
}