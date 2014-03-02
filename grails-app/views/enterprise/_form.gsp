<%@ page import="com.mechzombie.infraview.*" %>

<div class="fieldcontain ${hasErrors(bean: enterpriseInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="enterprise.name.label" default="Name" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" value="${enterpriseInstance?.name}"/>
</div>

<g:hiddenField name="location" value="${enterpriseInstance?.location?.id}"/>
<g:hiddenField name="location.address" value="${enterpriseInstance?.location?.address?.id}"/>   

<div>
    <label for="hasAddress">
        <g:message code="enterprise.hasAddress.label" default="Has Address" />

    </label>
    <g:checkBox name="hasAddress" value="${enterpriseInstance.hasAddress}"/>
</div>
        
<div class="fieldcontain ${hasErrors(bean: enterpriseInstance?.location?.address, field: 'addressLine1', 'error')} required">
 
    <g:if test="${enterpriseInstance.hasAddress == 'true'}"><div id="theAddressBlock"></g:if>
    <g:else><div id="theAddressBlock" style="display:none" ></g:else>
        <label for="location">
            <g:message code="enterprise.location.address.label" default="Address" />
        </label>
        <div>
            <label for="addressLine1">
                <g:message code="enterprise.address.addressline1.label" default="Address Line 1" />
                <span class="required-indicator">*</span>
            </label>
            <g:textField name="addressLine1" value="${enterpriseInstance?.location?.address?.addressLine1}"/>
        </div>
        <div>
            <label for="addressLine2">
                <g:message code="enterprise.address.addressLine2.label" default="Line 2"/>
            </label>
            <g:textField name="addressLine2" value="${enterpriseInstance?.location?.address?.addressLine2}" />
        </div>
        
        <label>City, State, Zip Code<span class="required-indicator">*</span></label>        
            <span class="fieldcontain ${hasErrors(bean: enterpriseInstance?.location?.address, field: 'city', 'error')} required">
                <g:textField name="city" value="${enterpriseInstance?.location?.address?.city}"/>, 
            </span>
            <span class="fieldcontain ${hasErrors(bean: enterpriseInstance?.location?.address, field: 'state', 'error')} required">
                <g:select name="state" from="${State.list(sort: "code", order: "asc")}" optionKey="id" optionValue="code" value="${enterpriseInstance?.location?.address?.state?.code}"/>, 
            </span>

            <span class="fieldcontain ${hasErrors(bean: enterpriseInstance?.location?.address, field: 'postalCode', 'error')} required">
                <g:textField name="postalCode" value="${enterpriseInstance?.location?.address?.postalCode}" size="5"/>
            </span>
   
    </div>

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
    <g:select name="assetClasses" from="${enterpriseInstance?.assetClasses}" multiple="yes" optionKey="id" optionValue="name" size="5" value="${enterpriseInstance?.assetClasses*.name}" class="many-to-many"/>
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

