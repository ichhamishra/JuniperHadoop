<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header2.jsp" />
<script type="text/javascript">
	//home button function-start
	function homebutton() {
		//alert("test");
		window.location.href = "${pageContext.request.contextPath}/";
	}
	//home button function-end

	//Document Ready----Start
	$(document)
			.ready(
					function() {

						$("#select_feed").on("change", function(){
					        $("#feed1").show();
					        $("#feed2").hide();
					    })
						$("#search_feed").on("change", function() {
					        $("#feed2").show();
					        $("#feed1").hide();
					  	}) 
						//feed id filter ---start
						$("#feedIdFilter")
								.change(
										function() {
											var feed_id = $(this).val();
											var s = document
													.getElementById('feedid_s');
											s.value = feed_id;
											var date = document
													.getElementById('datepicker').value;
											//if date is not selected
											if (feed_id != '' && date == '') {
												$
														.post(
																'${pageContext.request.contextPath}/hip/feedIdFilter',
																{
																	feed_id : feed_id
																},
																function(data) {
																	$(
																			'#adddetails')
																			.html(
																					data);

																	var x = document
																			.getElementById("x").value;
																	var y = document
																			.getElementById("y").value;
																	var newx = x
																			.split(',');
																	var newy = y
																			.split(',');

																	newx[0] = newx[0]
																			.replace(
																					"[",
																					"");
																	newy[0] = newy[0]
																			.replace(
																					"[",
																					"")
																	newx[newx.length - 1] = newx[newx.length - 1]
																			.replace(
																					"]",
																					"");
																	newy[newy.length - 1] = newy[newy.length - 1]
																			.replace(
																					"]",
																					"");

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

																	if ($("#areaChart").length) {
																		var areaChartCanvas = $(
																				"#areaChart")
																				.get(
																						0)
																				.getContext(
																						"2d");
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

												$
														.post(
																'${pageContext.request.contextPath}/hip/datefilter',
																{
																	feedIdFilter : feed_id,
																	date : date
																},
																function(data) {

																	$(
																			'#adddetails')
																			.html(
																					data);

																	var x = document
																			.getElementById("x").value;
																	var y = document
																			.getElementById("y").value;
																	var newx = x
																			.split(',');
																	var newy = y
																			.split(',');
																	//var a=[180801,180802,180805];
																	//var b=[26.0000,13.0000,18];
																	newx[0] = newx[0]
																			.replace(
																					"[",
																					"");
																	newy[0] = newy[0]
																			.replace(
																					"[",
																					"")
																	newx[newx.length - 1] = newx[newx.length - 1]
																			.replace(
																					"]",
																					"");
																	newy[newy.length - 1] = newy[newy.length - 1]
																			.replace(
																					"]",
																					"");

																	var areaData = {

																		labels : newx,
																		datasets : [ {
																			label : 'Duration of Feed in Minutes',
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
																		var areaChartCanvas = $(
																				"#areaChart")
																				.get(
																						0)
																				.getContext(
																						"2d");
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
										});

						//feed id filter ---end
						//search button-start

						$("#feedid_s")
								.keyup(
										function() {
											var feed_id = $(this).val();
											//alert("keyup"+feed_id);
											var s = document
													.getElementById('feedIdFilter');
											s.value = feed_id;
											var date = document
													.getElementById('datepicker').value;

											//feed id available check-start
											if (feed_id != '' && date == '') {

												//alert(feed_id);
												$
														.ajax({

															type : "POST",
															url : "${pageContext.request.contextPath}/hip/filterSearch",
															data : {
																feed_id : feed_id
															},
															cache : false,
															success : function(
																	data) {

																// alert(data);
																$('#adddetails')
																		.html(
																				data);

																var x = document
																		.getElementById("x").value;
																var y = document
																		.getElementById("y").value;
																var newx = x
																		.split(',');
																var newy = y
																		.split(',');
																//var a=[180801,180802,180805];
																//var b=[26.0000,13.0000,18];
																newx[0] = newx[0]
																		.replace(
																				"[",
																				"");
																newy[0] = newy[0]
																		.replace(
																				"[",
																				"")
																newx[newx.length - 1] = newx[newx.length - 1]
																		.replace(
																				"]",
																				"");
																newy[newy.length - 1] = newy[newy.length - 1]
																		.replace(
																				"]",
																				"");

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

																if ($("#areaChart").length) {
																	var areaChartCanvas = $(
																			"#areaChart")
																			.get(
																					0)
																			.getContext(
																					"2d");
																	var areaChart = new Chart(
																			areaChartCanvas,
																			{
																				type : 'line',
																				data : areaData,
																				options : areaOptions
																			});
																}

															}

														});

											} else {

												$
														.post(
																'${pageContext.request.contextPath}/hip/datefilter',
																{
																	feedIdFilter : feed_id,
																	date : date
																},
																function(data) {

																	$(
																			'#adddetails')
																			.html(
																					data);

																	var x = document
																			.getElementById("x").value;
																	var y = document
																			.getElementById("y").value;
																	var newx = x
																			.split(',');
																	var newy = y
																			.split(',');
																	//var a=[180801,180802,180805];
																	//var b=[26.0000,13.0000,18];
																	newx[0] = newx[0]
																			.replace(
																					"[",
																					"");
																	newy[0] = newy[0]
																			.replace(
																					"[",
																					"")
																	newx[newx.length - 1] = newx[newx.length - 1]
																			.replace(
																					"]",
																					"");
																	newy[newy.length - 1] = newy[newy.length - 1]
																			.replace(
																					"]",
																					"");

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

																	if ($("#areaChart").length) {
																		var areaChartCanvas = $(
																				"#areaChart")
																				.get(
																						0)
																				.getContext(
																						"2d");
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

										});

						//search button-end

						$('#clear').click(function() {
							location.reload();
						});
						
						$("#datepicker")
								.daterange(
										{
											onClose : function(dateRangeText) {
												var date = $(this).val();

												var feedIdFilter = document
														.getElementById('feedIdFilter').value;

												// alert("feedIdFilter"+feedIdFilter+"date"+date);

												if (feedIdFilter != ''
														&& date != '') {

													$
															.post(
																	'${pageContext.request.contextPath}/hip/datefilter',
																	{
																		feedIdFilter : feedIdFilter,
																		date : date
																	},
																	function(
																			data) {

																		$(
																				'#adddetails')
																				.html(
																						data);

																		var x = document
																				.getElementById("x").value;
																		var y = document
																				.getElementById("y").value;
																		var newx = x
																				.split(',');
																		var newy = y
																				.split(',');
																		//var a=[180801,180802,180805];
																		//var b=[26.0000,13.0000,18];
																		newx[0] = newx[0]
																				.replace(
																						"[",
																						"");
																		newy[0] = newy[0]
																				.replace(
																						"[",
																						"")
																		newx[newx.length - 1] = newx[newx.length - 1]
																				.replace(
																						"]",
																						"");
																		newy[newy.length - 1] = newy[newy.length - 1]
																				.replace(
																						"]",
																						"");

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
																						'rgba(255, 99, 132, 0.2)', ],
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
																			var areaChartCanvas = $(
																					"#areaChart")
																					.get(
																							0)
																					.getContext(
																							"2d");
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
											}
										});
$("#tbl_feed_id").change(function() {
		var tbl_feed_id = $(this).val();	
		var final_plt_id = document.getElementById('final_plt_id').value;	
		$.post('${pageContext.request.contextPath}/hip/tblFeedList', {
			tbl_feed_id : tbl_feed_id,
			final_plt_id : final_plt_id
		}, function(data) {
			$('#tableAddFeed').html(data)
		});
})

});

function getFeedList()
{
	var plt_id1 = document.getElementById('plt_id').value;	
	var p = plt_id1.split('~');
	var final_plt_id = p[0];
	var plt_id = p[1];
	document.getElementById('final_plt_id').value=final_plt_id;
	
	var newx = plt_id.split(',');
	newx[0] = newx[0].replace("[","");
	newx[newx.length - 1] = newx[newx.length - 1].replace("]","");
	var option = '';
	option += '<option value=\"\" selected disabled>Select feed name...</option>';
	
	$('#feedIdFilter option').remove();
	$('#tbl_feed_id option').remove();
	for (var i=0;i<newx.length;i++){
	   option += '<option value="'+ newx[i] + '">' + newx[i] + '</option>';
	}
	$('#feedIdFilter').append(option);
	$('#tbl_feed_id').append(option);
}
	//Document Ready----End

</script>
<div class="main-panel" style="width: 100%">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<div class="row">
							<label class=" col-md-2 form-check-label">
					             Select Platform :
					        </label>
					         <div class=" form-group col-md-2">
								<select name="plt_id" id="plt_id" class="form-control" onchange="getFeedList()" >
									<option value="" selected disabled>Select platform...</option>
									<c:forEach var="myMap" items="${plt_feed}">
						        		<option value="${myMap.key}~${myMap.value}"><c:out value="${myMap.key}"/></option>
						     		</c:forEach>
								</select> 
							</div>
							<input type="hidden" id="final_plt_id" name="final_plt_id">
							<div class="col-md-8">
								<h5 style="text-align: right;">
									<a style="text-decoration: none;" href="/">Home</a>
								</h5>
							</div>
						</div>
				<ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
				  <li class="nav-item">
				    <a class="nav-link active" id="pills-feed" data-toggle="pill" href="#pills-feed-conn" role="tab" aria-controls="pills-feed" aria-selected="true">Feed Dashboard</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" id="pills-table" data-toggle="pill" href="#pills-table-conn" role="tab" aria-controls="pills-table" aria-selected="false">Table Dashboard</a>
				  </li>
		  		</ul>	
		  	<div class="tab-content" id="pills-tabContent">
  				<div class="tab-pane fade show active" id="pills-feed-conn" role="tabpanel" aria-labelledby="pills-feed">
  				 <fieldset class="fs">
							<div class="form-group row" id="ds1" >
								<div class="col-md-2">
					                     <div class=" radio form-check form-check-info">
					                        <label class="form-check-label">
					                        <input type="radio" class="form-check-input has-error" name="dsoptradioo" id="select_feed" checked>
					                        Select Feed
					                        </label>
					            		</div>
            					</div>
						        <div class="col-md-2">
					         		     <div class=" radio form-check form-check-info">
					                        <label class="form-check-label">
					                        <input type="radio" class="form-check-input has-error" name="dsoptradioo" id="search_feed">
					                        Search Feed
					                        </label>
					                     </div>
					          	</div>	
								<div class="col-md-8 form-row align-items-center" >
						       		<label class="form-check-label ">
						       			Date Range
						       		</label>
								</div>
							</div>	
					
							<div class="form-group row"  >
							<div class="col-md-4" id="feed1" >
								<select name="feedIdFilter"
									id="feedIdFilter" class="form-control" style="height:43px;" >
									<option value="" selected disabled>Select Feed Data...</option>
								</select>
							</div>
							<div class="col-md-4" id="feed2" style="display: none;">
									<input type="text"
										class="form-control" id="feedid_s" name="feedid_s"
										placeholder="Feed ID">
							</div>
							<div class="col-md-8">
								  	<div class="input-group">
			                      <input id="datepicker" name="datepicker" class="form-control form__daterange">
			                      <div class="input-group-append">
			                        <button class="btn btn-sm btn-facebook" type="button" onclick="document.getElementById('datepicker').focus();">
			                          <i class="mdi mdi-calendar-multiple"></i>
			                        </button>
			                     </div>
                    		</div>	
		       			 </div>
							</div>
						<div id="adddetails"></div>
					</fieldset>
				</div>
		<div class="tab-pane fade" id="pills-table-conn" role="tabpanel" aria-labelledby="pills-table">
		  			<fieldset class="fs">
		  				<div class="form-group row">
							<label class="col-md-3 form-check-label">
					             Select Feed :
					        </label>
					         <div class="col-md-6">
								<select name="tbl_feed_id" id="tbl_feed_id" class="form-control">
									<option value="" selected disabled>Select Feed Data...</option>
								</select>
							</div>
						</div>
						<div id="tableAddFeed"></div>		   		 			
	  				</fieldset>
   				</div>
		</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer1.jsp" />

<script src="${pageContext.request.contextPath}/assets/jquery/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/assets/jquery/jquery.daterange.js"></script>
