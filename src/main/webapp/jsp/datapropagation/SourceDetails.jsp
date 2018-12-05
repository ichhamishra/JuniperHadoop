<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<script>
function jsonconstructbulk(val) {		
		
		var data = {};
		//document.getElementById('cron').value = cron_construct();
		$(".form-control").serializeArray().map(function(x2) {
			data[x2.name] = x2.value;
		});
		var x2 = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
			+ JSON.stringify(data) + '}}';	
		document.getElementById('x2').value = x2;
		//console.log(x2);
		alert(x2);
		document.getElementById('addbatch').submit();
}
	
function jsonconstruct(id) {		
	
	var data = {};
	/* document.getElementById('cron').value = cron_construct();
	document.getElementById('xtype').value=id; */
	$(".form-control").serializeArray().map(function(x) {
		data[x.name] = x.value;
	});
	var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
		+ JSON.stringify(data) + '}}';		
	document.getElementById('x').value = x;
	//console.log(x);
	alert(x);
	document.getElementById('addBatch1').submit();
} 
 
	</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Propagation</h4>
						
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
						
<ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
  <li class="nav-item">
    <a class="nav-link active" id="pills-new" data-toggle="pill" href="#pills-new-sys" role="tab" aria-controls="pills-new" aria-selected="true">Source Details</a>
  
  </li>
  <li class="nav-item">
    <a class="nav-link" id="pills-existing" data-toggle="pill" href="#pills-exist-sys" role="tab" aria-controls="pills-existing" aria-selected="false">Target Details</a>
  </li>
</ul>

   <div class="tab-content" id="pills-tabContent">
	
	<!-- First Tab:Start -->
	<div class="tab-pane fade show active" id="pills-new-sys" role="tabpanel" aria-labelledby="pills-new-sys">
	<form class="forms-sample" name="addBatch1" id="addBatch1"
		action="${pageContext.request.contextPath}/datapropagation/SubmitBatch1"
		method="post">
		<input id="x" value="" type="hidden" name="x">
		<input id="xtype" value="" type="hidden" name="xtype" class="form-control">
		<input id="cron" value="" type="hidden" name="cron" class="form-control">	
	
		<br>
		<br>
<div id="src">
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
				id="Knox_host_name" name="Knox_host_name" placeholder="Knox Host Name"
				value="${conn_val.host_name}">
		</div>
		<div class="col-sm-4">
			<label>Knox Port Number *</label> <input type="text" class="form-control"
				id="Knox_port" name="Knox_port" placeholder="Knox Port Number"
				value="${conn_val.port_no}">
		</div>
		<div class="col-sm-4">
			<label>Knox Gateway *</label> <input type="text" class="form-control"
				id="Knox_gateway" name="Knox_gateway" placeholder="Knox Gateway"
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
			<label>Password *</label> 
			<input type="password" class="form-control" id="password" name="password" placeholder="Password">
		</div>
	</div>
	<div class="form-group row">
		<div class="col-sm-12">
			<label>Trust Store Path *</label> <input type="text"
				class="form-control" id="TS_path" name="TS_path"
				placeholder="select TS_path" value="${conn_val.connection_name}">
		</div>
	</div>
	<div class="form-group row">
<div class="col-sm-12">
			<label>Trust Store Password *</label> 
			<input type="password" class="form-control" id="TS_password" name="TS_password" placeholder="TS password">
		</div>
	</div>
</fieldset>
<br>
<button class="btn btn-rounded btn-gradient-info mr-2" onclick="jsonconstruct()">Test and Save</button>
	</div>
	</div>
	<!-- First Tab:End -->
	
	<div class="tab-pane fade" id="pills-exist-sys" role="tabpanel" aria-labelledby="pills-exist-sys">
	<form class="forms-sample" name="addBatch" id="addBatch"
		action="${pageContext.request.contextPath}/datapropagation/SubmitBatch"
		method="post">
	<input id="x2" value="" type="hidden" name="x2">		
		<br>
		<br>
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
				id="Knox_host_name" name="Knox_host_name" placeholder="Knox Host Name"
				value="${conn_val.host_name}">
		</div>
		<div class="col-sm-4">
			<label>Knox Port Number *</label> <input type="text" class="form-control"
				id="Knox_port" name="Knox_port" placeholder="Knox Port Number"
				value="${conn_val.port_no}">
		</div>
		<div class="col-sm-4">
			<label>Knox Gateway *</label> <input type="text" class="form-control"
				id="Knox_Gateway" name="Knox_Gateway" placeholder="Knox Gateway"
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
			<label>Password *</label> <input type="password" class="form-control" id="password" name="password"
												placeholder="Password">
		</div>
	</div>
	<div class="form-group row">
		<div class="col-sm-12">
			<label>Trust Store Path *</label> <input type="text"
				class="form-control" id="TS_path" name="TS_path"
				placeholder="select TS_path" value="${conn_val.connection_name}">
		</div>
	</div>
	<div class="form-group row">
<div class="col-sm-12">
			<label>Trust Store Password *</label> 
			<input type="password" class="form-control" id="TS_password" name="TS_password" placeholder="TS Password">
												
		</div>
	</div>
	</fieldset>
<button type="submit" class="btn btn-rounded btn-gradient-info mr-2" onclick="jsonconstructbulk()">Test and Save</button>
	</div>
	
	



</div>
		</div>
      </div>
   </div>
</div>
<jsp:include page="../cdg_footer.jsp" />