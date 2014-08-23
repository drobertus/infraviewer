
<%@ page import="com.mechzombie.infraview.*" %>
<!DOCTYPE html>
<html>
	<head>
            <meta name='layout' content='main'/>
            <title>InfraView - The Civic Infrastructure Manager</title>
            <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
            <r:require module='application' />
	</head>
	<body>
		<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<sec:ifAnyGranted roles="ROLE_ADMIN">
                                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                                </sec:ifAnyGranted>
			</ul>
		</div>
		<div id="show-user" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list user">
			
				<g:if test="${infraUserInstance?.username}">
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="user.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${infraUserInstance}" field="username"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${infraUserInstance?.enterprise}">
				<li class="fieldcontain">
					<span id="enterprise-label" class="property-label"><g:message code="user.enterprise.label" default="Enterprise" /></span>
					
						<span class="property-value" aria-labelledby="password-label"><g:fieldValue bean="${infraUserInstance.enterprise}" field="name"/></span>
					
				</li><g:if test="${infraUserInstance?.authorities}">
				<li class="fieldcontain">
					<span id="authorities-label" class="property-label"><g:message code="user.authorities.label" default="Roles" /></span>
					
						<span class="property-value" aria-labelledby="user.authorities.label">${infraUserInstance?.getAuthorities().authority}</span>
					
				</li>
				</g:if>
				</g:if>
			
                                
                                
				<g:if test="${infraUserInstance?.accountExpired}">
				<li class="fieldcontain">
					<span id="accountExpired-label" class="property-label"><g:message code="user.accountExpired.label" default="Account Expired" /></span>
					
						<span class="property-value" aria-labelledby="accountExpired-label"><g:formatBoolean boolean="${infraUserInstance?.accountExpired}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${infraUserInstance?.accountLocked}">
				<li class="fieldcontain">
					<span id="accountLocked-label" class="property-label"><g:message code="user.accountLocked.label" default="Account Locked" /></span>
					
						<span class="property-value" aria-labelledby="accountLocked-label"><g:formatBoolean boolean="${infraUserInstance?.accountLocked}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${infraUserInstance?.enabled}">
				<li class="fieldcontain">
					<span id="enabled-label" class="property-label"><g:message code="user.enabled.label" default="Enabled" /></span>
					
						<span class="property-value" aria-labelledby="enabled-label"><g:formatBoolean boolean="${infraUserInstance?.enabled}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${infraUserInstance?.passwordExpired}">
				<li class="fieldcontain">
					<span id="passwordExpired-label" class="property-label"><g:message code="user.passwordExpired.label" default="Password Expired" /></span>
					
						<span class="property-value" aria-labelledby="passwordExpired-label"><g:formatBoolean boolean="${infraUserInstance?.passwordExpired}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:infraUserInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${infraUserInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
