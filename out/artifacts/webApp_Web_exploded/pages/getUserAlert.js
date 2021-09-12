var GET_USER_ALERT_URL = buildUrlWithContextPath("getUserAlert");
var refreshRate = 2000; //milli seconds

function ajaxUserAlertContent() {
    $.ajax({
       type: "GET",
        url: GET_USER_ALERT_URL,
        success: function(msg) {
           alert(msg)
            triggerAjaxUserAlertContent();
        },
        error: function(error) {
            triggerAjaxUserAlertContent();
        }
    });
}

function triggerAjaxUserAlertContent() {
    setTimeout(ajaxUserAlertContent, refreshRate);
}

$(function() {
     //The user alert content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxUserAlertContent();
});