
<html>
<head>
	<meta name='layout' content='main'/>
	<title>InfraView - The Civic Infrastructure Manager</title>
	<r:require module='application' />
</head>
<body>
<script>

</script>
    <h2>System Administration</h2>
    </p>
    <a href="#list-enterprise" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    		<div class="nav" role="navigation">
    			<ul>
    				<li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label"/></a></li>
    				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    			</ul>
    		</div>
    		<div id="list-enterprise" class="content scaffold-list" role="main">
    			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
    			<g:if test="${flash.message}">
    				<div class="message" role="status">${flash.message}</div>
    			</g:if>
    			<table>
    			<thead>
    					<tr>

    						<g:sortableColumn property="name" title="${message(code: 'enterprise.name.label', default: 'Name')}" />

    						<g:sortableColumn property="activeDate" title="${message(code: 'enterprise.activeDate.label', default: 'Active Date')}" />

    						<th><g:message code="enterprise.location.label" default="Location" /></th>

    					</tr>
    				</thead>
    				<tbody>
    				<g:each in="${allEnts}" status="i" var="enterpriseInstance">
    					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

    						<td><g:link controller="enterprise" action="show" id="${enterpriseInstance.id}">${fieldValue(bean: enterpriseInstance, field: "name")}</g:link></td>

    						<td><g:formatDate date="${enterpriseInstance.activeDate}" /></td>

    						<td>${fieldValue(bean: enterpriseInstance, field: "location")}</td>

    					</tr>
    				</g:each>
    				</tbody>
    			</table>
    			<div class="pagination">
    				<g:paginate total="${enterpriseInstanceCount ?: 0}" />
    			</div>
    		</div>
    <!--
    <table>
        <tr><th>Enterprise</th><th>Active</th></tr>
        <g:each in="${allEnts}">
        <tr><td>${it.name}</td><td><g:formatDate date="${it.activeDate}" type="datetime" style="SHORT"/></td></tr>
        </g:each>
    </table>
  -->
</body>
</html>