<%@ page import="com.mechzombie.infraview.*" %>
<table>
    <thead>
        <tr>
            <g:sortableColumn style="width: 400px" property="statusDate" title="${message(code: 'assetStatusEvent.statusDate.label', default: 'Date')}" />
            <g:sortableColumn style="width: 200px" property="status" title="${message(code: 'assetStatusEvent.status.label', default: 'Status')}" />
            <g:sortableColumn property="eventType" title="${message(code: 'assetStatusEvent.eventType.label', default: 'Event Type')}" />
            <g:sortableColumn property="status" title="${message(code: 'assetStatusEvent.actions.label', default: 'Actions')}" />                    
        </tr>
    </thead>
    <tbody>

        <!-- TODO: add a first row to allow direect entry with a SAVE button on the side -->
        <g:formRemote  name="addAssetStatusEventForm" on404="alert('not found!')" update="updateMe"
            url="[controller: 'assetStatusEvent', action:'saveToAssetPage']" onSuccess =""
            onFailure="alert('Error!')">
        <tr>
            <g:hiddenField name="asset.id" value="${assetInstance.id}"/>
            <td><g:datePicker name="statusDate" precision="day" relativeYears="[-100..0]" /></td>
            <td><g:textField style="width: 50px" name="status" /></td>
            <td><g:select name="eventType" from="${AssetStatusEventType.values()}" 
                    keys="${AssetStatusEventType.values()}" />
            </td>
            <td><g:submitButton name="Save" value="Save" /></td>
        </tr>
        </g:formRemote>
<g:each in="${assetStatusEventList}" status="i" var="assetStatusEvent">
    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
        <td><g:formatDate format="MM-dd-yyyy" date="${assetStatusEvent.statusDate}"/></td>
        <td>${fieldValue(bean: assetStatusEvent, field: "status")}</td>
        <td>${fieldValue(bean: assetStatusEvent, field: "eventType")}</td>
        <td>
            <g:remoteLink method="DELETE" controller="assetStatusEvent" class="delete" action="delete" id="${assetStatusEvent.ident()}" 
                update="updateMe" before="if(!confirm('Are you sure?')) return false" onSuccess="">
                <g:message code="assetStatus.button.delete.label" default="Delete" />
            </g:remoteLink>
        </td>                                        
    </tr>
</g:each>

    </tbody>
</table>