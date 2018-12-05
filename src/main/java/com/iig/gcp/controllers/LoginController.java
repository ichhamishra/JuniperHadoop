package com.iig.gcp.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.login.service.LoginService;



@Controller
@SessionAttributes(value= {"user","arrProject","menu_code","project","projectFeatureMap"})
public class LoginController {


	@Autowired
	LoginService loginService;

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public String homePage() {
		return "/index";
	}

	@RequestMapping(value = { "/login"}, method = RequestMethod.GET)
	public String login() {

		return "/login/login";
	}


	@RequestMapping(value = {"/accessDenied" })
	public String unAuthorizedPage() {
		return "accessDenied";
	}

	@RequestMapping(value = { "/login/dashboard"}, method = RequestMethod.GET)
	public String helpPage() {
		return "/cdg_home";
	}

	@RequestMapping(value = { "/login/submit"}, method = RequestMethod.GET)
	public ModelAndView authenticateUser(String username,String password,ModelMap modelMap , Principal principal ) {
		boolean flag=false;
		username = principal.getName();
		UserAccount user=null;
		try {
			ArrayList<UserAccount> arrUserAccount= loginService.getUserAccount();
			for(int i=0;i<arrUserAccount.size();i++) {
				user=arrUserAccount.get(i);
				if(user.getUser_id().equals(username)) {
					/*if(user.getUser_pass().equals(password)) {

				}*/
					flag=true;
					break;

				}
			}


			if(!flag) {
				modelMap.addAttribute("errorString","Please Raise GSR to Access Juniper");
				return new ModelAndView("login/login");
			}else {
				ArrayList<Project> arrProject = loginService.getProjects(username);
				modelMap.addAttribute("user",user);
				HashMap<String,Integer> hsmap=new HashMap<String,Integer>();
				for(Project project:arrProject ) {
					hsmap.put(project.getProject_id(), project.getProject_sequence());
				}
				modelMap.addAttribute("arrProject",arrProject);
				modelMap.addAttribute("projectFeatureMap", hsmap);
				String menu_code=loginService.getJAdminMenuCodes(user.getUser_sequence());
				if(!menu_code.isEmpty()||!menu_code.contains("")){
					modelMap.addAttribute("menu_code",menu_code);	
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorString",e.getMessage());
			return new ModelAndView("login/login");
		}
		return new ModelAndView("cdg_home");
	}

	/**
	 * 
	 * @param project
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/login/features"}, method = RequestMethod.POST)
	public ModelAndView getFeatures(String project,ModelMap modelMap ,HttpServletRequest request) {
		String menu_code=null;
		try {
			UserAccount user = (UserAccount)request.getSession().getAttribute("user");
			menu_code=loginService.getMenuCodes(user.getUser_sequence());
			modelMap.addAttribute("menu_code",menu_code);
			modelMap.addAttribute("project",project);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("cdg_home");
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/logout"}, method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "/login/login";
	}
}