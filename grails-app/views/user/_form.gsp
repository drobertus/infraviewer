<%@ page import="com.mechzombie.infraview.*" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="user.username.label" default="Username" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="email" name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
    <label for="password">
        <g:message code="user.password.label" default="Password" />
        <span class="required-indicator">*</span>
    </label>
    <g:passwordField name="password" required="" value="${userInstance?.password}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enterprise', 'error')} ">
    <label for="enterprise">
        <g:message code="user.enterprise.label" default="Enterprise" />

    </label>
    <g:select name="enterprise" from="${Enterprise.list()}" value="${userInstance.enterprise?.id}" 
        optionKey="id" optionValue="name" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'authorities?.all', 'error')} ">
    <label for="authorities">
        <g:message code="user.authorities.label" default="Roles" />

    </label>
    <g:select name="authorities" from="${Role.list()}" value="${userInstance.authorities?.id}" 
        optionKey="id" optionValue="authority" multiple='true' />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
    <label for="accountExpired">
        <g:message code="user.accountExpired.label" default="Account Expired" />

    </label>
    <g:checkBox name="accountExpired" value="${userInstance?.accountExpired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
    <label for="accountLocked">
        <g:message code="user.accountLocked.label" default="Account Locked" />

    </label>
    <g:checkBox name="accountLocked" value="${userInstance?.accountLocked}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
    <label for="enabled">
        <g:message code="user.enabled.label" default="Enabled" />

    </label>
    <g:checkBox name="enabled" value="${userInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
    <label for="passwordExpired">
        <g:message code="user.passwordExpired.label" default="Password Expired" />

    </label>
    <g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}" />
</div>

