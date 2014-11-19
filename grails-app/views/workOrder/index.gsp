
<%@ page import="com.mechzombie.infraview.WorkOrder" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'workOrder.label', default: 'WorkOrder')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-workOrder" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-workOrder" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="workType" title="${message(code: 'workOrder.workType.label', default: 'Work Type')}" />
					
						<th><g:message code="workOrder.asset.label" default="Asset" /></th>
					
						<th><g:message code="workOrder.certifiedCompleteBy.label" default="Certified Complete By" /></th>
					
						<g:sortableColumn property="completedDate" title="${message(code: 'workOrder.completedDate.label', default: 'Completed Date')}" />
					
						<th><g:message code="workOrder.enterprise.label" default="Enterprise" /></th>
					
						<th><g:message code="workOrder.location.label" default="Location" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${workOrderInstanceList}" status="i" var="workOrderInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${workOrderInstance.id}">${fieldValue(bean: workOrderInstance, field: "workType")}</g:link></td>
					
						<td>${fieldValue(bean: workOrderInstance, field: "asset")}</td>
					
						<td>${fieldValue(bean: workOrderInstance, field: "certifiedCompleteBy")}</td>
					
						<td><g:formatDate date="${workOrderInstance.completedDate}" /></td>
					
						<td>${fieldValue(bean: workOrderInstance, field: "enterprise")}</td>
					
						<td>${fieldValue(bean: workOrderInstance, field: "location")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${workOrderInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
