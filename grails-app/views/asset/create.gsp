<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'asset.label', default: 'Asset')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    <r:require module="application"/>
<g:javascript> 
$(document).ready(function() {
if ($('#hasAddress').is(':checked')) {
      $('#theAddressBlock').show();  
    }else { $('#theAddressBlock').hide(); }

    $("#hasAddress").on('change', function(){
        if (this.checked){ $('#theAddressBlock').show(); }
        else { $('#theAddressBlock').hide(); }        
    });
});
</g:javascript> 
</head>
<body>
    <a href="#create-asset" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="index" params="['assetClass.id': assetInstance.assetClass.id]"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-asset" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /> of type ${assetInstance.assetClass.name}</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${assetInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${assetInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form url="[resource:assetInstance, action:'save']" >
            <fieldset class="form">
                <g:render template="form"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>
