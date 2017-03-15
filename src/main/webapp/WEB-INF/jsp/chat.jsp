<%--
  Created by IntelliJ IDEA.
  User: dima
  Date: 02.03.17
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>chat</title>
</head>
<body>
hello, ${username}
<table>
    <tr>
        <td>
            <form>
                <textarea id="input-messages"></textarea>
                <input type="text" id="output-messages"/>
                <input type="button" value="send" onclick="send()"/>
                <input type="button" value="logout" onclick="disconnect()"/>
            </form>
        </td>
        <td>
            <div id="angular-webchat" ng-app="webchat" ng-controller="webchat-controller">
                <ul>
                    <li ng-repeat="item in items" ng-click="changeUser(item)">
                        {{item}}
                    </li>
                </ul>
            </div>
        </td>
    </tr>
</table>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script>
    var socket = new SockJS("${sessionScope.sockurl}");
    var flag = null;

    var angularApp = angular.module("webchat", []);

    angularApp.controller("webchat-controller", function ($scope) {
        $scope.setItems = function (items) {
            $scope.items = items;
        }
        $scope.changeUser = function (userName) {
            document.getElementById("output-message").value = userName + ":";
        }
    }
    );

    socket.onopen = function () {
        registrateUser();
        flag = setInterval(sendList, 2000);
        console.log("connection established");
    }
    socket.onclose = function (param) {
        if(param.wasClean){
            console.log("socket was closed");
        } else {
            console.log("socket was closed because of error");
        }
    }
    socket.onerror = function (param) {
        console.log(param);
    }
    socket.onmessage = function (param) {
        var jsonMessage = JSON.parse(param.data);
        if(typeof jsonMessage.auth !== 'undefined' && jsonMessage.auth == "yes"){
            console.log("authorization successful");
        }
        if(typeof jsonMessage.auth !== 'undefined' && jsonMessage.auth == "no"){
            console.log("authorization failed");
        }
        if(typeof jsonMessage.list !== 'undefined'){
            activeUsers = jsonMessage.list;
            var scopeAngular = document.getElementById("angular-webchat").scope();
            scopeAngular.$apply(function () {
                scopeAngular.setItems(activeUsers);
            }
            );
        }
        if(typeof jsonMessage.name !== 'undefined'){
            var sender = jsonMessage.name;
            var message = jsonMessage.message;
            document.getElementById("input-messages").value = sender + ":" + message + "\n" +
                document.getElementById("input-messages").value;
        }
        if(typeof jsonMessage.disconnect !== 'undefined'){
            window.location.href = "/";
        }
    }
</script>
</body>
</html>
