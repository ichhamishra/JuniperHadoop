<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Propagation</h4>
						<p class="card-description">Target Details</p>
						<%
							if (request.getAttribute("successString") != null) {
						%>
						<p class="text-success h4">${successString}</p>
						<%
							}
						%>
						<%
							if (request.getAttribute("errorString") != null) {
						%>
						<p class="text-danger h4">${errorString}</p>
						<%
							}
						%>
						<form class="forms-sample" id="Details" name="Details"
							method="POST"
							action="${pageContext.request.contextPath}/extraction/Details1"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="counter" id="counter" class="form-control"
								value="1"> <input type="hidden" name="button_type"
								id="button_type" value="">
								<input type="hidden" name="material"
								id="material" value="" class="form-control">
								<input type="hidden" name="project" id="project" class="form-control"
								value="${project}">
								<input type="hidden" name="user" id="user" class="form-control"
								value="${usernm}">
<fieldset class="fs">
<div class="form-group">
		<label>Select System *</label> <select name="system" id="system"
			class="form-control">
			<option value="" selected disabled>Select System...</option>
			<c:forEach items="${system}" var="system">
				<option value="${system}">${system}</option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group row">
		<div class="col-sm-12">
			<label>Connection Name *</label> <input type="text"
				class="form-control" id="connection_name" name="connection_name"
				placeholder="Connection Name" value="${conn_val.connection_name}">
		</div>
	</div>
	<div class="form-group row">
		<div class="col-sm-4">
			<label>Knox Host Name *</label> <input type="text" class="form-control"
				id="host_name" name="host_name" placeholder="Host Name"
				value="${conn_val.host_name}">
		</div>
		<div class="col-sm-4">
			<label>Knox Port Number *</label> <input type="text" class="form-control"
				id="port" name="port" placeholder="Port Number"
				value="${conn_val.port_no}">
		</div>
		<div class="col-sm-4">
			<label>Knox Gateway *</label> <input type="text" class="form-control"
				id="user_name" name="user_name" placeholder="Username"
				value="${conn_val.username}">
		</div>
		</div>
		<div class="form-group row">
		<div class="col-sm-6">
			<label>Username *</label> <input type="text" class="form-control"
				id="user_name" name="user_name" placeholder="Username"
				value="${conn_val.username}">
		</div>
	<div class="col-sm-6">
			<label>Password *</label> <input type="text" class="form-control"
				id="user_name" name="user_name" placeholder="Username"
				value="${conn_val.username}">
		</div>
	</div>
	<div class="form-group row">
		<div class="col-sm-12">
			<label>Trust Store Path *</label> <input type="text"
				class="form-control" id="connection_name" name="connection_name"
				placeholder="Connection Name" value="${conn_val.connection_name}">
		</div>
	</div>
	<div class="form-group row">
<div class="col-sm-12">
			<label>Trust Store Password *</label> <input type="text" class="form-control"
				id="user_name" name="user_name" placeholder="Username"
				value="${conn_val.username}">
		</div>
	</div>
	</fieldset>
<button onclick="jsonconstruct('upd');"
	class="btn btn-rounded btn-gradient-info mr-2">Test and Save</button>
	</div>
	</div>
	</div>
	</div>