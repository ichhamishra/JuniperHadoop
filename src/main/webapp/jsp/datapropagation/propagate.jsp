<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
function jsonconstruct() {		
		
		var data = {};
		//document.getElementById('cron').value = cron_construct();
		$(".form-control").serializeArray().map(function(x3) {
			data[x3.name] = x3.value;
		});
		var x3 = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
			+ JSON.stringify(data) + '}}';	
		document.getElementById('x3').value = x3;
		//console.log(x3);
		alert(x3);
		document.getElementById('prop').submit();
	}

</script>
<script>
	$(document).ready(function() {

		$("#feed_name").on("change", function() {
			
			$("#ds1").show();
		})
		$("#ds1").on("change", function() {
			$("#ds2").show();
		})
		$("#ds2").on("change", function() {
			$("#ds3").show();
		})
		$("#ds3").on("change", function() {
			$("#ds4").show();
		})
	});
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
						

<form class="forms-sample" name="prop" id="prop"
		action="${pageContext.request.contextPath}/datapropagation/SubmitProp"
		method="post">
		<input id="x3" value="" type="hidden" name="x3">	
	<fieldset class="fs">
<div class="form-group">
		<div>
		<label>Propagate Feed Name<span style="color:red">*</span></label>
		<input type="text" class="form-control" id="feed_name" name="feed_name" placeholder="feed name" >
</div>
</div>
	<div class="form-group row">
										<div class="col-sm-12">
		<div id="ds1" style="display: none;">
		
	
		<label>Source Connection Name<span style="color:red">*</span></label> <select class="form-control"
										id="connection_id" name="connection_id">
										<option value="" selected disabled>${src_val}
											Sources...</option>
										<c:forEach items="${system}" var="system">
				<option value="${system}">${system}</option>
			</c:forEach>
									</select>
	</div>
	</div>
	</div>
	<div class="form-group row">
										<div class="col-sm-12">
		<div id="ds2" style="display: none;">
	<label>Source Database Name</label> 
	<select name="system" id="system"
			class="form-control">
			<option value="" selected disabled>Select connection name...</option>
			<c:forEach items="${system}" var="system">
				<option value="${system}">${system}</option>
			</c:forEach>
		</select>
	</div>
	</div>
	</div>
	<div id="ds3" style="display: none;">
	<div class="form-group">
	<div class="col-md-12"></div>
	<label>Target Connection Name<span style="color:red">*</span></label>
		<select name="system" id="system"
			class="form-control">
			<option value="" selected disabled>Select connection name...</option>
			<c:forEach items="${system}" var="system">
				<option value="${system}">${system}</option>
			</c:forEach>
		</select>
		</div>
	</div>
	<div id="ds4" style="display: none;">
	<div class="form-group">
	<div class="col-md-12"></div>
		<label>Preffered Target Database Name (not mandatory)</label>
	<input type="text" class="form-control" id="target_db_name" name="target_db_name" placeholder="Target Db Name" >
	</div>
	</div>

	</fieldset>
	</form>
	
	<br>
<button class="btn btn-rounded btn-gradient-info mr-2" onclick="jsonconstruct()">Propagate</button>
</div>
</div>
	</div>
</div>
</div>
<jsp:include page="../cdg_footer.jsp" />