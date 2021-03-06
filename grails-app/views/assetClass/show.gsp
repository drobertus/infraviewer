
<%@ page import="com.mechzombie.infraview.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'assetClass.label', default: 'AssetClass')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    <r:require module="application"/>
</head>
<body>
    <a href="#show-assetClass" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            <li><g:link controller="asset" action="create" params="['assetClass': assetClassInstance]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            
            </ul>
        </div>
        <div id="show-assetClass" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <ol class="property-list assetClass">

            <g:set var="assetCountForAssetClass" value="${Asset.countByAssetClass(assetClassInstance)}"/>
            <li class="fieldcontain">
                <span id="assets-label" class="property-label"><g:message code="assetClass.assets.label" default="Assets" /></span>
                <g:if test="${assetCountForAssetClass > 0}">                    
                    <span class="property-value" aria-labelledby="assets-label"><g:link controller="asset" action="index" params="['assetClass.id': assetClassInstance.id]">Show List (count is ${assetCountForAssetClass})</g:link></span>                    
                </g:if>
                <span class="property-value" aria-labelledby="assets-label"><g:link class="create" action="create" controller="asset" params="['assetClass.id': assetClassInstance.id]">New Asset</g:link></span>
            </li>

            <g:if test="${assetClassInstance?.name}">
                <li class="fieldcontain">
                    <span id="name-label" class="property-label"><g:message code="assetClass.name.label" default="Name" /></span>
                    <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${assetClassInstance}" field="name"/></span>
                </li>
            </g:if>

            <li class="fieldcontain">
                <span id="category-label" class="property-label"><g:message code="assetClass.assetClassCategory.label" default="Category" /></span>
                <span class="property-value" aria-labelledby="category-label"><g:fieldValue bean="${assetClassInstance}" field="assetClassCategory"/></span>
            </li>

            <g:if test="${assetClassInstance?.description}">
                <li class="fieldcontain">
                    <span id="description-label" class="property-label"><g:message code="assetClass.description.label" default="Description" /></span>
                    <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${assetClassInstance}" field="description"/></span>
                </li>
            </g:if>

            <g:if test="${assetClassInstance?.assetExternalIdSource}">
                <li class="fieldcontain">
                    <span id="description-label" class="property-label"><g:message code="assetClass.externalIdSource.label" default="External Id Source" /></span>
                    <span class="property-value" aria-labelledby="externalIdSource-label"><g:fieldValue bean="${assetClassInstance}" field="assetExternalIdSource"/></span>
                </li>
            </g:if>

            <g:if test="${assetClassInstance?.assetHasLocation}">
            <li class="fieldcontain">
                <span id="hasLocation-label" class="property-label"><g:message code="assetClass.hasLocation.label" default="Has Location" /></span>
                <span class="property-value" aria-labelledby="hasLocation-label"><g:fieldValue bean="${assetClassInstance}" field="assetHasLocation"/></span>
            </li>

            <li class="fieldcontain">
                <span id="locationType-label" class="property-label"><g:message code="assetClass.locationType.label" default="Location Type" /></span>
                <span class="property-value" aria-labelledby="locationType-label"><g:fieldValue bean="${assetClassInstance}" field="assetGeometryType"/></span>
            </li>
            
            <li class="fieldcontain">
                <span id="hasAddress-label" class="property-label"><g:message code="assetClass.hasAddress.label" default="Has Address" /></span>
                <span class="property-value" aria-labelledby="hasAddress-label"><g:fieldValue bean="${assetClassInstance}" field="assetHasAddress"/></span>
            </li>
            
            </g:if>
                                   
            <li class="fieldcontain">
                <span id="organic-label" class="property-label"><g:message code="assetClass.organic.label" default="Is Biological" /></span>
                <span class="property-value" aria-labelledby="organic-label"><g:fieldValue bean="${assetClassInstance}" field="organic"/></span>
            </li>

            <g:if test="${assetClassInstance?.expectedLifeSpanYears}">
                <li class="fieldcontain">
                    <span id="expectedLifeSpanYears-label" class="property-label"><g:message code="assetClass.expectedLifeSpanYears.label" default="Expected Life Span Years" /></span>
                    <span class="property-value" aria-labelledby="expectedLifeSpanYears-label"><g:fieldValue bean="${assetClassInstance}" field="expectedLifeSpanYears"/></span>
                </li>
            </g:if>

            <g:if test="${assetClassInstance?.standardInspectionInterval}">
                <li class="fieldcontain">
                    <span id="standardInspectionInterval-label" class="property-label"><g:message code="assetClass.standardInspectionInterval.label" default="Inspection Interval (Years)" /></span>
                    <span class="property-value" aria-labelledby="standardInspectionInterval-label"><g:fieldValue bean="${assetClassInstance}" field="standardInspectionInterval"/></span>

                </li>
            </g:if>

            <g:if test="${assetClassInstance?.statusValueNew}">
                <li class="fieldcontain">
                    <span id="statusValueNew-label" class="property-label"><g:message code="assetClass.statusValueNew.label" default="Status Value New" /></span>
                    <span class="property-value" aria-labelledby="statusValueNew-label"><g:fieldValue bean="${assetClassInstance}" field="statusValueNew"/></span>
                </li>
            </g:if>

            <li class="fieldcontain">
                <span id="statusValueReplace-label" class="property-label"><g:message code="assetClass.statusValueReplace.label" default="Status Value Replace" /></span>
                <span class="property-value" aria-labelledby="statusValueReplace-label"><g:fieldValue bean="${assetClassInstance}" field="statusValueReplace"/></span>
            </li>

            <li class="fieldcontain">
                <span id="statusValueDestroyed-label" class="property-label"><g:message code="assetClass.statusValueDestroyed.label" default="Status Value Destroyed" /></span>
                <span class="property-value" aria-labelledby="statusValueDestroyed-label"><g:fieldValue bean="${assetClassInstance}" field="statusValueDestroyed"/></span>
            </li>

        </ol>
        <g:form url="[resource:assetClassInstance, action:'delete']" method="DELETE">
            <fieldset class="buttons">
                <g:link class="edit" action="edit" resource="${assetClassInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>
