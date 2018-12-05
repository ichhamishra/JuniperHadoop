<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/multi.min.css">
  <script src="${pageContext.request.contextPath}/assets/js/multi.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.daterange.js"></script>
<script>
$(document).ready(function () {
	
	$("#tbl_datepicker2").daterange({
	    onClose: function (dateRangeText) {
	    	  	}
	});
	
	$("#tbl_id").change(function() {
		var tbl_id = $(this).val();	
		var plt_id = document.getElementById('tbl_plt_id2').value;	
		var feed_id = document.getElementById('tbl_feed_id2').value;	
		var date = document.getElementById('tbl_datepicker2').value;
		//if date is not selected
		if (feed_id != '' && date == '') {
		$.post('${pageContext.request.contextPath}/hip/tableIdFilter',{
				plt_id : plt_id,
				feed_id : feed_id,
				tbl_id : tbl_id
					},function(data) {
						$('#tableChartDuration').html(data);
						
						var x = document.getElementById("tbl_x").value;
						var y = document.getElementById("tbl_y").value;
						var newx = x.split(',');
						var newy = y.split(',');

						newx[0] = newx[0].replace("[","");
						newy[0] = newy[0].replace("[","")
						newx[newx.length - 1] = newx[newx.length - 1].replace("]","");
						newy[newy.length - 1] = newy[newy.length - 1].replace("]","");

						var areaData = {

							labels : newx,
							datasets : [ {
								label : 'Duration of Feed in Minutes',
								data : newy,
								backgroundColor : [
										//'rgba(255, 99, 132, 0.2)',
										'rgba(54, 162, 235, 0.2)',
										'rgba(255, 206, 86, 0.2)',
										'rgba(75, 192, 192, 0.2)',
										'rgba(153, 102, 255, 0.2)',
										'rgba(255, 159, 64, 0.2)',
										'rgba(255, 99, 132, 0.2)' ],
								borderColor : [
										//'rgba(255, 159, 64, 1)',
										//'rgba(255,99,132,1)',
										'rgba(54, 162, 235, 1)',
										'rgba(255, 206, 86, 1)',
										'rgba(75, 192, 192, 1)',
										'rgba(153, 102, 255, 1)',
										'rgba(255, 159, 64, 1)' ],
								borderWidth : 1,
								fill : true, // 3: no fill
							} ]
						};

						var areaOptions = {
							plugins : {
								filler : {
									propagate : true
								}
							}
						}

						if ($("#tbl_areaChartDuration").length) {var areaChartCanvas = $("#tbl_areaChartDuration").get(0).getContext("2d");
							var areaChart = new Chart(
									areaChartCanvas,
									{
										type : 'line',
										data : areaData,
										options : areaOptions
									});
						}
						
						var x1 = document.getElementById("tbl_x").value;
						var y1 = document.getElementById("tbl_z").value;
						var newx1 = x1.split(',');
						var newy1 = y1.split(',');

						newx1[0] = newx1[0].replace("[","");
						newy1[0] = newy1[0].replace("[","")
						newx1[newx1.length - 1] = newx1[newx1.length - 1].replace("]","");
						newy1[newy1.length - 1] = newy1[newy1.length - 1].replace("]","");

						var areaData = {

							labels : newx1,
							datasets : [ {
								label : 'Count of Feed in numbers',
								data : newy1,
								backgroundColor : [
										'rgba(255, 99, 132, 0.2)',
										'rgba(54, 162, 235, 0.2)',
										'rgba(255, 206, 86, 0.2)',
										'rgba(75, 192, 192, 0.2)',
										'rgba(153, 102, 255, 0.2)',
										'rgba(255, 159, 64, 0.2)',
										'rgba(255, 99, 132, 0.2)' ],
								borderColor : [
										'rgba(255, 159, 64, 1)',
										'rgba(255,99,132,1)',
										'rgba(54, 162, 235, 1)',
										'rgba(255, 206, 86, 1)',
										'rgba(75, 192, 192, 1)',
										'rgba(153, 102, 255, 1)',
										'rgba(255, 159, 64, 1)' ],
								borderWidth : 1,
								fill : true, // 3: no fill
							} ]
						};

						var areaOptions = {
							plugins : {
								filler : {
									propagate : true
								}
							}
						}

						if ($("#tbl_areaChartCount").length) {var areaChartCanvas = $("#tbl_areaChartCount").get(0).getContext("2d");
							var areaChart = new Chart(
									areaChartCanvas,
									{
										type : 'line',
										data : areaData,
										options : areaOptions
									});
						}		
				});

}
//if date is selected
else {
	$.post('${pageContext.request.contextPath}/hip/tableDatefilter',{
						feed_id : feed_id,
						date : date
			},function(data) {$('#tableChartCount').html(data);

						var x = document.getElementById("tbl_x").value;
						var y = document.getElementById("tbl_y").value;
						var newx = x.split(',');
						var newy = y.split(',');
						//var a=[180801,180802,180805];
						//var b=[26.0000,13.0000,18];
						newx[0] = newx[0].replace("[","");
						newy[0] = newy[0].replace("[","")
						newx[newx.length - 1] = newx[newx.length - 1].replace("]","");
						newy[newy.length - 1] = newy[newy.length - 1].replace("]","");

						var areaData = {

							labels : newx,
							datasets : [ {
								label : 'Count of Feed in numbers',
								data : newy,
								backgroundColor : [
										//'rgba(255, 159, 64,  0.2)',
										'rgba(54, 162, 235, 0.2)',
										'rgba(255, 206, 86, 0.2)',
										'rgba(75, 192, 192, 0.2)',
										'rgba(153, 102, 255, 0.2)',
										'rgba(255, 159, 64, 0.2)',
										'rgba(255, 99, 132, 0.2)' ],
								borderColor : [
										//'rgba(255, 159, 64, 1)',
										//'rgba(255,99,132,1)',
										'rgba(54, 162, 235, 1)',
										'rgba(255, 206, 86, 1)',
										'rgba(75, 192, 192, 1)',
										'rgba(153, 102, 255, 1)',
										'rgba(255, 159, 64, 1)' ],
								borderWidth : 1,
								fill : true, // 3: no fill
							} ]
						};

						var areaOptions = {
							plugins : {
								filler : {
									propagate : true
								}
							}
						}

						if ($("#areaChart").length) {
							var areaChartCanvas = $("#areaChart").get(0).getContext("2d");
							var areaChart = new Chart(areaChartCanvas,
									{
										type : 'line',
										data : areaData,
										options : areaOptions
									});
						}

					});

}
				});
});
</script>

<input type="hidden" id="tbl_plt_id2" name="tbl_plt_id2" value="${plt_id}">
<input type="hidden" id="tbl_feed_id2" name="tbl_feed_id2" value="${tbl_feed_id}">    
<div class="form-group row ">
		<label class="col-md-3 form-check-label">
            Select Table :
       	</label>
        <div class="col-md-6">
		<select name="tbl_id" id="tbl_id" class="form-control" onchange="getFeedList()" >
			<option value="" selected disabled>Select table...</option>
			<c:forEach items="${table_list}" var="tbl">
				<option value="${tbl}">${tbl}</option>
			</c:forEach>
		</select>
		</div>
</div>
	<div class="form-group row " style="display: none;">
		<label class=" col-md-3 form-check-label">
            Select Date :
       	</label>
		<div class="col-md-6" >
	  		<div class="input-group">
	                 <input id="tbl_datepicker2" name="tbl_datepicker2" class="form-control form__daterange">
	                 <div class="input-group-append">
	                   <button class="btn btn-sm btn-facebook" type="button" onclick="document.getElementById('tbl_datepicker2').focus();">
	                     <i class="mdi mdi-calendar-multiple"></i>
	                   </button>
	                </div>
	        </div>
	    </div>	
 	</div>
	<div id="tableChartDuration" ></div>
