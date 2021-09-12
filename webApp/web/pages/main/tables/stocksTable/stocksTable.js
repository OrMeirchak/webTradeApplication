var GET_STOCKS_LIST_URL = buildUrlWithContextPath("stocksList");
var stocksListVersion = 0;
var refreshRate = 2000; //milli seconds

function ajaxStocksListContent() {
    $.ajax({
        url: GET_STOCKS_LIST_URL,
        data: "stockslistversion=" + stocksListVersion,
        dataType: 'json',
        success: function(data) {
            /*
             data will arrive in the next form:
             {
                "entries": [
                    {
                        "name":"google",
                        "symbol":"GOGL"
                        "gate":"290"
                        "turnover":"234983"
                    },
                ],
                "version":1
             }
             */
            console.log("Server stocks list version: " + data.version + ", Current stocks list version: " + stocksListVersion);
            if (data.version !== stocksListVersion) {
                stocksListVersion = data.version;
                appendToStocksTable(data.entries);
            }
            triggerAjaxStocksListContent();
        },
        error: function(error) {
            triggerAjaxStocksListContent();
        }
    });
}

function appendToStocksTable(entries) {
//    $("#chatarea").children(".success").removeClass("success");

    // add the relevant entries
    $.each(entries || [], appendStockEntry);
}

function appendStockEntry(index, entry){
    var entryElement = createStockRowEntry(entry);
    $("#stockslisttable").append(entryElement).append("</tr>");
}

function createStockRowEntry (entry){
    var stockButton= createStockButton(entry.symbol);

    return $("<tr class=\"ult\">").append("<td>"+entry.name+ "</td>" + "<td>"+stockButton+ "</td>"
    +"<td>"+entry.gate+ "</td>"+"<td>"+entry.turnover+ "</td>");
}

function createStockButton (stockSymbol){
var res = "<form id="+'"'+"specificStock"+'"'+" style="+'"'+"text-align: center;"+'"'+" action="+'"'+"getSpecificStockPage"+'"'+" method="+'"'+"GET"+'"'+
       "><input type="+'"'+"submit"+'"'+"id="+'"'+"selectStockButton"+'"'+" name="+'"'+"stocksymbol"+'"'+" value="+'"'+stockSymbol+'"'+" />"+
    "</form>";

    return res;
}

function triggerAjaxStocksListContent() {
    setTimeout(ajaxStocksListContent, refreshRate);
}

$(function() {
    //The stocks list content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxStocksListContent();
});