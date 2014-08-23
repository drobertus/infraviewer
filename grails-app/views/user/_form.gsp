<%@ page import="com.mechzombie.infraview.*" %>



<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="user.username.label" default="Username" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="email" name="username" required="" value="${infraUserInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'password', 'error')} required">
    <label for="password">
        <g:message code="user.password.label" default="Password" />
        <span class="required-indicator">*</span>
    </label>
    <g:passwordField name="password" required="" value="${infraUserInstance?.password}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'enterprise', 'error')} ">
    <label for="enterpriseName">
        <g:message code="user.enterprise.label" default="Enterprise" />
    </label>
    <g:hiddenField name="enterprise" value="${session.activeEnterprise.id}"/>
    <g:textField name="enterpriseName" disabled="true" value="${session.activeEnterprise.name}"/>
    
</div>

<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'authorities?.all', 'error')} ">
    <label for="authorities">
        <g:message code="user.authorities.label" default="Roles" />

    </label>
    <g:select name="authorities" from="${availableRoles}" value="${infraUserInstance.authorities?.id}" 
        optionKey="id" optionValue="authority" multiple='true' />
</div>

<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'accountExpired', 'error')} ">
    <label for="accountExpired">
        <g:message code="user.accountExpired.label" default="Account Expired" />

    </label>
    <g:checkBox name="accountExpired" value="${infraUserInstance?.accountExpired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'accountLocked', 'error')} ">
    <label for="accountLocked">
        <g:message code="user.accountLocked.label" default="Account Locked" />

    </label>
    <g:checkBox name="accountLocked" value="${infraUserInstance?.accountLocked}" />
</div>

<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'enabled', 'error')} ">
    <label for="enabled">
        <g:message code="user.enabled.label" default="Enabled" />

    </label>
    <g:checkBox name="enabled" value="${infraUserInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: infraUserInstance, field: 'passwordExpired', 'error')} ">
    <label for="passwordExpired">
        <g:message code="user.passwordExpired.label" default="Password Expired" />

    </label>
    <g:checkBox name="passwordExpired" value="${infraUserInstance?.passwordExpired}" />
</div>

