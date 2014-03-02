<%@ page import="com.mechzombie.infraview.Enterprise" %>
<!DOCTYPE html>
<html>

    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'enterprise.label', default: 'Enterprise')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    <r:require module="application"/>
<g:javascript>        
$(document).ready(function() {        
    //$("input[type='checkbox']").on('change', function(){
    $("#hasAddress").on('change', function(){
        if (this.checked){             
            $('#theAddressBlock').show();
        }
        else {
            $('#theAddressBlock').hide();
        }        
    });
});

</g:javascript>
</head>
<body>
    <a href="#edit-enterprise" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <sec:ifAnyGranted roles="ROLE_SUPERUSER">
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>                        
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </sec:ifAnyGranted>
            </ul>
        </div>
        <div id="edit-enterprise" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${enterpriseInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${enterpriseInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form url="[resource:enterpriseInstance, action:'update']" method="PUT" >
            <g:hiddenField name="version" value="${enterpriseInstance?.version}" />
            <fieldset class="form">
                <g:render template="form"/>
            </fieldset>
            <fieldset class="buttons">
                <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>
