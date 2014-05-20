
<%@ page import="com.mechzombie.infraview.Report" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <r:require module='application' />
    </head>
<body>
    <a href="#list-report" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-report" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /> for ${enterprise.name}</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table>
            <thead>
                <tr>
                    <g:sortableColumn property="title" title="${message(code: 'report.title.label', default: 'Title')}" />
                    <g:sortableColumn property="reportType" title="${message(code: 'report.reportType.label', default: 'Report Type')}" />                                       
                    <g:sortableColumn property="startDate" title="${message(code: 'report.startDate.label', default: 'Start Date')}" />
                    <g:sortableColumn property="endDate" title="${message(code: 'report.endDate.label', default: 'End Date')}" />
                    <g:sortableColumn property="scheduledRunDate" title="${message(code: 'report.scheduledRunDate.label', default: 'Scheduled Run Date')}" />
                </tr>
            </thead>
            <tbody>
                <g:each in="${reportInstanceList}" status="i" var="reportInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td><g:link controller="report" action="show" id="${reportInstance.id}">${reportInstance.title}</g:link></td>
                        <td>${fieldValue(bean: reportInstance, field: "reportType.fullTitle")}</td>
                        <td><g:formatDate format="yyyy-MM-dd" date="${reportInstance.startDate}" /></td>
                        <td><g:formatDate format="yyyy-MM-dd" date="${reportInstance.endDate}" /></td>                    
                        <td><g:formatDate format="yyyy-MM-dd" date="${reportInstance.scheduledRunDate}" /></td>                        
                    </tr>
                </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${reportInstanceCount ?: 0}" />
        </div>
    </div>
</body>
</html>
