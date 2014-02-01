<%@ page import="com.mechzombie.infraview.*" %>



<div class="fieldcontain ${hasErrors(bean: enterpriseInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="enterprise.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${enterpriseInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: enterpriseInstance, field: 'activeDate', 'error')} required">
	<label for="activeDate">
		<g:message code="enterprise.activeDate.label" default="Active Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="activeDate" precision="day"  value="${enterpriseInstance?.activeDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: enterpriseInstance, field: 'assetClasses', 'error')} ">
	<label for="assetClasses">
		<g:message code="enterprise.assetClasses.label" default="Asset Classes" />
		
	</label>
	<g:select name="assetClasses" from="${AssetClass.list()}" multiple="multiple" optionKey="id" size="5" value="${enterpriseInstance?.assetClasses*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: enterpriseInstance, field: 'location', 'error')} ">
	<label for="location">
		<g:message code="enterprise.location.label" default="Location" />
	</label>
	<g:select id="location" name="location.id" from="${Location.list()}" optionKey="id" value="${enterpriseInstance?.location?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: enterpriseInstance, field: 'users', 'error')} ">
	<label for="users">
		<g:message code="enterprise.users.label" default="Users" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${enterpriseInstance?.users}" var="u">
    <li><g:link controller="user" action="show" id="${u.id}">${u?.username}</g:link></li>
</g:each>
<li class="add">
<g:link controller="user" action="create" params="['enterprise.id': enterpriseInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'user.label', default: 'User')])}</g:link>
</li>
</ul>

</div>

