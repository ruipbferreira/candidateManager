<html>
<head>
<link rel="icon" href="resources/images/aubay.ico" type="image/x-icon" />
<link rel="shortcut icon" href="resources/images/aubay.ico" type="image/x-icon" />
<title>Login</title>
<style type="text/css">
body {
	text-align: center;
}
/* center all items within body, this property is inherited */
body>* {
	text-align: left;
}
/* left-align the CONTENTS all items within body, additionally
        you can add this text-align: left property to all elements
        manually */
form {
	display: inline-block;
}

html, body {
	height: 100%;
}

div#form-wrapper {
	position: absolute;
	top: 50%;
	right: 0;
	left: 0;
	display: inline-block;
}
</style>
</head>
<body>
	<div style="width: 100%; text-align: center">
		<img src="resources/images/aubay.jpeg"
			style="width: 129px; height: 129px; margin: 0 auto;">
	</div>
	<div id="form-wrapper"
		style="text-align: center; vertical-align: middle">
		<form name='f' action="j_spring_security_check" method='POST'>
			<table>
				<tr>
					<td>Username:</td>
					<td><input type='text' name='username' value=''></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td></td>
					<td><input name="submit" type="submit" value="submit" style="text-align: center;"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>