package com.iig.gcp.login.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.login.dao.LoginDAO;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

@Service
public class LoginServiceImpl implements LoginService{

	
	@Autowired
	LoginDAO loginDAO;
	
	@Override
	public ArrayList<UserAccount> getUserAccount() throws Exception {
		return loginDAO.getUserAccount();
	}

	@Override
	public UserAccount findUserFromId(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.findUserFromId(user_id);
	}
	
	@Override
	public List<String> findUserRoles(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.findUserRoles(user_id);
	}
	
	@Override
	public ArrayList<Project> getProjects(String username) throws Exception {
		return loginDAO.getProjects(username);
	}

	@Override
	public String getMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException {
		return loginDAO.getMenuCodes(user_sequence);
	}

	@Override
	public String getJAdminMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException {
		return loginDAO.getJAdminMenuCodes(user_sequence);
	}

}
