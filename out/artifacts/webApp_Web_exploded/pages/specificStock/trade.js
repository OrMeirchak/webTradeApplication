var GET_DETAILS_FROM_SERVER_URL = buildUrlWithContextPath("GetDetailsForTrade");
var MAKE_TRADE_URL = buildUrlWithContextPath("makeTrade");

var stockSymbol;
var userName;
var userType;
var theAmountTheTraderHoldInStock;
var traderBalance;
var stockSaleGate;
var stockBuyGate;

//properties
var isSellOrdinanceSelected;
var isBuyOrdinanceSelected;
var isLmtOrdinanceSelected;
var isFokOrdinanceSelected;
var isIocOrdinanceSelected;
var isMtkOrdinanceSelected;


$(function() { // onload...do
    //add a function to the submit event
    $("#maketradeform").submit(function() {
        $.ajax({
            data: $(this).serialize(),
            url: MAKE_TRADE_URL,
            //timeout: 2000,
            success: function(successMsg) {
                alert(successMsg);
                clearForm();

            },
            error: function(errorMsg) {
                   alert(errorMsg);
                    }
        });
        return false;
    });
});

function clearForm(){
document.getElementById("maketradeform").reset();
}

function loadTradeForm(){
if (theAmountTheTraderHoldInStock<=0){
document.getElementById("sellordinance").disabled = true;
}

//initilize properties
isSellOrdinanceSelected=false;
isBuyOrdinanceSelected=false;
isLmtOrdinanceSelected=false;
isFokOrdinanceSelected=false;
isIocOrdinanceSelected=false;
isMtkOrdinanceSelected=false;
document.getElementById("submitbutton").disabled=true;
}

function sellOrdinanceRadiobuttonClicked(){
isBuyOrdinanceSelected=false;
isSellOrdinanceSelected=true;

$(".amount").empty();
$(".amount").append("<label>Amount:</label><input type="+'"'+"number"+
'"'+" name="+'"'+"stockamount"+'"'+ "min="+'"'+'1'+'"'+" max="+theAmountTheTraderHoldInStock+" required>");

if(isMtkOrdinanceSelected==true){
document.getElementById("limittextfield").value = stockSaleGate;
}
}

function buyOrdinanceRadiobuttonClicked(){
isBuyOrdinanceSelected=true;
isSellOrdinanceSelected=false;

$(".amount").empty();
$(".amount").append("<label>Amount:</label><input type="+'"'+"number"+
'"'+" name="+'"'+"stockamount"+'"'+ "min="+'"'+'1'+'"'+" required>");

if(isMtkOrdinanceSelected==true){
document.getElementById("limittextfield").value = stockBuyGate;
}
}

function lmtRadiobuttonClicked(){
isLmtOrdinanceSelected=true;
isFokOrdinanceSelected=false;
isIocOrdinanceSelected=false;
isMtkOrdinanceSelected=false;

document.getElementById("limittextfield").disabled=false;
document.getElementById("submitbutton").disabled=false;
}

function fokRadiobuttonClicked(){
isLmtOrdinanceSelected=false;
isFokOrdinanceSelected=true;
isIocOrdinanceSelected=false;
isMtkOrdinanceSelected=false;

document.getElementById("limittextfield").disabled=false;
document.getElementById("submitbutton").disabled=false;
}

function iocRadiobuttonClicked(){
isLmtOrdinanceSelected=false;
isFokOrdinanceSelected=false;
isIocOrdinanceSelected=true;
isMtkOrdinanceSelected=false;

document.getElementById("limittextfield").disabled=false;
document.getElementById("submitbutton").disabled=false;
}

function mktRadiobuttonClicked(){
isLmtOrdinanceSelected=false;
isFokOrdinanceSelected=false;
isIocOrdinanceSelected=false;
isMtkOrdinanceSelected=true;

var stockGate;
if(isSellOrdinanceSelected==true){
document.getElementById("limittextfield").value=stockSaleGate;
}

else if(isBuyOrdinanceSelected==true){
document.getElementById("limittextfield").value=stockBuyGate;
}

document.getElementById("limittextfield").disabled=true;
document.getElementById("submitbutton").disabled=false;
}

function setHoldingInformation(){
$("#holdings").empty();
$("#holdings").append("<p><strong><span style="+'"'+"text-decoration: underline;"+'"'+">Your holdings :  </span></strong> : "+theAmountTheTraderHoldInStock+"</p>");
}

function getDetailsFromServer() {
    $.ajax({
        url: GET_DETAILS_FROM_SERVER_URL,
        dataType: 'json',

            /*
             data will arrive in the next form:
               {"userName":"or","userType":"trader","stockSymbol":"GOOG","theAmountTheTraderHoldInStock":10,"traderBalance":10}
             */
               success: function(data) {
           stockSymbol=data.stockSymbol;
           userName=data.userName;
           userType=data.userType;
           theAmountTheTraderHoldInStock=data.theAmountTheTraderHoldInStock;
           setHoldingInformation();
           traderBalance=data.traderBalance;
           stockSaleGate=data.stockSaleGate;
           stockBuyGate=data.stockBuyGate;
           loadTradeForm();
        },
        error: function(error) {
        }
    });
}