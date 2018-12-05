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
			<label>Disk Name *</label> <input type="text" class="form-control"
				id="disk_name" name="disk_name" placeholder="Disk Name"
				value="${disk_name}">
		</div>
		<div class="col-sm-4">
			<label>Disk Path *</label> <input type="text" class="form-control"
				id="disk_path" name="disk_path" placeholder="Disk Path"
				value="${disk_path}">
		</div>
		<div class="col-sm-4">
			<label>Data Path *</label> <input type="text" class="form-control"
				id="data_path" name="data_path" placeholder="Data Path"
				value="${data_path}">
		</div>
	</div>
	<div class="form-group">
		<label>Select System *</label> <select name="system" id="system"
			class="form-control">
			<option value="" selected disabled>Select System ...</option>
			<c:forEach items="${system}" var="system">
				<option value="${system}">${system}</option>
			</c:forEach>
		</select>
	</div>
</fieldset>
<button onclick="jsonconstruct('upd');"
	class="btn btn-rounded btn-gradient-info mr-2">Update</button>
<button onclick="jsonconstruct('del');"
	class="btn btn-rounded btn-gradient-info mr-2">Delete</button>