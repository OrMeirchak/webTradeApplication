var INDEX_URL = buildUrlWithContextPath("index.html");
var GET_USER_NAME_FROM_SESSION_URL = buildUrlWithContextPath("getUserDetails");


$(function() { // onload...do
getUserNameFromSession();
});

function updateUserName(userName) {
    //clear all current users
 $("#username").empty();
$( "<p>"+userName+"</p>").appendTo( ".username" );
    };


 //includes
  $(function(){
       $("#mainmenu").load("mainMenu.jsp");

       $("#userdetails").load("userDetails/userDetails.jsp");
  });



