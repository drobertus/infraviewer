<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><g:layoutTitle default="Infraviewer - The Civic Infrastructure Manager"/></title>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
	<table><tr><td>
	    <div align='left'>Infraviewer</div>
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
		<r:layoutResources />
	</body>
</html>
