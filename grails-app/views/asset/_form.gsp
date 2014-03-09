<%@ page import="com.mechzombie.infraview.*" %>

<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'externalId', 'error')} ">
    <label for="externalId">
        <g:message code="asset.externalId.label" default="External Id" />	
    </label>
	<g:textField name="externalId" value="${assetInstance?.externalId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'description', 'error')} ">
    <label for="description">
		<g:message code="asset.description.label" default="Description" />	
    </label>
	<g:textField name="description" value="${assetInstance?.description}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'assetClass', 'error')} required">
    <label for="assetClass">
		<g:message code="asset.assetClass.label" default="Asset Class" />
        <span class="required-indicator">*</span>
    </label>
	<g:hiddenField name="assetClass.id" value="${assetInstance.assetClass.id}"/>
        <g:textField name="assetClass.name" disabled="true" value="${assetInstance.assetClass.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetInstance?.location?.address, field: 'addressLine1', 'error')} required">
 
    <div id="theAddressBlock">    
        <label for="location">
            <g:message code="asset.location.address.label" default="Address" />
        </label>
        <div>
            <label for="addressLine1">
                <g:message code="assetddress.addressline1.label" default="Address Line 1" />
                <span class="required-indicator">*</span>
            </label>
            <g:textField name="addressLine1" value="${assetInstance?.location?.address?.addressLine1}"/>
        </div>
        <div>
            <label for="addressLine2">
                <g:message code="assetInstance.address.addressLine2.label" default="Line 2"/>
            </label>
            <g:textField name="addressLine2" value="${assetInstance?.location?.address?.addressLine2}" />
        </div>
        
        <label>City, State, Zip Code<span class="required-indicator">*</span></label>        
            <span class="fieldcontain ${hasErrors(bean: assetInstance?.location?.address, field: 'city', 'error')} required">
                <g:textField name="city" value="${assetInstance?.location?.address?.city}"/>, 
            </span>
            <span class="fieldcontain ${hasErrors(bean: assetInstance?.location?.address, field: 'state', 'error')} required">
                <g:select name="state" from="${State.getStateList()}" optionKey="id" optionValue="code" value="${assetInstance?.location?.address?.state?.id}"/>, 
            </span>

            <span class="fieldcontain ${hasErrors(bean: assetInstance?.location?.address, field: 'postalCode', 'error')} required">
                <g:textField name="postalCode" value="${assetInstance?.location?.address?.postalCode}" size="5"/>
            </span>
    </div>
</div>


<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'statusHistory', 'error')} ">
    <label for="statusHistory">
	<g:message code="asset.statusHistory.label" default="Status History" />		
    </label>	
    <ul class="one-to-many">
        <g:each in="${assetInstance?.statusHistory?}" var="s">
            <li><g:link controller="assetStatus" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="assetStatus" action="create" params="['asset.id': assetInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'assetStatus.label', default: 'AssetStatus')])}</g:link>
        </li>
    </ul>

</div>

<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'notes', 'error')} ">
    <label for="notes">
		<g:message code="asset.notes.label" default="Notes" />
    </label>
	<g:textArea name="notes" value="${assetInstance?.notes}"/>
</div>
