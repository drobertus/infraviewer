<%@ page import="com.mechzombie.infraview.Enterprise" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'enterprise.label', default: 'Enterprise')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    <r:require module='application' />
</head>
<body>
    <a href="#show-enterprise" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <sec:ifAnyGranted roles="ROLE_SUPERUSER">                 
                <li><g:link controller="sysadmin" action="index">List of Enterprises</g:link>            
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </sec:ifAnyGranted>     
            </ul>
        </div>
        <div id="show-enterprise" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <ol class="property-list enterprise">

            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="enterprise.name.label" default="Name" /></span>
                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${enterpriseInstance}" field="name"/></span>
            </li>

        <g:if test="${enterpriseInstance?.parentEnterprise}">    
            <li class="fieldcontain">
                <span id="parent-label" class="property-label"><g:message code="enterprise.parent.label" default="Parent" /></span>
                <span class="property-value" aria-labelledby="parent-label"><g:fieldValue bean="${enterpriseInstance.parentEnterprise}" field="name"/></span>
            </li>
        </g:if>

        <g:if test="${enterpriseInstance?.childEnterprises}">    
            <li class="fieldcontain">
                <span id="childEnterprise-label" class="property-label"><g:message code="enterprise.chileEnterprises.label" default="Child Enterprises" /></span>
                <g:each in="${enterpriseInstance.childEnterprises}" var="childEnt">
                    <span class="property-value" aria-labelledby="childEnterprise-label"><g:link controller="enterprise" action="show" id="${childEnt.id}">${childEnt.name}</g:link></span>
                </g:each>
            </li>
        </g:if>
        
            <li class="fieldcontain">
                <span id="activeDate-label" class="property-label"><g:message code="enterprise.activeDate.label" default="Active Date" /></span>
                <span class="property-value" aria-labelledby="activeDate-label"><g:formatDate date="${enterpriseInstance?.activeDate}" /></span>
            </li>
        
            <li class="fieldcontain">
                <span id="assetClasses-label" class="property-label"><g:message code="enterprise.assetClasses.label" default="Asset Classes" /></span>
                <g:if test="${enterpriseInstance?.assetClasses}">
                <g:each in="${enterpriseInstance.assetClasses}" var="a">
                    <span class="property-value" aria-labelledby="assetClasses-label"><g:link controller="assetClass" action="show" id="${a.id}">${a?.name}</g:link></span>
                </g:each>
                </g:if>    
                <g:else>
                    <span class="property-value" aria-labelledby="assetClasses-label">None</span>
                </g:else>
            </li>
        

        <g:if test="${enterpriseInstance?.location?.address}">
            <li class="fieldcontain">
                <span id="location-label" class="property-label"><g:message code="enterprise.address.label" default="Address" /></span>
                <span class="property-value" aria-labelledby="location-label">${enterpriseInstance?.location?.address?.getFormattedAddress()}</span>
            </li>
        </g:if>

        <g:if test="${enterpriseInstance?.users}">
            <li class="fieldcontain">
                <span id="users-label" class="property-label"><g:message code="enterprise.users.label" default="Users" /></span>
                <g:each in="${enterpriseInstance.users}" var="u">
                    <span class="property-value" aria-labelledby="users-label"><g:link controller="user" action="show" id="${u.id}">${u?.username}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <sec:ifAnyGranted roles="ROLE_ADMIN">
    <g:form url="[resource:enterpriseInstance, action:'delete']" method="DELETE">        
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${enterpriseInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />            
        </fieldset>
    </g:form>
    </sec:ifAnyGranted>
</div>
</body>
</html>
