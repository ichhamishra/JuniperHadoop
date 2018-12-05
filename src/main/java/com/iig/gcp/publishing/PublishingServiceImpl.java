package com.iig.gcp.publishing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.iig.gcp.constants.MySqlConstants;
import com.iig.gcp.utils.BigQueryUtils;
import com.iig.gcp.utils.ConnectionUtils;

@Service
public class PublishingServiceImpl implements PublishingService {
	
	@Autowired
	 private ConnectionUtils connectionUtils;
	
	
	@Override
	public String invokeRest(String json,String url) throws UnsupportedOperationException, Exception {
		String resp = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(MySqlConstants.PUBLISHING_COMPUTE_URL+url);
		System.out.println("url rest: "+MySqlConstants.PUBLISHING_COMPUTE_URL+url);
		postRequest.setHeader("Content-Type", "application/json");
		StringEntity input = new StringEntity(json);
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		String response_string = EntityUtils.toString(response.getEntity(),"UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			resp = "Error" + response_string;
			throw new Exception("Error" + response_string);
		} else {
			System.out.println(response_string);
			resp = response_string;
		}
		return resp;
	}
	
	
	@Override
	public String invokePythonRest(String json,String url) throws UnsupportedOperationException, Exception {
		String resp = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(MySqlConstants.PUBLISHING_PYTHON_REST_URL+url);
		System.out.println("url rest: "+MySqlConstants.PUBLISHING_PYTHON_REST_URL+url);
		postRequest.setHeader("Content-Type", "application/json");
		StringEntity input = new StringEntity(json);
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		String response_string = EntityUtils.toString(response.getEntity(),"UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			resp = "Error" + response_string;
			throw new Exception("Error" + response_string);
		} else {
			System.out.println(response_string);
			resp = response_string;
		}
		return resp;
	}
	
	public Map<String, String> getSysIds() {
		Connection conn = null;
		try{
			Map<String,String> src_map= new HashMap<String, String>();
 			 conn=connectionUtils.getConnection();
			String s_id="select A.src_sys_id,A.src_unique_name \r\n" + 
					"from source_system_master A\r\n" + 
					"INNER JOIN extraction_status B\r\n" + 
					"ON A.src_sys_id=B.src_sys_id\r\n" +
					"WHERE B.status='Extracted'";
			Statement statement = conn.createStatement();
			ResultSet rs =statement.executeQuery(s_id);
			while (rs.next()) {
				src_map.put(rs.getString(1), rs.getString(2));
			}
			//ConnectionUtils.closeQuietly(conn);
			conn.close();
			return src_map;
		}catch(Exception e){
			//conn.close();
			return null;	
		}
	}
	
public Map<String, String> getExSysIds() {
	Connection conn = null;
		try{
			Map<String,String> src_map= new HashMap<String, String>();
 			 conn=connectionUtils.getConnection();
			String s_id="select \r\n" + 
					"m.src_sys_id,\r\n" + 
					"m.src_unique_name, \r\n" + 
					"case when e.src_sys_id is null then 'Published' else 'Extracted' end as type \r\n" + 
					"from source_system_master m \r\n" + 
					"left join extraction_master e \r\n" + 
					"on (m.src_sys_id = e.src_sys_id);";
			Statement statement = conn.createStatement();
			ResultSet rs =statement.executeQuery(s_id);
			while (rs.next()) {
				src_map.put(rs.getString(1)+":"+rs.getString(3)+":"+rs.getString(2), rs.getString(2));
			}
			//connectionUtils.closeQuietly(conn);
			conn.close();
			return src_map;
		}catch(Exception e){
			//conn.close();
			return null;	
		}
	}

public Map<String, String> getMDSysList() {
	Connection conn = null;
	try{
		Map<String,String> src_map= new HashMap<String, String>();
			 conn=connectionUtils.getConnection();
		String s_id="SELECT src_sys_id,src_unique_name FROM cdg.source_system_master";
		Statement statement = conn.createStatement();
		ResultSet rs =statement.executeQuery(s_id);
		while (rs.next()) {
			src_map.put(rs.getString(1), rs.getString(2));
		}
		//connectionUtils.closeQuietly(conn);
		conn.close();
		return src_map;
	}catch(Exception e){
		//conn.close();
		return null;	
	}
}

public ArrayList<String> getRunIds(Integer src_id) {
	// TODO Auto-generated method stub
	Connection conn = null;
	ArrayList<String> run_list= new ArrayList<String>();
	try
		{
			conn=connectionUtils.getConnection();
			String s_id="select run_id from cdg.extraction_status where src_sys_id="+src_id+" and status='Extracted'";
			Statement statement = conn.createStatement();
			ResultSet rs =statement.executeQuery(s_id);
			while (rs.next()) {
				run_list.add(rs.getString(1));
			}
		//connectionUtils.closeQuietly(conn);
			conn.close();
		return run_list;
	}
	catch(Exception e){
		//conn.close();
		return run_list;	
	}
}

@Override
public Map<String, String> getRunIdsWithDate(Integer src_id , String dateRangeText) {
	// TODO Auto-generated method stub
	Connection conn = null;
	Map<String, String> run_list= new TreeMap<String, String>();
	try
		{
			conn=connectionUtils.getConnection();
			String s_id= null;
			String start_date="";
			String end_date="";
			SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			SimpleDateFormat tf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			if(dateRangeText != null && ! dateRangeText.equals("")) {
				
				if(dateRangeText.contains("-")) {
					String[] dates = dateRangeText.split("-");
					String first_date = dates[0].trim();
					String last_date = dates[1].trim();
					start_date = tf.format(sf.parse(first_date));
					end_date = tf.format(sf.parse(last_date));
					System.out.println(start_date + "**************" + end_date);
					
				} else {
					String date = dateRangeText.trim();
					start_date = tf.format(sf.parse(date));
					end_date = start_date;
					System.out.println(start_date + "**************" + end_date);
				}
				s_id="select run_id , extracted_date from cdg.extraction_status where src_sys_id="+src_id+" and status='Extracted' " + " AND extracted_date>="+"'"+start_date+"'"+" AND extracted_date <="+"'"+end_date+"' order by run_id asc"; 
			}else {
				s_id="select run_id , extracted_date from cdg.extraction_status where src_sys_id="+src_id+" and status='Extracted' order by run_id asc";
			}
			
			Statement statement = conn.createStatement();
			System.out.println("Query +" + s_id);
			ResultSet rs =statement.executeQuery(s_id);
			while (rs.next()) {
				run_list.put(rs.getString(1), rs.getString(2));
			}
		//connectionUtils.closeQuietly(conn);
			conn.close();
		return run_list;
	}
	catch(Exception e){
		//conn.close();
		return run_list;	
	}
}

public ArrayList<String> getMDFileList(Integer src_id) {
	// TODO Auto-generated method stub
	ArrayList<String> file_list= new ArrayList<String>();
	Connection conn = null;
	try
		{
			conn=connectionUtils.getConnection();
			String s_id="select src_file_id from cdg.pub_src_file_dtls where src_sys_id="+src_id;
			Statement statement = conn.createStatement();
			ResultSet rs =statement.executeQuery(s_id);
			while (rs.next()) {
				file_list.add(rs.getString(1));
			}
		//connectionUtils.closeQuietly(conn);
			conn.close();
		return file_list;
	}
	catch(Exception e){
		//conn.close();
		return file_list;	
	}
}

	public SourceSystemBean getSourceSystemMetadata(int src_sys_id) {
		SourceSystemBean system = new SourceSystemBean();
		Connection conn = null;
		try {

			conn = connectionUtils.getConnection();
			String s_id = "	select m.src_sys_id, m.src_unique_name ,s.src_bkt,s.src_loc,s.tgt_prjt,s.tgt_ds,s.tgt_type,s.src_type  "
					+ "from cdg.pub_src_sys_dtls s inner join source_system_master m "
					+ "on (s.src_sys_id = m.src_sys_id) where m.src_sys_id =" + src_sys_id;
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(s_id);
			while (rs.next()) {
				system.setSrc_sys_id(rs.getInt(1));
				system.setSrc_unique_name(rs.getString(2));
				system.setSrc_bkt(rs.getString(3));
				system.setSrc_loc(rs.getString(4));
				system.setTgt_prjt(rs.getString(5));
				system.setTgt_ds(rs.getString(6));
				system.setTgt_type(rs.getString(7));
				system.setSrc_type(rs.getString(8));
			}
			//connectionUtils.closeQuietly(conn);
			conn.close();
			return system;
		} catch (Exception e) {
			e.printStackTrace();
			//conn.close();
			return null;
		}
	}
public SourceSystemFileBean getSourceFileMetadata(int src_sys_id, String file_id){
	SourceSystemFileBean file = new SourceSystemFileBean();
	Connection conn = null;
	try {

		conn = connectionUtils.getConnection();
		String s_id = "select src_file_name ,src_file_desc ,src_file_type ,src_file_delimiter ,tgt_tbl_name ,"
				+ "src_sch_loc ,src_hdr_cnt ,src_trl_cnt ,src_cnt_start_idx ,src_cnt_end_idx ,data_class_catg ,src_load_type "
				+ "from pub_src_file_dtls where src_sys_id=" +src_sys_id+" and src_file_id='"+file_id+"'";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			file.setSrc_file_name(rs.getString(1));
			file.setSrc_file_desc(rs.getString(2));
			file.setSrc_file_type(rs.getString(3));
			file.setSrc_file_delimiter(rs.getString(4));
			file.setTgt_tbl_name(rs.getString(5));
			file.setSrc_sch_loc(rs.getString(6));
			file.setSrc_hdr_cnt(rs.getString(7));
			file.setSrc_trl_cnt(rs.getString(8));
			file.setSrc_cnt_start_idx(rs.getString(9));
			file.setSrc_cnt_end_idx(rs.getString(10));
			file.setData_class_catg(rs.getString(11));
			if(rs.getString(12).equalsIgnoreCase("F"))
			{
				file.setSrc_load_type("Full Load");
			}
			else if(rs.getString(12).equalsIgnoreCase("I"))
			{
				file.setSrc_load_type("Incremental Load");
			}
			
		}
		//connectionUtils.closeQuietly(conn);
		conn.close();
		return file;
	} catch (Exception e) {
		//conn.close();
		e.printStackTrace();
		return null;
	}
}

@Override
public List<SourceSystemFileBean> getSourceFiles(int src_sys_id){
	List<SourceSystemFileBean> files = new ArrayList<SourceSystemFileBean>();
	SourceSystemFileBean file = new SourceSystemFileBean();
	Connection conn = null;
	try {

		conn = connectionUtils.getConnection();
		String s_id = "select src_file_name ,src_file_desc ,src_file_type ,src_file_delimiter ,tgt_tbl_name ,"
				+ "src_sch_loc ,src_hdr_cnt ,src_trl_cnt ,src_cnt_start_idx ,src_cnt_end_idx ,data_class_catg ,src_load_type "
				+ "from pub_src_file_dtls where src_sys_id=" +src_sys_id;
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			file = new SourceSystemFileBean();
			file.setSrc_file_name(rs.getString(1));
			file.setSrc_file_desc(rs.getString(2));
			file.setSrc_file_type(rs.getString(3));
			file.setSrc_file_delimiter(rs.getString(4));
			file.setTgt_tbl_name(rs.getString(5));
			file.setSrc_sch_loc(rs.getString(6));
			file.setSrc_hdr_cnt(rs.getString(7));
			file.setSrc_trl_cnt(rs.getString(8));
			file.setSrc_cnt_start_idx(rs.getString(9));
			file.setSrc_cnt_end_idx(rs.getString(10));
			file.setData_class_catg(rs.getString(11));
			if(rs.getString(12).equalsIgnoreCase("F"))
			{
				file.setSrc_load_type("Full Load");
			}
			else if(rs.getString(12).equalsIgnoreCase("I"))
			{
				file.setSrc_load_type("Incremental Load");
			}
			files.add(file);
		}
		//connectionUtils.closeQuietly(conn);
		conn.close();
		return files;
	} catch (Exception e) {
		//conn.close();
		e.printStackTrace();
		return null;
	}
}

public List<SourceSystemFieldBean> getSourceFieldMetadata(int src_sys_id, String file_id){
	List<SourceSystemFieldBean> fields = new ArrayList<SourceSystemFieldBean>();
	SourceSystemFieldBean fieldBean = null;
	Connection conn = null;
	try {
		conn = connectionUtils.getConnection();
		String s_id = "select fld_pos_num,src_sch_name,src_fld_name,src_fld_desc,src_fld_data_typ,"
				+ "trg_fld_data_typ,fld_null_flg,tgt_tbl_prtn_flg,pii_flg,fxd_fld_strt_idx,"
				+ "fxd_fld_end_idx,fxd_fld_len,pkey from cdg.pub_src_fld_dtls"
				+ " where src_sys_id=" +src_sys_id+" and src_file_id='"+file_id+"'";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			fieldBean = new SourceSystemFieldBean();
			fieldBean.setFld_pos_num(rs.getInt(1));
			fieldBean.setSrc_sch_name(rs.getString(2));
			fieldBean.setSrc_fld_name(rs.getString(3));
			fieldBean.setSrc_fld_desc(rs.getString(4));
			//fieldBean.setSrc_fld_desc(rs.getString(5));
			fieldBean.setSrc_fld_data_typ(rs.getString(5));
			fieldBean.setTrg_fld_data_typ(rs.getString(6));
			fieldBean.setFld_null_flg(rs.getString(7));
			fieldBean.setTgt_tbl_prtn_flg(rs.getString(8));
			fieldBean.setPii_flg(rs.getString(9));
			fieldBean.setFxd_fld_strt_idx(rs.getInt(10));
			fieldBean.setFxd_fld_end_idx(rs.getInt(11));
			fieldBean.setFxd_fld_len(rs.getInt(12));
			fieldBean.setPkey(rs.getString(13));
			fields.add(fieldBean);
		}
		//connectionUtils.closeQuietly(conn);
		conn.close();
		return fields;
	} catch (Exception e) {
		e.printStackTrace();
		//conn.close();
		return null;
	}
}

@Override
public Map<String, List<String>> getSourceFields(int src_sys_id){
	Map<String, List<String>> fileFields = new HashMap<String, List<String>>();
	List<String> fields =null;
	Connection conn = null;
	try {
		conn = connectionUtils.getConnection();
		String s_id = "select src_file_id, src_fld_name from cdg.pub_src_fld_dtls"
				+ " where src_sys_id=" +src_sys_id + " AND trg_fld_data_typ = 'DATE' OR trg_fld_data_typ = 'TIMESTAMP'  order by src_file_id asc";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			if ( fileFields.get(rs.getString(1)) == null ) {
				fields = new ArrayList<String>();
				//fields.add("NONE");
				//fields.add("LOAD_START_TIME");
				fields.add(rs.getString(2));
				fileFields.put(rs.getString(1), fields);
			}else {
				fileFields.get(rs.getString(1)).add(rs.getString(2));
			}
		}
		conn.close();
		return fileFields;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}


@Override
public Map<String, List<SourceSystemFieldBean>> getSourceFieldMetadata(int src_sys_id){
	
	Map<String, List<SourceSystemFieldBean>> fileFields = new HashMap<String, List<SourceSystemFieldBean>>();
	List<SourceSystemFieldBean> fields =null;
	Connection conn = null;
	try {
		conn = connectionUtils.getConnection();
		String s_id = "select src_file_id, fld_pos_num,src_fld_name ,src_fld_data_typ,trg_fld_data_typ from cdg.pub_src_fld_dtls"
				+ " where src_sys_id=" +src_sys_id + "  order by src_file_id asc";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		SourceSystemFieldBean bean = null;
		while (rs.next()) {
			if ( fileFields.get(rs.getString(1)) == null ) {
				fields = new ArrayList<SourceSystemFieldBean>();
				 bean = new SourceSystemFieldBean();
				bean.setSrc_file_id(rs.getString(1));
				bean.setFld_pos_num(rs.getInt(2));
				bean.setSrc_fld_name(rs.getString(3));
				bean.setSrc_fld_data_typ(rs.getString(4));
				bean.setTrg_fld_data_typ(rs.getString(5));
				//fields.add("NONE");
				//fields.add("LOAD_START_TIME");
				fields.add(bean);
				fileFields.put(rs.getString(1), fields);
			}else {
				fields = fileFields.get(rs.getString(1));
				 bean = new SourceSystemFieldBean();
				bean.setSrc_file_id(rs.getString(1));
				bean.setFld_pos_num(rs.getInt(2));
				bean.setSrc_fld_name(rs.getString(3));
				bean.setSrc_fld_data_typ(rs.getString(4));
				bean.setTrg_fld_data_typ(rs.getString(5));
				//fields.add("NONE");
				//fields.add("LOAD_START_TIME");
				fields.add(bean);
				
						//.add(rs.getString(2));
			}
		}
		conn.close();
		return fileFields;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

public Map<String, List<String>> getSourceAllFields(int src_sys_id){
	Map<String, List<String>> fileFields = new HashMap<String, List<String>>();
	List<String> fields =null;
	Connection conn = null;
	try {
		conn = connectionUtils.getConnection();
		String s_id = "select src_file_id, src_fld_name from cdg.pub_src_fld_dtls"
				+ " where src_sys_id=" +src_sys_id + "  order by src_file_id asc";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			if ( fileFields.get(rs.getString(1)) == null ) {
				fields = new ArrayList<String>();
				//fields.add("NONE");
				//fields.add("LOAD_START_TIME");
				fields.add(rs.getString(2));
				fileFields.put(rs.getString(1), fields);
			}else {
				fileFields.get(rs.getString(1)).add(rs.getString(2));
			}
		}
		conn.close();
		return fileFields;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

public ArrayList<String> populateDatasets()
{
   ArrayList<String> dataSetList = BigQueryUtils.getDataSet();
   return dataSetList;
}

public ArrayList<String> populateSrcDBList() {
	// TODO Auto-generated method stub
	ArrayList<String> file_list= new ArrayList<String>();
	Connection conn = null;
	try
		{
			conn=connectionUtils.getConnection();
			String s_id="select distinct src_db_typ from cdg.mstr_datatype_link_dtls";
			Statement statement = conn.createStatement();
			ResultSet rs =statement.executeQuery(s_id);
			while (rs.next()) {
				file_list.add(rs.getString(1));
			}
		conn.close();
		return file_list;
	}
	catch(Exception e){
		return file_list;	
	}
}

public ArrayList<String> populateTgtDBList() {
	// TODO Auto-generated method stub
	ArrayList<String> file_list= new ArrayList<String>();
	Connection conn = null;
	try
		{
			conn=connectionUtils.getConnection();
			String s_id="select distinct tgt_db_typ from cdg.mstr_datatype_link_dtls";
			Statement statement = conn.createStatement();
			ResultSet rs =statement.executeQuery(s_id);
			while (rs.next()) {
				file_list.add(rs.getString(1));
			}
		//connectionUtils.closeQuietly(conn);
			conn.close();
		return file_list;
	}
	catch(Exception e){
		return file_list;	
	}
}

public List<DataTypeLinkBean> getDataTypeLinkList(String src_db, String tgt_db) {
	List<DataTypeLinkBean> dataTypeList = new ArrayList<DataTypeLinkBean>();
	DataTypeLinkBean dataBean = null;
	Connection conn = null;
	try {
		conn = connectionUtils.getConnection();
		String s_id = "select src_data_typ,tgt_data_typ from cdg.mstr_datatype_link_dtls where src_db_typ ="
						+"'"+src_db+"'"+"and tgt_db_typ='"+tgt_db+"'";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			dataBean = new DataTypeLinkBean();
			dataBean.setSrc_data_type(rs.getString(1));
			dataBean.setTgt_data_type(rs.getString(2));
			dataTypeList.add(dataBean);
		}
		//connectionUtils.closeQuietly(conn);
		conn.close();
		return dataTypeList;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

public String getFeedName(String feed_id) {
	Connection conn = null;
	String feed_name=null;
	try {
		conn = connectionUtils.getConnection();
		String s_id = "SELECT src_unique_name FROM cdg.source_system_master where src_sys_id="+feed_id;
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			feed_name=rs.getString(1);
			}
		conn.close();
		return feed_name;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

public ArrayList<String> populateTables(String dataset)
{
   ArrayList<String> tableList = BigQueryUtils.getTables(dataset);
   return tableList;
}

public ArrayList<String> populateRunIDs(String ds_name,String table_name) {
	ArrayList<String> tableList = new ArrayList<String>();	
	String query = "SELECT distinct run_id FROM " + ds_name + "." + table_name;
	try {
		//TODO: Need to get Project name 
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().toBuilder()
				.setProjectId(MySqlConstants.PROJECTNAME).build().getService();
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
		//ArrayList<String> fieldList = getFields(dataset, tableName);
		for (FieldValueList row : bigquery.query(queryConfig).iterateAll()) {
			for (int i = 0; i < row.size(); i++) {
				tableList.add(row.get(i).getValue().toString());
			}
		}
	} catch (NullPointerException e) {
		e.printStackTrace();
	} catch (JobException e) {
		e.printStackTrace();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	return tableList;
}

@Override
public Map<String, String> getReconSysIds() {
	Connection conn = null;
	try{
		Map<String,String> src_map= new HashMap<String, String>();
			 conn=connectionUtils.getConnection();
		String s_id="SELECT DISTINCT m.src_sys_id, n.src_unique_name \r\n" + 
				"FROM cdg.batch_audit_job_dtls  m  \r\n" + 
				"LEFT OUTER JOIN cdg.source_system_master n   \r\n" + 
				"ON (m.src_sys_id = n.src_sys_id)\r\n" + 
				"where n.src_unique_name IS NOT NULL";
		Statement statement = conn.createStatement();
		ResultSet rs =statement.executeQuery(s_id);
		while (rs.next()) {
			src_map.put(rs.getString(1), rs.getString(2));
		}
		conn.close();
		return src_map;
	}catch(Exception e){
		return null;	
	}
}

@Override
public ArrayList<String> reconRunIDs(Integer src_id) {
	// TODO Auto-generated method stub
		Connection conn = null;
		ArrayList<String> run_list= new ArrayList<String>();
		try
			{
				conn=connectionUtils.getConnection();
				String s_id="select distinct run_id from cdg.batch_audit_job_dtls where src_sys_id ="+src_id;
				Statement statement = conn.createStatement();
				ResultSet rs =statement.executeQuery(s_id);
				while (rs.next()) {
					run_list.add(rs.getString(1));
				}
			//connectionUtils.closeQuietly(conn);
				conn.close();
			return run_list;
		}
		catch(Exception e){
			//conn.close();
			return run_list;	
		}
}

@Override
public ArrayList<ReconDashboardBean> reconDashData(Integer src_id, String run_id) {
	ArrayList<ReconDashboardBean> reconBeanList = new ArrayList<ReconDashboardBean>();
	ReconDashboardBean reconBean = null;
	Connection conn = null;
	try {
		conn = connectionUtils.getConnection();
		String s_id = "SELECT  DISTINCT src_file_id,cnt_src,cnt_tgt \r\n" + 
				"FROM batch_audit_job_dtls \r\n" + 
				"WHERE src_sys_id ="+src_id+"\r\n" + 
				"AND run_id="+"'"+run_id+"'";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(s_id);
		while (rs.next()) {
			reconBean = new ReconDashboardBean();
			reconBean.setSrc_file_id(rs.getString(1));
			reconBean.setSrc_count(rs.getString(2));
			reconBean.setTgt_count(rs.getString(3));
			reconBeanList.add(reconBean);
		}
		//connectionUtils.closeQuietly(conn);
		conn.close();
		return reconBeanList;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

}