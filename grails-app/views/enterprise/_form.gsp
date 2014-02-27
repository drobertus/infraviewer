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
    <g:link controller="assetClass" action="index" params="['enterprise.id': enterpriseInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'assetClass.label', default: 'Asset Classes')])}</g:link>
    <g:select name="assetClasses" from="${enterpriseInstance.assetClasses}" multiple="yes" optionKey="id" size="5" value="${enterpriseInstance?.assetClasses*.name}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: enterpriseInstance.location, field: 'address', 'error')} ">
    <div>
    <label for="location">
        <g:message code="enterprise.location.address.label" default="Address" />
    </label>

        <div>Address Line 1 <g:textField name="addressLine1" value="${enterpriseInstance?.location?.address?.addressLine1}"/></div>
        <div>Address Line 2 <g:textField name="addressLine2" value="${enterpriseInstance?.location?.address?.addressLine2}"/></div>
        <div>City, State, Zip Code <g:textField name="city" value="${enterpriseInstance?.location?.address?.city}"/>, 
        <g:select name="state" from="${State.list(sort: "code", order: "asc")}" optionKey="id" optionValue="code" value="${enterpriseInstance?.location?.address?.state?.code}"/>, 
        <g:textField name="postalCode" value="${enterpriseInstance?.location?.address?.postalCode}" size="5"/>
        </div>

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

