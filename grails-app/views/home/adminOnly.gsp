<%@ page import="com.mechzombie.infraview.Enterprise" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>InfraViewer Home for ${enterprise.name}</title>
    <r:require module="application"/>
</head>
<body>
<body>
    <p>InfraViewer Home for ${enterprise.name}</p>

    <div>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <div>
                <div><g:link controller="enterprise" action="show" id="${enterprise.id}"> Enterprise Administration</g:link></dev>
                <div>Reports</div>    
                <div><g:link controller="user" action="index" params="[enterprise: enterprise]">User Administration</g:link></div>
            </div>    
        </sec:ifAnyGranted>
    </div>
</body>
</html>