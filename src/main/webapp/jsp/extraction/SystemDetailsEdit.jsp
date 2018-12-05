<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${ssm}" var="ssm">
	<fieldset class="fs">
		<div class="form-group row">
			<div class="col-sm-6">
				<label>Unique Extract Name *</label> <input type="text"
					class="form-control" id="src_unique_name" name="src_unique_name"
					placeholder="Unique Extract Name" value="${ssm.src_unique_name}">
				<div id="res" style="font-size: 0.7em; text-align: center;"></div>
			</div>
			<div class="col-sm-6">
				<label>Extract Description *</label> <input type="text"
					class="form-control" id="src_sys_desc" name="src_sys_desc"
					placeholder="Extract Description" value="${ssm.src_sys_desc}">
			</div>
		</div>
		<div class="form-group">
			<label>Select Source *</label> <select class="form-control"
				id="connection_id" name="connection_id">
				<c:forEach items="${conn_val}" var="conn_val">
					<c:choose>
						<c:when test="${ssm.connection_id==conn_val.connection_id}">
							<option value="${conn_val.connection_id}" selected>${conn_val.connection_name}</option>
						</c:when>
						<c:otherwise>
							<option value="${conn_val.connection_id}">${conn_val.connection_name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label>Select Target *</label> <select name="targetx" id="targetx"
				class="form-control" multiple="multiple">
				<c:forEach items="${tgt}" var="tgt">
					<c:set var="val" scope="session" value="0" />
					<c:forTokens items="${ssm.target}" delims="," var="mySplitx">
						<c:choose>
							<c:when test="${mySplitx==tgt}">
								<option value="${tgt}" selected>${tgt}</option>
								<c:set var="val" scope="session" value="1" />
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:forTokens>
					<c:if test="${val==0}">
						<option value="${tgt}">${tgt}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<div class="form-group row">
			<div class="col-sm-6">
				<label>Extraction Type *</label> <select name="src_extract_type"
					id="src_extract_type" class="form-control"
					onchange="sch(this.value);">
					<c:choose>
						<c:when test="${ssm.extraction_type=='Real'}">
							<option value="Real" selected>One Time Full Extract</option>
							<option value="Batch">Scheduled Batch Run</option>
						</c:when>
						<c:when test="${ssm.extraction_type=='Batch'}">
							<option value="Batch" selected>Scheduled Batch Run</option>
							<option value="Real">One Time Full Extract</option>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</select>
			</div>
			<div class="col-sm-6">
				<label>Country</label> <select class="form-control"
					id="country_code" name="country_code">
					<option value="" selected disabled>Country ...</option>
					<c:forEach items="${countries}" var="countries">
						<c:choose>
							<c:when test="${ssm.country_code==countries.country_code}">
								<option value="${countries.country_code}" selected>${countries.country_name}</option>
							</c:when>
							<c:otherwise>
								<option value="${countries.country_code}">${countries.country_name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div id="scheduling_div" style="display: none;"></div>
	</fieldset>
	<button onclick="jsonconstruct('upd');"
		class="btn btn-rounded btn-gradient-info mr-2">Update</button>
	<button onclick="jsonconstruct('del');"
		class="btn btn-rounded btn-gradient-info mr-2">Delete</button>
</c:forEach>

<script>
	var select = document.getElementById('targetx');
	multi(select, {});
</script>