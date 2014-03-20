
<%@ page import="com.mechzombie.infraview.Asset" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'asset.label', default: 'Asset')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    <r:require module="application"/>
</head>
<body>
    <a href="#show-asset" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            <li><g:link class="create" action="create" params="['assetClass.id': assetInstance.assetClass.id]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-asset" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /> of type ${assetInstance.assetClass.name}</<h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <ol class="property-list asset">

            <g:if test="${assetInstance?.externalId}">
                <li class="fieldcontain">
                    <span id="externalId-label" class="property-label"><g:message code="asset.externalId.label" default="External Id" /></span>
                    <span class="property-value" aria-labelledby="externalId-label"><g:fieldValue bean="${assetInstance}" field="externalId"/></span>
                </li>
            </g:if>
            
            <g:if test="${assetInstance?.description}">
                <li class="fieldcontain">
                    <span id="description-label" class="property-label"><g:message code="asset.description.label" default="Description" /></span>
                    <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${assetInstance}" field="description"/></span>
                </li>
            </g:if>

            <g:if test="${assetInstance?.notes}">
                <li class="fieldcontain">
                    <span id="notes-label" class="property-label"><g:message code="asset.notes.label" default="Notes" /></span>
                    <span class="property-value" aria-labelledby="notes-label"><g:fieldValue bean="${assetInstance}" field="notes"/></span>
                </li>
            </g:if>

            <g:if test="${assetInstance?.location}">
                <li class="fieldcontain">
                    <span id="location-label" class="property-label"><g:message code="asset.location.label" default="Location" /></span>
                    <span class="property-value" aria-labelledby="location-label"><g:link controller="location" action="show" id="${assetInstance?.location?.id}">${assetInstance?.location?.getLocationString()}</g:link></span>
                </li>
            </g:if>

            
            <g:if test="${assetInstance?.mostRecentStatus}">
                <li class="fieldcontain">
                    <span id="mostReccentStatus-label" class="property-label"><g:message code="asset.mostRecentStatus.label" default="Last Inspection" /></span>
                    <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${assetInstance.mostRecentStatus}" field="status"/>
                    at <g:formatDate format="yyyy-MM-dd" date="${assetInstance.mostRecentStatus.statusDate}"/></span>
                </li>
            </g:if>
            
            <g:if test="${assetInstance?.statusHistory}">
                <li class="fieldcontain">
                    <span id="statusHistory-label" class="property-label"><g:message code="asset.statusHistory.label" default="Status History" /></span>
                    <g:each in="${assetInstance.statusHistory}" var="s">
                        <span class="property-value" aria-labelledby="statusHistory-label"><g:link controller="assetStatus" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
                    </g:each>
                </li>
            </g:if>
        </ol>
        <g:form url="[resource:assetInstance, action:'delete']" method="DELETE">
            <fieldset class="buttons">
                <g:link class="edit" action="edit" resource="${assetInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>
