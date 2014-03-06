
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
            <div>Calendar</div>
            <div>Work Orders</div>
            <div>Infrastructure</div>
            <div><g:link controller="enterprise" action="show" id="${enterprise.id}">My Organization</g:link></div>
            <div><g:link controller="user" action="show" id="${user.id}">My Info</g:link></div>
        </div>    
    </div>
</body>
</html>