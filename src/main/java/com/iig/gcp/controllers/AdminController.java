package com.iig.gcp.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.admin.dto.Group;
import com.iig.gcp.admin.service.AdminService;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.login.service.LoginService;

@Controller
@SessionAttributes(value= {"user","arrProject","menu_code","project","user_sq","projectFeatureMap"})
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	LoginService loginService;

	@RequestMapping(value = { "/admin/usertogrouplink"}, method = RequestMethod.GET)
    public ModelAndView onBoardUser(ModelMap modelMap) throws Exception {
		ArrayList<String> usersid=	adminService.getAllUsers();
		modelMap.addAttribute("user_val",usersid);
		return new ModelAndView("admin/usertogrouplink");
	}

	@RequestMapping(value = { "/admin/userOnboarding"}, method = RequestMethod.GET)
	public ModelAndView userOnboarding(ModelMap modelMap) throws ClassNotFoundException, SQLException {
		List<String>userId=adminService.fetchUserIds();
		modelMap.addAttribute("user_val",userId);
		return new ModelAndView("admin/userOnboarding");
	}

	@RequestMapping(value = { "/admin/UserEdit"}, method = RequestMethod.POST)
	public ModelAndView updateUserDetails(@ModelAttribute("user_val") String userId,ModelMap modelMap) throws ClassNotFoundException, SQLException {
		UserAccount user=adminService.fetchUserDetails(userId);
		modelMap.addAttribute("username",user.getUser_id());
		modelMap.addAttribute("domain",user.getUser_domain());
		modelMap.addAttribute("name",user.getUser_fullname());
		modelMap.addAttribute("email",user.getUser_email());
		return new ModelAndView("admin/userOnboarding2");
	}

	@RequestMapping(value = { "/admin/userOnboarding1"}, method = RequestMethod.POST)
	public ModelAndView userOnboarding(@Valid @RequestParam("username") String userId,
			@RequestParam("domain") String domain, 
			@RequestParam("email") String userEmail,@RequestParam("name") String onboardedUserFullName, @ModelAttribute("button_type") String button_type,
			HttpServletRequest request, ModelMap modelMap) throws Exception {
		String message = null;
		UserAccount loggedInUser = (UserAccount) request.getSession().getAttribute("user");
		if(loggedInUser!=null) {
			try {
				if (button_type.equalsIgnoreCase("add")) {
					message = adminService.registerUserInSystem(userId,domain,onboardedUserFullName, userEmail,loggedInUser.getUser_id());
					if(message.equals("Success")) {
						modelMap.addAttribute("successString","User Registation Successful");
					}else {
						modelMap.addAttribute("errorString","User Registation Failed");
					}
				} else {
					message = adminService.updateUser(userId,domain,onboardedUserFullName,userEmail,loggedInUser.getUser_id());
					if(message.equals("Success")) {
						modelMap.addAttribute("successString","User Updated");
					}else {
						modelMap.addAttribute("errorString","User Update Failed");
					}
				} 
			} catch (Exception e) {
				modelMap.addAttribute("errorStatus", message);
				e.printStackTrace();
			}
		}
		ArrayList<String> usersid=	adminService.getAllUsers();
		modelMap.addAttribute("user_val",usersid);
		return new ModelAndView("admin/userOnboarding");
	}

	@RequestMapping(value = { "/admin/selectuser"}, method = RequestMethod.POST)
	public ModelAndView selectUser(@Valid @RequestParam(value = "user", required = true) String user,ModelMap modelMap,HttpServletRequest request) {
		try {
			String project=(String)request.getSession().getAttribute("project");
			String statuserid=	adminService.getUser(user);	
			String stat=statuserid.substring(0, 1);
			String userid=statuserid.substring(1, statuserid.length());
			ArrayList<Group> group = adminService.getGroups(user,project);
			ArrayList<Group> groupp = adminService.getGroupsAlready(user,project);

			modelMap.addAttribute("stat",Integer.parseInt(stat));
			modelMap.addAttribute("userid",userid);
			modelMap.addAttribute("group", group);
			modelMap.addAttribute("groupp", groupp);

		}catch(Exception e) {
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();
		}
		return new ModelAndView("admin/userdiv");
	}

	@RequestMapping(value = { "/admin/onboarduser"}, method = RequestMethod.POST)
    public ModelAndView submitUser(@Valid String x,@Valid boolean project_admin, ModelMap modelMap,HttpServletRequest request) throws Exception {
		
		try {
			adminService.onBoardUser(x, project_admin, request);
			modelMap.addAttribute("successString","User Onboarded");

		}catch(Exception e) {
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();

		}
		ArrayList<String> usersid=	adminService.getAllUsers();
		modelMap.addAttribute("user_val",usersid);
		return new ModelAndView("admin/usertogrouplink");
	}


	@RequestMapping(value = { "/admin/project"}, method = RequestMethod.GET)
	public ModelAndView onBoardProject(ModelMap modelMap) {
		List<String> projects = adminService.getProjects();
		modelMap.addAttribute("proj_val", projects);
		return new ModelAndView("admin/onboardProject");
	}

	@RequestMapping(value = { "/admin/saveProjectDetailsForm"}, method = RequestMethod.POST)
	public ModelAndView saveProjectDetails(ModelMap modelMap) {
		return new ModelAndView("admin/onboardProject");
	}

	@RequestMapping(value = { "/admin/saveSystemDetailsForm"}, method = RequestMethod.POST)
	public ModelAndView saveSystemDetails(ModelMap modelMap) {
		return new ModelAndView("admin/onboardProject");
	}



	@RequestMapping(value = { "/admin/addProjectDetails" }, method = RequestMethod.POST)
	public ModelAndView registerProject(@Valid @RequestParam("project_id") String projectId,
			@RequestParam("project_name") String projectName, @RequestParam("project_owner") String projectOwner,
			@RequestParam("project_description") String projectDescription, @ModelAttribute("button_type") String button_type,
			HttpServletRequest request, ModelMap modelMap) throws Exception{
		String message = null;
		String message1 = null;
		UserAccount user = (UserAccount) request.getSession().getAttribute("user");
		try {
			if (button_type.equalsIgnoreCase("add")) {
				message = adminService.registerProject(projectId, projectName, projectOwner, projectDescription,user.getUser_sequence());
				if(message.equals("Success")) {
					Project project = adminService.getProject(projectId);
					adminService.linkUserGroupsToNewProject(user.getUser_sequence(),project);
					fetchProjectDetailsForUser(modelMap, message, user);
					modelMap.addAttribute("successString","Project Registration Completed successfully");
				}else {
					modelMap.addAttribute("errorString","Project Registration Failed");
				}

			} else {
				message = adminService.updateProject(projectId, projectName, projectOwner, projectDescription,user.getUser_id());
				if(message.equals("Success")) {
					fetchProjectDetailsForUser(modelMap, message, user);
					modelMap.addAttribute("successString","Project update completed");
				}else {
					modelMap.addAttribute("errorString","Project update failed");
				}
			}
			/*Commenting this code as of now, may be needed in future. 
			else if (button_type.equalsIgnoreCase("del")) {
				message = adminService.deleteProject(projectId);
				fetchProjectDetailsForUser(modelMap, message, user);
			}*/
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", message);
			e.printStackTrace();
		}
		return onBoardProject(modelMap);
	}


	/**
	 * This method fetch projects related to user and create map with project id and project sequence.
	 * @param modelMap
	 * @param message
	 * @param user
	 * @throws Exception
	 */
	private void fetchProjectDetailsForUser(ModelMap modelMap, String message, UserAccount user) throws Exception {
		modelMap.addAttribute("successString", message);
		ArrayList<Project> arrProject = loginService.getProjects(user.getUser_id());
		modelMap.addAttribute("arrProject", arrProject);
		HashMap<String, Integer> hsmap = new HashMap<String, Integer>();
		for (Project project : arrProject) {

			hsmap.put(project.getProject_id(), project.getProject_sequence());
		}
		modelMap.addAttribute("projectFeatureMap", hsmap);
	}

	/**
	 * This method fetch project details related to project id.
	 * @param projId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/getProjectDetails", method = RequestMethod.POST)
	public ModelAndView fetchProjectDetails(@Valid String projId, ModelMap model) {
		Project project = adminService.fetchProjectDetails(projId);
		model.addAttribute("project_id", project.getProject_id());
		model.addAttribute("project_name", project.getProject_name());
		model.addAttribute("project_owner", project.getProject_owner());
		model.addAttribute("project_description", project.getProject_description());
		return new ModelAndView("admin/ProjectDetailsEdit");
	}


}
