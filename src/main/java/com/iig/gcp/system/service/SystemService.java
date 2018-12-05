package com.iig.gcp.system.service;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import com.iig.gcp.extraction.dto.CountryMaster;

public interface SystemService {
	
	List<CountryMaster> fetchCountries(@Valid String region);
	int checkEIM(@Valid String system_eim);
	String registerSystem(@Valid String systemEIM, String systemName, String region, String country,
			String environmentType,String userName) throws ClassNotFoundException, SQLException;
}
