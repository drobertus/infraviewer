<%@ page import="com.mechzombie.infraview.*" %>

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'workOrderId', 'error')} required">
    <label for="workOrderId">
        <g:message code="workOrder.workOrderId.label" default="Work Order Id" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="workOrderId" required="" value="${workOrderInstance?.workOrderId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'reportedDate', 'error')} required">
    <label for="reportedDate">
        <g:message code="workOrder.reportedDate.label" default="Reported Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="reportedDate" precision="day"  value="${workOrderInstance?.reportedDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'status', 'error')} required">
    <label for="workOrderId">
        <g:message code="workOrder.status.label" default="Status" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="status" from="${WorkOrderStatusType.values()}" 
    keys="${WorkOrderStatusType.values()}" value="${workOrderInstance?.status}" />
</div>                    

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'workType', 'error')} required">
    <label for="workType">
        <g:message code="workOrder.workType.label" default="Work Type" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="workType" from="${AssetStatusEventType.values()}" required="" value="${workOrderInstance?.workType}" valueMessagePrefix="workOrder.workType"/>
</div>


<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'assetClass', 'error')} required">
    <label for="asset">
        <g:message code="workOrder.assetClass.label" default="Asset Class" />
        <span class="required-indicator">*</span>
    </label>
        <g:select id="assetClass" name="assetClass.id" 
            from="${AssetClass.findAllWhere(enterprise: workOrderInstance?.enterprise)}" 
            noSelection="${['null':'Select One...']}"
            optionKey="id" optionValue="name"  value="${workOrderInstance?.assetClass?.id}" class="many-to-many"/>
        <!--g:select id="assetClass" name="assetClass.id" from="${AssetClass.findAllWhere(enterprise: workOrderInstance?.enterprise)}" optionKey="id" required="" value="${workOrderInstance?.assetClass?.name}" class="many-to-one"/-->
</div>


<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'reportedBy', 'error')} required">
    <label for="reportedBy">
        <g:message code="workOrder.reportedBy.label" default="Reported By" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="reportedBy" required="" value="${workOrderInstance?.reportedBy}"/>
</div>


<!-- div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'asset', 'error')} ">
    <label for="asset">
        <g:message code="workOrder.asset.label" default="Asset" />		        
    </label>
    <g:select id="asset" name="asset.id" from="${Asset.list()}" optionKey="id" required="" value="${workOrderInstance?.asset?.id}" class="many-to-one"/>
</div -->

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'scheduledWorkDate', 'error')} ">
    <label for="scheduledWorkDate">
        <g:message code="workOrder.scheduledWorkDate.label" default="Scheduled Work Date" />        
    </label>
    <g:datePicker name="scheduledWorkDate" precision="day"  value="${workOrderInstance?.scheduledWorkDate}"  noSelection="['':'-Choose-']" />
</div>

<g:set var="enterpriseUsers" value="${InfraUser.findAllWhere(enterprise: workOrderInstance.enterprise)}"/>     
<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'assignedStaff', 'error')} ">
    <label for="assignedStaff">
        <g:message code="workOrder.assignedStaff.label" default="Assigned Staff" />
    </label>
    <g:select name="assignedStaff" from="${enterpriseUsers}"
        multiple="multiple" optionKey="id" optionValue="username" size="5" value="${workOrderInstance?.assignedStaff*.id}" class="many-to-many"/>
</div>

<g:hiddenField id="enterprise" name="enterprise.id" value="${workOrderInstance.enterprise.id}" />

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'location', 'error')} required">
    <label for="location">
        <g:message code="workOrder.location.label" default="Location" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="location" name="location.id" from="${Location.list()}" optionKey="id" required="" value="${workOrderInstance?.location?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'notes', 'error')}">
    <label for="notes">
        <g:message code="workOrder.notes.label" default="Notes" />        
    </label>
    <g:textArea name="notes" value="${workOrderInstance?.notes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'projectManager', 'error')} ">
    <label for="projectManager">
        <g:message code="workOrder.projectManager.label" default="Project Manager" />        
    </label>
    <g:select id="projectManager" name="projectManager.id" 
    noSelection="${['null':'Select One...']}"
    from="${enterpriseUsers}" optionKey="id" optionValue="username"  
    value="${workOrderInstance?.projectManager?.username}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'certifiedCompleteBy', 'error')}">
    <label for="certifiedCompleteBy">
        <g:message code="workOrder.certifiedCompleteBy.label" default="Certified Complete By" />        
    </label>
    <g:select id="certifiedCompleteBy" name="certifiedCompleteBy.id" 
        noSelection="${['null':'Select One...']}"
    from="${enterpriseUsers}" optionKey="id" optionValue="username"
    required="" value="${workOrderInstance?.certifiedCompleteBy?.username}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workOrderInstance, field: 'completedDate', 'error')} ">
    <label for="completedDate">
        <g:message code="workOrder.completedDate.label" default="Completed Date" />        
    </label>
    <g:datePicker name="completedDate" precision="day"  value="${workOrderInstance?.completedDate}"  />
</div>
