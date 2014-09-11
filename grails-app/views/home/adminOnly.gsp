<%@ page import="com.mechzombie.infraview.Enterprise" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>InfraViewer</title>
        <r:require module='application' />
    </head>
<body>
    <p>Home for ${enterprise.name} Administration</p>
<a href="#show-enterprise" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li>Calendar</li>
            <li>Work Orders</li>            
            <li><g:link controller="assetClass" action="index">Asset Management</g:link></li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li><g:link controller="enterprise" action="show" id="${enterprise.id}"> Enterprise Administration</g:link></li>
            <li><g:link controller="report" action="index" id="${enterprise.id}">Reports</g:link></li>    
            <li><g:link controller="infraUser" action="index">User Administration</g:link></li>
            </sec:ifAnyGranted>
            <li><g:link controller="infraUser" action="show" id="${user.id}">My Info</g:link></li>
        </ul>
    </div>
</body>
</html>