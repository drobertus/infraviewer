
<%@ page import="com.mechzombie.infraview.WorkOrder" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'workOrder.label', default: 'WorkOrder')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-workOrder" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-workOrder" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list workOrder">
			
				<g:if test="${workOrderInstance?.workType}">
				<li class="fieldcontain">
					<span id="workType-label" class="property-label"><g:message code="workOrder.workType.label" default="Work Type" /></span>
					
						<span class="property-value" aria-labelledby="workType-label"><g:fieldValue bean="${workOrderInstance}" field="workType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.asset}">
				<li class="fieldcontain">
					<span id="asset-label" class="property-label"><g:message code="workOrder.asset.label" default="Asset" /></span>
					
						<span class="property-value" aria-labelledby="asset-label"><g:link controller="asset" action="show" id="${workOrderInstance?.asset?.id}">${workOrderInstance?.asset?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.assignedStaff}">
				<li class="fieldcontain">
					<span id="assignedStaff-label" class="property-label"><g:message code="workOrder.assignedStaff.label" default="Assigned Staff" /></span>
					
						<g:each in="${workOrderInstance.assignedStaff}" var="a">
						<span class="property-value" aria-labelledby="assignedStaff-label"><g:link controller="infraUser" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.certifiedCompleteBy}">
				<li class="fieldcontain">
					<span id="certifiedCompleteBy-label" class="property-label"><g:message code="workOrder.certifiedCompleteBy.label" default="Certified Complete By" /></span>
					
						<span class="property-value" aria-labelledby="certifiedCompleteBy-label"><g:link controller="infraUser" action="show" id="${workOrderInstance?.certifiedCompleteBy?.id}">${workOrderInstance?.certifiedCompleteBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.completedDate}">
				<li class="fieldcontain">
					<span id="completedDate-label" class="property-label"><g:message code="workOrder.completedDate.label" default="Completed Date" /></span>
					
						<span class="property-value" aria-labelledby="completedDate-label"><g:formatDate date="${workOrderInstance?.completedDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.enterprise}">
				<li class="fieldcontain">
					<span id="enterprise-label" class="property-label"><g:message code="workOrder.enterprise.label" default="Enterprise" /></span>
					
						<span class="property-value" aria-labelledby="enterprise-label"><g:link controller="enterprise" action="show" id="${workOrderInstance?.enterprise?.id}">${workOrderInstance?.enterprise?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.location}">
				<li class="fieldcontain">
					<span id="location-label" class="property-label"><g:message code="workOrder.location.label" default="Location" /></span>
					
						<span class="property-value" aria-labelledby="location-label"><g:link controller="location" action="show" id="${workOrderInstance?.location?.id}">${workOrderInstance?.location?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.notes}">
				<li class="fieldcontain">
					<span id="notes-label" class="property-label"><g:message code="workOrder.notes.label" default="Notes" /></span>
					
						<span class="property-value" aria-labelledby="notes-label"><g:fieldValue bean="${workOrderInstance}" field="notes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.projectManager}">
				<li class="fieldcontain">
					<span id="projectManager-label" class="property-label"><g:message code="workOrder.projectManager.label" default="Project Manager" /></span>
					
						<span class="property-value" aria-labelledby="projectManager-label"><g:link controller="infraUser" action="show" id="${workOrderInstance?.projectManager?.id}">${workOrderInstance?.projectManager?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.reportedBy}">
				<li class="fieldcontain">
					<span id="reportedBy-label" class="property-label"><g:message code="workOrder.reportedBy.label" default="Reported By" /></span>
					
						<span class="property-value" aria-labelledby="reportedBy-label"><g:fieldValue bean="${workOrderInstance}" field="reportedBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.reportedDate}">
				<li class="fieldcontain">
					<span id="reportedDate-label" class="property-label"><g:message code="workOrder.reportedDate.label" default="Reported Date" /></span>
					
						<span class="property-value" aria-labelledby="reportedDate-label"><g:formatDate date="${workOrderInstance?.reportedDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.scheduledWorkDate}">
				<li class="fieldcontain">
					<span id="scheduledWorkDate-label" class="property-label"><g:message code="workOrder.scheduledWorkDate.label" default="Scheduled Work Date" /></span>
					
						<span class="property-value" aria-labelledby="scheduledWorkDate-label"><g:formatDate date="${workOrderInstance?.scheduledWorkDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${workOrderInstance?.workOrderId}">
				<li class="fieldcontain">
					<span id="workOrderId-label" class="property-label"><g:message code="workOrder.workOrderId.label" default="Work Order Id" /></span>
					
						<span class="property-value" aria-labelledby="workOrderId-label"><g:fieldValue bean="${workOrderInstance}" field="workOrderId"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:workOrderInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${workOrderInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
