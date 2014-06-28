/**
 * Created by pjo336 on 6/27/14.
 */

/**
 * An Ajax post call used to alter html elements on the page to a username fetched from the server
 * @param url The url the request is routed to
 * @param dataToServer Contains any information you want to send to the server side
 * @param element the element to be hidden after removal is successful
 */
function ajaxDeletion(url, dataToServer, element) {
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: dataToServer,
        success: function(dataToServer){
            if(dataToServer.isValid) {
                // Data is processed in controller, hide the datas included element
                element.hide(200);
            } else {
                alert('There was a problem updating the data.');
            }
        }
    });
}