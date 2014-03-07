<%@ page import="com.mechzombie.infraview.Asset" %>

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
	<g:hiddenField name="assetClass.id" value="$assetClass.id}"/>
        <g:textField name="assetClass.name" disabled="true" value="${assetClass.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetInstance, field: 'location', 'error')} required">
    <label for="location">
        <g:message code="asset.location.label" default="Location" />
        <span class="required-indicator">*</span>
    </label>
	<g:select id="location" name="location.id" from="${com.mechzombie.infraview.Location.list()}" optionKey="id" required="" value="${assetInstance?.location?.id}" class="many-to-one"/>
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
