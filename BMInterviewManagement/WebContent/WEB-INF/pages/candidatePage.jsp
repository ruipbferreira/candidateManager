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

		var names = $('#name').val();
		var level = $('#level').val();
		var phone = $('#phone').val();
		var email = $('#email').val();
		var birthDate = $('#birthDate').val();
		var local = $('#local').val();
		var language = $('#language').val();
		var user = $('#user').val();
		var availability = $('#availability').val();
		var year = $('#year').val();
		var arqDev = $('#arqDev').val();
		var tecnology = $('#tecnology').val();
		var analisys = $('#analisys').val();
		var functional = $('#functional').val();
		var presentation = $('#presentation').val();
		var professionalObjectives = $('#professionalObjectives').val();
		var remuneration = $('#remuneration').val();
		var comments = $('#comments').val();

		$('.error').hide();
		var valid = true;
		//Name
		if (names == "" || validateLenght(names, 45)) {
			$('#name')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidName"/>" />');
			valid = false;
		}

		//Level
		if (level == "" || level == "-1") {
			$('#level')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidLevel"/>" />');
			valid = false;
		}
		//Phone
		if (phone == "" || !numberReg.test(phone)) {
			$('#phone')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidPhone"/>" />');
			valid = false;
		}
		//Email
		if (email == "" || !emailReg.test(email)) {
			$('#email')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidEmail"/>" />');
			valid = false;
		}
		//Birthdate
		if (birthDate == "" || !dateReg.test(birthDate)) {
			$('#birthDate')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidBirthDate"/>" />');
			valid = false;
		}
		//Local
		if (local == "" || local == "-1") {
			$('#local')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidLocal"/>" />');
			valid = false;
		}
		//Language
		if (language == "" || language == "-1") {
			$('#language')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidLanguage"/>" />');
			valid = false;
		}
		//User
		if (user == "" || user == "-1") {
			$('#user')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidUser"/>" />');
			valid = false;
		}
		//Availability
		if (validateLenght(availability, 45)) {
			$('#availability')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidAvailability"/>" />');
			valid = false;
		}
		//Year
		if (year != "") {
			if (!numberReg.test(year)) {
				$('#year')
						.after(
								'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidYear"/>" />');
				valid = false;
			}
		}
		//ArqDev
		if (validateLenght(arqDev, 512)) {
			$('#arqDev')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidArqDev"/>" />');
			valid = false;
		}
		//Tecnology
		if (validateLenght(tecnology, 512)) {
			$('#tecnology')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidTecnology"/>" />');
			valid = false;
		}
		//Analisys
		if (validateLenght(analisys, 512)) {
			$('#analisys')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidAnalisys"/>" />');
			valid = false;
		}
		//Functional
		if (validateLenght(functional, 512)) {
			$('#functional')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidFunctional"/>" />');
			valid = false;
		}
		//Presentation
		if (validateLenght(presentation, 512)) {
			$('#presentation')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidPresentation"/>" />');
			valid = false;
		}
		//ProfessionalObjectives
		if (validateLenght(professionalObjectives, 512)) {
			$('#professionalObjectives')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidProfessionalObjectives"/>" />');
			valid = false;
		}
		//Remuneration
		if (validateLenght(remuneration, 256)) {
			$('#remuneration')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidRemuneration"/>" />');
			valid = false;
		}
		//Comments
		if (validateLenght(comments, 2048)) {
			$('#comments')
					.after(
							'<img class="error" style="width: 14px;" src="resources/images/invalid.png" title="<spring:message code="invalidComments"/>" />');
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
				document.getElementById('candidateForm').submit();
				myFunction();
			}
		} else {
			alert('ha erros');
		}
	}
	
	function imageDownloadClick() {
		window.location = "myurl/" + 1;
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
						<c:if test="${!empty candidate.filename}">
							<a href="download/${candidate.id}"><img style="width: 20px;height: 20px; cursor: pointer;"
								src="resources/images/document.png" alt="Download" /></a>
						</c:if>
						<img style="width: 20px; cursor: pointer;"
						src="resources/images/save.png" alt="Gravar"
						onclick="imageSaveClick()" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="editForm">
		<form:form id="candidateForm" action="save" method="POST"
			commandName="candidate" autocomplete="off" enctype="multipart/form-data">
			<table>
				<h3>Informação Pessoal</h3>
				<tr>
					<form:hidden path="id" />
					<td><form:label path="name">
							<spring:message code="candidate.name" />
						</form:label></td>
					<td><form:input id="name" path="name" class="inputForm" /> <form:errors
							path="name" class="control-label" /></td>
					<td><form:label path="level">
							<spring:message code="candidate.level" />
						</form:label></td>
					<td><form:select id="level" path="level">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listLevels}" itemValue="code"
								itemLabel="name"></form:options>
						</form:select></td>
					<td><form:label path="phone">
							<spring:message code="candidate.phone" />
						</form:label></td>
					<td><form:input id="phone" path="phone" class="inputForm" /></td>
					<td><form:label path="email">
							<spring:message code="candidate.email" />
						</form:label></td>
					<td><form:input id="email" path="email" class="inputForm" /></td>
				</tr>
				<tr>
					<td><form:label path="birthDate">
							<spring:message code="candidate.birthDate" />
						</form:label></td>
					<fmt:formatDate var="fmtDate" value="${birthDate}"
						pattern="dd/MM/yyyy" />
					<td><form:input id="birthDate" path="birthDate"
							class="inputForm" value="${fmtDate}" /></td>
					<td><form:label path="local">
							<spring:message code="candidate.local" />
						</form:label></td>
					<td><form:select id="local" path="local.localId"
							class="inputForm">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listOfILocals}" itemValue="localId"
								itemLabel="name"></form:options>
						</form:select></td>
					<td><form:label path="language">
							<spring:message code="candidate.language" />
						</form:label></td>
					<td><form:select id="language" path="language.languageId">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listOfILanguages}" itemValue="languageId"
								itemLabel="name"></form:options>
						</form:select></td>
					<td><form:label path="user">
							<spring:message code="candidate.manager" />
						</form:label></td>
					<td><form:select id="user" path="user.userId">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listOfUsers}" itemValue="userId"
								itemLabel="fullName"></form:options>
						</form:select></td>
				</tr>
				<tr>
					<td colspan="2"><input name="file" type="file"/></td>
				</tr>
			</table>
			<table>
				<h3>Informação Profissional</h3>
				<tr>
					<form:hidden path="professionalInfo.infoId" />
					<td><form:label path="professionalInfo.situation">
							<spring:message code="candidate.situation" />
						</form:label></td>
					<td><form:select path="professionalInfo.situation">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listSituations}" itemValue="code"
								itemLabel="name"></form:options>
						</form:select></td>
					<td rowspan="10"><form:label path="professionalInfo.comments">
							<spring:message code="candidate.comments" />
						</form:label></td>
					<td rowspan="10"><form:textarea id="comments"
							path="professionalInfo.comments" class="inputForm" rows="45"
							cols="60" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.availability">
							<spring:message code="candidate.availability" />
						</form:label></td>
					<td><form:input id="availability"
							path="professionalInfo.availability" class="inputForm" size="42" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.year">
							<spring:message code="candidate.year" />
						</form:label></td>
					<td><form:input id="year" path="professionalInfo.year"
							class="inputForm" size="42" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.arqDev">
							<spring:message code="candidate.arqDev" />
						</form:label></td>
					<td><form:textarea id="arqDev" path="professionalInfo.arqDev"
							class="inputForm" cols="40" rows="5" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.tecnology">
							<spring:message code="candidate.tecnologies" />
						</form:label></td>
					<td><form:textarea id="tecnology"
							path="professionalInfo.tecnology" class="inputForm" cols="40"
							rows="5" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.analisys">
							<spring:message code="candidate.analisys" />
						</form:label></td>
					<td><form:textarea id="analisys"
							path="professionalInfo.analisys" class="inputForm" cols="40"
							rows="5" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.functional">
							<spring:message code="candidate.functional" />
						</form:label></td>
					<td><form:textarea id="functional"
							path="professionalInfo.functional" class="inputForm" cols="40"
							rows="5" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.presentation">
							<spring:message code="candidate.presentation" />
						</form:label></td>
					<td><form:textarea id="presentation"
							path="professionalInfo.presentation" class="inputForm" cols="40"
							rows="5" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.professionalObjectives">
							<spring:message code="candidate.professionalObjectives" />
						</form:label></td>
					<td><form:textarea id="professionalObjectives"
							path="professionalInfo.professionalObjectives" class="inputForm"
							cols="40" rows="5" /></td>
				</tr>
				<tr>
					<td><form:label path="professionalInfo.remuneration">
							<spring:message code="candidate.remuneration" />
						</form:label></td>
					<td><form:input id="remuneration"
							path="professionalInfo.remuneration" class="inputForm" size="42" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>