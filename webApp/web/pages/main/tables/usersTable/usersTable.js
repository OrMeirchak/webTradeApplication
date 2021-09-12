var GET_USER_LIST_URL = buildUrlWithContextPath("userList");
var userListVersion = 0;
var refreshRate = 2000; //milli seconds

function ajaxUserListContent() {
    $.ajax({
        url: GET_USER_LIST_URL,
        data: "userlistversion=" + userListVersion,
        dataType: 'json',
        success: function(data) {
            /*
             data will arrive in the next form:
             {
                "entries": [
                    {
                        "name":"or",
                        "type":"Admin"
                    },
                    {
                       "name":"imobile",
                       "type":"Trader"
                    }
                ],
                "version":1
             }
             */
            console.log("Server user list version: " + data.version + ", Current user list version: " + userListVersion);
            if (data.version !== userListVersion) {
                userListVersion = data.version;
                appendToUserListTable(data.entries);
            }
            triggerAjaxUserListContent();
        },
        error: function(error) {
            triggerAjaxUserListContent();
        }
    });
}

function appendToUserListTable(entries) {
//    $("#chatarea").children(".success").removeClass("success");

    // add the relevant entries
    $.each(entries || [], appendUserEntry);
}

function appendUserEntry(index, entry){
    var entryElement = createUserRowEntry(entry);
    $("#userlisttable").append(entryElement).append("</tr>");
}

function createUserRowEntry (entry){
    return $("<tr class=\"ult\">").append("<td>"+entry.name+ "</td>" + "<td>"+entry.type+ "</td>");
}

function triggerAjaxUserListContent() {
    setTimeout(ajaxUserListContent, refreshRate);
}

$(function() {

    //The user list content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxUserListContent();
});