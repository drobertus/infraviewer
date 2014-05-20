<%@ page import="com.mechzombie.infraview.*" %>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'reportType', 'error')} required">
    <label for="reportType">
        <g:message code="report.reportType.label" default="Report Type" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="reportType" from="${ReportType?.values()}" 
    keys="${ReportType.values()*.name()}" 
    required="" value="${reportInstance?.reportType?.name()}"
        optionKey="name" optionValue="fullTitle"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'title', 'error')} ">
    <label for="title">
        <g:message code="report.title.label" default="Title" />

    </label>
    <g:textField name="title" value="${reportInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'startDate', 'error')} required">
    <label for="startDate">
        <g:message code="report.startDate.label" default="Start Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="startDate" precision="day"  value="${reportInstance?.startDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'endDate', 'error')} required">
    <label for="endDate">
        <g:message code="report.endDate.label" default="End Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="endDate" precision="day"  value="${reportInstance?.endDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'reportedAssetClasses', 'error')} ">
    <label for="reportedAssetClasses">
        <g:message code="report.reportedAssetClasses.label" default="Reported Asset Classes" />
    </label>
    <g:select name="reportedAssetClasses" from="${reportInstance.enterprise.assetClasses}" multiple="multiple" 
    optionKey="id" optionValue="name" size="5" value="${reportInstance?.reportedAssetClasses*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'scheduledRunDate', 'error')} required">
    <label for="scheduledRunDate">
        <g:message code="report.runDate.label" default="Scheduled Run Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="scheduledRunDate" precision="day"  value="${reportInstance?.scheduledRunDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'bounds', 'error')} required">
    <label for="bounds">
        <g:message code="report.bounds.label" default="Bounds" />
        <span class="required-indicator">*</span>
    </label>
    <g:textArea id="bounds" name="bounds" value="" rows="2" cols="40" />	
</div>

<g:hiddenField name="enterprise" value="${reportInstance.enterprise?.id}" />
<g:hiddenField name="enterprise.id" value="${reportInstance.enterprise?.id}" />


