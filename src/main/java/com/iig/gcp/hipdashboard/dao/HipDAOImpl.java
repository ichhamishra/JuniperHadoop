package com.iig.gcp.hipdashboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
import com.iig.gcp.utils.ConnectionUtils;
@Component
public class HipDAOImpl implements HipDAO {

	
	@Override
	public ArrayList<String> getPlatform() throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
			connection = ConnectionUtils.getConnection();
			 pstm = connection.prepareStatement("select TRIM((lm.VALUE)) from logger_master lm where UPPER(TRIM(lm.subclassification))='PLATFORM'");
			 rs = pstm.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}	
	
	@Override
	public ArrayList<String> getPlatformFeed(String platform) throws SQLException, Exception {
		ArrayList<String> feed = new ArrayList<String>();	
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
			connection = ConnectionUtils.getConnection();
			 pstm = connection.prepareStatement("select DISTINCT TRIM(lsm.event_feed_id) from logger_master lm inner join logger_stats_master lsm \r\n" + 
			 		"on trim(upper(lm.feed_id))=trim(upper(lsm.event_feed_id))\r\n" + 
			 		"where UPPER(TRIM(lm.subclassification))='PLATFORM' and UPPER(TRIM(lm.classification))='TRANSFER'  and TRIM((lm.VALUE))=? order by 1");
			 pstm.setString(1, platform);
			 rs = pstm.executeQuery();
			while (rs.next()) {
				feed.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(connection);
		return feed;
	}		
	
	@Override
	public ArrayList<String> getTablefromFeed(String tbl_feed_id,String plt_id) throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
			connection = ConnectionUtils.getConnection();
			 pstm = connection.prepareStatement("select value from logger_master where upper(trim(feed_id))=upper(trim(?)) and (upper(trim(classification))='TABLE'"
			 		+ "or upper(trim(classification))='FILE')");
			 pstm.setString(1, tbl_feed_id);
			 rs = pstm.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}	
	
	@Override
	public ArrayList<String> getfeeds() throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		try {
			connection = ConnectionUtils.getConnection();
			 pstm = connection.prepareStatement("select distinct TRIM((lm.feed_id)) from logger_master lm");
			 rs = pstm.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}
	
	@Override
	public ArrayList<FeedLoggerDTO> getfeeddetails(String feed_id) throws SQLException, Exception {
		ArrayList<FeedLoggerDTO> arr = new ArrayList<FeedLoggerDTO>();
		Connection connection = null;
		PreparedStatement pstm =null;
		ResultSet rs= null;
		try {
			connection = ConnectionUtils.getConnection();
			 pstm = connection.prepareStatement("select * from logger_master where feed_id='"+feed_id+"' order by classification,subclassification");
			 rs = pstm.executeQuery();
			while (rs.next()) {
				FeedLoggerDTO fl=new FeedLoggerDTO();
				fl.setFeed_id(rs.getString(1));
				fl.setClassification(rs.getString(2));
				fl.setSubclassification(rs.getString(3));
				fl.setValue(rs.getString(4));
				arr.add(fl);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}

	@Override
	public ArrayList<String> getfeedsFromLoggerStats(@Valid String platform) throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement pstm =null;
		ResultSet rs= null;
		try {
			connection = ConnectionUtils.getConnection();
			 pstm = connection.prepareStatement("select distinct TRIM(UPPER(lm.feed_id)) from logger_master lm inner join (select EVENT_FEED_ID from LOGGER_STATS_MASTER where TRIM(UPPER(EVENT_TYPE)) = 'PLATFORM' and TRIM(UPPER(EVENT_VALUE))= ? ) lsm on upper(TRIM(lm.feed_id))=upper(TRIM(EVENT_FEED_ID)) order by 1");
			 pstm.setString(1, platform);
			 rs = pstm.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}

	@Override
	public ArrayList<HipDashboardDTO> getTableChartLoggerStats(@Valid String feed_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		ArrayList<HipDashboardDTO> arrHipDashboard=new ArrayList<HipDashboardDTO>();
		HipDashboardDTO hipDashboardDTO = null;
		Connection conn = ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement("WITH feed_id AS ( \r\n" + 
				"SELECT DISTINCT feed_id FROM logger_master ) \r\n" + 
				"SELECT st.event_feed_id, to_char(st.event_batch_date,'DD-MON-YY'), st.event_run_id, st.event_value AS start_time, en.event_value AS end_time, CAST((TO_DATE(concat(concat(en.event_batch_date, ' '), en.event_value), 'DD-MM-YY hh24:mi:ss') - TO_DATE(concat(concat(st.event_batch_date , ' '), st.event_value), 'DD-MM-YY hh24:mi:ss')) * 1440 AS DECIMAL(20, 2)) AS duration \r\n" + 
				"FROM ( SELECT * FROM logger_stats_master \r\n" + 
				"INNER JOIN feed_id ON upper(TRIM(feed_id)) = upper(TRIM(event_feed_id)) AND upper(TRIM(event_type)) = upper('feed-start') AND TRIM(upper(event_feed_id)) = TRIM(upper(?))) st \r\n" + 
				"INNER JOIN ( SELECT * FROM logger_stats_master \r\n" + 
				"INNER JOIN feed_id ON upper(TRIM(feed_id)) = upper(TRIM(event_feed_id)) AND upper(TRIM(event_type)) = upper('feed-end') \r\n" + 
				"AND TRIM(upper(event_feed_id)) = TRIM(upper(?))) en \r\n" + 
				"ON st.event_feed_id = en.event_feed_id AND st.event_run_id = en.event_run_id AND st.event_batch_date = en.event_batch_date \r\n" + 
				"ORDER BY st.event_batch_date");
		pstm.setString(1, feed_id);
		pstm.setString(2, feed_id);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			hipDashboardDTO = new HipDashboardDTO();
			hipDashboardDTO.setBatch_id(rs.getString(1));
			hipDashboardDTO.setBatch_date(rs.getString(2));
			hipDashboardDTO.setRun_id(rs.getString(3));
			hipDashboardDTO.setStart_time(rs.getString(4));
			hipDashboardDTO.setEnd_time(rs.getString(5));
			hipDashboardDTO.setDuration(rs.getString(6));
			
			arrHipDashboard.add(hipDashboardDTO);
		}
		
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(conn);
		return arrHipDashboard;
	}

	@Override
	public String checkFeedAvailable(@Valid String feed_id) throws SQLException, Exception {
		System.out.println("feed id"+feed_id);
		
		Connection connection=null;
		int stat=1;
		String strfeed_id=null;
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select event_feed_id from logger_stats_master where event_feed_id='"+feed_id+"'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				strfeed_id=rs.getString(1);
				stat=0;
				break;
			}
			ConnectionUtils.closeResultSet(rs);
			ConnectionUtils.closePrepareStatement(pstm);
			ConnectionUtils.closeQuietly(connection);
		return stat+strfeed_id;
	}

	@Override
	public ArrayList<HipDashboardDTO> getTablechartUsingDate(@Valid String feedIdFilter, String dateRangeText)
			throws SQLException, Exception {
		String start_date="";
		String end_date="";
			
		if(dateRangeText.contains("-")) {
			String[] dates = dateRangeText.split("-");
			String first_date = dates[0].trim();
			String last_date = dates[1].trim();
			
			Date sd = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(first_date);
			start_date=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(sd);
			Date ed = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(last_date);
			end_date=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(ed);
			
		} else {
			String date = dateRangeText.trim();
			Date sd = (Date) new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(date);
			start_date=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(sd);
			end_date = start_date;
		}
		
		System.out.println("staret date: "+start_date+" end date: "+end_date);
		ArrayList<HipDashboardDTO> arrHipDashboard=new ArrayList<HipDashboardDTO>();
		HipDashboardDTO hipDashboardDTO = null;
		Connection conn = ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement("WITH feed_id AS ( \r\n" + 
				"SELECT DISTINCT feed_id FROM logger_master ) \r\n" + 
				"SELECT st.event_feed_id, to_char(st.event_batch_date,'DD-MON-YY'), st.event_run_id, st.event_value AS start_time, en.event_value AS end_time, CAST((TO_DATE(concat(concat(en.event_batch_date, ' '), en.event_value), 'DD-MM-YY hh24:mi:ss') - TO_DATE(concat(concat(st.event_batch_date , ' '), st.event_value), 'DD-MM-YY hh24:mi:ss')) * 1440 AS DECIMAL(20, 2)) AS duration \r\n" + 
				"FROM ( SELECT * FROM logger_stats_master \r\n" + 
				"INNER JOIN feed_id ON upper(TRIM(feed_id)) = upper(TRIM(event_feed_id)) AND upper(TRIM(event_type)) = upper('feed-start') AND TRIM(upper(event_feed_id)) = TRIM(upper(?)) \r\n" + 
				"AND event_batch_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND ( TO_DATE(?, 'YYYY-MM-DD') ) ) st \r\n" + 
				"INNER JOIN ( SELECT * FROM logger_stats_master \r\n" + 
				"INNER JOIN feed_id ON upper(TRIM(feed_id)) = upper(TRIM(event_feed_id)) AND upper(TRIM(event_type)) = upper('feed-end') \r\n" + 
				"AND TRIM(upper(event_feed_id)) = TRIM(upper(?)) \r\n" + 
				"AND event_batch_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND ( TO_DATE(?, 'YYYY-MM-DD') ) ) en \r\n" + 
				"ON st.event_feed_id = en.event_feed_id AND st.event_run_id = en.event_run_id AND st.event_batch_date = en.event_batch_date \r\n" + 
				"ORDER BY st.event_batch_date");
		pstm.setString(1, feedIdFilter);
		pstm.setString(2, start_date);
		pstm.setString(3, end_date);
		pstm.setString(4, feedIdFilter);
		pstm.setString(5, start_date);
		pstm.setString(6, end_date);
		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			hipDashboardDTO = new HipDashboardDTO();
			hipDashboardDTO.setBatch_id(rs.getString(1));
			hipDashboardDTO.setBatch_date(rs.getString(2));
			hipDashboardDTO.setRun_id(rs.getString(3));
			hipDashboardDTO.setStart_time(rs.getString(4));
			hipDashboardDTO.setEnd_time(rs.getString(5));
			hipDashboardDTO.setDuration(rs.getString(6));
			
			arrHipDashboard.add(hipDashboardDTO);
		}
		for(HipDashboardDTO x:arrHipDashboard) {
			System.out.println(x.getDuration());
		}
		
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(conn);
		return arrHipDashboard;	
	}
	@Override
	public ArrayList<String> getFeedTables(@Valid String feed_id) throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		connection = ConnectionUtils.getConnection();
		pstm = connection.prepareStatement("select distinct feed_id from logger_master order by feed_id");
		rs = pstm.executeQuery();
		while (rs.next()) {
			arr.add(rs.getString(1));
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}

	@Override
	public ArrayList<HipDashboardDTO> getTableDashboardChartLoggerStats(String plt_id, String feed_id, String tbl_id)throws SQLException, Exception {
		ArrayList<HipDashboardDTO> arrHipDashboard=new ArrayList<HipDashboardDTO>();
		HipDashboardDTO hipDashboardDTO = null;
		Connection conn = ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(" WITH feed_id AS ( \r\n" + 
				" SELECT feed_id, value \r\n" + 
				" FROM logger_master \r\n" + 
				" WHERE upper(TRIM(feed_id)) = upper(TRIM(?)) \r\n" + 
				" AND ( upper(TRIM(classification)) = 'TABLE' OR upper(TRIM(classification)) = 'FILE' ) \r\n" + 
				" AND upper(TRIM(subclassification)) = 'NAME' \r\n" + 
				" AND upper(TRIM(value)) = ? ) \r\n" + 
				" SELECT st.event_feed_id, to_char(st.event_batch_date,'DD-MON-YY'), st.event_run_id, st.event_value AS start_time, en.event_value AS end_time, CAST((TO_DATE(concat(concat(en.event_batch_date, ' '), en.event_value), 'DD-MM-YY hh24:mi:ss') - TO_DATE(concat(concat(st.event_batch_date , ' '), st.event_value), 'DD-MM-YY hh24:mi:ss')) * 1440 AS DECIMAL(20, 2)) AS duration, st.value AS tb, coalesce(co.event_value, 'NA') AS count \r\n" + 
				" FROM (  SELECT * FROM logger_stats_master \r\n" + 
				" INNER JOIN feed_id ON upper(TRIM(feed_id)) = upper(TRIM(event_feed_id)) AND upper(TRIM(event_type)) = upper(concat(value,('-start'))) AND TRIM(upper(event_feed_id)) = TRIM(upper(?))) st \r\n" + 
				" INNER JOIN ( SELECT * FROM logger_stats_master \r\n" + 
				" INNER JOIN feed_id ON upper(TRIM(feed_id)) = upper(TRIM(event_feed_id)) AND upper(TRIM(event_type)) = upper(concat(value,('-end'))) \r\n" + 
				" AND TRIM(upper(event_feed_id)) = TRIM(upper(?))) en ON st.event_feed_id = en.event_feed_id \r\n" + 
				" AND st.event_run_id = en.event_run_id AND st.event_batch_date = en.event_batch_date \r\n" + 
				" LEFT OUTER JOIN ( SELECT * FROM logger_stats_master \r\n" + 
				" INNER JOIN feed_id ON upper(TRIM(feed_id)) = upper(TRIM(event_feed_id)) AND upper(TRIM(event_type)) = upper(concat(value,('-count'))) \r\n" + 
				" AND TRIM(upper(event_feed_id)) = TRIM(upper(?))) co \r\n" + 
				" ON st.event_feed_id = co.event_feed_id AND st.event_run_id = co.event_run_id AND st.event_batch_date = co.event_batch_date \r\n" + 
				" ORDER BY st.event_batch_date");
		pstm.setString(1, feed_id);
		pstm.setString(2, tbl_id);
		pstm.setString(3, feed_id);
		pstm.setString(4, feed_id);
		pstm.setString(5, feed_id);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			hipDashboardDTO = new HipDashboardDTO();
			hipDashboardDTO.setBatch_id(rs.getString(1));
			hipDashboardDTO.setBatch_date(rs.getString(2));
			hipDashboardDTO.setRun_id(rs.getString(3));
			hipDashboardDTO.setStart_time(rs.getString(4));
			hipDashboardDTO.setEnd_time(rs.getString(5));
			hipDashboardDTO.setDuration(rs.getString(6));
			hipDashboardDTO.setTable_name(rs.getString(7));
			hipDashboardDTO.setTable_count(rs.getString(8));
			arrHipDashboard.add(hipDashboardDTO);
		}
		
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		ConnectionUtils.closeQuietly(conn);
		return arrHipDashboard;
	}

}
