<%--
  Created by IntelliJ IDEA.
  User: ORMEI
  Date: 11/07/2021
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@page import="utils.*" %>
<%@ page import="constants.Constants" %>
<%@ page import="com.sun.org.apache.xpath.internal.operations.Bool" %>
<head>
    <title>Stock page</title>

    <script src="../../common/jquery-3.6.0.min.js"></script>
    <script src="../../common/context-path-helper.js"></script>
    <script src="../getUserAlert.js"></script>
    <script src="trade.js"></script>
    <script src="stockInformation.js"></script>
    <script src="dealsTable/dealsTable.js"></script>
    <script src="menu.js"></script>
    <link href="menu.css" rel="stylesheet">
    <link href="../main/tables/tables.css" rel="stylesheet">
</head>
<% String usernameFromSession = SessionUtils.getUsername(request);%>
<% String userTypeFromSession = SessionUtils.getUsertype(request);%>
<% Boolean thisUserIsTrader=(userTypeFromSession.equals("trader"));%>
<% String stockSymbolFromSession = SessionUtils.getStcokForTrade(request);%>
<body>

    <% if(usernameFromSession==null){ %>
   <%-- return to main--%>
    <% }else if(userTypeFromSession==null){ %>
    <%-- return to main--%>
    <% }else if(stockSymbolFromSession==null){ %>
    <%-- return to log in--%>
    <% }else{ %>
    <button class="accordion">Stock details</button>
    <div class="panel">
        <div id="stockname"></div>
        <div id="stocksymbol"></div>
        <div id="gate"></div>
        <div id="turnover"></div>
        <div id="holdings"></div>
    </div>

    <button class="accordion">Deals List</button>
    <div class="panel">
        <div id="dealstable"></div>
    </div>



    <% if(thisUserIsTrader){ %>
    <button class="accordion">Trade the stock</button>
    <div class="panel">

    <form id="maketradeform">
<script>getDetailsFromServer()</script>
        <div class="buyorsale">
            <input onclick="sellOrdinanceRadiobuttonClicked()" id="sellordinance" name="buyorsaleordinance" type="radio" value="sellordinance" required/>
            <label for="sellordinance">I want to sell</label>
            <input id="buyordinance" name="buyorsaleordinance" type="radio" value="buyordinance" onclick="buyOrdinanceRadiobuttonClicked()"/>
            <label for="buyordinance">I want to buy</label>
        </div>

        <div class="ordinanceType">
            <input id="lmt" name="typeofordinance" type="radio" value="lmt" onclick="lmtRadiobuttonClicked()" required/>
            <label for="lmt">LMT</label>
            <input id="fok" name="typeofordinance" type="radio" value="fok" onclick="fokRadiobuttonClicked()"/>
            <label for="fok">FOK</label>
            <input id="ioc" name="typeofordinance" type="radio" value="ioc" onclick="iocRadiobuttonClicked()"/>
            <label for="ioc">IOC</label>
            <input id="mkt" name="typeofordinance" type="radio" value="mkt" onclick="mktRadiobuttonClicked()"/>
            <label for="mkt">MKT</label>
        </div>

        <label>limit</label>
        <input type="number" id="limittextfield" name="limit" min="1" required>
        <div class="amount"></div>
        <input type="submit" id="submitbutton" value="done">
    </div>

    <% } %>
    <p style="text-align: center;"><a href="../main/main.html" role="button"><button type="button">Back to main</button></p>
            <% } %>
</body>
</html>
