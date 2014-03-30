$(document).ready(function() {

    /*
    Handles the input validation message for partner name
     */
    // Get each input
    var elements = document.getElementsByTagName("INPUT");

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
});