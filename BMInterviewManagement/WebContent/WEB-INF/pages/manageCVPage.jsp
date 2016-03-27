<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>

<link rel="icon" href="resources/images/aubay.ico" type="image/x-icon" />
<link rel="shortcut icon" href="resources/images/aubay.ico"
	type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="resources/css/manageCV.css">
<link rel="stylesheet" type="text/css" href="resources/css/common.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<title>Aubay - ${pageContext.request.userPrincipal.name}</title>

</head>
<script type="text/javascript">
	$(document).ready(function() {
		$('#table >tbody > tr').dblclick(function() {
			var $this = $(this);
			var row = $this.closest("tr");
			var id = row.data("id");

			editGridClick(id);
		});
	});

	function exportSearch(candidate) {
		alert(candidate);
	}
	
	function imageClick(url) {
		window.location = url;
	}

	function removeGridClick(id, msg) {
		var r = confirm(msg);
		if (r == true) {
			window.location = "remove/" + id;
		}
	}

	function imageNewClick() {
		var y = window.top.outerHeight / 2 + window.top.screenY - (700 / 2)
		var x = window.top.outerWidth / 2 + window.top.screenX - (1000 / 2)
		window
				.open(
						"candidate",
						'window',
						'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=1000, height=700, top='
								+ y + ', left=' + x);
		window.refresh = function(){
			document.getElementById("searchCandidate").submit();
		}
	}
	function editGridClick(id) {

		var y = window.top.outerHeight / 2 + window.top.screenY - (700 / 2)
		var x = window.top.outerWidth / 2 + window.top.screenX - (1000 / 2)
		window
				.open(
						"candidate?id=" + id,
						'window',
						'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=1000, height=700, top='
								+ y + ', left=' + x);
		window.refresh = function(){
			document.getElementById("searchCandidate").submit();
		}
	}
	function clearForm() {
		document.getElementById("searchCandidate").reset();
	}
</script>
<body>
	<div id="fixedheader">
		<table style="height: auto" width="95%">
			<tbody>
				<tr>
					<td><img style="height: 20px;"
						src="resources/images/Aubay_-_Logo.png" alt="aubay" /></td>
					<td align="right"><img style="width: 20px; cursor: pointer;"
						src="resources/images/add-user-icon.png" alt="Novo"
						onclick="imageNewClick()" /></td>
					<td align="right" width="20px"><img style="width: 20px; cursor: pointer;"
						src="resources/images/logout.png" alt="logout"
						onclick="imageClick('logout')" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="searchForm">
		<form:form id="searchCandidate" action="manageCV" method="POST" commandName="candidate" autocomplete="off">
			<table>

				<tr>
					<td align="right"><form:label path="name">
							<spring:message code="candidate.name" /> 
						</form:label></td>
					<td><form:input path="name" class="inputForm" /></td>
					<td align="right"><form:label path="level">
							<spring:message code="candidate.level" />
						</form:label></td>
					<td><form:select path="level">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listLevels}" itemValue="code"
								itemLabel="name"></form:options>
						</form:select></td>
					<td align="right"><form:label path="phone">
							<spring:message code="candidate.phone" />
						</form:label></td>
					<td><form:input path="phone" class="inputForm" /></td>
					<td align="right"><form:label path="email">
							<spring:message code="candidate.email" />
						</form:label></td>
					<td><form:input path="email" class="inputForm" /></td>
					<td><input type="submit" name="search" style="width:100px;"
						value="<spring:message code="search"/>" /></td>
				</tr>
				<tr>
					<td align="right"><form:label path="local">
							<spring:message code="candidate.local" />
						</form:label></td>
					<td><form:select path="local.localId" class="inputForm">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listOfILocals}" itemValue="localId"
								itemLabel="name"></form:options>
						</form:select></td>
					<td align="right"><form:label path="language">
							<spring:message code="candidate.language" />
						</form:label></td>
					<td><form:select path="language.languageId">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listOfILanguages}" itemValue="languageId"
								itemLabel="name"></form:options>
						</form:select></td>
					<td align="right"><form:label path="user">
							<spring:message code="candidate.manager" />
						</form:label></td>
					<td><form:select path="user.userId">
							<form:option value="-1"> --SELECT--</form:option>
							<form:options items="${listOfUsers}" itemValue="userId"
								itemLabel="fullName"></form:options>
						</form:select></td>
					<td align="right"><form:label path="professionalInfo.year">
							<spring:message code="candidate.year" />
						</form:label></td>
					<td><form:input path="professionalInfo.year"
							class="inputForm"/></td>
					<td><input type="reset" style="width:100px;" name="clear"
						value="<spring:message code="clear"/>" /></td>
				</tr>
				<tr>
					<td align="right"><form:label path="professionalInfo.tecnology">
							<spring:message code="candidate.tecnologies" />
						</form:label></td>
					<td><form:input path="professionalInfo.tecnology" class="inputForm" /></td>
					<td align="right"><form:label path="professionalInfo.arqDev">
							<spring:message code="candidate.arqDev" />
						</form:label></td>
					<td><form:input path="professionalInfo.arqDev" class="inputForm" /></td>
					<td align="right"><form:label path="professionalInfo.functional">
							<spring:message code="candidate.functional" />
						</form:label></td>
					<td><form:input path="professionalInfo.functional"
							class="inputForm" /></td>
					<td></td>
					<td></td>
					<td><input type="button" style="width:100px;" onclick="window.location.href='export'"
						value="<spring:message code="export"/>" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	<div>
		<table id="table" class="tg">
			<thead>
				<tr>
					<th width="20%"><spring:message code="candidate.name"/></th>
					<th width="20%"><spring:message code="candidate.email"/></th>
					<th width="5%"><spring:message code="candidate.level"/></th>
					<th width="10%"><spring:message code="candidate.phone"/></th>
					<th width="20%"><spring:message code="candidate.manager"/></th>
					<th width="10%"><spring:message code="candidate.local"/></th>
					<th width="5%">CV</th>
					<th width="5%"></th>
					<th width="5%"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${result}" var="candidate">
					<tr data-id=${candidate.id}>
						<td>${candidate.name}</td>
						<td>${candidate.email}</td>
						<td>${candidate.level}</td>
						<td>${candidate.phone}</td>
						<td>${candidate.user.fullName}</td>
						<td>${candidate.local.name}</td>
						<td style="text-align: center;">
							<c:if test="${!empty candidate.filename}">
								<a href="download/${candidate.id}"><img style="height: 20px; cursor: pointer;"
									src="resources/images/download_grid.png" alt="Download" /></a>
							</c:if>
						</td>
						<td style="text-align: center;"><img style="height: 20px; cursor: pointer;"
							src="resources/images/edit_grid.png" alt="Editar"
							onclick="editGridClick('${candidate.id}')" /></td>
						<td style="text-align: center;"><img style="height: 16px; cursor: pointer;"
							src="resources/images/remove_grid.png" alt="Remover"
							onclick="removeGridClick('${candidate.id}', '<spring:message code="removeAsk"/>')" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="fixedfooter">Aubay - 2016</div>
</body>
</html>