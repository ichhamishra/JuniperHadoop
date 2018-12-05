package com.iig.gcp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.system.service.SystemService;

@Controller
@SessionAttributes(value= {"user"})
public class SystemController {


	@Autowired
	private SystemService systemService;

	@RequestMapping(value = { "system/systemOnboardForm"}, method = RequestMethod.GET)
	public String onBoardSystem() {
		return "/system/SystemOnboard";
	}

	/**
	 * This method accepts region and fetch list of countries mapped against them.
	 * @param region
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/system/FetchCountriesForRegion"}, method = RequestMethod.POST)
	public ModelAndView fetchCountriesForRegion(@Valid @RequestParam("region") String region,ModelMap modelMap) {
		List<CountryMaster> countries = systemService.fetchCountries(region);
		modelMap.addAttribute("countries", countries);
		return new ModelAndView("/system/SystemOnboard1");
	}


	/**
	 * This method validated EIM, if duplicate.
	 * @param system_eim
	 * @param model
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws Exception
	 */
	@RequestMapping(value = "/system/EIMValidation", method = RequestMethod.POST)
	public ModelAndView eimValidation(@Valid @RequestParam(value = "eim", required = true) String system_eim, ModelMap model) throws UnsupportedOperationException, Exception {
		int stat = systemService.checkEIM(system_eim);
		model.addAttribute("stat", stat);
		return new ModelAndView("/system/SystemOnboard2");
	}





	/**
	 * This method register new system in db.
	 * @param feed_id
	 * @param job_id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/system/register"}, method = RequestMethod.POST)
	public ModelAndView registerSystem(@Valid @RequestParam("system_eim") String systemEIM,@RequestParam("system_name") String systemName,
			@RequestParam("system_region") String region,@RequestParam("system_country") String country,
			@RequestParam("environment_type") String environmentType,HttpServletRequest request,ModelMap modelMap) {
		try {
				UserAccount user = (UserAccount)request.getSession().getAttribute("user");
				if(user==null) {
					return new ModelAndView("/login/login");
				}
				String message = systemService.registerSystem(systemEIM,systemName,region,country,environmentType,user.getUser_id());
				if (message.equals("Success")) {
					modelMap.addAttribute("successString", "System Registeration Successful");
				} else {
					modelMap.addAttribute("errorString", "System Registeration Failure");
				}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorString", e.getMessage());
		}
		return new ModelAndView("/system/SystemOnboard");
	}
}
