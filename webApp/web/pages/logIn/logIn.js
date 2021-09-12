var LOGIN_URL = buildUrlWithContextPath("logInServlet");



$(function() {
    $.ajax({
        url: LOGIN_URL,
        timeout: 2000,
        success: function(nextPageUrl) {
        console.log(nextPageUrl);
             window.location.replace(nextPageUrl);
        }
    });
    return false;
});
