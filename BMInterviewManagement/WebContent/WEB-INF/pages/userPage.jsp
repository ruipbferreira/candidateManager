<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link rel="icon" href="resources/images/aubay.ico" type="image/x-icon" />
<link rel="shortcut icon" href="resources/images/aubay.ico"
	type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="resources/css/common.css">
<title>${title}</title>
<script type="text/javascript">
	function validateForm() {

		var nameReg = /^[A-Za-z]+$/;
		var numberReg = /^[0-9]+$/;
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var dateReg = /^(?:(0[1-9]|1[012])[\/.](0[1-9]|[12][0-9]|3[01])[\/.](19|20)[0-9]{2})$/;

		var username = $('#username').val();
		var fullname = $('#fullName').val();
		var email = $('#email').val();

		$('.error').hide();
		var valid = true;
		//Name
		if (username == "" || validateLenght(username, 45)) {
			$('#username')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidUsername"/>" />');
			valid = false;
		}
		
		//Fullname
		if (fullname == "" || validateLenght(fullname, 45)) {
			$('#fullName')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidUsername"/>" />');
			valid = false;
		}

		//Email
		if (email == "" || !emailReg.test(email)) {
			$('#email')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidEmail"/>" />');
			valid = false;
		}
		return valid;

	}

	function validateLenght(str, lenght) {
		return str.length > lenght;
	}

	function imageSaveClick() {
		if (validateForm()) {
			var r = confirm("Tem a certeza que quer gravar o registo?");
			if (r == true) {
				document.getElementById('userForm').submit();
				myFunction();
			}
		} else {
			alert('ha erros');
		}
	}
	
	function myFunction() {
		window.onunload = function(e) {
			opener.refresh();
		};
	}
</script>

</head>
<body>
	<div id="fixedheader">
		<table style="height: auto" width="95%">
			<tbody>
				<tr>
					<td><img style="height: 20px;"
						src="resources/images/Aubay_-_Logo.png" alt="aubay" /></td>
					<td align="right">
						<img style="width: 20px; cursor: pointer;"
						src="resources/images/save.png" alt="Gravar"
						onclick="imageSaveClick()" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="editForm">
		<form:form id="userForm" action="saveUser" method="POST"
			commandName="user" autocomplete="off" enctype="multipart/form-data">
			<table>
				<h3>Informação Utilizador</h3>
				<tr>
					<form:hidden path="userId" />
					<form:hidden path="role" />
					<form:hidden path="password" />
					<td><form:label path="username">
							<spring:message code="user.username" />
						</form:label></td>
					<td>
						<c:if test="${!empty user.userId}">
							<form:input id="username" readonly="true" path="username" class="inputForm" /> <form:errors
								path="username" class="control-label" />
						</c:if>
						<c:if test="${empty user.userId}">
							<form:input id="username" readonly="false" path="username" class="inputForm" /> <form:errors
								path="username" class="control-label" />
						</c:if>	
					</td>
				</tr>
				<tr>
					<td><form:label path="fullName">
							<spring:message code="user.fullname" />
						</form:label></td>
					<td><form:input id="fullName" path="fullName" class="inputForm" /></td>
				</tr>
				<tr>
					<td><form:label path="email">
							<spring:message code="user.email" />
						</form:label></td>
					<td><form:input id="email" path="email" class="inputForm" /></td>
				</tr>
				<tr>
					<td><form:label path="enabled">
							<spring:message code="user.enabled" />
						</form:label></td>
					<td><form:checkbox id="enabled" path="enabled" class="inputForm" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>