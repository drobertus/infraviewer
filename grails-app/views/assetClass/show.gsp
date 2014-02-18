
<%@ page import="com.mechzombie.infraview.AssetClass" %>
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
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-assetClass" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list assetClass">
			
				<g:if test="${assetClassInstance?.assets}">
				<li class="fieldcontain">
					<span id="assets-label" class="property-label"><g:message code="assetClass.assets.label" default="Assets" /></span>
					
						<g:each in="${assetClassInstance.assets}" var="a">
						<span class="property-value" aria-labelledby="assets-label"><g:link controller="asset" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="assetClass.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${assetClassInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.enterprise}">
				<li class="fieldcontain">
					<span id="enterprise-label" class="property-label"><g:message code="assetClass.enterprise.label" default="Enterprise" /></span>
					
						<span class="property-value" aria-labelledby="enterprise-label"><g:link controller="enterprise" action="show" id="${assetClassInstance?.enterprise?.id}">${assetClassInstance?.enterprise?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.expectedLifeSpanYears}">
				<li class="fieldcontain">
					<span id="expectedLifeSpanYears-label" class="property-label"><g:message code="assetClass.expectedLifeSpanYears.label" default="Expected Life Span Years" /></span>
					
						<span class="property-value" aria-labelledby="expectedLifeSpanYears-label"><g:fieldValue bean="${assetClassInstance}" field="expectedLifeSpanYears"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="assetClass.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${assetClassInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.standardInspectionSpanYears}">
				<li class="fieldcontain">
					<span id="standardInspectionSpanYears-label" class="property-label"><g:message code="assetClass.standardInspectionSpanYears.label" default="Standard Inspection Span Years" /></span>
					
						<span class="property-value" aria-labelledby="standardInspectionSpanYears-label"><g:fieldValue bean="${assetClassInstance}" field="standardInspectionSpanYears"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.statusValueDestroyed}">
				<li class="fieldcontain">
					<span id="statusValueDestroyed-label" class="property-label"><g:message code="assetClass.statusValueDestroyed.label" default="Status Value Destroyed" /></span>
					
						<span class="property-value" aria-labelledby="statusValueDestroyed-label"><g:fieldValue bean="${assetClassInstance}" field="statusValueDestroyed"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.statusValueNew}">
				<li class="fieldcontain">
					<span id="statusValueNew-label" class="property-label"><g:message code="assetClass.statusValueNew.label" default="Status Value New" /></span>
					
						<span class="property-value" aria-labelledby="statusValueNew-label"><g:fieldValue bean="${assetClassInstance}" field="statusValueNew"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${assetClassInstance?.statusValueReplace}">
				<li class="fieldcontain">
					<span id="statusValueReplace-label" class="property-label"><g:message code="assetClass.statusValueReplace.label" default="Status Value Replace" /></span>
					
						<span class="property-value" aria-labelledby="statusValueReplace-label"><g:fieldValue bean="${assetClassInstance}" field="statusValueReplace"/></span>
					
				</li>
				</g:if>
			
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