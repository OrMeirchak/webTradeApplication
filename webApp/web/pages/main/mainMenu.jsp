<%@ page import="utils.SessionUtils" %><%--
  Created by IntelliJ IDEA.
  User: ORMEI
  Date: 18/07/2021
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String userTypeFromSession = SessionUtils.getUsertype(request);%>
<% Boolean thisUserIsTrader=(userTypeFromSession.equals("trader"));%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        * {box-sizing: border-box}
        body {font-family: "Lato", sans-serif;}

        /* Style the tab */
        .tab {
            float: left;
            border: 1px solid #ccc;
            background-color: #f1f1f1;
            width: 30%;
            height: 300px;
        }

        /* Style the buttons inside the tab */
        .tab button {
            display: block;
            background-color: inherit;
            color: black;
            padding: 22px 16px;
            width: 100%;
            border: none;
            outline: none;
            text-align: left;
            cursor: pointer;
            transition: 0.3s;
            font-size: 17px;
        }

        /* Change background color of buttons on hover */
        .tab button:hover {
            background-color: #ddd;
        }

        /* Create an active/current "tab button" class */
        .tab button.active {
            background-color: #ccc;
        }

        /* Style the tab content */
        .tabcontent {
            float: left;
            padding: 0px 12px;
            border: 1px solid #ccc;
            width: 70%;
            border-left: none;
            height: 300px;
        }
    </style>
</head>
<body>
<% if(userTypeFromSession==null){ %>
<%-- return to main--%>
<%}else{ %>
<h2>Rizpa Stock Exchenge</h2>

<div class="tab">
    <button class="tablinks" onclick="openComponent(event, 'userstable')" id="defaultOpen">Users list</button>
    <button class="tablinks" onclick="openComponent(event, 'stocktable')">Stocks list</button>

    <% if(thisUserIsTrader){ %>

    <button class="tablinks" onclick="openComponent(event, 'tradertrafficstable')">Your traffics</button>
    <button class="tablinks" onclick="openComponent(event, 'makeissue')">Make issue</button>
    <button class="tablinks" onclick="openComponent(event, 'deposit')">Make a deposit</button>

    <% } %>

</div>

<div id="userstable" class="tabcontent"></div>
<div id="stocktable" class="tabcontent"></div>

<% if(thisUserIsTrader){ %>

<div id="tradertrafficstable" class="tabcontent"></div>
<div id="makeissue" class="tabcontent"></div>
<div id="deposit" class="tabcontent"></div>
<% } %>
<% } %>

<script>
function openComponent(evt, cityName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();

  $(function(){
     $("#userstable").load("tables/usersTable/usersTable.html");

       $("#stocktable").load("tables/stocksTable/stocksTable.html");

$("#tradertrafficstable").load("tables/traderTrafficsTable/traderTrafficsTable.html");

       $("#makeissue").load("issue/makeIssue.html");

       $("#deposit").load("deposit/deposit.html");
  });
</script>

</body>
</html>

