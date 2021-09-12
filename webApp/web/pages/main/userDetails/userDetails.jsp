<%@ page import="utils.SessionUtils" %><%--
  Created by IntelliJ IDEA.
  User: ORMEI
  Date: 18/07/2021
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String userTypeFromSession = SessionUtils.getUsertype(request);%>
<% Boolean thisUserIsTrader=(userTypeFromSession.equals("trader"));%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<body>
<div class="border">
    <div id="username"></div>
    <div id="userype"></div>
    <div id="balance"></div>

    <% if(thisUserIsTrader){ %>
    <div id="loadfile"></div>
    <script>
    $(function() {
    $("#loadfile").load("loadFile/loadFile.html");
    });
    </script>
    <% } %>
</div>
</body>
</html>
