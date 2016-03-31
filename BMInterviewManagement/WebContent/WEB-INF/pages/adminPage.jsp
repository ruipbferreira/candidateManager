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
<title>Aubay</title>

</head>
<script type="text/javascript">
	
	function imageClick(url) {
		window.location = url;
	}

	function removeGridClick(id, msg) {
		var r = confirm(msg);
		if (r == true) {
			window.location = "removeUser/" + id;
		}
	}

	function imageNewClick() {
		var y = window.top.outerHeight / 2 + window.top.screenY - (300 / 2)
		var x = window.top.outerWidth / 2 + window.top.screenX - (400 / 2)
		window
				.open(
						"user",
						'window',
						'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=400, height=300, top='
								+ y + ', left=' + x);
		window.refresh = function(){
			document.getElementById("searchCandidate").submit();
		}
	}
	function editGridClick(id) {

		var y = window.top.outerHeight / 2 + window.top.screenY - (300 / 2)
		var x = window.top.outerWidth / 2 + window.top.screenX - (400 / 2)
		window
				.open(
						"user?id=" + id,
						'window',
						'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=400, height=300, top='
								+ y + ', left=' + x);
		window.refresh = function(){
			document.getElementById("searchCandidate").submit();
		}
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
					<td align="right" width="20px"><img
						style="width: 20px; cursor: pointer;"
						src="resources/images/logout.png" alt="logout"
						onclick="imageClick('logout')" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div>
		<table id="table" class="tg">
			<tr>
				<th width="30%">Username</th>
				<th width="30%">Nome</th>
				<th width="30%">Email</th>
				<th width="5%"></th>
				<th width="5%"></th>
			</tr>
			<c:if test="${!empty listUsers}">
				<c:forEach items="${listUsers}" var="user">
					<tr>
					<tr data-id=${user.userId}>
						<td>${user.username}</td>
						<td>${user.fullName}</td>
						<td>${user.email}</td>
						<td style="text-align: center;"><img
							style="height: 20px; cursor: pointer;"
							src="resources/images/edit_grid.png" alt="Editar"
							onclick="editGridClick('${user.userId}')" /></td>
						<td style="text-align: center;">
							<c:if test="${user.enabled}">
								<img
									style="height: 20px; cursor: pointer;"
									src="resources/images/disable.png" alt="Inactivar"
									onclick="removeGridClick('${user.userId}', '<spring:message code="inactiveAsk"/>')" />
							</c:if>	
							<c:if test="${not user.enabled}">
								<img
									style="height: 20px; cursor: pointer;"
									src="resources/images/enable.png" alt="Activar"
									onclick="removeGridClick('${user.userId}', '<spring:message code="activeAsk"/>')" />
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>

	</div>
	<div id="fixedfooter">Aubay - 2016</div>
</body>
</html>