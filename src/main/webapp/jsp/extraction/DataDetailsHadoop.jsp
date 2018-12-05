<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct() {
		var data = {};
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//alert(x);
		//console.log(x);
		document.getElementById('DataDetails').submit();
	}
	function funccheck(val) {
		if (val == 'create') {
			window.location.reload();
		} else if (val == 'edit') {
			$("#src_sys_id").val("");
		}
	}
</script>

<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Extraction</h4>
						<p class="card-description">Data Details</p>
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
						<form class="forms-sample" id="DataDetails" name="DataDetails"
							method="POST"
							action="${pageContext.request.contextPath}/extraction/DataDetailsHadoop1"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="src_val" id="src_val" value="${src_val}">

							<div class="form-group row">
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio1"
											checked="checked" value="create"
											onclick="funccheck(this.value)"> Create
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio2"
											value="edit" onclick="funccheck(this.value)"> Edit
										</label>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label>Source Feed Name *</label> <select name="src_sys_id"
									id="src_sys_id" class="form-control">
									<option value="" selected disabled>Source Feed Name
										...</option>
									<c:forEach items="${src_sys_val}" var="src_sys_val">
										<option value="${src_sys_val.src_sys_id}">${src_sys_val.src_unique_name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
									<label>HDFS Path *</label> <input type="text"
										class="form-control" id="hdfs_path" name="hdfs_path"
										placeholder="HDFS Path">
								</div>
							<button onclick="jsonconstruct();"
	class="btn btn-rounded btn-gradient-info mr-2">Save</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />