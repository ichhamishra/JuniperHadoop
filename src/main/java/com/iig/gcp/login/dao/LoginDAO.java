package com.iig.gcp.login.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

public interface LoginDAO {
	
	//User
	ArrayList<UserAccount> getUserAccount() throws Exception;
	
	
	UserAccount findUserFromId(String user_id) throws Exception;
	
	List<String> findUserRoles(String user_id) throws Exception;
	
	
	//Project
	ArrayList<Project> getProjects(String username)throws Exception;
	
	
	//Feature
	String getMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException;
	String getJAdminMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException;
	
}
