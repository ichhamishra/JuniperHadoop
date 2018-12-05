package com.iig.gcp.controllers;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
import com.iig.gcp.hipdashboard.service.HipService;

@Controller
public class HipController {

	@Autowired
	HipService hipService;
	
	@RequestMapping(value = { "/hip"}, method = RequestMethod.GET)
    public String hipDashboard(ModelMap map) {
		
		try {
		Map<String,List<String>> plt_feed = new HashMap<String, List<String>>();
		ArrayList<String> feed = new ArrayList<String>();
		/*feed1.add("Ingestion001");
		feed1.add("Ingestion002");
		feed1.add("Ingestion003");
		ArrayList<String> feed2 = new ArrayList<String>();
		feed2.add("Ingestion008");
		feed2.add("Ingestion009");
		feed2.add("Ingestion010");
		
		plt_feed.put("GBDS", feed1);
		plt_feed.put("BAU", feed2);*/
		
		ArrayList<String> pltList = hipService.getPlatform();
		for (String platform: pltList) {
			feed = hipService.getPlatformFeed(platform);
			plt_feed.put(platform, feed);
		}
		map.addAttribute("plt_feed", plt_feed);
		//ArrayList<String> fs = hipService.getfeedsFromLoggerStats();
		//map.addAttribute("feed_id", fs);
		map.addAttribute("pltList", pltList);
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
        return "/hip/hipdashboard";
    }
	
	
	@RequestMapping(value="/hip/tblFeedList",method=RequestMethod.POST)
	public ModelAndView tblFeedList(@Valid @ModelAttribute("tbl_feed_id")String tbl_feed_id,@ModelAttribute("final_plt_id")String plt_id, ModelMap map) {
		try {
			ArrayList<String> table_list = hipService.getTablefromFeed(tbl_feed_id,plt_id);
			System.out.println("in cont: "+tbl_feed_id);
			System.out.println("in com " +plt_id);
			
			map.addAttribute("table_list", table_list);
			map.addAttribute("plt_id", plt_id);
			map.addAttribute("tbl_feed_id", tbl_feed_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("/hip/hiptableList0");
	}
	@RequestMapping(value = { "/hip/tableIdFilter"}, method = RequestMethod.POST)
	public ModelAndView tableIdFilter(@Valid @ModelAttribute("plt_id")String plt_id,@ModelAttribute("feed_id")String feed_id,@ModelAttribute("tbl_id")String tbl_id, ModelMap map)
			throws Exception {
	
		ArrayList<String> tblArrBatchDate=new ArrayList<String>();
		ArrayList<String> tblArrDuration=new ArrayList<String>();
		ArrayList<String> tblArrCount=new ArrayList<String>();
		ArrayList<HipDashboardDTO> tblArrHipDashboard = hipService.getTableDashboardChartLoggerStats(plt_id,feed_id,tbl_id);
		map.addAttribute("tblArrHipDashboard", tblArrHipDashboard);
		ArrayList<Integer> arrDurationInt = new ArrayList<Integer>();
		ArrayList<Integer> arrCountInt = new ArrayList<Integer>();
		
		for(HipDashboardDTO hipVO :tblArrHipDashboard) {
			arrDurationInt.add(Integer.parseInt(hipVO.getDuration()));
			arrCountInt.add(Integer.parseInt(hipVO.getTable_count()));
			tblArrBatchDate.add(hipVO.getBatch_date().toString());
			System.out.println("batch date"+hipVO.getBatch_date().toString());
			tblArrDuration.add(hipVO.getDuration());
			System.out.println("Duration"+hipVO.getDuration());
			tblArrCount.add(hipVO.getTable_count());
			System.out.println("count"+hipVO.getTable_count());
		}
		map.addAttribute("feed_id", feed_id);
		map.addAttribute("tbl_x", tblArrBatchDate);
		map.addAttribute("tbl_y",tblArrDuration);
		map.addAttribute("tbl_z",tblArrCount);
		map.addAttribute("stat",2);
		map.addAttribute("tbl_max_duration",Collections.max(arrDurationInt));
		map.addAttribute("tbl_min_duration",Collections.min(arrDurationInt));
		map.addAttribute("tbl_avg_duration",calculateAverage(arrDurationInt));
		map.addAttribute("tbl_max_count",Collections.max(arrCountInt));
		map.addAttribute("tbl_min_count",Collections.min(arrCountInt));
		map.addAttribute("tbl_avg_count",calculateAverage(arrCountInt));
		System.out.println("before JSP--filter");
        return  new ModelAndView("/hip/hipTableDashboard");
    }
	
	@RequestMapping(value = { "/hip/tableDatefilter"}, method = RequestMethod.POST)
	public ModelAndView tableDatefilter(@Valid @ModelAttribute("plt_id")String plt_id,@ModelAttribute("feed_id")String feed_id,@ModelAttribute("tbl_id")String tbl_id, ModelMap map)
			throws Exception {
		System.out.println(feed_id);
		System.out.println(plt_id);
		System.out.println(tbl_id);
		ArrayList<String> arrBatchDate=new ArrayList<String>();
		ArrayList<String> arrDuration=new ArrayList<String>();
		ArrayList<HipDashboardDTO> arrHipDashboard = hipService.getTableChartLoggerStats(feed_id);
		map.addAttribute("arrHipDashboard", arrHipDashboard);
		ArrayList<Integer> arrDurationInt = new ArrayList<Integer>();
		for(HipDashboardDTO hipVO :arrHipDashboard) {
			arrDurationInt.add(Integer.parseInt(hipVO.getDuration()));
			arrBatchDate.add(hipVO.getBatch_date().toString());
			System.out.println("batch date"+hipVO.getBatch_date().toString());
			arrDuration.add(hipVO.getDuration());
			System.out.println("Duration"+hipVO.getDuration());
		}
		map.addAttribute("feed_id", feed_id);
		map.addAttribute("x", arrBatchDate);
		map.addAttribute("y",arrDuration);
		map.addAttribute("stat",2);
		//map.addAttribute("max",Collections.max(arrDuration));
		//map.addAttribute("min",Collections.min(arrDuration));
		//map.addAttribute("average",calculateAverage(arrDurationInt));
		System.out.println("before JSP--filter");
        return  new ModelAndView("/hip/hipTableDashboard");
    }
	
	@RequestMapping(value = { "/hipmaster"}, method = RequestMethod.GET)
	public ModelAndView hipmasterDashboard(ModelMap map)
			throws Exception {
		ArrayList<String> fs = hipService.getfeeds();
		map.addAttribute("feed_id", fs);
        return  new ModelAndView("/hip/hipmasterdashboard");
    }
	
	@RequestMapping(value = { "/hip/feedIdFilter"}, method = RequestMethod.POST)
	public ModelAndView hipFeedFilter(ModelMap map,@Valid @RequestParam("feed_id") String feed_id)
			throws Exception {
		
		System.out.println("Inside FeedFilter in controller");
		ArrayList<String> arrBatchDate=new ArrayList<String>();
		ArrayList<String> arrDuration=new ArrayList<String>();
		ArrayList<HipDashboardDTO> arrHipDashboard = hipService.getTableChartLoggerStats(feed_id);
		map.addAttribute("arrHipDashboard", arrHipDashboard);
		ArrayList<Integer> arrDurationInt = new ArrayList<Integer>();
		for(HipDashboardDTO hipVO :arrHipDashboard) {
			arrDurationInt.add(Integer.parseInt(hipVO.getDuration()));
			arrBatchDate.add(hipVO.getBatch_date().toString());
			System.out.println("batch date"+hipVO.getBatch_date().toString());
			arrDuration.add(hipVO.getDuration());
			System.out.println("Duration"+hipVO.getDuration());
		}
		map.addAttribute("feed_id", feed_id);
		map.addAttribute("x", arrBatchDate);
		map.addAttribute("y",arrDuration);
		map.addAttribute("stat",2);
		map.addAttribute("max",Collections.max(arrDurationInt));
		map.addAttribute("min",Collections.min(arrDurationInt));
		map.addAttribute("average",calculateAverage(arrDurationInt));
		System.out.println("before JSP--filter");
        return  new ModelAndView("/hip/hipdashboard2");
    }
	
	public  double calculateAverage(List<Integer> arr) {
		  Double sum = 0.0;
		  if(!arr.isEmpty()) {
		    for (Integer mark : arr) {
		        sum += mark;
		    }
		    Double a= sum/ arr.size();
		    double roundOff = Math.round(a * 100.0) / 100.0;
		    return roundOff;
		  }
		  return sum;
		}

	
	@RequestMapping(value = { "/hip/hipmasterdashboard1"}, method = RequestMethod.POST)
	public ModelAndView hipmasterDashboard1(@Valid @ModelAttribute("feed_id") String feed_id, ModelMap map)
			throws Exception {
		ArrayList<FeedLoggerDTO> fs = hipService.getfeeddetails(feed_id);
		map.addAttribute("feed", fs);
        return  new ModelAndView("/hip/hipmasterdashboard1");
    }
	
	@RequestMapping(value = { "/hip/filterSearch"}, method = RequestMethod.POST)
	public ModelAndView hipFeedSearchFilter(ModelMap map,@Valid @RequestParam("feed_id") String feed_id)
			throws Exception {
		System.out.println("Ins	ide filterSearch in controller");
		String statFeed=hipService.checkFeedAvailable(feed_id);
		String stat=statFeed.substring(0, 1);
		System.out.println("help me"+stat);
		if(stat.equals("0")) {
			map.addAttribute("stat",Integer.parseInt(stat));
			ArrayList<String> arrBatchDate=new ArrayList<String>();
			ArrayList<String> arrDuration=new ArrayList<String>();
			ArrayList<HipDashboardDTO> arrHipDashboard = hipService.getTableChartLoggerStats(feed_id);
			map.addAttribute("arrHipDashboard", arrHipDashboard);
			ArrayList<Integer> arrDurationInt = new ArrayList<Integer>();
			for(HipDashboardDTO hipVO :arrHipDashboard) {
				arrDurationInt.add(Integer.parseInt(hipVO.getDuration()));
				arrBatchDate.add(hipVO.getBatch_date().toString());
				arrDuration.add(hipVO.getDuration());
			}
			map.addAttribute("feed_id", feed_id);
			map.addAttribute("x", arrBatchDate);
			map.addAttribute("y",arrDuration);
			map.addAttribute("max",Collections.max(arrDurationInt));
			map.addAttribute("min",Collections.min(arrDurationInt));
			map.addAttribute("average",calculateAverage(arrDurationInt));
			System.out.println("before JSP--filterSearch");
			 return  new ModelAndView("/hip/hipdashboard2");
		}else {
			
			map.addAttribute("stat",Integer.parseInt(stat));
			System.out.println("in else");
			System.out.println("before JSP--message");
			 return  new ModelAndView("/hip/hipdashboard2");
		}
		
	}
	
	
	@RequestMapping(value = { "/hip/datefilter"}, method = RequestMethod.POST)
	public ModelAndView hipdateFilter(ModelMap map,@Valid @RequestParam("feedIdFilter") String feedIdFilter,@RequestParam("date") String date)
			throws Exception {
		
		String statFeed=hipService.checkFeedAvailable(feedIdFilter);
		String stat=statFeed.substring(0, 1);
		System.out.println("help me"+stat);
		System.out.println(date);
		System.out.println(feedIdFilter);
		if(stat.equals("0")) {
			map.addAttribute("stat",Integer.parseInt(stat));
			ArrayList<String> arrBatchDate=new ArrayList<String>();
			ArrayList<String> arrDuration=new ArrayList<String>();
			ArrayList<HipDashboardDTO> arrHipDashboard = hipService.getTablechartUsingDate(feedIdFilter,date);
			map.addAttribute("arrHipDashboard", arrHipDashboard);
			ArrayList<Integer> arrDurationInt = new ArrayList<Integer>();
			for(HipDashboardDTO hipVO :arrHipDashboard) {
				arrDurationInt.add(Integer.parseInt(hipVO.getDuration()));
				System.out.println("Job Id:"+hipVO.getBatch_date().toString());
				arrBatchDate.add(hipVO.getBatch_date().toString());
				arrDuration.add(hipVO.getDuration());
			}
			map.addAttribute("feed_id", feedIdFilter);
			map.addAttribute("x", arrBatchDate);
			map.addAttribute("y",arrDuration);
			map.addAttribute("max",Collections.max(arrDuration));
			map.addAttribute("min",Collections.min(arrDuration));
			map.addAttribute("average",calculateAverage(arrDurationInt));
			return  new ModelAndView("/hip/hipdashboard2");
		}else {
			map.addAttribute("stat",Integer.parseInt(stat));
			System.out.println("in else");
			
			 return  new ModelAndView("/hip/hipdashboard2");
		}
		
	}
	
	//------------------------START HIP2 DASHBOARD----------------------------------------------
	
	@RequestMapping(value = { "/hip/feedTablesByDropdown"}, method = RequestMethod.POST)
	public ModelAndView feedTablesByDropdown(ModelMap map,@Valid @RequestParam("feed_id") String feed_id) {
		try {
			ArrayList<String> feedTables= hipService.getFeedTables(feed_id);
			map.addAttribute("feedTables", feedTables);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
	return  new ModelAndView("/hip/hipdashboard3");
	}
	
	
	@RequestMapping(value = { "/hip/feedTablesBySearch"}, method = RequestMethod.POST)
	public ModelAndView feedTablesBySearch(ModelMap map,@Valid @RequestParam("feed_id") String feed_id) {
		ModelAndView modelview=null;
		try {
			
			String statFeed = hipService.checkFeedAvailable(feed_id);
			String stat=statFeed.substring(0, 1);
			if(stat.equals("0")) {
				ArrayList<String> feedTables= hipService.getFeedTables(feed_id);
				map.addAttribute("feedTables", feedTables);
				modelview =new ModelAndView("/hip/hipdashboard3");
				return  modelview;
			}
			else {
				map.addAttribute("stat",Integer.parseInt(stat));
				modelview =new ModelAndView("/hip/hipdashboard3");
				 return  modelview;
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return modelview;
		
	
	}
	
	//------------------------STOP HIP2 DASHBOARD----------------------------------------------
	
	
	
}
