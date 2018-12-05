<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page import="com.cdg.gcp.constants.*"%> --%>

<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet">

<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<style>
   .table-condensed{
   font-size: 5px;
   }
   .borderless td, .borderless th {
   border: none;
   }
   .stepwizard-step p {
   margin-top: 0px;
   color:#666;
   }
   .stepwizard-row {
   display: table-row;
   }
   .stepwizard {
   display: table;
   width: 100%;
   position: relative;
   }
   .stepwizard-step button[disabled] {
   /*opacity: 1 !important;
   filter: alpha(opacity=100) !important;*/
   }
   .stepwizard .btn.disabled, .stepwizard .btn[disabled], .stepwizard fieldset[disabled] .btn {
   opacity:1 !important;
   color:#bbb;
   }
   .stepwizard-row:before {
   top: 14px;
   bottom: 0;
   position: absolute;
   content:" ";
   width: 100%;
   height: 1px;
   background-color: #ccc;
   z-index: 0;
   }
   .stepwizard-step {
   display: table-cell;
   text-align: center;
   position: relative;
   }
   .btn-circle {
   width: 30px;
   height: 30px;
   text-align: center;
   padding: 6px 0;
   font-size: 12px;
   line-height: 1.428571429;
   border-radius: 15px;
   }
</style>
<script>
   $(document).ready(function () {
   
       var navListItems = $('div.setup-panel div a'),
           allWells = $('.setup-content'),
           allNextBtn = $('.nextBtn');
   
       allWells.hide();
   
       navListItems.click(function (e) {
           e.preventDefault();
           var $target = $($(this).attr('href')),
               $item = $(this);
   
           if (!$item.hasClass('disabled')) {
               navListItems.removeClass('btn-success').addClass('btn-default');
               $item.addClass('btn-success');
               allWells.hide();
               $target.show();
               $target.find('input:eq(0)').focus();
           }
       });
   
       allNextBtn.click(function () {
           var curStep = $(this).closest(".setup-content"),
               curStepBtn = curStep.attr("id"),
               nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
               curInputs = curStep.find("input[type='text'],input[type='url']"),
               isValid = true;
    
           $(".form-group").removeClass("has-error");
           for (var i = 0; i < curInputs.length; i++) {
               if (!curInputs[i].validity.valid) {
                   isValid = false;
                   $(curInputs[i]).closest(".form-group").addClass("has-error");
               }
           }
   
           if (isValid) nextStepWizard.removeAttr('disabled').trigger('click');
       });
   
       $('div.setup-panel div a.btn-success').trigger('click');
   });
</script>
<script>
   function sub()
   {
   	document.getElementById("tgt_dataset").value=document.getElementById("dataset").value;
   	document.bulksystemdata.submit();	
   }
   
   function getTableInfo()
   {
   	var entry_cnt=document.getElementById('counter').value;
   	if(entry_cnt>=1)
   		{
   		 $("#final_bt1").show();  
   		}
   	 for(var i=1;i<=entry_cnt;++i)
   		{
   			var tbl_type = document.getElementById('type'+i);
   			var tbl_key = document.getElementById('key'+i);
   			document.getElementById('type_'+i).innerHTML=tbl_type.value;
   			document.getElementById('key_'+i).innerHTML=tbl_key.value;
   		} 
   }
   
    function getQuesValues()
   {	
   	var ques1 = $('input[name=allow_table_addition]:checked').val(); 
   	var ques2 = $('input[name=allow_column_addition]:checked').val(); 
   	 
   	if(ques1 == 'LOAD_EXISTING_DS'){
   		 	document.getElementById('ques1').innerHTML="Load in Existing Dataset";
   	} else if(ques1 == 'DONOT_ALLOW_NOTIFY') {
   		document.getElementById('ques1').innerHTML="Don't Allow! Notify Me";
   	}
   	
   	if(ques2 == 'LOAD_EXISTING_DS'){
   		 	document.getElementById('ques2').innerHTML="Load in Existing Dataset";
   	} else if(ques2 == 'DONOT_ALLOW_NOTIFY') {
   		document.getElementById('ques2').innerHTML="Don't Allow! Notify Me";
   	} else if(ques2 == 'ALLOW_NEW_TABLE') {
   		document.getElementById('ques2').innerHTML="Allow! Load in new Table";
   	}
   	 	
   }
    
   function jsonconstruct()
   {
   	var tgt_typ = document.getElementById('target_type').value;
   	if(tgt_typ == 'BigQuery')
   		{
   		var a=document.getElementById('target_dataset1').value;
   		var b=document.getElementById('target_dataset2').value;
   			if(b != ""){
   				var s = document.getElementById('target_dataset');
   				s.value=b;
   			}
   			else{	
   				var s = document.getElementById('target_dataset');
   				s.value=a;
   			}
   		}
   	if(tgt_typ == 'MySQL' || tgt_typ == 'PostgreSQL')
   		{
   			var s = document.getElementById('target_dataset');
   			s.value=document.getElementById('dataset').value;
   		}
   	var data = {};
   	$(".form-control1").serializeArray().map(function(x){data[x.name] = x.value;});
   	var x = '{"header":{"user":"cdg_admin","service_account":"Publishing_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'+JSON.stringify(data)+'}}';
   	//alert(x);      
   	document.getElementById('x').value = x;
   	console.log(x);
   }
   
   function jsonConstructExtract()
   {
	 //  alert('Success')
	   multisel('col_name', 'run_id_list');
	  // alert('Success')
   	var tgt_typ = document.getElementById('target_type').value;
   	if(tgt_typ == 'BigQuery')
   		{
   		var a=document.getElementById('target_dataset3').value;
   		var b=document.getElementById('target_dataset4').value;
   			if(b != ""){
   				var s = document.getElementById('target_ex_dataset');
   				s.value=b;
   			}
   			else{	
   				var s = document.getElementById('target_ex_dataset');
   				s.value=a;
   			}
   	}
   	if(tgt_typ == 'MySQL' || tgt_typ == 'PostgreSQL')
   		{
   			var s = document.getElementById('target_ex_dataset');
   			s.value=document.getElementById('dataset').value;
   		}
   	var data = {};
   	$(".form-control2").serializeArray().map(function(y){data[y.name] = y.value;});
   	var y = '{"header":{"user":"cdg_admin","service_account":"Publishing_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'+JSON.stringify(data)+'}}';
   	      
   	document.getElementById('y').value = y;
   //	alert(y);
   	console.log(y); 
   	}
   
   function jsonFinalConstruct()
   {
   	var data = {};
   	$(".form-control3").serializeArray().map(function(x){data[x.name] = x.value;});
   	var z = '{"header":{"user":"cdg_admin","service_account":"Publishing_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'+JSON.stringify(data)+'}}';
   	//alert(z);
   	document.getElementById('z').value = z;
     	console.log(z);
   	
   }
   
   
   function jsonconstructPublish()
   {
   	document.getElementById('sch_freq').value = cron_construct();
   	var data = {};
   	$(".form-control5").serializeArray().map(function(x){data[x.name] = x.value;});
   	var x = '{"header":{"user":"cdg_admin","service_account":"Publishing_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'+JSON.stringify(data)+'}}';
   	      document.getElementById('p').value = x;
   //	alert(x);
   	     // console.log(x);cron_construct()
   	  document.getElementById('publishingExtracted').submit();
   }
   
   
   function togg(ids, idx) {
   	var x5 = idx.slice(-1);
   	if (ids == "min") {
   		document.getElementById("dynlong" + x5).style.display = "none";
   		document.getElementById("dynshort" + x5).style.display = "block";
   
   		/* var x7 = document.getElementById("dynshort" + x5).innerHTML;
   		x7 = x7.substr(x7.indexOf('<'), x7.length);
   
   		var x6 = "Pipeline Name : "
   				+ document.getElementById("pipelineName" + x5).value;
   
   		document.getElementById("dynshort" + x5).innerHTML = x6 + x7; */
   	}
   	if (ids == "max") {
   		document.getElementById("dynlong" + x5).style.display = "block";
   		document.getElementById("dynshort" + x5).style.display = "none";
   	}
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
               On-board Publishing Feed
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
            <div class="form-group">
               <div class="container">
                  <div class="stepwizard">
                     <div class="stepwizard-row setup-panel">
                        <div class="stepwizard-step col-xs-3">
                           <a href="#step-1" type="button" class="btn btn-success btn-circle">1</a>  <!-- style="pointer-events: none; cursor: default;" -->
                           <p><small>Add Feed Details</small></p>
                        </div>
                        <div class="stepwizard-step col-xs-3">
                           <a href="#step-2" type="button" class="btn btn-default btn-circle">2</a>
                           <p><small>Metadata Changes</small></p>
                        </div>
                        <div class="stepwizard-step col-xs-3">
                           <a href="#step-3" type="button" class="btn btn-default btn-circle" >3</a>
                           <p><small>Target Partitioning</small></p>
                        </div>
                        <div class="stepwizard-step col-xs-3">
                           <a href="#step-4" type="button" class="btn btn-default btn-circle">4</a>
                           <p><small>Verify Feed Details</small></p>
                        </div>
                        <div class="stepwizard-step col-xs-3">
                           <a href="#step-5" type="button" class="btn btn-default btn-circle">5</a>
                           <p><small>Publishing Data</small></p>
                        </div>
                     </div>
                  </div>
                  <fieldset class="fs">
                     <div class="panel panel-primary setup-content" id="step-1">
                        <ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
                           <li class="nav-item">
                              <a class="nav-link active" id="pills-existing" data-toggle="pill" href="#pills-exist-sys" role="tab" aria-controls="pills-existing" aria-selected="true">Juniper Extracted Source</a>
                           </li>
                           <li class="nav-item">
                              <a class="nav-link" id="pills-new" data-toggle="pill" href="#pills-new-sys" role="tab" aria-controls="pills-new" aria-selected="false">On board New System</a>
                           </li>
                        </ul>
                        <div class="tab-content" id="pills-tabContent">
                           <div class="tab-pane fade show active" id="pills-exist-sys" role="tabpanel" aria-labelledby="pills-exist-sys">
                              <form class="forms-sample" id="publishingExtract" 
                                 name="publishingExtract" method="POST"
                                 action="/publishing/publishingExtract1" 
                                 enctype="application/json" data-toggle="validator" role="form">
                                 <input type="hidden" class="form-control2" id="target_type" name="target_type" value="${tgt_val}">
                                 <input type="hidden" name="y" id="y">
                                 <input type="hidden" class="form-control2" name="target_ex_dataset" id="target_ex_dataset">
                                 <input type="hidden" class="form-control2" name="screen_loc" id="screen_loc"  value="Extracted Source">
                                 <input type="hidden" class="form-control2" name="run_id_list" id="run_id_list" >
                                 
                                 <br>
                                 <div class="form-group row" id="src_sys" >
                                    <div class="col-md-3">
                                       <label>Feed Name<span style="color:red">*</span></label> 
                                    </div>
                                    <div class="col-md-9">
                                       <select class="form-control form-control2" id="src_sys_id" name="src_sys_id" >
                                          <option value="" selected disabled>select feed system...</option>
                                          <c:forEach var="myMap" items="${srcSysIds}">
                                             <option value="${myMap.key}">
                                                <c:out value="${myMap.value}"/>
                                             </option>
                                          </c:forEach>
                                       </select>
                                    </div>
                                 </div>
                                 <div class="row" id="runidLabel" style="display: none;">
		                                 <div class="col-md-3">
		                                 	<label>Run Id's to be Published <span style="color:red">*</span></label>
		                                  </div>
										<div class="col-md-8">
											<div class="input-group">
		                     					 <input id="d" class="form__daterange">
		                    					  	<div class="input-group-append">
				                     					   <button class="btn btn-sm btn-facebook" type="button" onclick="document.getElementById('d').focus();">
				                     					     <i class="mdi mdi-calendar-multiple"></i>
				                    				    </button>
		                    				    		
		                   				   			</div>
		                   				   			<label> ( Pick Date Range Here To Filter Run Id ) </label>
		                  				  	</div> 
		                  				 </div>
                  				   </div>
                    
                    
                    
                                 <div id="loadRunIds" ></div>
                                 <div class="row" id="sa_dtls" >
                                    <div class="col-md-3">
                                       <label>Service Account Name<span style="color:red">*</span></label> 
                                    </div>
                                    <div class="form-group col-md-9" >
                                       <select class="form-control form-control1" id="sa_list" name="sa_list">
                                          <option value="" selected disabled>Select Service account...</option>
                                           <option value="SA_Publishing">SA_Publishing</option>
                                          <%-- <c:forEach items="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.getServiceAccountNames()}" var="sa">
                                             <option value="${sa}">${sa}</option>
                                          </c:forEach> --%>
                                       </select>
                                    </div>
                                 </div>
                                 <c:choose>
                                    <c:when test = "${tgt_val == 'BigQuery'}">
                                       <div class="row">
                                          <label class="col-sm-3 col-form-label">Target Dataset<span style="color:red">*</span></label> 
                                          <div class="col-sm-3">
                                             <div class=" radio form-check form-check-info">
                                                <label class="form-check-label">
                                                <input type="radio" class="form-check-input has-error" name="dsoptradioo" id="dsradio3" value="existing" >
                                                Existing
                                                </label>
                                             </div>
                                          </div>
                                          <div class="col-sm-3">
                                             <div class=" radio form-check form-check-info">
                                                <label class="form-check-label">
                                                <input type="radio" class="form-check-input has-error" name="dsoptradioo" id="dsradio4" value="new" >
                                                New
                                                </label>
                                             </div>
                                          </div>
                                       </div>
                                       <div class="row" id="ds3" style="display: none;">
                                          <div class="col-md-3"></div>
                                          <div class="col-md-9" >
                                             <select class="form-control has-error" id="target_dataset3" name="target_dataset3" >
                                                <option value="" selected disabled>target dataset...</option>
                                                <c:forEach items="${allDatabases}" var="dataSetList">
                                                   <option value="${dataSetList}">${dataSetList}</option>
                                                </c:forEach>
                                             </select>
                                          </div>
                                       </div>
                                       <div class="row"  id="ds4" style="display: none;">
                                          <div class="col-md-3"></div>
                                          <div class="col-md-9">
                                             <input type="text" class="form-control has-error" placeholder="enter dataset name" id="target_dataset4" name="target_dataset4" > 
                                          </div>
                                       </div>
                                    </c:when>
                                    <c:when test = "${tgt_val == 'MySQL' || tgt_val == 'PostgreSQL'}">
                                       <div class="form-group">
                                          <label>SQL Database Name<span style="color:red">*</span></label>
                                          <input type="text" class="form-control" placeholder="SQL Database Name" id="ex_dataset" name="ex_dataset" >
                                       </div>
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                 </c:choose>
                                 <div class="row">
                                    <div class="col-md-8"></div>
                                    <div class="col-md-2">
                                       <button type="submit" id="ex_sub" class="btn btn-rounded btn-gradient-info mr-2 btn-group float-right mt-2" onclick="jsonConstructExtract();">
                                       Save
                                       </button>
                                    </div>
                                    <div class="col-md-2">
                                       <button id="nxt1" class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info"  type="button"  ${ next_button_active != 'active'  ? 'disabled="disabled"' : ''} >Next <strong>></strong></button>
                                    </div>
                                 </div>
                              </form>
                           </div>
                           <div class="tab-pane fade" id="pills-new-sys" role="tabpanel" aria-labelledby="pills-new-sys">
                              <form class="forms-sample" id="publishingAddMetadata"
                                 name="publishingAddMetadata" method="POST"
                                 action="/publishing/publishingAddMetadata1" 
                                 enctype="application/json">
                                 <input type="hidden" class="form-control1" id="target_type" name="target_type" value="${tgt_val}">
                                 <input type="hidden" name="x" id="x">
                                 <input type="hidden" class="form-control1" name="target_dataset" id="target_dataset">
                                 <input type="hidden" class="form-control1" name="screen_loc" id="screen_loc"  value="New Publishing Source">
                                 <label class="col-form-label">System<span style="color:red">*</span></label>
                                 <div class="row">
                                    <div class="col-md-4">
                                       <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                          <label class="btn btn-info">
                                          <input type="radio" class="form-control" id="sysop1" name="sysop"  autocomplete="off" >Local
                                          </label>
                                          <label class="btn btn-info">
                                          <input type="radio" class="form-control" id="sysop2" name="sysop"  autocomplete="off" >GCS
                                          </label>
                                       </div>
                                    </div>
                                    <div class="col-md-8">
                                       <div id="sys_local" style="display: none;" >
                                          <input type="file" id="syslocal" name="file" class="file-upload-default">
                                          <div class="input-group col-xs-12">
                                             <input type="text" class="form-control form-control1 file-upload-info" name="system_path" disabled placeholder="Upload File" >
                                             <span class="input-group-append">
                                             <button class="file-upload-browse btn-rounded btn btn-gradient-info" type="button">Upload File</button>
                                             </span>
                                          </div>
                                       </div>
                                       <div id="sys_gcs" style="display: none;">
                                          <input type="text" class="form-control form-control1" id="sysgs" name="system_path" placeholder="GCS File Path" >
                                       </div>
                                    </div>
                                 </div>
                                 <label class="col-form-label">File<span style="color:red">*</span></label>
                                 <div class="row">
                                    <div class="col-md-4">
                                       <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                          <label class="btn btn-info">
                                          <input type="radio" class="form-control" id="fileop1" name="fileop" autocomplete="off" >Local
                                          </label>
                                          <label class="btn btn-info">
                                          <input type="radio" class="form-control" id="fileop2" name="fileop" autocomplete="off" >GCS
                                          </label>
                                       </div>
                                    </div>
                                    <div class="col-md-8">
                                       <div id="file_local" style="display: none;" >
                                          <input type="file" id="filelocal" name="file" class="file-upload-default">
                                          <div class="input-group col-xs-12">
                                             <input type="text" class="form-control form-control1 file-upload-info" name="file_path" disabled placeholder="Upload File" >
                                             <span class="input-group-append">
                                             <button class="file-upload-browse btn-rounded btn btn-gradient-info" type="button">Upload File</button>
                                             </span>
                                          </div>
                                       </div>
                                       <div id="file_gcs" style="display: none;">
                                          <input type="text" class="form-control form-control1" id="filegs" name="file_path" placeholder="GCS File Path" >
                                       </div>
                                    </div>
                                 </div>
                                 <label class="col-form-label">Field<span style="color:red">*</span></label>
                                 <div class="row">
                                    <div class="col-md-4">
                                       <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                          <label class="btn btn-info">
                                          <input type="radio" class="form-control" id="fieldop1" name="fieldop" autocomplete="off" >Local
                                          </label>
                                          <label class="btn btn-info">
                                          <input type="radio" class="form-control" id="fieldop2" name="fieldop" autocomplete="off" >GCS
                                          </label>
                                       </div>
                                    </div>
                                    <div class="col-md-8">
                                       <div id="field_local" style="display: none;" >
                                          <input type="file" id="fieldlocal" name="file" class="file-upload-default">
                                          <div class="input-group col-xs-12">
                                             <input type="text" class="form-control form-control1 file-upload-info" name="field_path" disabled placeholder="Upload File" >
                                             <span class="input-group-append">
                                             <button class="file-upload-browse btn-rounded btn btn-gradient-info" type="button">Upload File</button>
                                             </span>
                                          </div>
                                       </div>
                                       <div id="field_gcs" style="display: none;">
                                          <input type="text" class="form-control form-control1" id="fieldgs" name="field_path" placeholder="GCS File Path" >
                                       </div>
                                    </div>
                                 </div>
                                 <c:choose>
                                    <c:when test = "${tgt_val == 'BigQuery'}">
                                       <br>
                                       <div class="row" id="sa_dtls" >
                                          <div class="col-md-3">
                                             <label>Service Account Name<span style="color:red">*</span></label> 
                                          </div>
                                          <div class="col-md-9">
                                             <select class="form-control form-control1" id="sa_list" name="sa_list">
                                                <option value="" selected disabled>Select Service account...</option>
                                                <c:forEach items="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.getServiceAccountNames()}" var="sa">
                                                   <option value="${sa}">${sa}</option>
                                                </c:forEach>
                                             </select>
                                          </div>
                                       </div>
                                       <div class="row">
                                          <label class="col-sm-3 col-form-label">Target Dataset<span style="color:red" >*</span></label> 
                                          <div class="col-sm-3">
                                             <div class="form-check form-check-info">
                                                <label class="form-check-label">
                                                <input type="radio" class="form-check-input" name="dsoptradioo" id="dsradio1" value="existing" >
                                                Existing
                                                </label>
                                             </div>
                                          </div>
                                          <div class="col-sm-3">
                                             <div class="form-check form-check-info">
                                                <label class="form-check-label">
                                                <input type="radio" class="form-check-input" name="dsoptradioo" id="dsradio2" value="new" >
                                                New
                                                </label>
                                             </div>
                                          </div>
                                       </div>
                                       <div class="row" id="ds1" style="display: none;">
                                          <div class="col-md-3"></div>
                                          <div class="col-md-9" >
                                             <select class="form-control" id="target_dataset1" name="target_dataset1" >
                                                <option value="" selected disabled>Select Dataset...</option>
                                                <c:forEach items="${allDatabases}" var="dataSetList">
                                                   <option value="${dataSetList}">${dataSetList}</option>
                                                </c:forEach>
                                             </select>
                                          </div>
                                       </div>
                                       <div class="row" id="ds2" style="display: none;">
                                          <div class="col-md-3"></div>
                                          <div class="col-md-9">
                                             <input type="text" class="form-control" placeholder="enter dataset name" id="target_dataset2" name="target_dataset2" > 
                                          </div>
                                       </div>
                                    </c:when>
                                    <c:when test = "${tgt_val == 'MySQL' || tgt_val == 'PostgreSQL'}">
                                       <div class="form-group">
                                          <label>SQL Database Name</label>
                                          <input type="text" class="form-control" placeholder="SQL Database Name" id="dataset" name="dataset" >
                                       </div>
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                 </c:choose>
                                 <div class="row">
                                    <div class="col-md-8"></div>
                                    <div class="col-md-2">
                                       <button type="submit" id="add_sub" class="btn btn-rounded btn-gradient-info mr-2 btn-group float-right mt-2" onclick="jsonconstruct();">
                                       Save
                                       </button>
                                    </div>
                                    <div class="col-md-2">
                                       <button id="nxt2" class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info"  type="button" ${ next_button_active != 'active'  ? 'disabled="disabled"' : ''}>Next <strong>></strong></button>
                                    </div>
                                 </div>
                              </form>
                           </div>
                        </div>
                     </div>
                     <form class="forms-sample" id="finalAddMetadata" name="finalAddMetadata"  method="POST"   action="/publishing/finalAddMetadata1" 
                        enctype="application/json">
                        <div class="panel panel-primary setup-content" id="step-2">
                           <div class="panel-heading">
                              <!-- <h3 class="panel-title">Metadata Changes</h3> -->
                              <c:if test="${entry_cnt <= 0}">
                                 <h5 class="panel-title">Metadata Changes</h5>
                              </c:if>
                              <c:if test="${entry_cnt > 0}">
                                 <h5 class="panel-title">Feed - Source Vs Target Mapping</h5>
                              </c:if>
                           </div>
                           <div class="panel-body">
                              <%-- <c:if test="${entry_cnt > 0}">
                                 <h5 class="panel-title">Feed - Source Vs Target Mapping</h5>
                                 </c:if> --%>
                              <input class="form-control3" type="hidden" name="tc" id="tc" value="${entry_cnt}" />
                              <c:set var="file_count" value="1" scope="page" />
                              <c:forEach items="${fileList}" var="file" varStatus="status">
                                 <div id="dyn${file_count}" class="fs">
                                    <div id="dynshort${file_count}" style="display: none; padding:10px ">
                                       <div style="float: right; z-index: 999; cursor: pointer;" onclick="togg('max',this.parentNode.id)">
                                          <b>+</b>
                                       </div>
                                       <h5 class="panel-title">${file.src_file_name}      <font color="blue">*** Expand this to update the Default Mapping</font></h5>
                                    </div>
                                    <div id="dynlong${file_count}" style="display: block;">
                                       <div style="float: right; z-index: 999; cursor: pointer;" onclick="togg('min',this.parentNode.id)">
                                          <b>-</b>
                                       </div>
                                       <div>
                                          <br/>
                                          <input class="form-control3" type="hidden" name="t${file_count}srcname" id="t${file_count}srcname" value="${file.src_file_name}" />
                                          ${file.src_file_name}  : <input class="form-control3" type="text" name="t${file_count}tgtname" id="t${file_count}tgtname" value="${file.tgt_tbl_name}" /> <<=== Change the default Target Table
                                          <br/>
                                          <table class="table table-bordered shadow  table-bordered p-3 mb-5 bg-white rounded">
                                             <thead>
                                                <tr class="table-info">
                                                   <th class="text-center"><b>Field Position</b></th>
                                                   <th class="text-center"><b>Field Name</b></th>
                                                   <th class="text-center"><b>Source Data Type</b></th>
                                                   <th class="text-center"><b>Target Data Type</b></th>
                                                </tr>
                                             </thead>
                                             <c:set var="column_count" value="1" scope="page" />
                                             <c:forEach items="${fileAllfields[file.src_file_name]}" var="field">
                                                <tr class="table-default">
                                                   <td>${field.fld_pos_num}</td>
                                                   <%-- <td>${field.src_fld_name}</td> --%>
                                                   <td><input type="text" class="form-control3"  id="t${file_count}f${column_count}" name="t${file_count}f${column_count}" value="${field.src_fld_name}" readonly="readonly" style="border: none;"></td>
                                                   <td>${field.src_fld_data_typ}</td>
                                                   <td>
                                                      <select class="form-control3" id="t${file_count}ft${column_count}" name="t${file_count}ft${column_count}">
                                                         <option value="${field.trg_fld_data_typ}">${field.trg_fld_data_typ}</option>
                                                         <option value="String">STRING</option>
                                                         <option value="Date">DATE</option>
                                                         <option value="Int">INT</option>
                                                         <option value="Number">NUMBER</option>
                                                      </select>
                                                   </td>
                                                </tr>
                                                <c:set var="column_count" value="${column_count + 1}" scope="page"/>
                                             </c:forEach>
                                          </table>
                                          <input type="hidden" class="form-control3" name="t${file_count}fc" id="t${file_count}fc" value="${column_count - 1}" /> 
                                       </div>
                                    </div>
                                 </div>
                                 <c:set var="file_count" value="${file_count + 1}" scope="page"/>
                              </c:forEach>
                              <div id="dyn${file_count}" class="fs">
                                 <div id="dynshort${file_count}" style="display: none; padding:10px ">
                                    <div style="float: right; z-index: 999; cursor: pointer;"
                                       onclick="togg('max',this.parentNode.id)">
                                       <b>+</b>
                                    </div>
                                    <h5 class="panel-title">If any Metadata Changes</h5>
                                 </div>
                                 <div id="dynlong${file_count}" style="display: block;">
                                    <div style="float: right; z-index: 999; cursor: pointer;"
                                       onclick="togg('min',this.parentNode.id)">
                                       <b>-</b>
                                    </div>
                                    <div class="table-responsive">
                                       <table class="table borderless" >
                                          <tr>
                                             <td>Allow Table addition into the existing Feed?</td>
                                             <td class="text-center">
                                                <div class="radio form-check form-check-info">
                                                   <label class="form-check-label">
                                                      <!-- <input type="radio" class="form-control3 form-check-input has-error" name="allow_table_addition" value="LOAD_EXISTING_DS"  checked> --> - Yes, Will be created in Existing DataSet 
                                                   </label>
                                                </div>
                                             </td>
                                             <td>
                                                <div class=" radio form-check form-check-info">
                                                   <label class="form-check-label">
                                                   <input type="checkbox" class="form-control3 form-check-input has-error" name="allow_table_addition" value="DONOT_ALLOW_NOTIFY"  checked="checked">Notify
                                                   </label>
                                                </div>
                                             </td>
                                             <td></td>
                                          </tr>
                                          <tr>
                                             <td>Allow Column addition/removal into the existing Table?</td>
                                             <!-- <td>
                                                <div class=" radio form-check form-check-info">
                                                	<label class="form-check-label">
                                                		<input type="radio" class="form-control3 form-check-input has-error" name="allow_column_addition" value="LOAD_EXISTING_DS" checked >Load in Existing Table
                                                	</label>
                                                </div>
                                                </td> -->
                                             <!-- 		<td>
                                                <div class=" radio form-check form-check-info">
                                                	<label class="form-check-label">
                                                		<input type="radio" class="form-control3 form-check-input has-error" name="allow_column_addition" value="DONOT_ALLOW_NOTIFY" >Don't Allow! Notify Me
                                                	</label>
                                                </div>
                                                </td> -->
                                             <td>
                                                <div class=" radio form-check form-check-info">
                                                   <label class="form-check-label">
                                                      <!-- <input type="radio" class="form-control3 form-check-input has-error" name="allow_column_addition" value="ALLOW_NEW_TABLE" checked> --> - Yes, New Table Will be created if any such Column Addition/Removal
                                                   </label>
                                                </div>
                                             </td>
                                             <td>
                                                <div class=" radio form-check form-check-info">
                                                   <label class="form-check-label">
                                                   <input type="checkbox" class="form-control3 form-check-input has-error" name="notify" value="DONOT_ALLOW_NOTIFY"  checked="checked">Notify
                                                   </label>
                                                </div>
                                             </td>
                                             <td></td>
                                          </tr>
                                       </table>
                                    </div>
                                 </div>
                              </div>
                              <!-- <div class="row" >
                                 <div class="col-md-10"></div>
                                 <div class="col-md-2">
                                 <button class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info" id="load_tbl_nxt" onclick="getQuesValues();" type="button">Next <strong>></strong></i></button>
                                 </div>
                                 </div> -->
                                 <!-- <div class="col-md-2">  <img id="loading" src="../../assets/img/preloader.gif"  style="display:none;"/> </div> -->
                              <div class="row"> 
                              <div class="col-md-8"><img id="loading" src="../../assets/img/preloader.gif"  style="display:none;"/></div>
                                 <div class="col-md-2">
                                
                                    <button type="button" id="saveMetaData" class="btn btn-rounded btn-gradient-info mr-2 btn-group float-right mt-2" >
                                       <!-- onclick="jsonconstruct();" -->
                                       Save
                                    </button>
                                 </div>
                                 <div class="col-md-2">
                                    <button id="load_tbl_nxt" class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info"  type="button" ${ next_button_active != 'active'  ? 'disabled="disabled"' : ''}>Next <strong>></strong></button>
                                 </div>
                              </div>
                           </div>
                        </div>
                        <div class="panel panel-primary setup-content" id="step-3">
                           <div class="panel-heading">
                              <h3 class="panel-title">Target Partitioning</h3>
                           </div>
                           <div class="panel-body">
                              <div class="form-group">
                                 <label class="control-label">Feed Name :</label>
                                 <label class="control-label"><b>${feed_name.src_unique_name}</b></label>
                              </div>
                              <input type="hidden" class="form-control3 form-control4" name="counter" id="counter" value="${entry_cnt}">
                              <input type="hidden" class="form-control3 form-control4" name="feed_id" id="feed_id" value="${feed_id}">
                              <input type="hidden" name="z" id="z">
                              <div id="partitioninfo" ></div>
                              <%-- <div class="table-responsive">
                                 <table class="table table-bordered shadow  table-bordered p-3 mb-5 bg-white rounded">
                                 <thead>
                                 <tr class="table-info">
                                 <th class="text-center"><b>S.No.</b></th>
                                 <th class="text-center"><b>Table Name</b></th>
                                 <th class="text-center"><b>Extracted Type</b></th>
                                 <th class="text-center"><b>Publishing Type</b></th>
                                 <th class="text-center"><b>Partition Key</b></th>
                                 </tr>
                                 </thead>
                                 
                                 <c:forEach items="${fileList}" var="file" varStatus="file_cnt">
                                 <tr class="table-default">
                                 <td>${file_cnt.count}</td>
                                 <td id="table${file_cnt.count}">
                                 <input type="hidden" class="form-control form-control3"  name="table${file_cnt.count}"  value="${file.src_file_name}">${file.src_file_name}
                                 </td>
                                 <td>${file.src_load_type}</td>
                                 <td>
                                 <select class="form-control form-control3" id="type${file_cnt.count}" name="type${file_cnt.count}">
                                 <option value="${publishingType[file.src_load_type]}" selected disabled>${publishingType[file.src_load_type]}</option>
                                 <option value="Append">Append</option>
                                 <option value="Truncate&Load">Truncate & Load</option>
                                 </select>
                                 </td>
                                 <td>
                                 <select class="form-control form-control3" id="key${file_cnt.count}" name="key${file_cnt.count}">
                                 <option value="NONE">None</option>
                                 <option value="LOAD_START_TIME">LOAD_START_TIME</option>
                                 <c:forEach var="myMap" items="${fileFields[file.src_file_name]}">
                                 <option value="${myMap}"><c:out value="${myMap}"/></option>
                                 </c:forEach>
                                 </select>
                                 </td>
                                 </tr>
                                 </c:forEach>
                                 
                                 </table>
                                 </div> --%>
                              <!-- <button class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info"  type="button" onclick="getTableInfo();">Next <b>></b></button> -->
                              <div class="row">
                                 <div class="col-md-8"><img id="loading1" src="../../assets/img/preloader.gif"  style="display:none;"/></div>
                                 <div class="col-md-2">
                                    <button type="button" id="savePartitionInfo" class="btn btn-rounded btn-gradient-info mr-2 btn-group float-right mt-2" >
                                       <!-- onclick="jsonconstruct();" -->
                                       Save
                                    </button>
                                 </div>
                                 <div class="col-md-2">
                                    <button class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info"  type="button" onclick="getTableInfo();">Next <b>></b></button>
                                 </div>
                              </div>
                           </div>
                        </div>
                        <div class="panel panel-primary setup-content" id="step-4">
                           <div class="panel-heading">
                              <h3 class="panel-title">Confirmation!</h3>
                              <p class="card-description">
                                 Verify the below feed details,
                              </p>
                           </div>
                           <div class="panel-body">
                              <div class="row">
                                 <div class="col-md-6 stretch-card grid-margin">
                                    <div class="card bg-gradient-light card-img-holder text-black">
                                       <div class="card-body">
                                          <img src="/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                                          <h3 class="font-weight-normal mb-3">Feed Details</h3>
                                          <div class="table-responsive">
                                             <table>
                                                <tr class="table-default">
                                                   <td><b>Feed Name</b></td>
                                                   <td>:</td>
                                                   <td>${feed_name.src_unique_name}</td>
                                                </tr>
                                                <tr class="table-default">
                                                   <td><b>Source Bucket</b></td>
                                                   <td> : </td>
                                                   <td>${feed_name.src_bkt}</td>
                                                </tr>
                                                <tr class="table-default">
                                                   <td><b>Target Type</b></td>
                                                   <td> : </td>
                                                   <td>${feed_name.tgt_type}</td>
                                                </tr>
                                                <tr class="table-default">
                                                   <td><b>Target Dataset</b></td>
                                                   <td> : </td>
                                                   <td>${feed_name.tgt_ds}</td>
                                                </tr>
                                             </table>
                                          </div>
                                       </div>
                                    </div>
                                 </div>
                                 <div class="col-md-6 stretch-card grid-margin">
                                    <div class="card bg-gradient-light card-img-holder text-black">
                                       <div class="card-body">
                                          <img src="/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                                          <h3 class="font-weight-normal mb-3">Table Details</h3>
                                          <div class="table-responsive">
                                             <table class="table table-hover table-sm table-bordered shadow  table-bordered p-3 bg-white rounded">
                                                <thead>
                                                   <tr class="table-info">
                                                      <th class="text-center">Table Name</th>
                                                      <th class="text-center">Extracted Type</th>
                                                   </tr>
                                                </thead>
                                                <c:forEach items="${fileList}" var="file">
                                                   <tr class="table-default">
                                                      <td>${file.src_file_name}</td>
                                                      <td>${file.src_load_type}</td>
                                                   </tr>
                                                </c:forEach>
                                             </table>
                                          </div>
                                       </div>
                                    </div>
                                 </div>
                                 <div class="col-md-6 stretch-card grid-margin">
                                    <div class="card bg-gradient-light card-img-holder text-black">
                                       <div class="card-body">
                                          <img src="/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                                          <h3 class="font-weight-normal mb-3">Metadata Changes</h3>
                                          <div class="table-responsive">
                                             <table>
                                                <tr class="table-default">
                                                   <td><b>1. Allow Table Addition :</b></td>
                                                </tr>
                                                <tr class="table-default">
                                                   <td id="ques1">Yes, Will be created in Existing DataSet</td>
                                                </tr>
                                                <tr class="table-default">
                                                   <td><br><b>2. Allow Column Addition/Removal :</b></td>
                                                </tr>
                                                <tr class="table-default">
                                                   <td id="ques2"> Yes, New Table Will be created if any such Column Addition/Removal</td>
                                                </tr>
                                             </table>
                                          </div>
                                       </div>
                                    </div>
                                 </div>
                                 <div class="col-md-6 stretch-card grid-margin">
                                    <div class="card bg-gradient-light card-img-holder text-black">
                                       <div class="card-body">
                                          <img src="/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                                          <h3 class="font-weight-normal mb-3">Target Partitioning</h3>
                                          <div class="table-responsive">
                                             <table class="table table-hover table-sm table-bordered shadow  table-bordered p-3 bg-white rounded">
                                                <thead>
                                                   <tr class="table-info">
                                                      <th class="text-center">Table Name</th>
                                                      <th class="text-center">Publishing Type</th>
                                                      <th class="text-center">Partition Key</th>
                                                   </tr>
                                                </thead>
                                                <c:forEach items="${fileList}" var="file" varStatus="file_cnt">
                                                   <tr class="table-default">
                                                      <td id="table_${file_cnt.count}" >${file.src_file_name}</td>
                                                      <td id="type_${file_cnt.count}"></td>
                                                      <td id="key_${file_cnt.count}"></td>
                                                   </tr>
                                                </c:forEach>
                                             </table>
                                          </div>
                                       </div>
                                    </div>
                                 </div>
                              </div>
                              <div  id="final_bt1" style="display: none;">
                                 <button class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info" type="button"  onclick="jsonFinalConstruct();">Next</button>
                              </div>
                           </div>
                        </div>
                     </form>
                     <form class="forms-sample" id="publishingExtracted" 
                        name="publishingExtracted" method="POST"
                        action="/publishing/publishingExtracted1" 
                        enctype="application/json">
                        <input type="hidden" name="x" id="x">
                        <input type="hidden" class="form-control5" name="sch_freq" id="sch_freq">
                        <input type="hidden" class="form-control5" id="target_type" name="target_type" value="${feed_name.tgt_type}">
                        <input type="hidden" class="form-control5" name="src_type" id="src_type" value="${src_type}">
                        <input type="hidden" class="form-control5" id="deploy_type" name="deploy_type">
                        <input type="hidden" class="form-control5" id="pub_type" name="pub_type" value="BQ Load">
                        <input type="hidden" class="form-control5" id="run_id" name="run_id" value="${run_id}">
                        <input type="hidden" class="form-control5" id="src_sys_id" name="src_sys_id" value="${feed_id}:Extracted:${feed_name.src_unique_name}">
                         <input type="hidden" name="p" id="p">
                         
                        <div class="panel panel-primary setup-content" id="step-5">
                           <div class="panel-heading">
                              <h3 class="panel-title">Publish Data</h3>
                              <br/>
                           </div>
                           <div class="panel-body">
                              <%-- <label>Feed Name :</label> ${feed_name.src_unique_name}
                                 <label>Run ID :</label> ${run_id} --%>
                                 <div class="col-md-10 stretch-card grid-margin">
                                    <div class="card bg-gradient-light card-img-holder text-black">
                                       <div class="card-body">
                                          <img src="/assets/img/circle.svg" class="card-img-absolute" alt="circle-image"/>
                                          <h3 class="font-weight-normal mb-3">Feed Details</h3>
                                          
                              <div class="table-responsive">
                                 <table>
                                    <tr class="table-default">
                                       <td><b>Feed Name</b></td>
                                       <td width="10px">:</td>
                                       <td>${feed_name.src_unique_name}</td>
                                       <td width="40px"/>
                                       <td><b>Target Type</b></td>
                                       <td width="10px"> : </td>
                                       <td>${feed_name.tgt_type}</td>
                                    </tr>
                                    <tr height="20px"/>
                                    <tr class="table-default">
                                       <td><b>Run ID</b></td>
                                       <td width="10px"> : </td>
                                       <td style="max-width: 300px;">${run_id}</td>
                                       <td width="40px"/>
                                       <td><b>Target Dataset</b></td>
                                       <td width="10px"> : </td>
                                       <td>${feed_name.tgt_ds}</td>
                                    </tr>
                                 </table>
                              </div>
                              </div>
                              </div>
                              </div>
                              <%-- <div class="form-group">
                                 <select class="form-control form-control1" id="src_sys_id" name="src_sys_id">
                                 <option value="" selected disabled>select source system...</option>
                                 <c:forEach var="myMap" items="${exSrcSysId}">
                                      		<option value="${myMap.key}"><c:out value="${myMap.value}"/></option>
                                   		</c:forEach>
                                 </select>
                                 </div> --%>
                                 
                                  <h4 class="font-weight-normal mb-3"> Publish By<span style="color:red">*</span> </h4> 
                              <div class="form_group row">
                                 <div class="col-sm-3">
                                    <div class="form-check form-check-info">
                                       <label class="form-check-label">
                                       <input type="radio" class="form-check-input" name="pub_by" id="publish_by1" value="BQLoad" checked="checked">
                                       BQ Load
                                       </label>
                                    </div>
                                 </div>
                                 <div class="col-sm-3">
                                    <div class="form-check form-check-info">
                                       <label class="form-check-label">
                                       <input type="radio" class="form-check-input" name="pub_by" id="publish_by2" value="Dataflow">
                                       Dataflow
                                       </label>
                                    </div>
                                 </div>
                              </div>
                              <br/>
                              <h4 class="font-weight-normal mb-3"> Publishing Type<span style="color:red">*</span> </h4> 
                              <div class="form_group row">
                                 <div class="col-sm-3">
                                    <div class="form-check form-check-info">
                                       <label class="form-check-label">
                                       <input type="radio" class="form-check-input" name="pub_type" id="deploy_type1" value="OnDemand" checked="checked">
                                       On Demand
                                       </label>
                                    </div>
                                 </div>
                                 <div class="col-sm-3">
                                    <div class="form-check form-check-info">
                                       <label class="form-check-label">
                                       <input type="radio" class="form-check-input" name="pub_type" id="deploy_type2" value="Event_Based">
                                       Event Based
                                       </label>
                                    </div>
                                 </div>
                                 <div class="col-sm-3">
                                    <div class="form-check form-check-info">
                                       <label class="form-check-label">
                                       <input type="radio" class="form-check-input" name="pub_type" id="deploy_type3" value="Time_Based">
                                       Time Based
                                       </label>
                                    </div>
                                 </div>
                              </div>
                              
                              <br>
                              <div class=form-group id="rsrv_div"  style="display: none;" >
                                 <div class="col-sm-3">
                                    <label>Reservoir ID<span style="color:red">*</span></label> 
                                 </div>
                                 <div class="form-group">
                                    <select class="form-control form-control5" id="reservoir_id" name="reservoir_id">
                                       <option value="" selected disabled>select reservoir id...</option>
                                       <option value="R0001">R0001</option>
                                    </select>
                                 </div>
                              </div>
                              <div class="form-group" id="sch" style="display: none;">
                                 <jsp:include page="../cdg_scheduler.jsp"/>
                              </div>
                              <br>
                              <div id="loadRunIds" style="display: none;"></div>
                              <br>
                              <!--  <div class="form-group" >
                                 <button type="submit" class="btn btn-rounded btn-gradient-info mr-2" onclick="jsonconstruct();">
                                 Publish
                                 </button>
                                 </div> -->
                              <div class="row" >
                                 <div class="col-md-10"></div>
                                 <div class="col-md-2">
                                    <button class="btn mr-2 btn-info nextBtn float-right mt-2 btn-rounded btn-gradient-info" id="publish" onclick="jsonconstructPublish();" type="submit">Publish </button>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </form>
                  </fieldset>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
<script src="../../assets/js/file-upload.js"></script>
<jsp:include page="../cdg_footer.jsp" />
<script src="../../assets/jquery/jquery-ui.js"></script>
<script src="../../assets/jquery/jquery.daterange.js"></script>
<script>
   $(document).ready(function () {
       
	   $("#d").daterange({
           onClose: function (dateRangeText) {
           //	alert(dateRangeText);
           var src_sys_id = document.getElementById('src_sys_id');
		   var feed_id = src_sys_id.value;
           	//alert(feed_id);
           	$.post('/publishing/addMetadataRunIds', {
         			src_sys_id : feed_id , dateRangeText : dateRangeText
         		}, function(data) {
         			$('#loadRunIds').html(data)
         		}); 
           	}
		});
	   
       	$("#dsradio1").on("change", function(){
               $("#ds1").show();
               $("#ds2").hide();
           })
       	$("#dsradio2").on("change", function() {
               $("#ds2").show();
               $("#ds1").hide();
         	})    
       	$("#dsradio3").on("change", function() {
               $("#ds3").show();
               $("#ds4").hide();
           })
       	$("#dsradio4").on("change", function(){
               $("#ds4").show();
               $("#ds3").hide();
           })
   	$("#sysop1").on("change", function(){
   	   $("#sys_local").show();
   	   $("#sys_gcs").hide();
   	})
   	$("#sysop2").on("change", function(){
   	   $("#sys_gcs").show();
   	   $("#sys_local").hide();
   	})
   	$("#fileop1").on("change", function(){
   	   $("#file_local").show();
   	   $("#file_gcs").hide();
   	})
   	$("#fileop2").on("change", function(){
   	   $("#file_gcs").show();
   	   $("#file_local").hide();
   	})
   	$("#fieldop1").on("change", function(){
   	   $("#field_local").show();
   	   $("#field_gcs").hide();
   	})
   	$("#fieldop2").on("change", function(){
   	   $("#field_gcs").show();
   	   $("#field_local").hide();
   	})
   	$("#src_sys_id").change(function() {
   		var src_sys_id = $(this).val();	
   		$.post('/publishing/addMetadataRunIds', {
   			src_sys_id : src_sys_id
   		}, function(data) {
   			$('#runidLabel').show();
   			document.getElementById('d').value = '';
   			$('#loadRunIds').html(data);
   		});
   	})
   	
   	$("#saveMetaData").click(function() {
   		var data = {};
   		$("#loading").show();
   		$(".form-control3").serializeArray().map(function(x){data[x.name] = x.value;});
   		var z = '{"header":{"user":"cdg_admin","service_account":"Publishing_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'+JSON.stringify(data)+'}}';
   		//alert(z);
   		var src_sys_id = document.getElementById('feed_id');
   		var feed_id = src_sys_id.value;
   		//alert('Success'); 
   		//alert(feed_id);
   		$.post('/publishing/saveMetadataChanges', {
   			z : z , src_sys_id : feed_id
   		}, function(data) {
   			$('#partitioninfo').html(data);
   			$("#loading").hide();
   			document.getElementById('successString').value="Succes"; 
   			$("#success-alert").fadeTo(10000,10).slideUp(2000, function(){
            });
   			
   			//alert('Saved Successfully')
   		});
   		
   	})
   	
   	$("#load_tbl_nxt").click(function() {
   		//alert('Success'); 
   		var src_sys_id = document.getElementById('feed_id');
   		var feed_id = src_sys_id.value;
   		//alert(feed_id);
   		var z = '';
   		$.post('/publishing/saveMetadataChanges', {
   			z : z , src_sys_id : feed_id
   		}, function(data) {
   			$('#partitioninfo').html(data)
   		});
   		
   	})
   	
   	
   	
   	$("#savePartitionInfo").click(function() {
   		var data = {};
   		$("#loading1").show();
   		$(".form-control4").serializeArray().map(function(x){data[x.name] = x.value;});
   		var z = '{"header":{"user":"cdg_admin","service_account":"Publishing_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'+JSON.stringify(data)+'}}';
   		//alert(z);
   		//alert('Success'); 
   		$.post('/publishing/savePartitionInfo', {
   			z : z
   		}, function(data) {
   			$("#loading1").hide();
   			//$('#partitioninfo1').html(data);
   		});
   		
   	})
   	
   	
   	$("#deploy_type1").on("change", function(){
   		if(document.getElementById('src_type').value == 'Published')
   		{
   			$("#loadRunIds").hide();
   		} 
   		else if(document.getElementById('src_type').value == 'Extracted')
   		{ 
   			$("#loadRunIds").show();
   		}
   		$("#sch").hide();
   		$("#rsrv_div").hide();
   		document.getElementById('deploy_type').value="OnDemand";
   	})
   	$("#deploy_type2").on("change", function(){
   		$("#rsrv_div").show();
   		$("#sch").hide();
   		$("#loadRunIds").hide();
   		document.getElementById('deploy_type').value="Event_Based";
   	})	
   	$("#deploy_type3").on("change", function(){
   		$("#rsrv_div").show();
   		$("#sch").show();
   		$("#loadRunIds").hide();
   		document.getElementById('deploy_type').value="Time_Based";
   	})
   	
   	
   	$("#publish_by1").on("change", function(){
   		document.getElementById('pub_type').value="BQ Load";
   	})	
   	$("#publish_by2").on("change", function(){
   		document.getElementById('pub_type').value="Dataflow";
   	})
   	
   	
   	$("#nxt1").click(function() {
   		document.getElementById('successString').value=""; 
   	})
   	$("#nxt2").click(function() {
   		document.getElementById('successString').value=""; 
   	})   	

   	
   	$("#success-alert").hide();
              $("#success-alert").fadeTo(10000,10).slideUp(2000, function(){
              });   
       $("#error-alert").hide();
              $("#error-alert").fadeTo(10000,10).slideUp(2000, function(){
               });
       
   });
</script>
