<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct() {
		var schema = document.getElementById("schema_name").value;
		for (var y = 1; y <= document.getElementById("counter").value; y++) {
			multisel('col_name' + y, 'columns_name' + y);
			if (document.getElementById("where_clause" + y).value === "") {
				document.getElementById("where_clause" + y).value = "1=1";
			}
		}
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
	var i = 1;
	function dup_div() {
		var tbl = document.getElementById('table_name' + i);
		var col = document.getElementById('col_name' + i);
		var whr = document.getElementById('where_clause' + i);
		var fth = document.getElementById('fetch_type' + i);
		var inc = document.getElementById('incr_col' + i);
		var tbldiv = tbl.parentNode.cloneNode(true);
		var coldiv = col.parentNode.cloneNode(true);
		var whrdiv = whr.parentNode.cloneNode(true);
		var fthdiv = fth.parentNode.cloneNode(true);
		var incdiv = inc.parentNode.cloneNode(true);
		var tbl1 = tbldiv.childNodes[3];
		var col1 = coldiv.childNodes[3];
		var whr1 = whrdiv.childNodes[3];
		var fth1 = fthdiv.childNodes[3];
		var inc1 = incdiv.childNodes[3];

		var x = ++i;
		tbl1.id = "table_name" + x;
		col1.id = "col_name" + x;
		whr1.id = "where_clause" + x;
		fth1.id = "fetch_type" + x;
		inc1.id = "incr_col" + x;
		tbl1.name = "table_name" + x;
		col1.name = "col_name" + x;
		whr1.name = "where_clause" + x;
		fth1.name = "fetch_type" + x;
		inc1.name = "incr_col" + x;
		incdiv.id = "incc" + x;

		var c = document.createElement('div');
		c.className = "form-group row";
		var d = document.createElement('div');
		d.id = "fldd" + i;
		var del = document.createElement('div');
		del.id = "delete" + x;
		del.style.cssFloat = "right";
		del.innerHTML = '<button id="del'
				+ x
				+ '" type="button" class="btn btn-rounded btn-gradient-danger mr-2" onclick="delblock(\''
				+ x + '\')">X</button>';

		tbl.parentNode.parentNode.parentNode.appendChild(del);
		tbl.parentNode.parentNode.parentNode.appendChild(c);
		c.appendChild(tbldiv);
		c.appendChild(fthdiv);
		col.parentNode.parentNode.parentNode.appendChild(d);
		d.appendChild(incdiv);
		d.appendChild(coldiv);
		whr.parentNode.parentNode.appendChild(whrdiv);

		var counter = document.getElementById('counter').value;
		for (var j = 1; j <= counter; j++) {
			var vl = document.getElementById('table_name' + j).value;
			var curr = document.getElementById(tbl1.id);
			for (var k = 0; k < curr.length; k++) {
				if (curr.options[k].value == vl)
					curr.remove(k);
			}
		}

		document.getElementById('incc' + x).style.display = "none";
		document.getElementById('counter').value = i;
	}
	function delblock(val) {
		document.getElementById('table_name' + val).parentNode.parentNode.id = "del";
		document.getElementById("del").innerHTML = "";
		document.getElementById("del").remove();
		document.getElementById('col_name' + val).parentNode.parentNode.id = "del";
		document.getElementById("del").innerHTML = "";
		document.getElementById("del").remove();
		document.getElementById('where_clause' + val).parentNode.id = "del";
		document.getElementById("del").innerHTML = "";
		document.getElementById("del").remove();
		document.getElementById("delete" + val).remove();
		if (val < document.getElementById('counter').value) {
		var p;
			for (var q = parseInt(val) + 1; q <= document.getElementById('counter').value; q++) {
				p = parseInt(q) - 1; 
				document.getElementById('table_name' + q).id = 'table_name' + p;
				document.getElementById('col_name' + q).id = 'col_name' + p;
				document.getElementById('where_clause' + q).id = 'where_clause'
						+ p;
				document.getElementById('fetch_type' + q).id = 'fetch_type' + p;
				document.getElementById('incr_col' + q).id = 'incr_col' + p;
				document.getElementById('incc' + q).id = 'incc' + p;
				document.getElementById('fldd' + q).id = 'fldd' + p;
				document.getElementById('del' + q).id = 'del' + p;
				document.getElementById('delete' + q).id = 'delete' + p;
				document.getElementById('columns_name' + q).id = 'columns_name' + p;
				document.getElementById('table_name' + p).name = 'table_name' + p;
				document.getElementById('col_name' + p).name = 'col_name' + p;
				document.getElementById('where_clause' + p).name = 'where_clause'
						+ p;
				document.getElementById('fetch_type' + p).name = 'fetch_type' + p;
				document.getElementById('incr_col' + p).name = 'incr_col' + p;
				document.getElementById('columns_name' + p).name = 'columns_name' + p;
			}
		}
		i = document.getElementById('counter').value;
		--i;
		document.getElementById('counter').value = i;
	}
	function incr(id, val) {
		var in1 = id.slice(-1);
		var in2 = id.slice(-2, -1);
		if (in2 === "e")
			;
		else {
			in1 = id.slice(-2);
		}
		var in3 = 'incc' + in1;
		if (val == "incr") {
			document.getElementById(in3).style.display = "block";
		} else if (val == "full") {
			document.getElementById(in3).style.display = "none";
		}
	}
	function getcols(id, val) {
		var in1 = id.slice(-1);
		var in2 = id.slice(-2, -1);
		if (in2 === "e")
			;
		else {
			in1 = id.slice(-2);
		}
		var id = in1;
		var table_name = val;
		var src_val = document.getElementById("src_val").value;
		var connection_id = document.getElementById("connection_id").value;
		var schema_name = document.getElementById("schema_name").value;
		$.post('${pageContext.request.contextPath}/extraction/DataDetailsOracle2', {
			id : id,
			src_val : src_val,
			table_name : table_name,
			connection_id : connection_id,
			schema_name : schema_name
		}, function(data) {
			$("#fldd" + id).html(data);
		});
	}
	$(document).ready(function() {
		$("#src_sys_id").change(function() {
			var src_sys_id = $(this).val();
			var src_val = document.getElementById("src_val").value;
			if(document.getElementById('radio1').checked)
			{
				$.post('${pageContext.request.contextPath}/extraction/DataDetailsOracle0', {
					src_sys_id : src_sys_id,
					src_val : src_val
				}, function(data) {
					$('#schdyn').html(data)
				});
			}
			else if(document.getElementById('radio2').checked) 
			{
				$.post('${pageContext.request.contextPath}/extraction/DataDetailsEditOracle', {
					src_sys_id : src_sys_id,
					src_val : src_val
				}, function(data) {
					$('#datdyn').html(data)
				});
			}
		});
	});

	function funccheck(val) {
		if (val == 'create') {
			window.location.reload();
		} else if(val=='edit') {
			document.getElementById('datdyn').innerHTML="";
			document.getElementById('schdyn').innerHTML="";
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
							method="POST" action="${pageContext.request.contextPath}/extraction/DataDetailsOracle3"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="src_val" id="src_val" value="${src_val}">

							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Data Tables</label>
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
							<div id="schdyn"></div>
							<div id="datdyn"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
<jsp:include page="../cdg_footer.jsp" />