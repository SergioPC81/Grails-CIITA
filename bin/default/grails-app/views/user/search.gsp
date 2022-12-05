<html>
<head>
    <title>Search Hubbub</title>
    <meta name = "layout" content = "main"/>
</head>
<body>
    <formset>
        <legend>Search for friends</legend>
        <g:form action = "results">
            <label for = "loginId">loginId</label>
            <g:textField name = "LoginId" />
            <g:submitButton name = "search" value = "Search"/>
        </g:form>
    </formset>
</body>
</html>