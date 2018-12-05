package com.iig.gcp.hipdashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;

public interface HipService {

	ArrayList<String> getfeeds() throws SQLException, Exception;
	ArrayList<FeedLoggerDTO> getfeeddetails(String feed_id) throws SQLException, Exception;
	ArrayList<HipDashboardDTO> getTableChartLoggerStats(@Valid String feed_id) throws SQLException, Exception;
	String checkFeedAvailable(@Valid String feed_id)throws SQLException, Exception;
	ArrayList<HipDashboardDTO> getTablechartUsingDate(@Valid String feedIdFilter, String date) throws SQLException, Exception ;
	ArrayList<String> getFeedTables(@Valid String feed_id) throws SQLException, Exception;
	ArrayList<String> getfeedsFromLoggerStats(String platform) throws SQLException, Exception;
	ArrayList<String> getPlatform() throws SQLException, Exception;
	ArrayList<String> getTablefromFeed(String tbl_feed_id,String plt_id)throws SQLException, Exception;
	ArrayList<HipDashboardDTO> getTableDashboardChartLoggerStats(String plt_id,String feed_id,String tbl_id)throws SQLException, Exception;
	ArrayList<String> getPlatformFeed(String platform) throws SQLException, Exception;
}
