
<html>
<head>
	<meta name='layout' content='main'/>
	<title>Infraview - The Civic Infrastructure Manager</title>
	<r:require module='application' />
</head>
<body>
<script>

</script>
    <h2>System Administration</h2>
    </p>
    <table>
        <tr><th>Enterprise</th><th>Active</th></tr>
        <g:each in="${allEnts}">
        <tr><td>${it.name}</td><td><g:formatDate date="${it.activeDate}" type="datetime" style="SHORT"/></td></tr>
        </g:each>
    </table>

</body>
</html>