package com.iig.gcp.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.publishing.DataTypeLinkBean;
import com.iig.gcp.publishing.PublishingService;
import com.iig.gcp.publishing.ReconDashboardBean;
import com.iig.gcp.publishing.SourceSystemBean;
import com.iig.gcp.publishing.SourceSystemFieldBean;
import com.iig.gcp.publishing.SourceSystemFileBean;


@Controller
public class PublishingController {

	@Autowired
	private PublishingService ps;
	
	@RequestMapping(value="/publishing/addMetaDataHome",method=RequestMethod.GET)
	public ModelAndView publishingMetadataHome() {
		return new ModelAndView("/publishing/addMetaDataHome");
	}
	
	@RequestMapping(value="/publishing/publishingHome",method=RequestMethod.GET)
	public ModelAndView publishingHome() {
		return new ModelAndView("/publishing/publishingHome");
	}
	
	@RequestMapping(value="/publishing/resetMetadataHome",method=RequestMethod.GET)
	public ModelAndView resetMetadataHome(@Valid ModelMap model) {
		ArrayList<String> allDatabases= ps.populateDatasets();
		model.addAttribute("allDatabases", allDatabases);
		return new ModelAndView("/publishing/resetMetadataHome");
	}
	
	@RequestMapping(value="/publishing/viewMetadataHome",method=RequestMethod.GET)
		public ModelAndView viewMetadataHome(@Valid ModelMap model) {
		Map<String,String> viewSSID = ps.getMDSysList();
		//get Source DB list
		ArrayList<String> srcDBList= ps.populateSrcDBList();
		ArrayList<String> tgtDBList= ps.populateTgtDBList();
		model.addAttribute("viewSSID", viewSSID);
		model.addAttribute("srcDBList", srcDBList);
		model.addAttribute("tgtDBList", tgtDBList);
		return new ModelAndView("/publishing/viewMetadataHome");
	}
	
	@RequestMapping(value="/publishing/editMetadataHome",method=RequestMethod.GET)
	public ModelAndView editMetadataHome(@Valid ModelMap model) {
		ArrayList<String> srcDBList= ps.populateSrcDBList();
		ArrayList<String> tgtDBList= ps.populateTgtDBList();
		Map<String,String> editSSID = ps.getMDSysList();
		model.addAttribute("editSSID", editSSID);
		model.addAttribute("srcDBList", srcDBList);
		model.addAttribute("tgtDBList", tgtDBList);
		return new ModelAndView("/publishing/editMetadataHome");
	}
	
	@RequestMapping(value="/publishing/reconDashboard",method=RequestMethod.GET)
	public ModelAndView reconDashboard(@Valid ModelMap model) {
		Map<String,String> srcSysId = ps.getReconSysIds();
		model.addAttribute("srcSysId", srcSysId);
	return new ModelAndView("/publishing/reconDashboard");
	}
		
	@RequestMapping(value="/publishing/publishingAddMetaData",method=RequestMethod.POST)
	public ModelAndView publishingAddMetadataHome(@Valid @ModelAttribute("tgt_val")String tgt_val, ModelMap model) {
		ArrayList<String> allDatabases= ps.populateDatasets();
		Map<String,String> srcSysIds = ps.getSysIds();
		model.addAttribute("allDatabases", allDatabases);
		model.addAttribute("tgt_val", tgt_val);
		model.addAttribute("srcSysIds", srcSysIds);
		return new ModelAndView("/publishing/publishingAddMetaData");
	}

	@RequestMapping(value="/publishing/publishingScheduler",method=RequestMethod.POST)
	public ModelAndView publishingScheduler(@Valid @ModelAttribute("pub_tgt_val")String pub_tgt_val, ModelMap model) {
		Map<String,String> exSrcSysId = ps.getExSysIds();
		model.addAttribute("exSrcSysId", exSrcSysId);
		model.addAttribute("pub_tgt_val", pub_tgt_val);
		return new ModelAndView("/publishing/publishingScheduler");
	}
	
	/* Add Metadata controller for new system*/
	@RequestMapping(value="/publishing/publishingAddMetadata1",method=RequestMethod.POST)
	public ModelAndView extractionManageConnection1(@Valid @ModelAttribute("x")String x,@ModelAttribute("target_type")String tgt_typ,ModelMap model) throws UnsupportedOperationException, Exception {
		     String resp = ps.invokeRest(x,"publish/addMetadata/newSource");
		     ArrayList<String> allDatabases= ps.populateDatasets();
		     model.addAttribute("allDatabases", allDatabases);
			 model.addAttribute("tgt_val", tgt_typ);
			 String status = "";
			 if (resp != null) {
				 status = "Success";
				 String feed_id = resp;
				 Integer src_id=Integer.parseInt(feed_id);
				    SourceSystemBean feed_name = ps.getSourceSystemMetadata(src_id);
				       
				    model.addAttribute("feed_id", feed_id);
				    model.addAttribute("feed_name", feed_name);
				   // model.addAttribute("next_button_active", "active");
				    //Success or Failed status
				    
				    List<SourceSystemFileBean> fileList;
					
					fileList=ps.getSourceFiles(src_id);
					Integer entry_cnt=fileList.size();
					model.addAttribute("fileList", fileList);
					model.addAttribute("entry_cnt", entry_cnt);
					Map<String, List<String>> filefields = ps.getSourceFields(src_id);
					model.addAttribute("fileFields", filefields);
					
					Map<String, List<SourceSystemFieldBean>> fileAllfields = ps.getSourceFieldMetadata(src_id);
					model.addAttribute("fileAllfields", fileAllfields);
				
					Map<String, String> publishingType = new HashMap<String,String>();
					publishingType.put("Full Load", "TRUNCATE&LOAD");
					publishingType.put("Incremental Load", "APPEND");
					model.addAttribute("publishingType", publishingType);
					
					model.addAttribute("next_button_active", "active");
					
					model.addAttribute("src_type", "Published");
			 }else {
				 status ="Failed";
			 }
			//Success or Failed status
			   /* String status0[] = resp.toString().split(":");
			    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
			    String status1[]=status0[1].split(",");
			    String status=status1[0].replaceAll("\'","").trim();
			    String message0=status0[2];
			    String message=message0.replaceAll("[\'}]","").trim();
			    String final_message=status+": "+message;
			    System.out.println("final: "+final_message);*/
			    if(status.equalsIgnoreCase("Failed"))
			    {
			    	model.addAttribute("errorString", "Error While Adding Metadata");
			    } 
			    else if(status.equalsIgnoreCase("Success"))
			    {
			    	model.addAttribute("successString", "Metadata Added Successfully");
			    }
		    return new ModelAndView("/publishing/publishingAddMetaData");
	}	
	
	/* Add Metadata controller for extracted system*/
	@RequestMapping(value="/publishing/publishingExtract1",method=RequestMethod.POST)
	public ModelAndView addMetadataExtraction1(@Valid @ModelAttribute("y")String y,@ModelAttribute("target_type")String tgt_typ,@ModelAttribute("src_sys_id")String feed_id, @ModelAttribute("run_id_list")String run_id, ModelMap model) throws UnsupportedOperationException, Exception {
		    String resp = ps.invokeRest(y,"publish/addMetadata/extractedSource");
		    System.out.println("RUNID is" + run_id);
		    model.addAttribute("run_id", run_id);
			ArrayList<String> allDatabases= ps.populateDatasets();	
			Map<String,String> srcSysIds = ps.getSysIds();
			model.addAttribute("srcSysIds", srcSysIds);
			model.addAttribute("allDatabases", allDatabases);
		    model.addAttribute("tgt_val", tgt_typ);
		    Integer src_id=Integer.parseInt(feed_id);
		    SourceSystemBean feed_name = ps.getSourceSystemMetadata(src_id);
		       
		    model.addAttribute("feed_id", feed_id);
		    model.addAttribute("feed_name", feed_name);
		   // model.addAttribute("next_button_active", "active");
		    //Success or Failed status
		    
		    List<SourceSystemFileBean> fileList;
			
			fileList=ps.getSourceFiles(src_id);
			Integer entry_cnt=fileList.size();
			model.addAttribute("fileList", fileList);
			model.addAttribute("entry_cnt", entry_cnt);
			Map<String, List<String>> filefields = ps.getSourceFields(src_id);
			model.addAttribute("fileFields", filefields);
			
			Map<String, List<SourceSystemFieldBean>> fileAllfields = ps.getSourceFieldMetadata(src_id);
			model.addAttribute("fileAllfields", fileAllfields);
		
			Map<String, String> publishingType = new HashMap<String,String>();
			publishingType.put("Full Load", "TRUNCATE&LOAD");
			publishingType.put("Incremental Load", "APPEND");
			model.addAttribute("publishingType", publishingType);
			
			model.addAttribute("src_type", "Extracted");
			
		    String status0[] = resp.toString().split(":");
		    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
		    String status1[]=status0[1].split(",");
		    String status=status1[0].replaceAll("\'","").trim();
		    String message0=status0[2];
		    String message=message0.replaceAll("[\'}]","").trim();
		    String final_message=status+": "+message;
		    System.out.println("final: "+final_message);
		    if(status.equalsIgnoreCase("Failed"))
		    {
		    	model.addAttribute("errorString", final_message);
		    } 
		    else if(status.equalsIgnoreCase("Success"))
		    {
		    	model.addAttribute("successString", final_message);
		    	model.addAttribute("next_button_active", "active");
		    }
		   /* JSONObject jObject  = new JSONObject(resp);
		     String status=jObject.get("status").toString();
		     String msg=jObject.get("message").toString();
		     System.out.println("status: "+status);
		     System.out.println("msg: "+msg);
		     
		     if(status.equalsIgnoreCase("success"))
		     {
		    	 model.addAttribute("successString",msg);
		     }
		     else if(status.equalsIgnoreCase("failed"))
		     {
		    	 model.addAttribute("errorString",msg);
		     }*/
		    
		return new ModelAndView("/publishing/publishingAddMetaData");
	}
	
	
	@RequestMapping(value="/publishing/finalAddMetadata1",method=RequestMethod.POST)
	public ModelAndView finalAddMetadataTemp(@Valid @ModelAttribute("z")String z, ModelMap model) throws UnsupportedOperationException, Exception {
		    System.out.println("inside final add "+z);
			//String resp = ps.invokeRest(z,"publish/confirmFeedDetails");
			//Thread.sleep(5000);
			String resp="{ 'status': 'Success','message':'FEED DETAILS SAVED SUCCESSFULLY' }";
		    String status0[] = resp.toString().split(":");
		    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
		    String status1[]=status0[1].split(",");
		    String status=status1[0].replaceAll("\'","").trim();
		    String message0=status0[2];
		    String message=message0.replaceAll("[\'}]","").trim();
		    String final_message=status+": "+message;
		    System.out.println("final: "+final_message);
		    if(status.equalsIgnoreCase("Failed"))
		    {
		    	model.addAttribute("errorString", final_message);
		    } 
		    else if(status.equalsIgnoreCase("Success"))
		    {
		    	model.addAttribute("successString", final_message);
		    }
	return new ModelAndView("redirect:/publishing/publishingExtract1#step-2");
	}
	
	
	
	
	@RequestMapping(value="/publishing/savePartitionInfo",method=RequestMethod.POST)
	public ModelAndView savePartitionInfo(@Valid @ModelAttribute("z")String z, ModelMap model) throws UnsupportedOperationException, Exception {
		//confirmFeedDetails
		System.out.println("input Json in partition :" + z);
		String resp = ps.invokeRest(z,"publish/savePartitionInfo");
		
		String status0[] = resp.toString().split(":");
	    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
	    String status1[]=status0[1].split(",");
	    String status=status1[0].replaceAll("\'","").trim();
	    String message0=status0[2];
	    String message=message0.replaceAll("[\'}]","").trim();
	    String final_message=status+": "+message;
	    System.out.println("final: "+final_message);
	    if(status.equalsIgnoreCase("Failed"))
	    {
	    	model.addAttribute("errorString", final_message);
	    } 
	    else if(status.equalsIgnoreCase("Success"))
	    {
	    	model.addAttribute("successString", final_message);
	    }
		return new ModelAndView("/publishing/publishingPartitioning");
	}
	
	@RequestMapping(value="/publishing/saveMetadataChanges",method=RequestMethod.POST)
	public ModelAndView saveMetadataChanges(@Valid @ModelAttribute("z")String z,@ModelAttribute("src_sys_id")String src_sys_id,  ModelMap model) throws UnsupportedOperationException, Exception {
		/*ArrayList<String> runIdList;
		Integer src_id=Integer.parseInt(src_sys_id);
		runIdList=ps.getRunIds(src_id); 
		model.addAttribute("runIdList", runIdList);*/
		System.out.println("input Json :" + z);
		if(! z.isEmpty()) {
		String resp = ps.invokeRest(z,"publish/saveMetadataChanges");
		
		String status0[] = resp.toString().split(":");
	    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
	    String status1[]=status0[1].split(",");
	    String status=status1[0].replaceAll("\'","").trim();
	    String message0=status0[2];
	    String message=message0.replaceAll("[\'}]","").trim();
	    String final_message=status+": "+message;
	    System.out.println("final: "+final_message);
	    if(status.equalsIgnoreCase("Failed"))
	    {
	    	model.addAttribute("errorString", final_message);
	    } 
	    else if(status.equalsIgnoreCase("Success"))
	    {
	    	model.addAttribute("successString", final_message);
	    }
		}
		System.out.println("################# " + src_sys_id);
		List<SourceSystemFileBean> fileList;
		int feed_id = Integer.parseInt(src_sys_id);
		fileList=ps.getSourceFiles(feed_id);
		Integer entry_cnt=fileList.size();
		model.addAttribute("fileList", fileList);
		model.addAttribute("entry_cnt", entry_cnt);
		
		
		Map<String, String> publishingType = new HashMap<String,String>();
		publishingType.put("Full Load", "TRUNCATE&LOAD");
		publishingType.put("Incremental Load", "APPEND");
		model.addAttribute("publishingType", publishingType);
		
		Map<String, List<String>> filefields = ps.getSourceFields(feed_id);
		model.addAttribute("fileFields", filefields);
		
		return new ModelAndView("/publishing/publishingPartitioning");
	}
	
	
	/* Add Metadata controller for extracted system*/
	@RequestMapping(value="/publishing/finalAddMetadata",method=RequestMethod.POST)
	public ModelAndView finalAddMetadata(@Valid @ModelAttribute("z")String z, ModelMap model) throws UnsupportedOperationException, Exception {
		    System.out.println("inside final add "+z);
			String resp = ps.invokeRest(z,"publish/confirmFeedDetails");
			//Thread.sleep(5000);
			//String resp="{ 'status': 'Success','message':'FEED DETAILS SAVED SUCCESSFULLY' }";
		    String status0[] = resp.toString().split(":");
		    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
		    String status1[]=status0[1].split(",");
		    String status=status1[0].replaceAll("\'","").trim();
		    String message0=status0[2];
		    String message=message0.replaceAll("[\'}]","").trim();
		    String final_message=status+": "+message;
		    System.out.println("final: "+final_message);
		    if(status.equalsIgnoreCase("Failed"))
		    {
		    	model.addAttribute("errorString", final_message);
		    } 
		    else if(status.equalsIgnoreCase("Success"))
		    {
		    	model.addAttribute("successString", final_message);
		    }
	return new ModelAndView("/publishing/addMetaDataHome");
	}
	
	/* Publish Metadata controller for existing system*/
	@RequestMapping(value="/publishing/publishingExtracted1",method=RequestMethod.POST)
	public ModelAndView publishingExtracted1(@Valid @ModelAttribute("p")String x, @ModelAttribute("pub_type")String pub_type, ModelMap model) throws UnsupportedOperationException, Exception {
			System.out.println("JSON Publish" + x); 
			System.out.println("pub_type" + pub_type);
			String resp = "";
			if (pub_type.equalsIgnoreCase("BQ Load")) {
			 resp = ps.invokePythonRest(x,"publish/publishData");
			}else {
			 resp = ps.invokeRest(x,"publish/publishData");
			}
		  //Success or Failed status'
			System.out.println("JSON Publish" + x);
		   // String resp="{ 'status': 'Success','message':'PUBLISHING JOB TRIGGERED SUCCESSFULLY FOR THIS FEED' }";
		    String status0[] = resp.toString().split(":");
		    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
		    String status1[]=status0[1].split(",");
		    String status=status1[0].replaceAll("\'","").trim();
		    String message0=status0[2];
		    String message=message0.replaceAll("[\'}]","").trim();
		    String final_message=status+": "+message;
		    System.out.println("final: "+final_message);
		    if(status.equalsIgnoreCase("Failed"))
		    {
		    	model.addAttribute("errorString", final_message);
		    } 
		    else if(status.equalsIgnoreCase("Success"))
		    {
		    	model.addAttribute("successString", final_message);
		    }
		return new ModelAndView("/publishing/addMetaDataHome");
	}
	
	
	@RequestMapping(value="/publishing/runIds",method=RequestMethod.POST)
	public ModelAndView publishingRunIds(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		ArrayList<String> runIdList;
		Integer src_id=Integer.parseInt(src_sys_id);
		runIdList=ps.getRunIds(src_id); 
		model.addAttribute("runIdList", runIdList);
		return new ModelAndView("/publishing/publishingSch0");
	}
	

	@RequestMapping(value="/publishing/addMetadataRunIds",method=RequestMethod.POST)
	public ModelAndView publishingAddMetadataRunIds(@Valid @ModelAttribute("src_sys_id")String src_sys_id,  @ModelAttribute("dateRangeText")String dateRangeText , ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		Map<String, String> runIdList;
		Integer src_id=Integer.parseInt(src_sys_id);
		runIdList=ps.getRunIdsWithDate(src_id, dateRangeText); 
		model.addAttribute("runIdList", runIdList);
		model.addAttribute("feed_id",src_sys_id);
		return new ModelAndView("/publishing/publishingSch1");
	}
	
	@RequestMapping(value="/publishing/viewMDSysList",method=RequestMethod.POST)
	public ModelAndView viewMDSysList(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		Integer src_id=Integer.parseInt(src_sys_id);
		SourceSystemBean system = new SourceSystemBean();
		system=ps.getSourceSystemMetadata(src_id);
		model.addAttribute("systemBean", system);
		return new ModelAndView("/publishing/viewSysMetadata0");
	}
	
	@RequestMapping(value="/publishing/viewMDFileList",method=RequestMethod.POST)
	public ModelAndView viewMDFileList(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		ArrayList<String> fileList;
		Integer src_id=Integer.parseInt(src_sys_id);
		fileList=ps.getMDFileList(src_id); 
		model.addAttribute("fileList", fileList);
		model.addAttribute("sys_id",src_id);
		return new ModelAndView("/publishing/viewFileMetadata0");
	}
	
	@RequestMapping(value="/publishing/viewFileMDInfo",method=RequestMethod.POST)
	public ModelAndView viewMDInfo(@Valid @ModelAttribute("file_id")String file_id,@ModelAttribute("sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		Integer src_id=Integer.parseInt(src_sys_id);
		SourceSystemFileBean file = new SourceSystemFileBean();
		file=ps.getSourceFileMetadata(src_id, file_id);
		model.addAttribute("fileBean", file);
		return new ModelAndView("/publishing/viewFileMetadata1");
	}
	
	@RequestMapping(value="/publishing/viewMDFieldList",method=RequestMethod.POST)
	public ModelAndView viewMDFieldList(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		ArrayList<String> fileList;
		Integer src_id=Integer.parseInt(src_sys_id);
		fileList=ps.getMDFileList(src_id); 
		model.addAttribute("fileList", fileList);
		model.addAttribute("sys_id",src_id);
		return new ModelAndView("/publishing/viewFieldMetadata0");
	}
	
	@RequestMapping(value="/publishing/viewFieldMDInfo",method=RequestMethod.POST)
	public ModelAndView viewFieldMDInfo(@Valid @ModelAttribute("file_id")String file_id,@ModelAttribute("sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		Integer src_id=Integer.parseInt(src_sys_id);
		List<SourceSystemFieldBean> fields = new ArrayList<SourceSystemFieldBean>();
		fields=ps.getSourceFieldMetadata(src_id, file_id);
		model.addAttribute("fieldBeanList", fields);
		return new ModelAndView("/publishing/viewFieldMetadata1");
	}

	//Edit Metadata Information
	//Edit System Information
	@RequestMapping(value="/publishing/editMDSysList",method=RequestMethod.POST)
	public ModelAndView editMDSysList(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		Integer src_id=Integer.parseInt(src_sys_id);
		SourceSystemBean system = new SourceSystemBean();
		system=ps.getSourceSystemMetadata(src_id);
		model.addAttribute("systemBean", system);
		return new ModelAndView("/publishing/editSysMetadata0");
	}
	
	//Rest API call for System Edit
	@RequestMapping(value="/publishing/updateSysMD",method=RequestMethod.POST)
	public ModelAndView updateSysMD(@Valid @ModelAttribute("x")String x, ModelMap model) throws UnsupportedOperationException, Exception {
		   	//String resp = ps.invokeRest(x,"publish/editSysMD");
		Thread.sleep(5000);
		String resp="{ 'status': 'Success','message':'FEED DETAILS UPDATED SUCCESSFULLY' }";
	    String status0[] = resp.toString().split(":");
	    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
	    String status1[]=status0[1].split(",");
	    String status=status1[0].replaceAll("\'","").trim();
	    String message0=status0[2];
	    String message=message0.replaceAll("[\'}]","").trim();
	    String final_message=status+": "+message;
	    System.out.println("final: "+final_message);
	    if(status.equalsIgnoreCase("Failed"))
	    {
	    	model.addAttribute("errorString", final_message);
	    } 
	    else if(status.equalsIgnoreCase("Success"))
	    {
	    	model.addAttribute("successString", final_message);
	    }
	    ArrayList<String> srcDBList= ps.populateSrcDBList();
		ArrayList<String> tgtDBList= ps.populateTgtDBList();
		Map<String,String> editSSID = ps.getMDSysList();
		model.addAttribute("editSSID", editSSID);
		model.addAttribute("srcDBList", srcDBList);
		model.addAttribute("tgtDBList", tgtDBList);
		return new ModelAndView("/publishing/editMetadataHome");
	}
	//Edit File Information
	@RequestMapping(value="/publishing/editMDFileList",method=RequestMethod.POST)
	public ModelAndView editMDFileList(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		ArrayList<String> fileList;
		Integer src_id=Integer.parseInt(src_sys_id);
		fileList=ps.getMDFileList(src_id); 
		model.addAttribute("fileList", fileList);
		model.addAttribute("sys_id",src_id);
		return new ModelAndView("/publishing/editFileMetadata0");
	}
	
	//Edit File Information
	@RequestMapping(value="/publishing/editFileMDInfo",method=RequestMethod.POST)
	public ModelAndView editFileMDInfo(@Valid @ModelAttribute("file_id")String file_id,@ModelAttribute("sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		Integer src_id=Integer.parseInt(src_sys_id);
		SourceSystemFileBean file = new SourceSystemFileBean();
		file=ps.getSourceFileMetadata(src_id, file_id);
		model.addAttribute("fileBean", file);
		return new ModelAndView("/publishing/editFileMetadata1");
	}
	
	//Rest API call for File Edit
		@RequestMapping(value="/publishing/updateFileMD",method=RequestMethod.POST)
		public ModelAndView updateFileMD(@Valid @ModelAttribute("x")String x, ModelMap model) throws UnsupportedOperationException, Exception {
			    //String resp = ps.invokeRest(x,"publish/editFileMD");
			Thread.sleep(5000);
			String resp="{ 'status': 'Success','message':'FILE DETAILS UPDATED SUCCESSFULLY' }";
		    String status0[] = resp.toString().split(":");
		    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
		    String status1[]=status0[1].split(",");
		    String status=status1[0].replaceAll("\'","").trim();
		    String message0=status0[2];
		    String message=message0.replaceAll("[\'}]","").trim();
		    String final_message=status+": "+message;
		    System.out.println("final: "+final_message);
		    if(status.equalsIgnoreCase("Failed"))
		    {
		    	model.addAttribute("errorString", final_message);
		    } 
		    else if(status.equalsIgnoreCase("Success"))
		    {
		    	model.addAttribute("successString", final_message);
		    }
		    ArrayList<String> srcDBList= ps.populateSrcDBList();
			ArrayList<String> tgtDBList= ps.populateTgtDBList();
			Map<String,String> editSSID = ps.getMDSysList();
			model.addAttribute("editSSID", editSSID);
			model.addAttribute("srcDBList", srcDBList);
			model.addAttribute("tgtDBList", tgtDBList);
			return new ModelAndView("/publishing/editMetadataHome");
		}
		
	//Edit Field Information
	@RequestMapping(value="/publishing/editMDFieldList",method=RequestMethod.POST)
	public ModelAndView editMDFieldList(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		ArrayList<String> fileList;
		Integer src_id=Integer.parseInt(src_sys_id);
		fileList=ps.getMDFileList(src_id); 
		model.addAttribute("fileList", fileList);
		model.addAttribute("sys_id",src_id);
		return new ModelAndView("/publishing/editFieldMetadata0");
	}
	
	//Edit Field Information
	@RequestMapping(value="/publishing/editFieldMDInfo",method=RequestMethod.POST)
	public ModelAndView editFieldMDInfo(@Valid @ModelAttribute("file_id")String file_id,@ModelAttribute("sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
		Integer src_id=Integer.parseInt(src_sys_id);
		List<SourceSystemFieldBean> fields = new ArrayList<SourceSystemFieldBean>();
		fields=ps.getSourceFieldMetadata(src_id, file_id);
		model.addAttribute("fieldBeanList", fields);
		return new ModelAndView("/publishing/editFieldMetadata1");
	}
	
	//Rest API call for Field Edit
	@RequestMapping(value="/publishing/updateFieldMD",method=RequestMethod.POST)
	public ModelAndView updateFieldMD(@Valid @ModelAttribute("x")String x, ModelMap model) throws UnsupportedOperationException, Exception {
		    //String resp = ps.invokeRest(x,"publish/editFieldMD");
		Thread.sleep(5000);
		String resp="{ 'status': 'Success','message':'FIELD DETAILS UPDATED SUCCESSFULLY' }";
	    String status0[] = resp.toString().split(":");
	    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
	    String status1[]=status0[1].split(",");
	    String status=status1[0].replaceAll("\'","").trim();
	    String message0=status0[2];
	    String message=message0.replaceAll("[\'}]","").trim();
	    String final_message=status+": "+message;
	    System.out.println("final: "+final_message);
	    if(status.equalsIgnoreCase("Failed"))
	    {
	    	model.addAttribute("errorString", final_message);
	    } 
	    else if(status.equalsIgnoreCase("Success"))
	    {
	    	model.addAttribute("successString", final_message);
	    }
	    ArrayList<String> srcDBList= ps.populateSrcDBList();
		ArrayList<String> tgtDBList= ps.populateTgtDBList();
		Map<String,String> editSSID = ps.getMDSysList();
		model.addAttribute("editSSID", editSSID);
		model.addAttribute("srcDBList", srcDBList);
		model.addAttribute("tgtDBList", tgtDBList);
		return new ModelAndView("/publishing/editMetadataHome");
	}
	//View Source & Target DataType Details
	@RequestMapping(value="/publishing/viewDataTypeLinkDetails",method=RequestMethod.POST)
	public ModelAndView viewDataTypeLinkDetails(@Valid @ModelAttribute("src_db")String src_db,@ModelAttribute("tgt_db")String tgt_db, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
			List<DataTypeLinkBean> dataTypeInfo = new ArrayList<DataTypeLinkBean>();
			dataTypeInfo=ps.getDataTypeLinkList(src_db, tgt_db);
			model.addAttribute("dataTypeInfo", dataTypeInfo);
			return new ModelAndView("/publishing/viewDataTypeLink");
	}
	//View Source & Target DataType Details
	@RequestMapping(value="/publishing/editDataTypeLinkDetails",method=RequestMethod.POST)
	public ModelAndView editDataTypeLinkDetails(@Valid @ModelAttribute("src_db")String src_db,@ModelAttribute("tgt_db")String tgt_db, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
			List<DataTypeLinkBean> dataTypeInfo = new ArrayList<DataTypeLinkBean>();
			dataTypeInfo=ps.getDataTypeLinkList(src_db, tgt_db);
			model.addAttribute("dataTypeInfo", dataTypeInfo);
			return new ModelAndView("/publishing/editDataTypeLink");
	}
	//Rest API call for Update Datatype
		@RequestMapping(value="/publishing/updateDataType",method=RequestMethod.POST)
		public ModelAndView updateDataType(@Valid @ModelAttribute("x")String x, ModelMap model) throws UnsupportedOperationException, Exception {
			String resp = ps.invokeRest(x,"publish/updateDataType");
			//Thread.sleep(5000);
			//String resp="{ 'status': 'Success','message':'DATATYPE MAPPING UPDATED SUCCESSFULLY' }";
		    String status0[] = resp.toString().split(":");
		    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
		    String status1[]=status0[1].split(",");
		    String status=status1[0].replaceAll("\'","").trim();
		    String message0=status0[2];
		    String message=message0.replaceAll("[\'}]","").trim();
		    String final_message=status+": "+message;
		    System.out.println("final: "+final_message);
		    if(status.equalsIgnoreCase("Failed"))
		    {
		    	model.addAttribute("errorString", final_message);
		    } 
		    else if(status.equalsIgnoreCase("Success"))
		    {
		    	model.addAttribute("successString", final_message);
		    }
		    ArrayList<String> srcDBList= ps.populateSrcDBList();
			ArrayList<String> tgtDBList= ps.populateTgtDBList();
			Map<String,String> editSSID = ps.getMDSysList();
			model.addAttribute("editSSID", editSSID);
			model.addAttribute("srcDBList", srcDBList);
			model.addAttribute("tgtDBList", tgtDBList);
			return new ModelAndView("/publishing/editMetadataHome");
		}
		
		//Reset Table Information
		@RequestMapping(value="/publishing/resetTable",method=RequestMethod.POST)
		public ModelAndView resetTable(@Valid @ModelAttribute("ds_name")String ds_name, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
			ArrayList<String> tableList= ps.populateTables(ds_name);
			model.addAttribute("tableList", tableList);
			return new ModelAndView("/publishing/resetTable0");
		}
		
		//Reset Table Information
		@RequestMapping(value="/publishing/resetRunIDTableList",method=RequestMethod.POST)
		public ModelAndView resetRunIDTableList(@Valid @ModelAttribute("ds_name")String ds_name, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
			ArrayList<String> tableList= ps.populateTables(ds_name);
			model.addAttribute("tableList", tableList);
			return new ModelAndView("/publishing/resetRunID0");
		}
		
		//Reset RUN ID Information
		@RequestMapping(value="/publishing/resetRunIDList",method=RequestMethod.POST)
		public ModelAndView resetRunIDList(@Valid @ModelAttribute("ds_name")String ds_name,@ModelAttribute("table_name")String table_name, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
			ArrayList<String> runIDList= ps.populateRunIDs(ds_name,table_name);
			model.addAttribute("runIDList", runIDList);
			return new ModelAndView("/publishing/resetRunID1");
		}
		
		/* Add Metadata controller for new system*/
		@RequestMapping(value="/publishing/resetPublishing",method=RequestMethod.POST)
		public ModelAndView resetPublishing(@Valid @ModelAttribute("x")String x,@ModelAttribute("target_type")String tgt_typ,ModelMap model) throws UnsupportedOperationException, Exception {
			     String resp = ps.invokeRest(x,"publish/resetMetadata");
			    
				//Success or Failed status
				    String status0[] = resp.toString().split(":");
				    System.out.println(status0[0]+" value "+status0[1]+" value3: "+status0[2]);
				    String status1[]=status0[1].split(",");
				    String status=status1[0].replaceAll("\'","").trim();
				    String message0=status0[2];
				    String message=message0.replaceAll("[\'}]","").trim();
				    String final_message=status+": "+message;
				    System.out.println("final: "+final_message);
				    
				    ArrayList<String> allDatabases= ps.populateDatasets();
					model.addAttribute("allDatabases", allDatabases);
					
					
					/*ArrayList<String> tableList= ps.populateTables(ds_name);
					model.addAttribute("tableList", tableList);*/
					
					
				    if(status.equalsIgnoreCase("Failed"))
				    {
				    	model.addAttribute("errorString", final_message);
				    } 
				    else if(status.equalsIgnoreCase("Success"))
				    {
				    	model.addAttribute("successString", final_message);
				    }
			    return new ModelAndView("/publishing/resetMetadataHome");
		}	
		
		//Recon Dashboard RUN ID Information
		@RequestMapping(value="/publishing/reconRunIds",method=RequestMethod.POST)
		public ModelAndView reconRunIds(@Valid @ModelAttribute("src_sys_id")String src_sys_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
			Integer src_id=Integer.parseInt(src_sys_id);
			ArrayList<String> runIDList= ps.reconRunIDs(src_id);
			model.addAttribute("runIDList", runIDList);
			model.addAttribute("src_sys_id", src_sys_id);
			return new ModelAndView("/publishing/reconDashboard0");
		} 
		//Recon Dashboard values
		@RequestMapping(value="/publishing/reconDashboardValues",method=RequestMethod.POST)
		public ModelAndView reconDashboardValues(@Valid @ModelAttribute("src_sys_id")String src_sys_id,@Valid @ModelAttribute("recon_run_id")String recon_run_id, ModelMap model) throws IOException, ClassNotFoundException, SQLException {
			Integer src_id=Integer.parseInt(src_sys_id);
			ArrayList<ReconDashboardBean> reconDataList= ps.reconDashData(src_id,recon_run_id);
			model.addAttribute("reconDataList",reconDataList);
			return new ModelAndView("/publishing/reconDashboard1");
		}
}
