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
            <sec:ifAnyGranted roles="ROLE_ADMIN">

            <div><g:link controller="enterprise" action="show" id="${enterprise.id}"> Enterprise Administration</g:link></dev>
            <div>Reports</div>    
            <div><g:link controller="user" action="index" params="[enterprise: enterprise]">User Administration</g:link></div>

                
            </sec:ifAnyGranted>
            <div><g:link controller="assetClass" action="list">Asset Management</g:link></div>
        </div>
    </div>
</body>
</html>