<%@ page import="com.mechzombie.infraview.*" %>

<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'externalId', 'error')} ">
    <label for="externalId">
        <g:message code="asset.externalId.label" default="External Id" />	
    </label>
	<g:textField name="externalId" value="${assetInstance?.externalId}"/>
</div>
<!--
<g:set var="mostRecentStatus" value="${assetInstance?.findMostRecentStatusEvent()}" />
<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'mostRecentStatus', 'error')} ">
    <g:hiddenField name="mostRecentStatus.id" value="${mostRecentStatus?.id}"/>
    <span>
    <label for="mostRecentStatus.status">Last Inspection Status</br>(max:${assetInstance.assetClass.statusValueNew} min:${assetInstance.assetClass.statusValueDestroyed})</label>
    <g:textField name="mostRecentStatus.status" value="${mostRecentStatus?.status}"/></span>
    <span><g:message code="asset.mostRecentStatus.statusDate.label" default="Date" />
    <g:datePicker name="mostRecentStatus.statusDate" precision="day" relativeYears="[-100..0]" value="${mostRecentStatus?.statusDate}"  />
    </span>
    <span>
    <label for="eventType">Event Type</label>
    
    <g:select name="eventType" from="${AssetStatusEventType.values()}" 
    keys="${AssetStatusEventType.values()}"
    value="${mostRecentStatus?.status}" /></span>
</div>
-->
<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="asset.description.label" default="Description" />	
    </label>
    <g:textField name="description" value="${assetInstance?.description}"/>
</div>

<g:hiddenField name="assetClass.id" value="${assetInstance.assetClass.id}"/>
<g:hiddenField name="location.id" value="${assetInstance?.location?.id}"/>
<div>
    <label for="hasAddress">
        <g:message code="asset.location.hasAddress.label" default="Has Address" />
    </label>
    <g:checkBox name="hasAddress" value="${assetInstance?.location?.hasAddress}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetInstance?.location?.address, field: 'addressLine1', 'error')} required">
   
    <g:if test="${assetInstance?.location?.hasAddress == 'true'}"><div id="theAddressBlock"></g:if>
    <g:else><div id="theAddressBlock" style="display:none" ></g:else>  
    
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
        <div>
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
</div>


<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'notes', 'error')} ">
    <label for="notes">
		<g:message code="asset.notes.label" default="Notes" />
    </label>
	<g:textArea name="notes" value="${assetInstance?.notes}"/>
</div>
