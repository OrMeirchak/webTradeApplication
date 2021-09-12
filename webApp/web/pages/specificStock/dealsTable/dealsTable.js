var GET_DEALS_LIST_URL = buildUrlWithContextPath("dealsList");
var dealsListVersion = 0;
var refreshRate = 2000; //milli seconds

function ajaxDealsListContent() {
    $.ajax({
        url: GET_DEALS_LIST_URL,
        data: "dealslistversion=" + dealsListVersion,
        dataType: 'json',
        success: function(data) {
            /*
             data will arrive in the next form:

             {
             "entries":[
             {"date":"Wed Jul 14 14:38:03 IDT 2021",
             "amount":"50",
             "price":"290",
             "buyer":"mish",
             "seller":"mish"},
             {"date":"Wed Jul 14 14:38:03 IDT 2021",
             "amount":"50",
             "price":"290",
             "buyer":"mish",
             "seller":"mish"}],
             "version":2}
             */
            console.log("Server deals list version: " + data.version + ", Current deals list version: " + dealsListVersion);
            if (data.version !== dealsListVersion) {
                dealsListVersion = data.version;
                appendToDealsListTable(data.entries);
            }
            triggerAjaxDealsListContent();
        },
        error: function(error) {
            triggerAjaxDealsListContent();
        }
    });
}

function appendToDealsListTable(entries) {
//    $("#chatarea").children(".success").removeClass("success");

    // add the relevant entries
    $.each(entries || [], appendDealEntry);
}

function appendDealEntry(index, entry){
    var entryElement = createDealRowEntry(entry);
    $("#dealslisttable").append(entryElement).append("</tr>");
}

function createDealRowEntry (entry){
    return $("<tr class=\"ult\">").append("<td>"+entry.date+ "</td>" + "<td>"+ entry.amount+ "<td>"+entry.price+
    "</td>"+ "<td>"+entry.buyer+ "</td>"+ "<td>"+entry.seller+ "</td>");
}

function triggerAjaxDealsListContent() {
    setTimeout(ajaxDealsListContent, refreshRate);
}

$(function() {
    //The user list content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxDealsListContent();
});