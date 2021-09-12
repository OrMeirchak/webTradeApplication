var GET_TRADER_TRAFFICS_LIST_URL = buildUrlWithContextPath("TraderTrafficsList");
var traderTrafficsListVersion = 0;
var refreshRate = 2000; //milli seconds

function ajaxTraderTrafficsListContent() {
    $.ajax({
        url: GET_TRADER_TRAFFICS_LIST_URL,
        data: "tradertrafficslistversion=" + traderTrafficsListVersion,
        dataType: 'json',
        success: function(data) {

/*    {"entries":
    [{"description":"Deposit",
    "timestamp":"2021-07-08 14:27:51.752",
    "change":"80",
    "Buyer":"0",
    "Seller":"80"}],
    "version":1}*/

            console.log("Server trader traffics list version: " + data.version + ",Current trader traffics list version: " + traderTrafficsListVersion);
            if (data.version !== traderTrafficsListVersion) {
                traderTrafficsListVersion = data.version;
                appendToTrafficsTable(data.entries);
            }
            triggerAjaxTraderTrafficsContent();
        },
        error: function(error) {
            triggerAjaxTraderTrafficsContent();
        }
    });
}

function appendToTrafficsTable(entries) {
//    $("#chatarea").children(".success").removeClass("success");

    // add the relevant entries
    $.each(entries || [], appendTrafficEntry);
}

function appendTrafficEntry(index, entry){
    var entryElement = createTrafficRowEntry(entry);
    $("#tradertrafficslisttable").append(entryElement).append("</tr>");
}

function createTrafficRowEntry (entry){
    return $("<tr class=\"ult\">").append("<td>"+ entry.description + "</td>" + "<td>" + entry.timestamp + "</td>"
    + "<td>" + entry.change + "</td>"+"<td>" + entry.budgetbefore + "</td>"+"<td>" + entry.budgetafter + "</td>");
}

function triggerAjaxTraderTrafficsContent() {
    setTimeout(ajaxTraderTrafficsListContent, refreshRate);
}

$(function() {
    //The stocks list content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxTraderTrafficsContent();
});