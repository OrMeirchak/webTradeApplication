var GET_STOCK_INFORMATION = buildUrlWithContextPath("getStockInformation");
var MAIN_URL = buildUrlWithContextPath("pages/main/main.html");

var stockName;
var stocksymbol;
var gate;
var turnOver;

function ajaxStockInformationContent() { // onload...do
    //add a function to the submit event
        $.ajax({
            url: GET_STOCK_INFORMATION,
            //timeout: 2000,
            //data will arrive in the next form:{name: "google", symbol: "GOOG", gate: "290", turnover: "0"}
            success: function(data) {

updateStockInformation(data);
triggerAjaxStockInformationContent();
            },
            error: function() {
      window.location.replace(MAIN_URL);
      triggerAjaxStockInformationContent();
                    }
        });
        return false;
};

function updateStockInformation(data){
updateStockName(data.name);
updateStockSymbol(data.symbol);
updateStockGate(data.gate);
updateStockTurnOver(data.turnover);
}

function loadTradeTable(){
$("#dealstable").load("dealsTable/dealsTable.html");
}

function updateStockName(stockName){
$("#stockname").empty();
$("#stockname").append("<p><strong><span style="+'"'+"text-decoration: underline;"+'"'+">Company name </span></strong> : "+stockName+"</p>");
}

function updateStockSymbol(symbol){
$("#stocksymbol").empty();
$("#stocksymbol").append("<p><strong><span style="+'"'+"text-decoration: underline;"+'"'+">SYMBOL</span></strong> : "+symbol+"</p>");
}

function updateStockGate(gate){
$("#gate").empty();
$("#gate").append("<p><strong><span style="+'"'+"text-decoration: underline;"+'"'+">Gate</span></strong> : "+gate+"</p>");
}

function updateStockTurnOver(turnOver){
$("#turnover").empty();
$("#turnover").append("<p><strong><span style="+'"'+"text-decoration: underline;"+'"'+">Total turn over</span></strong> : "+turnOver+"</p>");
}

function triggerAjaxStockInformationContent() {
    setTimeout(ajaxStockInformationContent, refreshRate);
}

$(function() {
    //The user list content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    loadTradeTable();
    triggerAjaxStockInformationContent();
});