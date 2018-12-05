<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<fieldset class="fs">
	<div class="form-group row">
		<div class="col-sm-6">
			<label>Connection Name *</label> <input type="text"
				class="form-control" id="connection_name" name="connection_name"
				placeholder="Connection Name" value="${conn_val.connection_name}">
		</div>
		<div class="col-sm-6">
			<label>Connection Type *</label> <input type="text"
				class="form-control" id="connection_type" name="connection_type"
				value="${src_val}" readonly="readonly">
		</div>
	</div>
	<div class="form-group row">
		<div class="col-sm-4">
			<label>Host Name *</label> <input type="text" class="form-control"
				id="host_name" name="host_name" placeholder="Host Name"
				value="${conn_val.host_name}">
		</div>
		<div class="col-sm-4">
			<label>Port Number *</label> <input type="text" class="form-control"
				id="port" name="port" placeholder="Port Number"
				value="${conn_val.port_no}">
		</div>
		<div class="col-sm-4">
			<label>Username *</label> <input type="text" class="form-control"
				id="user_name" name="user_name" placeholder="Username"
				value="${conn_val.username}">
		</div>
	</div>
	<!--  <div class="form-group row">
		<label class="col-sm-3 col-form-label">Authentication *</label>
		<div class="col-sm-4">
			<div class="form-check form-check-info">
				<label class="form-check-label"> <input type="radio"
					class="form-check-input" name="radio1" id="radio2"
					checked="checked" value="pass" onclick="funccheck1(this.value)">
					Password
				</label>
			</div>
		</div>
		<div class="col-sm-4">
			<div class="form-check form-check-info">
				<label class="form-check-label"> <input type="radio"
					class="form-check-input" name="radio1" id="radio2" value="pri"
					onclick="funccheck1(this.value)"> Keytab File
				</label>
			</div>
		</div>
	</div>-->
	<div class="form-group row">
		<div class="col-sm-6">
			<div class="form-group" id="pass">
				<label>Password *</label> <input type="password"
					class="form-control" id="password" name="password"
					placeholder="Password" value="${conn_val.password}">
			</div>
		</div>
		<!-- <div class="form-group" id="pri" style="display: none;">
		<label>Keytab File Path *</label> <input type="text"
			class="form-control" id="private_key_path" name="private_key_path"
			placeholder="Private Key Path">
	</div> -->
		<div class="col-sm-6">
			<div class="form-group">
				<label>Select System *</label> <select name="system" id="system"
					class="form-control">
					<option value="" selected disabled>Select System...</option>
					<c:forEach items="${system}" var="system">
						<option value="${system}">${system}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="form-group row">
		<div class="col-sm-12">
			<label>Keytab file path *</label> <input type="text"
				class="form-control" id="key_tab_path" name=""
				key_tab_path""
												placeholder="Key Tab File Path of service account">
		</div>
	</div>
</fieldset>
<button onclick="jsonconstruct('upd');"
	class="btn btn-rounded btn-gradient-info mr-2">Update</button>
<button onclick="jsonconstruct('del');"
	class="btn btn-rounded btn-gradient-info mr-2">Delete</button>