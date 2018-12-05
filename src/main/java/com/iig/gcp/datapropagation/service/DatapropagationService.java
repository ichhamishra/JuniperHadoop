package com.iig.gcp.datapropagation.service;

import java.util.ArrayList;
import com.iig.gcp.datapropagation.dto.ConnectionMaster;


public interface DatapropagationService {

	public String invokeRest(String json,String url) throws UnsupportedOperationException, Exception ;
	public ArrayList<String> getSystem(String project);
}
