package com.iig.gcp.controllers;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.datapropagation.service.DatapropagationService;
/*import com.iig.gcp.datapropagation.DatapropagationService;
import com.iig.gcp.datapropagation.dao.DatapropagationDAO;
import com.iig.gcp.datapropagation.dto.ReservoirDTO;*/
import com.iig.gcp.utils.BigQueryUtils;
@SpringBootApplication
@Controller
@RestController
public class DatapropagationController {
	
	@Autowired
	DatapropagationService es;

	/*@Autowired
	DatapropagationDAO iDatapropagationDAO;*/
	
@RequestMapping(value = "/datapropagation/DatapropagationHome", method = RequestMethod.GET)
	public ModelAndView DatapropagationHome() {
		return new ModelAndView("datapropagation/DatapropagationHome");
	}


@RequestMapping(value = "/datapropagation/SourceDetails", method = RequestMethod.POST)
public ModelAndView ArchiveData(@Valid @ModelAttribute("src_val") String src_val, ModelMap model,HttpServletRequest request) {
	ArrayList<String> system = es.getSystem((String)request.getSession().getAttribute("project"));
	model.addAttribute("system", system);
if(src_val.equalsIgnoreCase("Hive")) { return new ModelAndView("/datapropagation/SourceDetails"); }
else { return new ModelAndView("/datapropagation/DatapropagationHome"); }
}
@RequestMapping(value = "/datapropagation/FeedHome", method = RequestMethod.GET)
public ModelAndView TargetHome() {
	return new ModelAndView("datapropagation/FeedHome");

}
@RequestMapping(value = "/datapropagation/propagate", method = RequestMethod.GET)
public ModelAndView Datapropagate(@Valid @ModelAttribute("src_val") String src_val, ModelMap model,HttpServletRequest request) {
	ArrayList<String> system = es.getSystem((String)request.getSession().getAttribute("project"));
	model.addAttribute("system", system); 
	return new ModelAndView("datapropagation/propagate"); 
}
@RequestMapping(value = "/datapropagation/Submit", method = RequestMethod.POST)
public ModelAndView dataprop(@Valid @ModelAttribute("x") String x, ModelMap model)
		throws UnsupportedOperationException, Exception {
	
	return new ModelAndView("datapropagation/propagate");
}

@RequestMapping(value = "/datapropagation/SubmitBatch", method = RequestMethod.POST)
public ModelAndView datapropagationSubmitBatch(@Valid @ModelAttribute("x2") String x2, ModelMap model)
		throws UnsupportedOperationException, Exception {

	String resp = es.invokeRest(x2, "addConnection");
	model.addAttribute("successString", resp.toString());
	/*ArrayList<ReservoirDTO> arr = eService.getReservior();
	model.addAttribute("loadReservior", arr);*/
	return new ModelAndView("datapropagation/SourceDetails");
}



@RequestMapping(value = "/datapropagation/SubmitBatch1", method = RequestMethod.POST)
public ModelAndView datapropagationSubmitBatch1(@Valid @ModelAttribute("x") String x, ModelMap model)
		throws UnsupportedOperationException, Exception {
	String resp = es.invokeRest(x, "addConnection");
	model.addAttribute("successString", resp.toString());
	/*ArrayList<ReservoirDTO> arr = eService.getReservior();
	model.addAttribute("loadReservior", arr);*/
	return new ModelAndView("datapropagation/TargetDetails");
}



}

