  var INDEX_URL = buildUrlWithContextPath("index.html");
  var GET_USER_DETAILS_FROM_SERVLET_URL = buildUrlWithContextPath("getUserDetails");

    function getUserDetailsFromSession() {
    $.ajax({
        url: GET_USER_DETAILS_FROM_SERVLET_URL,
      //  timeout: 2000,

      //the data return :

     /* {"username":"shon",
     "usertype":"trader",
     "userbalance":20}*/
         error: function (jqXHR, exception) {
         if(jqXHR.status==409){
                     window.location.replace(INDEX_URL);
                     }
                     triggerAjaxUserDetailsContent();
                    },
                    success: function(data) {
                     updateUserNameFromSessison(data.username);
                     updateUserType(data.usertype);
                     if (data.usertype=="trader"){
                     updateUserBalance(data.userbalance)
                     }
                    triggerAjaxUserDetailsContent();
                    }
                });
               };


function updateUserNameFromSessison(userName){
$("#username").empty();
$("#username").append("<p>Hello "+userName+"</p>");
}

function updateUserType(userType){
$("#userype").empty();
$("#userype").append("<p><strong><span style="+'"'+"text-decoration: underline;"+'"'+">User type </span></strong> : "+userType+"</p>");
}


function updateUserBalance(userBalance){
$("#balance").empty();
$("#balance").append("<p><strong><span style="+'"'+"text-decoration: underline;"+'"'+">Budget : </span></strong> : "+userBalance+"</p>");
}


               function triggerAjaxUserDetailsContent() {
    setTimeout(getUserDetailsFromSession, refreshRate);
}

$(function() {

    //The user list content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxUserDetailsContent();

    $("#loadfile").load("loadFile/loadFile.html");
});