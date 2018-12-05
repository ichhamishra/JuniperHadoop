package com.iig.gcp.hipdashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.hipdashboard.dao.HipDAO;
import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;

@Service
public class HipServiceImpl implements HipService {

	@Autowired
	HipDAO hipDao;
	
	@Override
	public ArrayList<String> getfeeds() throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getfeeds();
	}
	
	@Override
	public ArrayList<FeedLoggerDTO> getfeeddetails(String feed_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getfeeddetails(feed_id);
	}

	@Override
	public ArrayList<String> getfeedsFromLoggerStats(String platform) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getfeedsFromLoggerStats(platform);
	}

	@Override
	public ArrayList<HipDashboardDTO> getTableChartLoggerStats(@Valid String feed_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getTableChartLoggerStats(feed_id);
	}

	@Override
	public String checkFeedAvailable(@Valid String feed_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.checkFeedAvailable(feed_id);
	}

	@Override
	public ArrayList<HipDashboardDTO> getTablechartUsingDate(@Valid String feedIdFilter, String date)
			throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getTablechartUsingDate(feedIdFilter,date);
	}

	@Override
	public ArrayList<String> getFeedTables(@Valid String feed_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getFeedTables(feed_id);
	}
	
	@Override
	public ArrayList<String> getPlatform() throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getPlatform();
	}	
	@Override
	public ArrayList<String> getTablefromFeed(String tbl_feed_id,String plt_id)throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getTablefromFeed(tbl_feed_id,plt_id);
	}

	@Override
	public ArrayList<HipDashboardDTO> getTableDashboardChartLoggerStats(String plt_id, String feed_id, String tbl_id)
			throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getTableDashboardChartLoggerStats(plt_id,feed_id,tbl_id);
	}
	
	@Override
	public ArrayList<String> getPlatformFeed(String platform) throws SQLException, Exception{
		return hipDao.getPlatformFeed(platform);
	}

}
