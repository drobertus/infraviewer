<%@ page import="com.mechzombie.infraview.Enterprise" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>InfraViewer Home for ${session.activeEnterprise.name}</title>
    <r:require module="application"/>
</head>
<body>
<body>
    <p>InfraViewer Home for ${session.activeEnterprise.name}</p>

    <div>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <div>
                <div>Enterprise Administration</div>
                <div>Reporting</div>
                <div>User Administration</div>
            </div>    
        </sec:ifAnyGranted>
    </div>
</body>
</html>