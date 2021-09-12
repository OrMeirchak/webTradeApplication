<%--
    Document   : index
    Created on : Jan 24, 2012, 6:01:31 AM
    Author     : blecherl
    This is the login JSP for the online chat application
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@page import="utils.*" %>
<%@ page import="constants.Constants" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Online Chat</title>
    <!--        Link the Bootstrap (from twitter) CSS framework in order to use its classes-->
    <!--        Link jQuery JavaScript library in order to use the $ (jQuery) method-->
    <!--        <script src="script/jquery-2.0.3.min.js"></script>-->
    <!--        and\or any other scripts you might need to operate the JSP file behind the scene once it arrives to the client-->
</head>
<body>
<div class="container">
    <% String usernameFromSession = SessionUtils.getUsername(request);%>
    <% String usernameFromParameter = request.getParameter(Constants.USERNAME) != null ? request.getParameter(Constants.USERNAME) : "";%>
    <% if (usernameFromSession == null) {%>

    <p>&nbsp;</p>
    <p style="text-align: right;">&nbsp;</p>
    <p>&nbsp;</p>
    <p style="text-align: center;">Welcome to rizpa trade application</p>
    <p style="text-align: center;"><span style="font-family: Arial;">
    <span style="font-size: 13.3333px; background-color: #efefef;">Please enter user name</span>
</span>
    </p>
    <form id="signUpForm" style="text-align: center;" action="signUp" method="GET">

        <input class="usernametextfield" name="username" type="text" />

        <div>&nbsp;</div>

        <div>
            <input id="tradertype" name="usertype" type="radio" value="trader" checked/>
            <label for="tradertype">Trader</label>
            <input id="admintype" name="usertype" type="radio" value="admin" />
            <label for="admintype">Admin</label>
        </div>

        <div>&nbsp;</div>

        <input type="submit" value="Login" />
    </form>
    <div id="error-placeholder" style="text-align: center;" class="alert-danger" role="alert">&nbsp;</div>

    <% Object errorMessage = request.getAttribute(Constants.USER_NAME_ERROR);%>
    <% if (errorMessage != null) {%>

    <p style="text-align: center;"><span style="color: #ff0000;">User name already exist</span></p>

    <% } %>
    <% } else { %>
    <script src="../loginError/login_attempt_after_error.js"></script>
    <script>moveToLogIn();</script>
    <% }%>
</div>
</body>
</html>