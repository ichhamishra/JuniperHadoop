<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<link href="${pageContext.request.contextPath}/assets/css/bootstrap-table.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/css/pagination.css" rel="stylesheet">
	
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap-table.min.js"></script>
<script>
$(document).ready(function () {
	var feed_id='${feed_id}'
	document.getElementById('feedIdFilter').value=feed_id;
	document.getElementById('feedid_s').value=feed_id;
});
</script>
<input type="hidden" name="eimvalidation" id="eimvalidation" value="${stat}">
<input type="hidden" id="x" value='${x}' name="x"/>
<input type="hidden" id="y" value='${y}'name="y"/>
<p></p>
<c:if test="${stat eq 0}">  
<div class="form-group row">
	<div class="col-md-3" ></div>
	<div class="col-md-6" >
		<h4 align="center">Duration of Feed in Minutes</h4>
	</div>
	<div class="col-md-3" ></div>
</div>	
<div class="container">
	    <div class="row">
	    <div class="col-md-2"></div>
	      <div class="col-md-3">
	      <div class="card-counter success">
	        <i class="fa fa-ticket"></i>
	        <span class="count-numbers">${min}</span>
	        <span class="count-name">Minimum</span>
	      </div>
	    </div>
		 <div class="col-md-3">
	      <div class="card-counter primary">
	        <i class="fa fa-code-fork"></i>
	        <span class="count-numbers">${average}</span>
	        <span class="count-name">Average</span>
	      </div>
	    </div>
		
	    <div class="col-md-3">
	      <div class="card-counter danger">
	        <i class="fa fa-database"></i>
	        <span class="count-numbers">${max}</span>
	        <span class="count-name">Maximum</span>
	      </div>
	    </div>
	    <div class="col-md-1"></div>
	</div>
  </div>	
 
	<div class="row">
		<div class="col-md-2" ></div>
		<div class="col-md-8" >
			<div class="col-md- grid-margin stretch-card" >
			<canvas id="areaChart" style="width:100%"></canvas>
	    </div>
		</div>
		<div class="col-md-2" ></div>
	</div>
		
	<br>
	<div class="row">
		<div class="col-md-2" ></div>
		<div class="col-md-8" >
			<table id="dashboard" 
			class="table table-hover table-sm table-striped table-bordered shadow p-3 mb-5 bg-white rounded table-condensed"
			data-show-header="true"
			data-classes="table table-hover table-striped table-sm table-bordered shadow p-3 mb-5 bg-white rounded table-condensed" 
			data-toggle="table"  
			data-striped="true"
		    data-sort-name="Feed Id"
		    data-sort-order="desc"
		   	data-pagination="true"  
		   	data-id-field="name" 
		   	data-page-size="5"
		   	data-page-list="[5, 10, 25, 50, 100, ALL]" >
	            <thead>
	              <tr>
	              	<th data-sortable="true" onclick="myFunction(this)">
	                  Feed Id
	                </th>
	                <th data-sortable="true">
	                  Batch Date
	                </th>
	                <th data-sortable="true">
	                 Run Id
	                </th>
	                <th data-sortable="true">
	                 Start Time
	                </th>
	            	<th data-sortable="true">
	                 End Time
	                </th>
	                <th data-sortable="true">
	                Duration
	                </th>
	              </tr>
	            </thead>
                <tbody>
               <c:forEach var="row" items="${arrHipDashboard}">
                 <tr>
                 	<td><c:out value="${row.batch_id}" /></td>
                 	<td><c:out value="${row.batch_date}" /></td>
					<td><c:out value="${row.run_id}" /></td>
					<td><c:out value="${row.start_time}" /></td>
					<td><c:out value="${row.end_time}" /></td>
					<td><c:out value="${row.duration}" /></td>
				</tr>
             </c:forEach>
                  
                </tbody>
        	</table>
        		
		<div class="col-md-2" ></div>
	</div>
</c:if>
	
<c:if test="${stat eq 1}">
	<div class="alert alert-danger" id="error-alert">
    				<button type="button" class="close" data-dismiss="alert">x</button>
    		 		Feed ID DOESNOT EXIST
				</div>
</c:if>
<c:if test="${stat eq 2}">

	<div class="form-group row">
	<div class="col-md-3" ></div>
	<div class="col-md-6" >
		<h4 align="center">Duration of Feed in Minutes</h4>
	</div>
	<div class="col-md-3" ></div>
</div>	
<div class="container">
	    <div class="row">
	    <div class="col-md-2"></div>
	      <div class="col-md-3">
	      <div class="card-counter success">
	        <i class="fa fa-ticket"></i>
	        <span class="count-numbers">${min}</span>
	        <span class="count-name">Minimum</span>
	      </div>
	    </div>
		 <div class="col-md-3">
	      <div class="card-counter primary">
	        <i class="fa fa-code-fork"></i>
	        <span class="count-numbers">${average}</span>
	        <span class="count-name">Average</span>
	      </div>
	    </div>
		
	    <div class="col-md-3">
	      <div class="card-counter danger">
	        <i class="fa fa-database"></i>
	        <span class="count-numbers">${max}</span>
	        <span class="count-name">Maximum</span>
	      </div>
	    </div>
	    <div class="col-md-1"></div>
	</div>
  </div>	
	<div class="row">
		<div class="col-md-2" ></div>
		<div class="col-md-8" >
			<div class="col-md- grid-margin stretch-card" >
			<canvas id="areaChart" style="width:100%"></canvas>
	    </div>
		</div>
		<div class="col-md-2" ></div>
	</div>
	<br>
	<div class="row">
		<div class="col-md-2" ></div>
		<div class="col-md-8" >
			<table id="dashboard" 
			class="table table-hover table-sm table-striped table-bordered shadow p-3 mb-5 bg-white rounded table-condensed"
			data-show-header="true"
			data-classes="table table-hover table-striped table-sm table-bordered shadow p-3 mb-5 bg-white rounded table-condensed" 
			data-toggle="table"  
			data-striped="true"
		    data-sort-name="Feed Id"
		    data-sort-order="desc"
		   	data-pagination="true"  
		   	data-id-field="name" 
		   	data-page-size="5"
		   	data-page-list="[5, 10, 25, 50, 100, ALL]" >
	             <thead>
	              <tr>
	              <th data-sortable="true" onclick="myFunction(this)">
	                  Feed Id
	                </th>
	                <th data-sortable="true">
	                  Batch Date
	                </th>
	                <th data-sortable="true">
	                 Run Id
	                </th>
	                <th data-sortable="true">
	                 Start Time
	                </th>
	            	<th data-sortable="true">
	                 End Time
	                </th>
	                <th data-sortable="true">
	                Duration
	                </th>
	              </tr>
	            </thead>
                <tbody>
               <c:forEach var="row" items="${arrHipDashboard}">
                 <tr>
                 	<td><c:out value="${row.batch_id}" /></td>
                 	<td><c:out value="${row.batch_date}" /></td>
					<td><c:out value="${row.run_id}" /></td>
					<td><c:out value="${row.start_time}" /></td>
					<td><c:out value="${row.end_time}" /></td>
					<td><c:out value="${row.duration}" /></td>
				</tr>
             </c:forEach>
                  
                </tbody>
        	</table>
        	</div>
		<div class="col-md-2" ></div>
	</div>
</c:if>
	

