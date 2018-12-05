<jsp:include page="../cdg_header.jsp" />
<script>
function pass(val)
{
document.getElementById('tgt_val').value=val;	
document.getElementById('loadconn1').submit();
}
</script>
<div class="main-panel">
<div class="content-wrapper">
<div class="row">
   <div class="col-12 grid-margin stretch-card">
      <div class="card">
         <div class="card-body">
            <h4 class="card-title">Data Publishing</h4>
            <p class="card-description">
               Add Metadata
            </p>
             <%
              if(request.getAttribute("successString") != null) {
             %>
            	<div class="alert alert-success" id="success-alert">
    				<button type="button" class="close" data-dismiss="alert">x</button>
    		 		${successString}
				</div>
            <%
               }
               %>
            <%
             if(request.getAttribute("errorString") != null) {
             %>
            	<div class="alert alert-danger" id="error-alert">
    				<button type="button" class="close" data-dismiss="alert">x</button>
    		 		${errorString}
				</div>
            <%
             }
            %>
          <form id="loadconn1" method="post" action="/publishing/publishingAddMetaData">
           <input type="hidden" name="tgt_val" id="tgt_val" value="">
           	<div class="container">
				<div class="row text-center text-lg-left">
					<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
					      	<a class="d-block mb-4 h-100" href="#" onclick="pass('BigQuery');">
					      		<img class="img-fluid img-thumbnail" src="../assets/img/bigQuery.png" >
					      	</a>
					</div>
           			<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
					      	<a class="d-block mb-4 h-100" href="#" onclick="pass('Bigtable');">
					      		<img class="img-fluid img-thumbnail" src="../assets/img/bigTable.png" >
					      	</a>
					</div>
           			<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
					      	<a class="d-block mb-4 h-100" href="#" onclick="pass('MySQL');">
					      		<img class="img-fluid img-thumbnail" src="../assets/img/mysql.png" >
					      	</a>
					</div>
           			<div class="thumbnail col-lg-3 col-md-3 col-xs-6">
					      	<a class="d-block mb-4 h-100" href="#" onclick="pass('PostgreSQL');">
					      		<img class="img-fluid img-thumbnail" src="../assets/img/postgresql.png" >
					      	</a>
					</div>
          		</div>
          	</div>
          </form>
									</div></div></div></div>
<jsp:include page="../cdg_footer.jsp" />
<script>
$(document).ready(function () {
    $("#success-alert").hide();
           $("#success-alert").fadeTo(10000,10).slideUp(4000, function(){
           });   
    $("#error-alert").hide();
           $("#error-alert").fadeTo(10000,10).slideUp(4000, function(){
            });
    
});
</script>