
<%@ page import="com.mechzombie.infraview.AssetClass" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'assetClass.label', default: 'AssetClass')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="application"/>
</head>
<body>
    <a href="#list-assetClass" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-assetClass" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /> for ${enterprise.name}</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table>
            <thead>
                <tr>
                    <g:sortableColumn property="name" title="${message(code: 'assetClass.name.label', default: 'Name')}" />
                    <g:sortableColumn property="description" title="${message(code: 'assetClass.description.label', default: 'Description')}" />
                    <g:sortableColumn property="expectedLifeSpanYears" title="${message(code: 'assetClass.expectedLifeSpanYears.label', default: 'Expected Life Span Years')}" />                  
                    <g:sortableColumn property="standardInspectionSpanYears" title="${message(code: 'assetClass.standardInspectionSpanYears.label', default: 'Standard Inspection Span Years')}" />
                    <g:sortableColumn property="statusValueDestroyed" title="${message(code: 'assetClass.statusValueDestroyed.label', default: 'Status Value Destroyed')}" />
                </tr>
            </thead>
            <tbody>
                <g:each in="${assetClassInstanceList}" status="i" var="assetClassInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td><g:link action="show" id="${assetClassInstance.id}">${fieldValue(bean: assetClassInstance, field: "name")}</g:link></td>
                        <td>${fieldValue(bean: assetClassInstance, field: "description")}</td>					
                        <td>${fieldValue(bean: assetClassInstance, field: "expectedLifeSpanYears")}</td>
                        <td>${fieldValue(bean: assetClassInstance, field: "standardInspectionSpanYears")}</td>					
                        <td>${fieldValue(bean: assetClassInstance, field: "statusValueDestroyed")}</td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${assetClassInstanceCount ?: 0}" />
        </div>
    </div>
</body>
</html>
