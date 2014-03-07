<%@ page import="com.mechzombie.infraview.AssetClass;com.mechzombie.infraview.Enterprise;" %>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'assets', 'error')} ">
	<label for="assets">
		<g:message code="assetClass.assets.label" default="Assets" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${assetClassInstance?.assets?}" var="a">
    <li><g:link controller="asset" action="show" id="${a.id}">${a?.id}</g:link></li>
</g:each>
<li class="add">
<g:link controller="asset" action="create" params="['assetClass.id': assetClassInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'asset.label', default: 'Asset')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="assetClass.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${assetClassInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="assetClass.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${assetClassInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'enterprise', 'error')} ">
    <label for="enterpriseName">
        <g:message code="assetClass.enterprise.label" default="Enterprise" />
    </label>
    <g:hiddenField name="enterprise" value="${enterprise.id}"/>
    <g:textField name="enterpriseName" disabled="true" value="${enterprise.name}"/>
    
</div>

<!-- div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'enterprise', 'error')} required">
	<label for="enterprise">
		<g:message code="assetClass.enterprise.label" default="Enterprise" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="enterprise" name="enterprise.id" from="${com.mechzombie.infraview.Enterprise.list()}" optionKey="id" required="" value="${assetClassInstance?.enterprise?.id}" class="many-to-one"/>
</div -->

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'expectedLifeSpanYears', 'error')} required">
	<label for="expectedLifeSpanYears">
		<g:message code="assetClass.expectedLifeSpanYears.label" default="Expected Life Span (Years)" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="expectedLifeSpanYears" value="${fieldValue(bean: assetClassInstance, field: 'expectedLifeSpanYears')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'standardInspectionSpanYears', 'error')} required">
	<label for="standardInspectionSpanYears">
		<g:message code="assetClass.standardInspectionSpanYears.label" default="Standard Inspection Span (Years)" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="standardInspectionSpanYears" value="${fieldValue(bean: assetClassInstance, field: 'standardInspectionSpanYears')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'statusValueNew', 'error')} required">
	<label for="statusValueNew">
		<g:message code="assetClass.statusValueNew.label" default="Status Value When New" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="statusValueNew" value="${fieldValue(bean: assetClassInstance, field: 'statusValueNew')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'statusValueDestroyed', 'error')} required">
	<label for="statusValueDestroyed">
		<g:message code="assetClass.statusValueDestroyed.label" default="Status Value When Destroyed" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="statusValueDestroyed" value="${fieldValue(bean: assetClassInstance, field: 'statusValueDestroyed')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: assetClassInstance, field: 'statusValueReplace', 'error')} required">
	<label for="statusValueReplace">
		<g:message code="assetClass.statusValueReplace.label" default="Status Value for Replacement" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="statusValueReplace" value="${fieldValue(bean: assetClassInstance, field: 'statusValueReplace')}" required=""/>
</div>

