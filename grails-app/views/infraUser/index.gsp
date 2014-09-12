
<%@ page import="com.mechzombie.infraview.InfraUser" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <r:require module='application' />
    </head>
<body>
    <a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </sec:ifAnyGranted>
        </ul>
        </div>
        <div id="list-user" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table>
            <thead>
                <tr>
                    <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'User Name')}" />
                    <g:sortableColumn property="enterprise" title="${message(code: 'user.username.label', default: 'Enterprise')}" />
                    <g:sortableColumn property="authorities" title="${message(code: 'user.username.label', default: 'Roles')}" />
                    <g:sortableColumn property="accountExpired" title="${message(code: 'user.accountExpired.label', default: 'Expired')}" />
                    <g:sortableColumn property="accountLocked" title="${message(code: 'user.accountLocked.label', default: 'Locked')}" />
                    <g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Enabled')}" />
                    <g:sortableColumn property="passwordExpired" title="${message(code: 'user.passwordExpired.label', default: 'Password Expired')}" />
                </tr>
            </thead>
            <tbody>
                <g:each in="${infraUserInstanceList}" status="i" var="infraUserInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td><g:link action="show" id="${infraUserInstance.id}">${fieldValue(bean: infraUserInstance, field: "username")}</g:link></td>
                        <td>${fieldValue(bean: infraUserInstance.enterprise, field: "name")}</td>
                        <td>${infraUserInstance.getAuthorities().authority}</td>
                        <td><g:formatBoolean boolean="${infraUserInstance.accountExpired}" /></td>
                        <td><g:formatBoolean boolean="${infraUserInstance.accountLocked}" /></td>
                        <td><g:formatBoolean boolean="${infraUserInstance.enabled}" /></td>
                        <td><g:formatBoolean boolean="${infraUserInstance.passwordExpired}" /></td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${infraUserInstanceCount ?: 0}" />
        </div>
    </div>
</body>
</html>
