package com.iig.gcp.datapropagation.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.iig.gcp.extraction.dto.SourceSystemDetailBean;
import com.iig.gcp.extraction.dto.DataDetailBean;
import com.iig.gcp.extraction.dto.DriveMaster;
import com.iig.gcp.constants.OracleConstants;
import com.iig.gcp.extraction.dto.ConnectionMaster;
import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.extraction.dto.ReservoirMaster;
import com.iig.gcp.extraction.dto.SourceSystemMaster;
import com.iig.gcp.utils.ConnectionUtils;

@Service
public class DatapropagationServiceImpl implements DatapropagationService {
	@Override
	public String invokeRest(String json, String url) throws UnsupportedOperationException, Exception {
		String resp = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(OracleConstants.EXTRACTION_COMPUTE_URL + url);
		postRequest.setHeader("Content-Type", "application/json");
		StringEntity input = new StringEntity(json);
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		String response_string = EntityUtils.toString(response.getEntity(), "UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			resp = "Error" + response_string;
			throw new Exception("Error" + response_string);
		} else {
			resp = response_string;
		}
		return resp;
	}
	@Override
	public String invokeRestprop(String json, String url) throws UnsupportedOperationException, Exception {
		String resp = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(OracleConstants.EXTRACTION_COMPUTE_URL + url);
		postRequest.setHeader("Content-Type", "application/json");
		StringEntity input = new StringEntity(json);
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		String response_string = EntityUtils.toString(response.getEntity(), "UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			resp = "Error" + response_string;
			throw new Exception("Error" + response_string);
		} else {
			resp = response_string;
		}
		return resp;
	}

	public ArrayList<String> getSystem(String project)
	{
		ArrayList<String> sys=new ArrayList<String>();
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"select a.system_name from JUNIPER_SYSTEM_MASTER a");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				sys.add(rs.getString(1));
			}
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			//connection.close();
		}
		return sys;
	}}
