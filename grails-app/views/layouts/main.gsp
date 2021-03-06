<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><g:layoutTitle default="InfraViewer - The Civic Infrastructure Manager"/></title>
		<g:layoutHead/>
            <asset:javascript src="application.js"/>  
            <g:javascript library="jquery" plugin="jquery"/>
            <asset:stylesheet href="application.css"/>		
	</head>
	<body>
	<table><tr><td>
	    <div align='left'>InfraViewer</div>
	    </td><td>
	    <div align='right'>
	    <sec:ifLoggedIn>
	    Hello <sec:username/> - <g:link controller='logout'>Log out</g:link>
	    </sec:ifLoggedIn>
	    <sec:ifNotLoggedIn>
        <g:link controller='login'>Login</g:link>
        </sec:ifNotLoggedIn>
        </div>
     </td></tr></table>
		<g:layoutBody/>		
	</body>
</html>
