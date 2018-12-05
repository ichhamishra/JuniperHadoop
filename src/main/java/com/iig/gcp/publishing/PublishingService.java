package com.iig.gcp.publishing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface PublishingService {

		public String invokeRest(String json,String url) throws UnsupportedOperationException, Exception ;
		public String invokePythonRest(String json,String url) throws UnsupportedOperationException, Exception;
		public ArrayList<String> populateDatasets();
		public ArrayList<String> populateTables(String dataset);
		public ArrayList<String> populateRunIDs(String ds_name,String table_name);
		public Map<String, String> getSysIds();
		public Map<String, String> getExSysIds();
		public Map<String, String> getMDSysList();
		public ArrayList<String> getRunIds(Integer src_id);
		public Map<String, String> getRunIdsWithDate(Integer src_id ,String dateRangeText);
		public ArrayList<String> getMDFileList(Integer src_id);
		public SourceSystemBean getSourceSystemMetadata(int src_sys_id);
		public SourceSystemFileBean getSourceFileMetadata(int src_sys_id, String file_id);
		public List<SourceSystemFileBean> getSourceFiles(int src_sys_id);
		public List<SourceSystemFieldBean> getSourceFieldMetadata(int src_sys_id, String file_id);
		public Map<String, List<SourceSystemFieldBean>> getSourceFieldMetadata(int src_sys_id);
		public ArrayList<String> populateSrcDBList();
		public ArrayList<String> populateTgtDBList();
		public List<DataTypeLinkBean> getDataTypeLinkList(String src_db, String tgt_db);
		public String getFeedName(String feed_id);
		public Map<String, List<String>> getSourceFields(int src_sys_id);
		public Map<String, List<String>> getSourceAllFields(int src_sys_id);
		public Map<String, String>  getReconSysIds(); 
		public ArrayList<String> reconRunIDs(Integer src_id);
		public ArrayList<ReconDashboardBean> reconDashData(Integer src_id, String run_id);
	}
