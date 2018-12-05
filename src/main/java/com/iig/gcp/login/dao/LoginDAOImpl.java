package com.iig.gcp.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.login.dto.UserGroup;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class LoginDAOImpl implements LoginDAO {

	private static String USER_MASTER_TABLE = "JUNIPER_USER_MASTER";
	private static String USER_GROUP_MASTER_TABLE = "JUNIPER_USER_GROUP_MASTER";
	private static String UGROUP_USER_MASTER_TABLE = "JUNIPER_UGROUP_USER_LINK";
	private static String FEATURE_MASTER_TABLE = "JUNIPER_FEATURE_MASTER";
	
	//User_Group_Constants
	private static String JADMIN_GROUP = "JUNIPER_JADMIN";

	/**
	 * 
	 */
	@Override
	public ArrayList<UserAccount> getUserAccount() throws Exception {
		ArrayList<UserAccount> arrUsers= new ArrayList<UserAccount>();
		String sql = "select user_id,user_pass,user_sequence from "+USER_MASTER_TABLE+"";
		Connection conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql); 
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			UserAccount user = new UserAccount();
			user.setUser_id(rs.getString(1));
			user.setUser_pass(rs.getString(2));
			user.setUser_sequence(rs.getInt(3));
			arrUsers.add(user);	
		}
		ConnectionUtils.closeQuietly(conn);
		return arrUsers;
	}


	
	@Override
	public UserAccount findUserFromId(String user_id) throws Exception {
		
		String sql = "select user_id,user_pass,user_sequence from juniper_user_master where user_id='"+user_id+"'";

		Connection conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql); 
		ResultSet rs = pstm.executeQuery();
		UserAccount user = new UserAccount();
		while (rs.next()) {
				user.setUser_id(rs.getString(1));
				user.setUser_pass(rs.getString(2));
				user.setUser_sequence(rs.getInt(3));
		}
		ConnectionUtils.closeQuietly(conn);
		return user;
	}
	
	
	@Override
	public List<String> findUserRoles(String user_id) throws Exception {
		
		String sql = "select ugm.feature_list from juniper_user_master u,juniper_ugroup_user_link ugl,juniper_user_group_master ugm where u.user_sequence=ugl.user_sequence and ugl.USER_GROUP_SEQUENCE =ugm.USER_GROUP_SEQUENCE and u.USER_ID='"+user_id+"'";

		Connection conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql); 
		ResultSet rs = pstm.executeQuery();
		List<String> userRole = new ArrayList<String>();
		StringBuffer featureId = new StringBuffer();
		while (rs.next()) {
			for(String r: rs.getString(1).split(",")) {
				featureId.append(r + ",");
				//userRole.add(r);	
			}
		}
		if(featureId.length() > 0) {
			featureId.setLength(featureId.length()-1);
			System.out.println("Features" + featureId.toString());
			sql = "select feature_name FROM juniper_feature_master f where f.feature_sequence in (" +featureId.toString()+ ")";
			pstm = conn.prepareStatement(sql); 
			rs = pstm.executeQuery();
			while (rs.next()) {
				userRole.add(rs.getString(1));	
			}
		}
		ConnectionUtils.closeQuietly(conn);
		return userRole;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ArrayList<Project> getProjects(String username) throws Exception {

		ArrayList<Project> arrProject=new ArrayList<Project>();
		String sql =  "SELECT  DISTINCT p.PROJECT_ID ,p.project_sequence FROM "+UGROUP_USER_MASTER_TABLE+" l inner join "
				+ "juniper_project_master p on l.project_sequence=p.project_sequence where user_sequence = "
				+ "(select user_sequence from "+USER_MASTER_TABLE+" where user_id=?)";
		Connection conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, username);
		ResultSet rs = pstm.executeQuery();
		Project prj =null;
		while (rs.next()) {
			prj=new Project();
			prj.setProject_id(rs.getString(1));
			prj.setProject_sequence(rs.getInt(2));
			arrProject.add(prj);	
		}
		ConnectionUtils.closeQuietly(conn);
		return arrProject;

	}

	/**
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public List<UserGroup> fetchGroupDetailsForUser(Connection conn,int user_sequence) throws ClassNotFoundException, SQLException {
		List<UserGroup> userGroups=new ArrayList<UserGroup>();

		String sql = "select distinct b.user_group_sequence,b.feature_list from \r\n" + 
				"(select user_group_sequence from "+UGROUP_USER_MASTER_TABLE+"  where user_sequence=?)a\r\n" + 
				"join \r\n" + 
				"(select * from "+USER_GROUP_MASTER_TABLE+")b\r\n" + 
				"on a.user_group_sequence = b.user_group_sequence";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, user_sequence);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			UserGroup ugroup = new UserGroup();
			ugroup.setUser_group_sequence(Integer.parseInt(rs.getString(1)));
			ugroup.setFeature_list(rs.getString(2));
			userGroups.add(ugroup);
		}
		ConnectionUtils.closeQuietly(conn);
		return userGroups;

	}
	
	/**
	 * 
	 * @param user_sequence
	 * @param project
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public String getMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException {
		String menu_code="";
		String menu_link=null;
		List<String> menu_name=new ArrayList<String>();
		List<Integer> menu_levell=new ArrayList<Integer>();  
		Set<Integer> masterFeatureList=new HashSet<Integer>();     
		int menu_level=0;
		int i=0;
		Connection conn= ConnectionUtils.getConnection();
		List<UserGroup> userGroups=fetchGroupDetailsForUser(conn, user_sequence);
			Set<Integer>sortedFeatureList=fetchSortedFeatureIds(userGroups);
			String sql = "select f.feature_link,f.feature_level,f.feature_name FROM "+FEATURE_MASTER_TABLE+" f where f.feature_sequence=?";
			PreparedStatement pstm = conn.prepareStatement(sql);

			for(int featureId:sortedFeatureList) {
				
				masterFeatureList.add(featureId);
				pstm.setInt(1, featureId);
				ResultSet rs = pstm.executeQuery();
				while(rs.next())
				{
					menu_link = rs.getString(1);
					menu_level = rs.getInt(2);
					menu_name.add(rs.getString(3));
					menu_levell.add(menu_level);
					if(menu_level==1){
						if(i==0){
							menu_code=menu_link;
						}
						else{
							menu_code=menu_code+"</ul></div></li>"+menu_link;
						}
					}
					else{
						menu_code=menu_code+menu_link;
					}
					i++;
				}
			}
		ConnectionUtils.closeQuietly(conn);
		return menu_code=menu_code+"</ul></div></li>";
	}


	/**
	 * 
	 * @param userGroup
	 * @return
	 */
	private Set<Integer> fetchSortedFeatureIds(List<UserGroup> userGroups) {
		Set<Integer> sortedFeatures = new TreeSet<Integer>();
		for(UserGroup userGroup:userGroups)
		{
			String feature = userGroup.getFeature_list();
			Set<Integer> features = new HashSet<Integer>();
			if(feature!=null&&!feature.isEmpty()){
				List<String> featureList = Arrays.asList(feature.split(","));
				for(String current:featureList){
					features.add(Integer.parseInt(current));
				}
				sortedFeatures.addAll(features);
			}
		}
		return sortedFeatures;
	}

/**
 * This method check if user belongs to JAdmin, if yes he will be given JAdmin features.
 */
	@Override
	public String getJAdminMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException {
		String menu_code="";
		String menu_link=null;
		List<String> menu_name=new ArrayList<String>();
		List<Integer> menu_levell=new ArrayList<Integer>();  
		int menu_level=0;
		int i=0;
		Connection conn= ConnectionUtils.getConnection();
		List<UserGroup> userGroups=fetchGroupDetailsForUser(conn, user_sequence);
		
		String isAdmin=checkIfUserBelongsToJAdminGroup(conn,userGroups);
		
		//If user is not part of JAdmin then return
		if(!isAdmin.equals("Y")||!isAdmin.contains("Y")) {
			return menu_code;
		}
			Set<Integer>sortedFeatureList=fetchSortedFeatureIds(userGroups);
			String sql = "select f.feature_link,f.feature_level,f.feature_name FROM "+FEATURE_MASTER_TABLE+" f where f.feature_admin= ? and f.feature_sequence=?";
			PreparedStatement pstm = conn.prepareStatement(sql);

			for(int featureId:sortedFeatureList) {
				pstm.setString(1, "Y");
				pstm.setInt(2, featureId);
				ResultSet rs = pstm.executeQuery();
				while(rs.next())
				{
					menu_link = rs.getString(1);
					menu_level = rs.getInt(2);
					menu_name.add(rs.getString(3));
					menu_levell.add(menu_level);
					if(menu_level==1){
						if(i==0){
							menu_code=menu_link;
						}
						else{
							menu_code=menu_code+"</ul></div></li>"+menu_link;
						}
					}
					else{
						menu_code=menu_code+menu_link;
					}
					i++;
				}
			}
		ConnectionUtils.closeQuietly(conn);
		return menu_code=menu_code+"</ul></div></li>";
	}

/**
 * 
 * @param conn
 * @param userGroups
 * @return
 * @throws SQLException 
 */
	private String checkIfUserBelongsToJAdminGroup(Connection conn,List<UserGroup> userGroups) throws SQLException {
			String userPartOfJAdminUserGroup = "N";
			for(UserGroup userGroup:userGroups){
				String sql = "select user_group_name from "+USER_GROUP_MASTER_TABLE+" where user_group_sequence="+userGroup.getUser_group_sequence()+"";
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				while (rs.next()) {
					String userGroupName=rs.getString(1);
					if(userGroupName.equals(""+JADMIN_GROUP+"")) {
						userPartOfJAdminUserGroup="Y";
						return userPartOfJAdminUserGroup;
					}
				}
			}
			return userPartOfJAdminUserGroup;
		}
}
