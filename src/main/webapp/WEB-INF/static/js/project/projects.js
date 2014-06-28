/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/22/14
 * RoloDuck
 */

/**
 * Delete a project when the trash button is clicked on a particular row.
 * @param id passed from the view, the id coorelating to the row to be removed.
 */
function trash(id) {
    var url = '/remove';
    var element = $('#projectRow' + id);
    var data = {deleted : id};

    // TODO make the element only hide if the delete is successful
    ajaxDeletion(url, data, element);
}