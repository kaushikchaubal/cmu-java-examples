<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>AddrBook</title>
		<link href="search-form.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="ajax.js"> </script>
		<script type="text/javascript" src="search-suggest.js"> </script>
	</head>

	
	<body onLoad="document.login.userName.focus()">

		<jsp:include page="search-form.jsp"/>

		<h2>AddrBook Login</h2>

		<jsp:include page="errors-and-messages.jsp"/>
		
		<form name="login" action="login.do" method="post">
			<input type="hidden" name="redirect" value="${form.redirect}" />
			<table>
			    <tr>
			    	<td><font size="4">User Name:</font></td>
			    	<td><font size="4"><input type="text" name="userName" size="20" value="${form.userName}" /></font></td>
			    </tr>
			    <tr>
			    	<td><font size="4">Password:</font></td>
			    	<td><font size="4"><input type="password" name="password" size="20" /></font></td>
			    </tr>
			    <tr>
			    	<td></td>
			    	<td><font size="4"><input type="submit" value="Submit" name="Submit" /></font></td>
			    </tr>
			</table>
		</form>
		
	</body>
</html>
