
<%@ page import="com.mechzombie.infraview.Report" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <r:require module='application' />
</head>
<body>
    <a href="#show-report" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-report" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <ol class="property-list report">

            <g:if test="${reportInstance?.title}">
                <li class="fieldcontain">
                    <span id="title-label" class="property-label"><g:message code="report.title.label" default="Title" /></span>

                    <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${reportInstance}" field="title"/></span>

                </li>
            </g:if>

                <li class="fieldcontain">
                    <span id="startDate-label" class="property-label"><g:message code="report.dateRange.label" default="Report Date Range" /></span>
                    <span class="property-value" aria-labelledby="startDate-label"><g:formatDate format="yyyy-MM-dd" date="${reportInstance?.startDate}" /> to <g:formatDate format="yyyy-MM-dd" date="${reportInstance?.endDate}" /></span>
                </li>


            <g:if test="${reportInstance?.reportType}">
                <li class="fieldcontain">
                    <span id="reportType-label" class="property-label"><g:message code="report.reportType.label" default="Report Type" /></span>

                    <span class="property-value" aria-labelledby="reportType-label"><g:fieldValue bean="${reportInstance}" field="reportType.fullTitle"/></span>

                </li>
            </g:if>

            <g:if test="${reportInstance?.reportedAssetClasses}">
                <li class="fieldcontain">
                    <span id="reportedAssetClasses-label" class="property-label"><g:message code="report.reportedAssetClasses.label" default="Reported Asset Classes" /></span>

                    <g:each in="${reportInstance.reportedAssetClasses}" var="r">
                        <span class="property-value" aria-labelledby="reportedAssetClasses-label"><g:link controller="assetClass" action="show" id="${r.id}">${r?.name}</g:link></span>
                    </g:each>

                </li>
            </g:if>

            <g:if test="${reportInstance?.runDate}">
                <li class="fieldcontain">
                    <span id="runDate-label" class="property-label"><g:message code="report.runDate.label" default="Run Date" /></span>

                    <span class="property-value" aria-labelledby="runDate-label"><g:formatDate format="yyyy-MM-dd" date="${reportInstance?.runDate}" /></span>

                </li>
            </g:if>

            

            <g:if test="${reportInstance?.bounds}">
                <li class="fieldcontain">
                    <span id="bounds-label" class="property-label"><g:message code="report.bounds.label" default="Bounds" /></span>

                    <span class="property-value" aria-labelledby="bounds-label"><g:link controller="geometry" action="show" id="${reportInstance?.bounds?.id}">${reportInstance?.bounds?.encodeAsHTML()}</g:link></span>

                </li>
            </g:if>

        </ol>
        <g:form url="[resource:reportInstance, action:'delete']" method="DELETE">
            <fieldset class="buttons">
                <g:link class="edit" action="edit" resource="${reportInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>
