<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <r:require module="application" />
</head>
<body>
    <p>Home Controller</p>
    <sec:ifAnyGranted roles="ROLE_ADMIN">Only Admin Users can see this</sec:ifAnyGranted>
</body>
</html>