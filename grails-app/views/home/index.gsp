
<%@ page import="com.mechzombie.infraview.Enterprise" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>InfraViewer</title>
    <r:require module="application"/>
</head>
<body>
<body>
    <p>InfraViewer Home for ${enterprise.name}</p>
    <div>        
        <div>            
            <div>My Calendar</div>
            <div><g:link controller="workOrder" action="index">My Work Orders</g:link></div>
            <div>Infrastructure</div>
            <div><g:link controller="enterprise" action="show" id="${enterprise.id}">My Organization</g:link></div>
            <div><g:link controller="infraUser" action="show" id="${user.id}">My Info</g:link></div>
        </div>    
    </div>
</body>
</html>