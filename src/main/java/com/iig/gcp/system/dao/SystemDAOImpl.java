package com.iig.gcp.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class SystemDAOImpl implements SystemDAO {

	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String SYSTEM_MASTER_TABLE = "JUNIPER_SYSTEM_MASTER";
	private static String COUNTRY_REGION_MAPPING_TABLE = "JUNIPER_REGION_COUNTRY_MASTER";

	/**
	 * This method fetch list of countries mapped with region from database
	 * 
	 * @param : String - region
	 * @return: List of countries
	 */
	@Override
	public List<CountryMaster> fetchCountries(@Valid String region) {
		CountryMaster cm = null;
		ArrayList<CountryMaster> countries = new ArrayList<CountryMaster>();
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			String query ="select country_code,country_name from "+ COUNTRY_REGION_MAPPING_TABLE+"  where region_name=? ";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setString(1, region);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				cm = new CountryMaster();
				cm.setCountry_code(rs.getString(1));
				cm.setCountry_name(rs.getString(2));
				countries.add(cm);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return countries;
		}

	/**
	 * This method check if EIM already exists in database or not.
	 * 
	 * @param: String eim
	 * @return 1 if EIM already exists otherwise returns 0.
	 */
	@Override
	public int checkEIM(@Valid String system_eim) {
		Connection connection = null;
		int stat = 0;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection
					.prepareStatement("select system_eim from " + SYSTEM_MASTER_TABLE + " where system_eim=?");
			pstm.setString(1, system_eim);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				stat = 1;
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ConnectionUtils.closeQuietly(connection);
		return stat;
	}

	/**
	 * This method register system form details and push them in database.
	 */
	@Override
	public String registerSystem(@Valid String systemEIM, String systemName, String region, String country,
			String environmentType, String userName)
			throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionUtils.getConnection();
		String registerProjectQuery;
		try {
				registerProjectQuery = "INSERT INTO" + SPACE + SYSTEM_MASTER_TABLE + SPACE
						+ "(system_eim,system_name,system_region,system_country,environment_type,created_by,created_date)"
						+ "VALUES (" + QUOTE + systemEIM + QUOTE + COMMA + QUOTE + systemName + QUOTE + COMMA + QUOTE
						+ region + QUOTE + COMMA + QUOTE + country + QUOTE + COMMA 
						+ QUOTE + environmentType + QUOTE+ COMMA + QUOTE + userName + QUOTE + COMMA + "SYSTIMESTAMP"
						+ ")";
			Statement statement = conn.createStatement();
			statement.execute(registerProjectQuery);
			ConnectionUtils.closeQuietly(conn);
			return "Success";
		} catch (Exception e) {
			e.printStackTrace();
			ConnectionUtils.closeQuietly(conn);
			return "Failure";
		}
	}
}
