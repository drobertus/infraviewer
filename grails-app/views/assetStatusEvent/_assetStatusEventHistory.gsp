<%@ page import="com.mechzombie.infraview.*" %>

<!-- TODO:  add tabbed panes rather than table under table -->
<div>
<table style="width: 75%">
    <thead>
        <tr>
            <th style="text-align:center">Calculation</th>
            <th style="text-align:center">Value</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td style="text-align:left">Projected Status</td>
            <td style="text-align:center">${assetInstance.getProjectedStatus()}</td>
        </tr>
        <tr>
            <td style="text-align:left">Next Projected Maintenance</td>
            <td style="text-align:center"><g:formatDate format="MM-dd-yyyy" date="${assetInstance.getNextProjectedMaintenance()}"/> </td>
        </tr>
        <tr>
            <td style="text-align:left">Next Projected Inspection</td>
            <td style="text-align:center"><g:formatDate format="MM-dd-yyyy" date="${assetInstance.getNextProjectedInspection()}"/></td>
        </tr>
    </tbody>
</table>


    <div id="statusHistory-label" class="property-label"><g:message code="statusHistory.label" default="Status History" /></div>
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
            onFailure="alert('Error!')" onUninitialized="alert('uh-oh!')">
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
        <g:set var="history" value="${assetInstance.getStatusHistory()}}"/> 
<g:each in="${history}" status="i" var="assetStatusEvent">
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
    </div>
  