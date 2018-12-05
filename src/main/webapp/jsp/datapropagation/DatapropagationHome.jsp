<jsp:include page="../cdg_header.jsp" />

<script>
	function pass(val) {
		document.getElementById('src_val').value = val;
		document.getElementById('DatapropagationHome').submit();
	}
</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Data Propagation</h4>
						
						<form class="forms-sample" id="DatapropagationHome" name="DatapropagationHome"
							method="post"
							action="${pageContext.request.contextPath}/datapropagation/ArchiveData">
							<input type="hidden" name="src_val" id="src_val" value="">
							<div class="container">
								<div class="row text-center text-lg-left">
									
									<div class="thumbnail col-lg-3 col-md-4 col-xs-6">
										<a href="#" class="d-block mb-4 h-100"
											onclick="pass('Hadoop');"> <img
											class="img-fluid img-thumbnail"
											src="${pageContext.request.contextPath}/assets/img/hadoop.png">
										</a>
									</div>
									
									
								    <div class="thumbnail col-lg-3 col-md-4 col-xs-6">
								      <a class="d-block mb-4 h-100" href="#" onclick="pass('Hive');">
								      	<img class="img-fluid img-thumbnail" src="${pageContext.request.contextPath}/assets/img/hive.png">
								      </a> 
								    </div>
									<div class="thumbnail col-lg-3 col-md-4 col-xs-6">
										<a class="d-block mb-4 h-100" href="#"> <img
											class="img-fluid img-thumbnail"
											src="${pageContext.request.contextPath}/assets/img/hbase.png">
										</a>
									</div>
									
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<jsp:include page="../cdg_footer.jsp" />